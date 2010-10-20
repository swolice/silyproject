package util;

//JavaMail��һЩ������Ҫ����
//1.Properties
//
//JavaMail��ҪProperties������һ��session����������ֵ���Ƿ����ʼ����������磺
//
//Properties props = new Properties ();
//props.put("mail.smtp.host", "smtp.xxxx.com");//���Ի������smtp����������������OutLook������smtp������һ����
//props.put("mail.smtp.auth", "true"); //ͨ����֤
//
//2.Session
//
//���еĻ���JavaMail�ĳ���������Ҫһ����ȫ���ĶԻ�Ŀ�ꡣ
//
//Session session = Session.getInstance(props, null);
//
//3.MimeMessage
//
//��Ϣ���󽫰��������͵��ʼ���ʵ�ķ�ӳ������
//
//MimeMessage msg = new MimeMessage(session);
//
//4.Transport
//
//�ʼ��ķ�������Transport����ɵģ�
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

    // ���ڱ��淢�͸������ļ����б�
    List arrFileName = new ArrayList();

    public MailSender(String to, String subject, String body) {
        // ��ʼ�������ˡ��ռ��ˡ�����
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
    // // ��ʼ�������ˡ��ռ��ˡ������
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

    // �����ռ�������
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
                // TODO �Զ����� catch ��
                e.printStackTrace();
            }
        }
    }

    private boolean sendMail() {

        // ����Properties����
        Properties props = System.getProperties();
        // �����ż�������
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.auth", "true"); // ͨ����֤
        // �õ�Ĭ�ϵĶԻ�����
        Session session = Session.getDefaultInstance(props, null);
        try {
            // ����һ����Ϣ������ʼ������Ϣ�ĸ���Ԫ��
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
            // �����BodyPart�����뵽�˴�������Multipart��
            Multipart mp = new MimeMultipart();
            // ��ȡ����
            int FileCount = this.arrFileName.size();
            if (FileCount > 0) {
                for (int i = 0; i < FileCount; i++) {
                    MimeBodyPart mbp = new MimeBodyPart();
                    // ѡ���������
                    fileName = arrFileName.get(i).toString();
                    // �õ�����Դ
                    FileDataSource fds = new FileDataSource(fileName);
                    // �õ�������������BodyPart
                    mbp.setDataHandler(new DataHandler(fds));
                    // �õ��ļ���ͬ������BodyPart
                    mbp.setFileName(fds.getName());
                    mp.addBodyPart(mbp);
                }

                MimeBodyPart mbp = new MimeBodyPart();
                mbp.setText(this.body);
                mp.addBodyPart(mbp);
                // ���߼����е�����Ԫ��
                arrFileName.clear();
                // Multipart���뵽�ż�
                msg.setContent(mp);
            } else {
                // �����ʼ�����
                msg.setText(this.body);

                BodyPart mdp = new MimeBodyPart(); //�½�һ������ż����ݵ�BodyPart����
                mdp.setContent(this.body, "text/html;charset=gb2312"); //��BodyPart�����������ݺ͸�ʽ/���뷽ʽ
                Multipart mm = new MimeMultipart(); //�½�һ��MimeMultipart�����������BodyPart����(��ʵ�Ͽ��Դ�Ŷ��)
                mm.addBodyPart(mdp); //��BodyPart���뵽MimeMultipart������(���Լ�����BodyPart)
                msg.setContent(mm);
                msg.saveChanges();

            }
            // �����ż�ͷ�ķ�������
            msg.setSentDate(new Date());
            msg.saveChanges();
            // �����ż�
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
        Logger.getLogger().debug("�����ʼ�[" + this.subject + "]�ɹ���");
        return true;
    }

    public static void main(String[] args) {

        try {
            //MailSender sendmail = new MailSender("�ʼ�����", "�ʼ�����");
            // sendmail.attachFile("E:\\eclipse\\workspace\\MailSender\\MailSender.rar");//������ַ
            // sendmail.attachFile("����2��ַ");....................
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
