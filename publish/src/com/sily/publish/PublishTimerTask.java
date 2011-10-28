package com.sily.publish;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

public class PublishTimerTask extends TimerTask {

	private static boolean isRunning = false;
	private static ServletContext context;

	private int hour = -1;
	private int minute = 10;
	private long period = 24*60*60*1000;
	
	public PublishTimerTask(ServletContext context) {
		this.context = context;
	}
	
	public PublishTimerTask() {
	}

	public void run() {
		Logger.getLogger("publish").info("发布项目run方法开始");
		if (!getSwitch()) {
			return;
		}

		if (!isRunning) {

			isRunning = true;

			executeLogic();
			
			isRunning = false;
		} else {
			Logger.getLogger("publish").info("上一次任务执行还未结束");
		}
		
		Logger.getLogger("publish").info("发布项目run方法结束");
	}
	
	
	private void executeLogic(){
		try {
			PublishLogic.logic();
		} catch (Exception e) {
			Logger.getLogger("publish").info("发布账务executeLogic方法出错",e);
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
		
		String inerfaceCheck_switch = prop.getProperty("switch");
		if (null != inerfaceCheck_switch
				&& "1".equals(inerfaceCheck_switch.trim())) {
			return true;
		}
		return false;
	}
	
	
	public static Properties getProp() throws Exception{
		
		String comm = context.getRealPath("/WEB-INF/classes/common.properties");
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
			e1.printStackTrace();
			return null;
		}
		String rimis_hour = prop.getProperty("hour");
		String rimis_period = prop.getProperty("period");
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
				String rimis_minute = prop.getProperty("minute");
				
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
