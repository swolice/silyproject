/*
 * Created on 2009-1-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cthq.crm.eai.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.cthq.crm.webservice.common.constants.LogLabConst;
import com.cthq.crm.webservice.common.log.TraceDebugLogger;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SendMail {
	//private static Logger logger = Logger.getLogger(SendMail.class);
	private static Logger logger = TraceDebugLogger.getLogger(LogLabConst.CRM_ONECHARGE);
	//服务器
	private static String smtpHost= OneChargeConfigHelper.MAIL_SERVER;
	//发送人
	private static String sender = OneChargeConfigHelper.MAIL_SENDER;
	//发送人密码
	private static String password = OneChargeConfigHelper.MAIL_SENDER_PWD;
	//收件人
	private static String receiver = OneChargeConfigHelper.MAIL_RECEIVER;
	//抄送人
	private static String copyReceiver = OneChargeConfigHelper.MAIL_COPYRECEIVER;
	//使用smtp发送邮件 主程序
	private static void smtp(String title,StringBuffer content) throws MessagingException {
		if(smtpHost==null || smtpHost.trim().equals("")){
			throw new MessagingException("smtpHost is not found ");
		}
		if(sender==null || sender.trim().equals("")){
			throw new MessagingException("sender is  not found ");
		}
		if(password==null || password.trim().equals("")){
			throw new MessagingException("password is  not found ");
		}
		Properties pro = new Properties();
		pro.setProperty("mail.smtp.host",smtpHost);
		//对发件人不用验证
		pro.setProperty("mail.smtp.auth","false");
		Session session = Session.getInstance(pro, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });
		//获得邮件会话对象
        MimeMessage mimeMsg = new MimeMessage(session);// 创建MIME邮件对象
        //发件人
        mimeMsg.setFrom(new InternetAddress(sender));
        //收件人
        mimeMsg.setRecipients(Message.RecipientType.TO, receiver);
        //抄送人
        mimeMsg.setRecipients(Message.RecipientType.CC,copyReceiver);
        //设置邮件主题
        mimeMsg.setSubject(title, "UTF-8");
        //内容
        MimeBodyPart body= new MimeBodyPart();
        body.setText(content.toString(),"UTF-8");
        //设置邮件格式为html
        body.setContent(content.toString(), "text/html;charset=UTF-8 ");
        Multipart mpart = new MimeMultipart();
        //在 Multipart 中增加mail内容部分
        mpart.addBodyPart(body);
        //增加 Multipart 到信息体
        mimeMsg.setContent(mpart);
        //设置发送日期
        mimeMsg.setSentDate(new Date());
        //发送邮件
        Transport.send(mimeMsg);
	}
	/**
     * 发送邮件
     * 
     * @param mailAddress
     *            收件人地址
     * @param copyAddress
     *            抄送人地址
     * @param subject
     *            邮件主题
     * @param content
     *            邮件内容
     * @return Boolean true 发送成功 false 发送失败
     */
	public static boolean sendMails(String subject,StringBuffer content) {
        try {
            smtp(subject,content);
        } catch (Exception ex) {
        	logger.error("发送邮件出错", ex);
            return false;
        }
        return true;
    }
}
