package cn.com.yumincun;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

	private Properties properties;
	private Session mailSession;
	private MimeMessage mailMessage;
	private Transport trans;

	public SendEmail() {
	}

//	public static void main(String args[]){
//		new SendEmail().sendMail();
//	}
	@SuppressWarnings("static-access")
	public void sendMail() {
		try {
			Multipart mm = new MimeMultipart();
			
			properties = new Properties();
			// 设置邮件服务器
			properties.put("mail.smtp.host", "smtp.163.com");
			// 验证
			properties.put("mail.smtp.auth", "true");
			// 根据属性新建一个邮件会话
			mailSession = Session.getInstance(properties, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("jishijun204", "58413142727527");
				}
			});
			mailSession.setDebug(true);
			// 建立消息对象
			mailMessage = new MimeMessage(mailSession);
			// 发件人
			mailMessage.setFrom(new InternetAddress("jishijun204@163.com"));
			// 收件人
			mailMessage.setRecipient(MimeMessage.RecipientType.TO,
					new InternetAddress("16009413@qq.com"));
			// 主题
			mailMessage.setSubject("项目错误信息");
			
			//String ss = "字符串作为文本文件";
			MimeBodyPart mbp1 = new MimeBodyPart();
			// email内容1 显示在email内容区域
			mbp1.setText("请查看附件中的错误信息");
			mm.addBodyPart(mbp1);
			
			//没有设置mbp1.setFileName("文件名");这一步，会自动生成一个文件名做为附件发送，如果前面没有设置，将作为内容显示
//			mbp1 = new MimeBodyPart();
//			mbp1.setContent(ss,"text/plain;charset=gb2312");
//			mm.addBodyPart(mbp1);
			//文本附件
//			DataHandler dh1 = new DataHandler(ss,"text/plain;charset=gb2312"); 
//			if(ss.length()!=0){
//				mbp1 = new MimeBodyPart();
//				mbp1.setFileName("test.txt");
//				mbp1.setDataHandler(dh1);
//				mm.addBodyPart(mbp1);
//			}
			//本地文件附件
			String str="D:\\eclipse\\downsource\\log\\log4jdaily.log";
			MimeBodyPart mbp = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(str);
			DataHandler dh = new DataHandler(fds);
			int ddd = str.lastIndexOf("\\");
			String fname = str.substring(ddd + 1);
			String ffname = javax.mail.internet.MimeUtility.encodeText(fname); 
			mbp.setFileName(ffname);
			mbp.setDataHandler(dh);
			mm.addBodyPart(mbp);
			mailMessage.setContent(mm);
			// 发信时间
			mailMessage.setSentDate(new Date());
			// 存储信息
			mailMessage.saveChanges();
			// 
			trans = mailSession.getTransport("smtp");
			// 发送
			trans.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				trans.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
}
