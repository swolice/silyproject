package com.sily.publish;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.sily.email.ReceiveMail;

public class WordPressTimerTask extends TimerTask {

	private static boolean isRunning = false;
	private ServletContext context;

	private int hour = -1;
	private int minute = 10;
	private long period = 24*60*60*1000;
	
	public WordPressTimerTask(ServletContext context) {
		this.context = context;
	}
	
	public void run() {
		Logger.getLogger("publish").info("  start  ");
		
		if (!getSwitch()) {
			return;
		}

		if (!isRunning) {

			isRunning = true;

			executeLogic();
			
			isRunning = false;
			
			Logger.getLogger("publish").info("  end  ");
		} else {
			Logger.getLogger("publish").info("上一次任务执行还未结束");
		}
	}
	
	
	private void executeLogic(){
		try {
			ReceiveMail.logic();
		} catch (Exception e) {
			Logger.getLogger("publish").info(" 接收邮件 发布wordpress出错 ");
		}
		
		
	}

	/**
	 * 判断接口稽核是否打开
	 * 
	 * @return
	 */
	private boolean getSwitch() {
		Properties prop;
		try {
			prop = getProp();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		String inerfaceCheck_switch = prop.getProperty("wp_switch");
		if (null != inerfaceCheck_switch
				&& "1".equals(inerfaceCheck_switch.trim())) {
			return true;
		}
		return false;
	}
	
	
	public Properties getProp() throws Exception{
		
		String comm = this.context.getRealPath("/WEB-INF/classes/common.properties");
		//类还没有初始化  调用的话出现空指针异常
//		String comm = PublishResourceBundle.getResourcesAbsolutePath();
		
		Properties prop  = new Properties();
		
		prop.load(new FileInputStream(new File(comm)));
		
		return prop;
	}
	
	private boolean isNotNull(String string){
		if(null == string){
			return false;
		}
		if("".equals(string.trim())){
			return false;
		}
		return true;
	}
	
	/**
	 * 获取第一次执行定时器的时间 以及循环时间间隔
	 * @return
	 */
	public Date initTimes(){
		Properties prop;
		try {
			prop = getProp();
		} catch (Exception e1) {
			Logger.getLogger("publish").error(e1.getMessage(), e1);
			return null;
		}
		String rimis_hour = prop.getProperty("wp_hour");
		String rimis_period = prop.getProperty("wp_period");
		try {
			if(isNotNull(rimis_period)){
				String[] str = rimis_period.split("\\*");
				long _preoidTime =1;
				for (int i = 0; i < str.length; i++) {
					_preoidTime = _preoidTime * Integer.parseInt(str[i]);
				}
				this.setPeriod(_preoidTime);
			}
			if(isNotNull(rimis_hour)){
				String rimis_minute = prop.getProperty("wp_minute");
				
				this.setHour(Integer.parseInt(rimis_hour));
				if(null != rimis_minute){
					this.setMinute(Integer.parseInt(rimis_minute));
				}else{
					this.setMinute(0);
				}
				Calendar cal = Calendar.getInstance();
				
				//设置的日期
				Calendar cal1 = Calendar.getInstance();
				cal1.set(Calendar.HOUR_OF_DAY, this.getHour());
				cal1.set(Calendar.MINUTE, this.getMinute());
				long time1 = cal1.getTimeInMillis() - cal.getTimeInMillis();
				
				if(time1>10*60*1000){
					//如果当前时间比规定的时间小,则设置为本日开始定时,为避免JAVA调度造成的时间差,提前量为10分钟
					return cal1.getTime();
				}else{
					//如果当前时间比规定的时间大,则设置为下一天固定时间开始定时
					long time2 = cal1.getTimeInMillis();
					long time3 = cal.getTimeInMillis();
					while(time2-time3<600000){
						time2 = time2 + period;
					}
					cal.setTimeInMillis(time2);
					return cal.getTime();
				}
			}
		} catch (Exception e) {
			Logger.getLogger("publish").error(e.getMessage(), e);
		}
		return null;
	}
	
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}
}
