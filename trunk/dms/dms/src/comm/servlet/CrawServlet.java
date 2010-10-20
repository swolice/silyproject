package comm.servlet;

import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import comm.task.CrawlTask;

public class CrawServlet extends HttpServlet {
	Logger log = Logger.getLogger("CrawServlet");

	public void init() throws ServletException {
		log.info("CrawServlet initialed");

		Timer taskTimer = new Timer(); //统计任务的时钟
		CrawlTask task = new CrawlTask(); //统计任务

		try {
			taskTimer.scheduleAtFixedRate(task, new Date(), 10 * 60 * 1000);
		} catch (Exception ex) {
			log.error("taskTimer goes wrong.", ex);
		}
	}

	//Clean up resources
	public void destroy() {
	}
}
