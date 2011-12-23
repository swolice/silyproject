package test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class EmailSendListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		new Thread(){
			public void run() {
				EmailSendTest.txtNoAtta();
			}
		}.start();
	}

}
