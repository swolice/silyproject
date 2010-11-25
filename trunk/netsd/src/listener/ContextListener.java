package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;


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
		event.getServletContext().log("ContextListener已启动");
		timer = new java.util.Timer(true);
		timer.schedule(new NetDownloadFileTask(event.getServletContext()), 10000);
		event.getServletContext().log("ContextListener完成");
	}

	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
		event.getServletContext().log("定时器销毁");
	}

}
