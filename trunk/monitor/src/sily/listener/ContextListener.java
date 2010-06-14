package sily.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import sily.monitor.CameraPhoto;

public class ContextListener extends HttpServlet implements
		ServletContextListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContextListener() {
	}

	private java.util.Timer timer = null;
	private java.util.Timer timer1 = null;

	public void contextInitialized(ServletContextEvent event) {

		event.getServletContext().log("定时器已启动");
		timer = new java.util.Timer(true);
		timer.schedule(new FtpTask(event.getServletContext()), 0,
					5 * 60 * 1000);
		timer1 = new java.util.Timer(true);
		timer1.schedule(new PhotoTask(event.getServletContext()), 0,
				20 * 1000);
		event.getServletContext().log("已经添加任务调度表");
	}

	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
		event.getServletContext().log("定时器销毁");
	}

}
