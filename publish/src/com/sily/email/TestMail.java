/** 
 * 文件名：		TestMail.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-6-27 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package com.sily.email;

import java.net.MalformedURLException;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.sily.publish.PublishResourceBundle;

/** 
 * 名称：	TestMail 
 * 描述： 
 * 创建人：	吉仕军
 * 创建时间：	2011-6-27 下午04:10:59
 * 修改人：	
 * 修改时间：	
 * 修改备注： 
 * @version 1.0
 */
public class TestMail {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		txtNoAtta();
		
//		txtAndAtta();
		
		htmlAndAtta();
	}
	
	
	
	public static void txtNoAtta(){
		String config = PublishResourceBundle.
		getResourcesAbsolutePath("mail.properties");
		SendSimpleEmail sse = new SendSimpleEmail("主题","文本内容");
		sse.getInitDataByConfig(config);
		try {
			sse.send();
		} catch (EmailException e) {
			Logger.getLogger("publish").error(e.getMessage(),e);
		}
	}
	
	public static void  txtAndAtta(){
		String config = PublishResourceBundle.getResourcesAbsolutePath("mail.properties");
		SendMultiPartEmail sse = new SendMultiPartEmail("主题","文本内容");
		sse.getInitDataByConfig(config);
		sse.addAttachment("E:/ant_publish/AccountAdminLog.log");
		sse.addAttachment("http://www.hinews.cn/pic/0/10/79/16/10791690_355327.jpg");
		try {
			sse.send();
		} catch (EmailException e) {
			Logger.getLogger("publish").error(e.getMessage(),e);
		} catch (MalformedURLException e) {
			Logger.getLogger("publish").error(e.getMessage(),e);
		}
	}
	
	public static void  htmlAndAtta(){
		String config = PublishResourceBundle.getResourcesAbsolutePath("mail.properties");
		SendHtmlEmail sse = new SendHtmlEmail("主题","(<font color=red>Hi, " +
				"i'm sily.</font><h1>html内容，文件格式没有变化，测试一下html格式的输出 </h1>");
		sse.getInitDataByConfig(config);
		sse.addAttachment("E:/ant_publish/AccountAdminLog.log");
		sse.addAttachment("http://hiphotos.baidu.com/%CC%EC%C9%CF%B5%F4%BF%C3%B2%DD" +
				"/pic/item/3c54ca2167d171a7e7cd40ee.jpg");
		try {
			sse.send();
		} catch (EmailException e) {
			Logger.getLogger("publish").error(e.getMessage(),e);
		} catch (MalformedURLException e) {
			Logger.getLogger("publish").error(e.getMessage(),e);
		}
	}
	

}
