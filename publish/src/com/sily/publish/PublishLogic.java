package com.sily.publish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PublishLogic {

	public static void logic(){
		new PublishLogic().mutiTaskRun();
	}
	
	public void processLogic(String commandPath){
		String text = null;
		String command = commandPath;// exe,bat文件名OR DOS命令
		try {
			Process proc = Runtime.getRuntime().exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					proc.getInputStream(),"GBK"));
			while ((text = in.readLine()) != null) {
				Logger.getLogger("publish").error(text); // 输出测试
			}
		} catch (IOException ioError) {
			Logger.getLogger("publish").error(ioError.getMessage(),ioError);
		}
	}
	
	
	public void mutiTaskRun(){
		Properties prop;
		try {
			prop =PublishTimerTask.getProp();
		} catch (Exception e) {
			Logger.getLogger("publish").error(e.getMessage(),e);
			return;
		}
		
		for (int i = 1; i < 10; i++) {
			String path = prop.getProperty("commandFile_"+i);
			if(null == path || "".equals(path)){
				continue;
			}
			processLogic(path);
		}
	}
}
