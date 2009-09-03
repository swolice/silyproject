/*
 * Created on 2009-8-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.com.sily;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author a
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestListener implements ServletContextListener{

	public static void main(String[] args) {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("监听程序开始执行");
//		while(true){
//			try {
//				Thread.sleep(1000);
//				System.out.println("zhixingzhong......");
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("监听程序停止执行");
		
	}
}
