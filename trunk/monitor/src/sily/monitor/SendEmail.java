package sily.monitor;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

	// public static void main(String args[]){
	// new SendEmail().sendMail();
	// }
	public void sendMail(List<String> filePathList) throws Exception {
		try {
			Multipart mm = new MimeMultipart();

			properties = new Properties();
			// 设置邮件服务�?
			properties.put("mail.smtp.host", "smtp.163.com");
			// 验证
			properties.put("mail.smtp.auth", "true");
			// 根据属�?�新建一个邮件会�?
			mailSession = Session.getInstance(properties, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("jishijun204",
							"");
				}
			});
			mailSession.setDebug(true);
			// 建立消息对象
			mailMessage = new MimeMessage(mailSession);
			// 发件�re人
			mailMessage.setFrom(new InternetAddress("jishijun204@163.com"));
			// 收件�人
			mailMessage.setRecipient(MimeMessage.RecipientType.TO,
					new InternetAddress("16009413@qq.com"));
			// 主题
			mailMessage.setSubject("我的监控信息");

			// String ss = "字符串作为文本文�?";
			MimeBodyPart mbp1 = new MimeBodyPart();
			// email内容1 显示在email内容区域
			mbp1.setText("附件中都是摄像头监控照片");
			mm.addBodyPart(mbp1);

			// 没有设置mbp1.setFileName("文件�?");这一步，会自动生成一个文件名做为附件发�?�，如果前面没有设置，将作为内容显示
			// mbp1 = new MimeBodyPart();
			// mbp1.setContent(ss,"text/plain;charset=gb2312");
			// mm.addBodyPart(mbp1);
			// 文本附件
			// DataHandler dh1 = new
			// DataHandler(ss,"text/plain;charset=gb2312");
			// if(ss.length()!=0){
			// mbp1 = new MimeBodyPart();
			// mbp1.setFileName("test.txt");
			// mbp1.setDataHandler(dh1);
			// mm.addBodyPart(mbp1);
			// }
			// 本地文件附件

			for (int i = 0; i < filePathList.size(); i++) {
				MimeBodyPart mbp = new MimeBodyPart();
				String str = filePathList.get(i);
				FileDataSource fds = new FileDataSource(str);
				DataHandler dh = new DataHandler(fds);
				int ddd = str.lastIndexOf("\\");
				String fname = str.substring(ddd + 1);
				String ffname = javax.mail.internet.MimeUtility
						.encodeText(fname);
				mbp.setFileName(ffname);
				mbp.setDataHandler(dh);
				mm.addBodyPart(mbp);
			}

			mailMessage.setContent(mm);
			// 发信时间
			mailMessage.setSentDate(new Date());
			// 存储信息
			mailMessage.saveChanges();
			// 
			trans = mailSession.getTransport("smtp");
			// 发�??
			trans.send(mailMessage);

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				trans.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
	public void sendMail(String msg) throws Exception {
		try {
			Multipart mm = new MimeMultipart();
			
			properties = new Properties();
			// 设置邮件服务�?
			properties.put("mail.smtp.host", "smtp.163.com");
			// 验证
			properties.put("mail.smtp.auth", "true");
			// 根据属�?�新建一个邮件会�?
			mailSession = Session.getInstance(properties, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("jishijun204",
					"");
				}
			});
			mailSession.setDebug(true);
			// 建立消息对象
			mailMessage = new MimeMessage(mailSession);
			// 发件�re人
			mailMessage.setFrom(new InternetAddress("jishijun204@163.com"));
			// 收件�人
			mailMessage.setRecipient(MimeMessage.RecipientType.TO,
					new InternetAddress("16009413@qq.com"));
			// 主题
			mailMessage.setSubject("我的监控信息");
			
			// String ss = "字符串作为文本文�?";
			MimeBodyPart mbp1 = new MimeBodyPart();
			// email内容1 显示在email内容区域
			mbp1.setText(msg);
			mm.addBodyPart(mbp1);
			
			// 没有设置mbp1.setFileName("文件�?");这一步，会自动生成一个文件名做为附件发�?�，如果前面没有设置，将作为内容显示
			// mbp1 = new MimeBodyPart();
			// mbp1.setContent(ss,"text/plain;charset=gb2312");
			// mm.addBodyPart(mbp1);
			// 文本附件
			// DataHandler dh1 = new
			// DataHandler(ss,"text/plain;charset=gb2312");
			// if(ss.length()!=0){
			// mbp1 = new MimeBodyPart();
			// mbp1.setFileName("test.txt");
			// mbp1.setDataHandler(dh1);
			// mm.addBodyPart(mbp1);
			// }
			// 本地文件附件
			
			
			mailMessage.setContent(mm);
			// 发信时间
			mailMessage.setSentDate(new Date());
			// 存储信息
			mailMessage.saveChanges();
			// 
			trans = mailSession.getTransport("smtp");
			// 发�??
			trans.send(mailMessage);
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				trans.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	private void deleteFile(List<String> filePathList) {
		for (int i = 0; i < filePathList.size(); i++) {
			File file = new File(filePathList.get(i));
			if (file.exists()) {
				file.delete();
			}
		}
	}

	public void sendMonitorPhoto() {
		File file = new File("f:/photo");
		if (file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				list.add(files[i].getAbsolutePath());
				if (list.size() == 20) {
					try {
						sendMail(list);
					} catch (Exception e) {
						list.clear();
						continue;
					}
					deleteFile(list);
					list.clear();
				}
			}
			if (list.size()> 0) {
				try {
					sendMail(list);
				} catch (Exception e) {
					list.clear();
					return;
				}
				deleteFile(list);
				list.clear();
			}
		}
	}
	
	public static void main(String[] args) {
		SendEmail se = new SendEmail();
		se.sendMonitorPhoto();
	}

}
