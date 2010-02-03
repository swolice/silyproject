package cn.com.yumincun;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jTest {
	
	public static void main(String args[]) {
		
		String path = new Log4jTest().getClass().getClassLoader().getResource("log4j.properties").toString();
		String filepath = path.substring(6);
		PropertyConfigurator.configure(filepath);
		Logger logger1 = Logger.getLogger("console");
		logger1.debug("debug!!!");
		logger1.info("info!!!");
		logger1.warn("warn!!!");
		logger1.error("error!!!");
		logger1.fatal("fatal!!!");
		
		Logger logger2 = Logger.getLogger("LogFile");
		logger2.debug("debug!!!");
		logger2.info("info!!!");
		logger2.warn("warn!!!");
		logger2.error("error!!!");
		logger2.fatal("fatal!!!");
		
		while(true){
			Logger logger3 = Logger.getLogger("FILE");
			logger3.debug("debug!!!");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//new SendEmail().sendMail();
	}
}
