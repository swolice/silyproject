package com.sily.publish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

public class PublishLogic {
	
	public static String pubType = "0";

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
		//发布程序
		String path = prop.getProperty("commandFile_"+pubType);
		processLogic(path);
		
		//发送邮件
		path = prop.getProperty("commandFile_email");
		processLogic(path);
	}
}
