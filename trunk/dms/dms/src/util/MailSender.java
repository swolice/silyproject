package util;

//JavaMail中一些我们需要的类
//1.Properties
//
//JavaMail需要Properties来创建一个session对象，其属性值就是发送邮件的主机，如：
//
//Properties props = new Properties ();
//props.put("mail.smtp.host", "smtp.xxxx.com");//可以换上你的smtp主机名，就像你在OutLook中设置smtp主机名一样。
//props.put("mail.smtp.auth", "true"); //通过验证
//
//2.Session
//
//所有的基于JavaMail的程序都至少需要一个或全部的对话目标。
//
//Session session = Session.getInstance(props, null);
//
//3.MimeMessage
//
//信息对象将把你所发送的邮件真实的反映出来。
//
//MimeMessage msg = new MimeMessage(session);
//
//4.Transport
//
//邮件的发送是由Transport来完成的：
//
//Transport.send(msg);

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.ArrayList;
import javax.mail.BodyPart;


public class MailSender extends Thread {

    private String host;

    private String userName;

    private String password;

    private String from;

    private String to;

    private String subject;

    private String body;

    private String fileName;

    // 用于保存发送附件的文件名列表
    List arrFileName = new ArrayList();

    public MailSender(String to, String subject, String body) {
        // 初始化发件人、收件人、主题
        this.from = Configer.getInstance().getProperty("mail_from");
        this.host = Configer.getInstance().getProperty("mail_host");
        this.userName = Configer.getInstance().getProperty("mail_userName");
        this.password = Configer.getInstance().getProperty("mail_password");
        this.to = to;
        this.subject = subject;
        this.body = body + ("\n\n\n System Date:" + new Date());
    }

    //
    // public MailSender(String subject, String body){
    // // 初始化发件人、收件人、主题等
    //
    // this.from = "systemmanagement@sina.com.cn";
    // this.host = "smtp.sina.com.cn";
    // this.userName = "systemmanagement";
    // this.password = "system";
    // this.to = "systemmanagement@sina.com.cn";
    //
    // this.subject = subject+new SimpleDateFormat("yyyy-MM-dd
    // HH:mm:ss").format(new Date());
    // this.body = body +"\n\n\n"+ new SimpleDateFormat("yyyy-MM-dd
    // HH:mm:ss").format(new Date());
    //	}

    // 用于收集附件名
    public void attachFile(String fileName) {
        this.arrFileName.add(fileName);
    }


    public void startSend() {
        this.start();
    }

    public void run() {
        this.sendMail();

    }

    public void waitEnd() {
        while (this.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
            }
        }
    }

    private boolean sendMail() {

        // 创建Properties对象
        Properties props = System.getProperties();
        // 创建信件服务器
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.auth", "true"); // 通过验证
        // 得到默认的对话对象
        Session session = Session.getDefaultInstance(props, null);
        try {
            // 创建一个消息，并初始化该消息的各项元素
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(this.from));
            if (this.to.trim().length() > 0) {
                String[] arr = this.to.split(",");
                // int ReceiverCount=1;
                int receiverCount = arr.length;
                if (receiverCount > 0) {
                    InternetAddress[] address = new InternetAddress[
                                                receiverCount];
                    for (int i = 0; i < receiverCount; i++) {
                        address[i] = new InternetAddress(arr[i]);
                    }
                    msg.addRecipients(Message.RecipientType.TO, address);
                } else {
                    System.out
                            .println("None receiver! The program will be exit!");
                    System.exit(0);
                }
            } else {
                System.out.println("None receiver! The program will be exit!");
                System.exit(0);
            }
            msg.setSubject(subject);
            // 后面的BodyPart将加入到此处创建的Multipart中
            Multipart mp = new MimeMultipart();
            // 获取附件
            int FileCount = this.arrFileName.size();
            if (FileCount > 0) {
                for (int i = 0; i < FileCount; i++) {
                    MimeBodyPart mbp = new MimeBodyPart();
                    // 选择出附件名
                    fileName = arrFileName.get(i).toString();
                    // 得到数据源
                    FileDataSource fds = new FileDataSource(fileName);
                    // 得到附件本身并至入BodyPart
                    mbp.setDataHandler(new DataHandler(fds));
                    // 得到文件名同样至入BodyPart
                    mbp.setFileName(fds.getName());
                    mp.addBodyPart(mbp);
                }

                MimeBodyPart mbp = new MimeBodyPart();
                mbp.setText(this.body);
                mp.addBodyPart(mbp);
                // 移走集合中的所有元素
                arrFileName.clear();
                // Multipart加入到信件
                msg.setContent(mp);
            } else {
                // 设置邮件正文
                msg.setText(this.body);

                BodyPart mdp = new MimeBodyPart(); //新建一个存放信件内容的BodyPart对象
                mdp.setContent(this.body, "text/html;charset=gb2312"); //给BodyPart对象设置内容和格式/编码方式
                Multipart mm = new MimeMultipart(); //新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
                mm.addBodyPart(mdp); //将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
                msg.setContent(mm);
                msg.saveChanges();

            }
            // 设置信件头的发送日期
            msg.setSentDate(new Date());
            msg.saveChanges();
            // 发送信件
            Transport transport = session.getTransport("smtp");
            transport.connect(this.host, this.userName, this.password);
            transport.sendMessage(msg, msg
                                  .getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (MessagingException mex) {
            mex.printStackTrace();
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) {
                ex.printStackTrace();
            }
            return false;
        }
        Logger.getLogger().debug("发送邮件[" + this.subject + "]成功！");
        return true;
    }

    public static void main(String[] args) {

        try {
            //MailSender sendmail = new MailSender("邮件主题", "邮件内容");
            // sendmail.attachFile("E:\\eclipse\\workspace\\MailSender\\MailSender.rar");//附件地址
            // sendmail.attachFile("附件2地址");....................
            //sendmail.sendMail();

            MailSender sendmail = new MailSender("wenf@emotte.com", "test1",
                                                 "<html><body><a href='http://www.jtfw.com'>test</a></body></html>");
//            sendmail.attachFile(
//                    "d:/3.jpg");
//            sendmail.attachFile(
//                    "d:/3.jpg");

            sendmail.sendMail();
            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
