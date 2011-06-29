/** 
 * 文件名：		SendMultiPartEmail.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-6-27 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package com.sily.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;

import com.sily.util.StringUtils;
import com.sily.validate.CheckValidateException;

/** 
 * 发送普通文本格式email
 * 名称：	SendMultiPartEmail 
 * 描述： 
 * 创建人：	吉仕军
 * 创建时间：	2011-6-27 下午04:35:19
 * 修改人：	
 * 修改时间：	
 * 修改备注： 
 * @version 1.0
 */
public class SendMultiPartEmail{

	//邮件编码
	private String emailCode;
	//邮件服务器
	private String mailHost;
	private String user;
	private String password;
	//收件人的列表key：mail value:用户名 不填为空
	private Map<String,String> to = new HashMap<String,String>();
	//发件人邮箱 用户名
	private Map<String,String> from = new HashMap<String,String>();
	//主题
	private String subject;
	//文本内容
	private String msg;
	//附件列表
	private List<String> attaList;
	
	public void setAttaList(List<String> attaList) {
		this.attaList = attaList;
	}

	private EmailAttachment attachment;
	
	private MultiPartEmail email = new MultiPartEmail();
	
	public SendMultiPartEmail(String subject, String msg) {
		this.subject = subject;
		this.msg = msg;
	}
	
	public SendMultiPartEmail(String emailCode, String mailHost, String user,
			String password, Map<String, String> to, Map<String, String> from,
			String subject, String msg,List<String> attaList) {
		super();
		this.emailCode = emailCode;
		this.mailHost = mailHost;
		this.user = user;
		this.password = password;
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.msg = msg;
		this.attaList = attaList;
	}
	
	public void addAttachment(String path){
		if(null == this.attaList){
			this.attaList = new ArrayList<String>();
		}
		this.attaList.add(path);
	}
	
	private void emailAtta(String attaPath) throws EmailException, MalformedURLException{
		if(null == this.attachment){
			this.attachment = new EmailAttachment();
		}
		if(attaPath.indexOf("http://")> -1){
			URL url = new URL(attaPath);
			attachment.setURL(url);
			String name = attaPath.substring(attaPath.lastIndexOf("/")+1);
			attachment.setName(name);
			// 设置附件类型
			attachment.setDisposition(EmailAttachment.INLINE);
		}else{
			File file = new File(attaPath);
			attachment.setPath(file.getPath());
			attachment.setName(file.getName());
			// 设置附件类型
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
		}
		// 设置附件描述
		attachment.setDescription("附件说明："+attaPath);
		// 添加附件
		email.attach(attachment);
	}
	
	/**
	 * 通过配置文件初始化发送邮件
	 * 构造方法 SendSimpleEmail. 
	 * 
	 * @param configPath
	 */
	public void getInitDataByConfig(String configPath) throws CheckValidateException {
		File file = new File(configPath);
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			Logger.getLogger("publish").error(e.getMessage(),e);
		} catch (IOException e) {
			Logger.getLogger("publish").error(e.getMessage(),e);
		}
		emailCode = prop.getProperty("emailCode");
		if(StringUtils.isNull(emailCode)){
			this.emailCode = "UTF-8";
			Logger.getLogger("publish").debug("邮件编码为空，默认为UTF-8");
		}
		mailHost = prop.getProperty("mailHost");
		checkProperties(mailHost,"邮件服务器不能为空");
		user = prop.getProperty("user");
		checkProperties(mailHost,"用户名不能为空");
		password = prop.getProperty("password");
		checkProperties(mailHost,"密码不能为空");
		String toStr = prop.getProperty("to");
		checkProperties(mailHost,"收件人不能为空");
		String[] toStrs = toStr.split(";");
		for (int i = 0; i < toStrs.length; i++) {
			String[] toInfo = toStrs[i].split("\\|");
			if(toInfo.length == 2){
				to.put(toInfo[0], toInfo[1]);
			}else if(toInfo.length == 1){
				to.put(toInfo[0], null);
			}else{
				throw new CheckValidateException("接收人信息不正确");
			}
		}
		String fromStr = prop.getProperty("from");
		checkProperties(mailHost,"发件人不能为空");
		String[] froms = fromStr.split("\\|");
		if(froms.length == 2){
			from.put(froms[0], froms[1]);
		}else if(froms.length == 1){
			from.put(froms[0], null);
		}else{
			throw new CheckValidateException("发收人信息不正确");
		}
	}
	
	private void checkProperties(String value,String msg) throws CheckValidateException{
		if(StringUtils.isNull(value)){
			throw new CheckValidateException(msg);
		}
	}
	
	/**
	 *  初始化邮件发送信息
	 * @throws EmailException
	 */
	public void initSend() throws EmailException{
		// 设置邮件编码
		email.setCharset(emailCode);
		// 设置邮件服务器
		email.setHostName(mailHost);
		// 设置登录邮件服务器用户名和密码
		email.setAuthentication(user, password);
		// 添加收件人
		Iterator<String> it = to.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			String value = to.get(key);
			email.addTo(key, value);
		}
		// 设置发件人
		Iterator<String> it1 = from.keySet().iterator();
		while(it1.hasNext()){
			String key = it1.next();
			String value = from.get(key);
			email.setFrom(key,value);
		}
		
	}
	
	public void send() throws EmailException, MalformedURLException{
		initSend();
		// 设置邮件标题
		email.setSubject(subject);
		// 设置邮件正文内容
		email.setMsg(msg);
		
		if(null != this.attaList && !this.attaList.isEmpty()){
			for (int i = 0; i < this.attaList.size(); i++) {
				emailAtta(this.attaList.get(i));
			}
		}
		// 发送邮件
		email.send();
		
		disposeSend();
	}
	
	private void disposeSend(){
		if(null != to){
			to.clear();
		}
		if(null != from){
			from.clear();
		}
		if(null != this.attachment){
			attachment = null;
		}
		
		if(null != attaList){
			attaList.clear();
		}
	}


	public String getEmailCode() {
		return emailCode;
	}


	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}


	public String getMailHost() {
		return mailHost;
	}


	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Map<String, String> getTo() {
		return to;
	}


	public void setTo(Map<String, String> to) {
		this.to = to;
	}


	public Map<String, String> getFrom() {
		return from;
	}


	public void setFrom(Map<String, String> from) {
		this.from = from;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

}
