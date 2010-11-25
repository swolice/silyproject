package logic;

import org.apache.log4j.Logger;

public class JsoupThread implements Runnable{

	private static Logger logger = Logger.getLogger("log");
	
	public void run() {
		logger.debug("JsoupThread == 开始 == " + System.currentTimeMillis());
		JsoupLogic.process();
		logger.debug("JsoupThread == 结束 == " + System.currentTimeMillis());
	}

}
