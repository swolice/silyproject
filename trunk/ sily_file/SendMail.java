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
	//������
	private static String smtpHost= OneChargeConfigHelper.MAIL_SERVER;
	//������
	private static String sender = OneChargeConfigHelper.MAIL_SENDER;
	//����������
	private static String password = OneChargeConfigHelper.MAIL_SENDER_PWD;
	//�ռ���
	private static String receiver = OneChargeConfigHelper.MAIL_RECEIVER;
	//������
	private static String copyReceiver = OneChargeConfigHelper.MAIL_COPYRECEIVER;
	//ʹ��smtp�����ʼ� ������
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
		//�Է����˲�����֤
		pro.setProperty("mail.smtp.auth","false");
		Session session = Session.getInstance(pro, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });
		//����ʼ��Ự����
        MimeMessage mimeMsg = new MimeMessage(session);// ����MIME�ʼ�����
        //������
        mimeMsg.setFrom(new InternetAddress(sender));
        //�ռ���
        mimeMsg.setRecipients(Message.RecipientType.TO, receiver);
        //������
        mimeMsg.setRecipients(Message.RecipientType.CC,copyReceiver);
        //�����ʼ�����
        mimeMsg.setSubject(title, "UTF-8");
        //����
        MimeBodyPart body= new MimeBodyPart();
        body.setText(content.toString(),"UTF-8");
        //�����ʼ���ʽΪhtml
        body.setContent(content.toString(), "text/html;charset=UTF-8 ");
        Multipart mpart = new MimeMultipart();
        //�� Multipart ������mail���ݲ���
        mpart.addBodyPart(body);
        //���� Multipart ����Ϣ��
        mimeMsg.setContent(mpart);
        //���÷�������
        mimeMsg.setSentDate(new Date());
        //�����ʼ�
        Transport.send(mimeMsg);
	}
	/**
     * �����ʼ�
     * 
     * @param mailAddress
     *            �ռ��˵�ַ
     * @param copyAddress
     *            �����˵�ַ
     * @param subject
     *            �ʼ�����
     * @param content
     *            �ʼ�����
     * @return Boolean true ���ͳɹ� false ����ʧ��
     */
	public static boolean sendMails(String subject,StringBuffer content) {
        try {
            smtp(subject,content);
        } catch (Exception ex) {
        	logger.error("�����ʼ�����", ex);
            return false;
        }
        return true;
    }
}
