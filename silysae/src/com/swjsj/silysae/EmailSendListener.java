package com.swjsj.silysae;

import java.util.Calendar;
import java.util.Timer;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class EmailSendListener implements ServletContextListener {
	
	private Timer timer;
	
	public void contextDestroyed(ServletContextEvent arg0) {
		if(null != timer){
			timer.cancel();
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {
		Logger.getLogger(this.getClass()).info("contextInitialized 开始");
		timer = new Timer();
		MailTimerTask mtt = new MailTimerTask(arg0.getServletContext());
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 7);
		c.set(Calendar.MINUTE, 01);
		timer.schedule(mtt, 10, 24*60*60*1000);
		Logger.getLogger(this.getClass()).info("contextInitialized 结束");
	}
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 7);
		c.set(Calendar.MINUTE, 01);
		
		System.out.println(c.getTime());
	}

}
