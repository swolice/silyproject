/*
 * Created on 2010-6-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package sample;

import java.util.Timer;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestThread {

	public static void main(String[] args) {
		System.setProperty("WEB_SERVICE_XML_PATH", "F:\\crmwork\\LOG");

		ThreadServerPool thread = new ThreadServerPool();
		for(int i=0;i<10; i++) {
			TestWorkRun workrun = new TestWorkRun("A0"+i);
			thread.run(workrun);
		}
		Timer busTrigerTimer = new Timer(true);
		//定时器出发逻辑池 定时器 一秒中循环处理
		BussinessTrigerPoolTask bussTimerTrigerTask = new BussinessTrigerPoolTask();
		busTrigerTimer.schedule(bussTimerTrigerTask, 0 , 3 * 1000);
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
