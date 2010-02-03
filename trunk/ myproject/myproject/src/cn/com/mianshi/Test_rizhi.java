package cn.com.mianshi;

import java.io.File;

import org.apache.log4j.Logger;

public class Test_rizhi {

	public static void main(String[] args) {
		File file = new File("log/test.log");
		Logger logger = Logger.getLogger("FILE1");
		while(true){
			logger.debug("");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
