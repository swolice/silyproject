package com.swjsj.silysae;


import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.sina.sae.fetchurl.SaeFetchurl;

public class MailTimerTask extends TimerTask {

	private static boolean isRunning = false;  
	
	private ServletContext context;
	
	public MailTimerTask(ServletContext context) {
		this.context = context;
	}
	
	public void run() {
		Logger.getLogger(this.getClass()).info("  start  ");

		if (!isRunning) {

			isRunning = true;

			executeLogic();
			
			isRunning = false;
			
			Logger.getLogger(this.getClass()).info("  end  ");
		} else {
			Logger.getLogger(this.getClass()).info("上一次任务执行还未结束");
		}
	}
	
	private void executeLogic(){
		XmlReaderByJsoup xrbj = new XmlReaderByJsoup();
		try {
			xrbj.sendMail("昌平");
		} catch (Exception e) {
			Logger.getLogger(XmlReaderByJsoup.class).info("发送邮件出错 ");
		}
	}
	
	public static void main(String[] args) {
		
		XmlReaderByJsoup xrbj = new XmlReaderByJsoup();
		try {
			xrbj.sendMail("昌平");
		} catch (Exception e) {
			Logger.getLogger(XmlReaderByJsoup.class).info("发送邮件出错 ");
		}
	}
}
