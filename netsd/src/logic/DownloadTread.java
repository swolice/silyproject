package logic;

import org.apache.log4j.Logger;

public class DownloadTread implements Runnable{
	
	private static boolean isfinish = true;

	private static Logger logger = Logger.getLogger("log");
	
	public void run() {
		if(isfinish){
			logger.debug("DownloadTread == start == " + System.currentTimeMillis());
			JsoupLogic.downFileByList();
			logger.debug("DownloadTread == end == " + System.currentTimeMillis());
		}
	}

	public static void setIsfinish(boolean isfinish) {
		DownloadTread.isfinish = isfinish;
	}

}
