package cn.com.yumincun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;

import sun.misc.BASE64Decoder;

public class ReceiveMail {

	private String host = "pop3.163.com";

	public static void main(String[] args) {
		new ReceiveMail().receive("jishijun204@163.com", "jishijun204",
				"");
	}

	// 处理任何邮件时需要的方法
	private void handle(Message msg) throws MessagingException {
		System.out.println("邮件主题：" + msg.getSubject());
		System.out.println("邮件作者：" + msg.getFrom()[0].toString());
		System.out.println("发送日期：" + msg.getSentDate());
	}

	// 处理文本邮件
	private void handleText(Message msg) throws MessagingException, IOException {
		System.out
				.println("----------------------------------------------------");
		this.handle(msg);
		System.out.println("邮件内容：" + msg.getContent());
		System.out
				.println("----------------------------------------------------");
	}

	// 文件名解码
	public String Base64Decoder(String s) throws Exception {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}
	public static String toChinese(String strvalue){ 
	    try{ 
	      if(strvalue==null) 
	        return null; 
	      else{ 
	        strvalue = new String(strvalue.getBytes("ISO8859_1"), "GBK"); 
	        return strvalue; 
	      } 
	    }catch(Exception e){ 
	      return null; 
	    } 
	  } 

	// 保存附件
	private void saveAttach(BodyPart part) throws Exception {
		// 得到未处理的附件名字
		String temp = part.getFileName();
		// 除去发送邮件时对中文附件名编码的头和尾，得到正确的附件名
		System.out.println(temp);
		// 文件名解码
		String fileName = "";
		if ((temp.startsWith("=?GBK?B?") && temp.endsWith("?="))
				|| (temp.startsWith("=?gbk?b?") && temp.endsWith("?="))) {
			temp = Base64Decoder(temp.substring(8, temp
					.indexOf("?=") - 1));
			fileName = MimeUtility.decodeText(temp);
		} else {
			temp = toChinese(temp);
			fileName = MimeUtility.decodeText(temp);
		}
		
		System.out.println("有附件：" + fileName);
		InputStream in = part.getInputStream();
		FileOutputStream writer = new FileOutputStream(new File("D:/workspace/"
				+ fileName));
		int read = 0;
		while ((read = in.read()) != -1) {
			writer.write(read);
		}
		writer.close();
		in.close();
	}

	// 处理Multipart邮件，包括了保存附件的功能
	private void handleMultipart(Message msg) throws Exception {

		String disposition;
		BodyPart bodyPart;
		// Multipart mp = (Multipart) msg.getContent();
		Object content = msg.getContent();

		String contentType = msg.getContentType();
		// 假如邮件内容是纯文本或者是Html，那么打印出信息
		System.out.println("CONTENT:" + contentType);
		if (contentType.startsWith("text/plain")
				|| contentType.startsWith("text/html")) {
			// System.out.println(msg.getContent());
		}
		System.out.println("-------------- END ---------------");
		// 附件
		Multipart mp = null;
		if (content instanceof Multipart) {
			mp = (Multipart) content;
			System.out.println("[ Multipart Message ]");
		}
		if (mp != null) {
			int mpCount = mp.getCount();
			for (int i = 0; i < mpCount; i++) {
				this.handle(msg);
				bodyPart = mp.getBodyPart(i);
				disposition = bodyPart.getDisposition();
				// 判断是否有附件
				if (disposition != null && disposition.equals(Part.ATTACHMENT)) {
					this.saveAttach(bodyPart);
					// handleText(msg);
				} else {
					System.out.println(bodyPart.getContent());
				}
			}
		}
	}

	// 接受邮件
	public void receive(String receiveMailBoxAddress, String userName,
			String password) {
		try {
			Properties pops = new Properties();
			pops.put("mail.pop3.host", host);
			pops.put("mail.pop.auth", "true");
			Session session = Session.getDefaultInstance(pops, null);
			Store store = session.getStore("pop3");
			store.connect(host, userName, password);
			Folder inbox = store.getDefaultFolder().getFolder("INBOX");
			// 设置inbox对象属性为可读写，这样可以控制读完邮件后直接删除该附件
			inbox.open(Folder.READ_WRITE);
			Message[] msg = inbox.getMessages();
			FetchProfile profile = new FetchProfile();
			profile.add(FetchProfile.Item.ENVELOPE);
			inbox.fetch(msg, profile);
			System.out.println(msg.length);
			for (int i = 0; i < msg.length; i++) {
				// 标记此邮件的flag标志对象的DELETEED为true，可以在看完邮件后直接删除该邮件，在调用inbox.close（）时
				msg[i].setFlag(Flags.Flag.RECENT, true);
				handleMultipart(msg[i]);
				System.out
						.println("----------------------------------------------------");
			}
			if (inbox != null)
				inbox.close(true);
			if (store != null)
				store.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
