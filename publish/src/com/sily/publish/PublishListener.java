package com.sily.publish;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PublishListener implements ServletContextListener {

	private Timer timer;

	public void contextInitialized(ServletContextEvent sce) {
		//初始化log4j
		String prefix = sce.getServletContext().getRealPath("/");
		System.setProperty("publish.root", prefix);
		String file = sce.getServletContext().getInitParameter("log4j-init-file");
		if (file != null) {
			PropertyConfigurator.configure(prefix + file);
		}
		
		
		
		Logger.getLogger("publish").info("发布监听开始.....1");
		
		Logger.getLogger("publish").info(System.getProperty("java.version"));
		Logger.getLogger("publish").info(System.getProperty("java.home"));
		Logger.getLogger("publish").info(System.getProperty("java.specification.version"));

		timer = new Timer();
		PublishTimerTask ptt = new PublishTimerTask(sce.getServletContext());
		java.util.Date date = ptt.initTimes();
		if (null == date) {
			Logger.getLogger("publish").info("第一次执行的时间60 * 1000时间后，执行周期"+ ptt.getPeriod());
			timer.schedule(ptt, 60 * 1000, ptt.getPeriod());
		} else {
			Logger.getLogger("publish").info("第一次执行的时间"+new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date)+"，执行周期"+ ptt.getPeriod());
			timer.schedule(ptt, date, ptt.getPeriod());
		}

		Logger.getLogger("publish").info("发布监听开始.....2");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		Logger.getLogger("publish").info("发布监听结束.....");

		if (null != timer) {
			timer.cancel();
		}
	}
}
