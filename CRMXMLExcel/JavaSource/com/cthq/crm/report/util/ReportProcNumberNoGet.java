/*
 * ReportProcNumberNoGet.java
 * 创建日期：2009年11月24日
 */
package com.cthq.crm.report.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取一定格式的时间
 * @author 吉仕军
 */
public class ReportProcNumberNoGet {
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat createDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static String bakDate = null;
	private static int increaments = 1;
	/**
	 * 时间格式：yyyy-MM-dd
	 * @return
	 */
	public synchronized static String getDate() {
		Date now = new Date();
		String today = dateFormat.format(now);	
		return today ;
	}
	/**
	 * 时间格式：yyyyMMddHHmmss
	 * @return
	 */
	public synchronized static String getCreateTime() {
		Date now = new Date();
		String time = createDateFormat.format(now);
		return time;
	}
	/**
	 * 时间格式：HH-mm-ss-ms
	 * @return
	 */
	public synchronized static String getTime() {
		Date now = new Date();
		SimpleDateFormat timeFm = new SimpleDateFormat("HH-mm-ss-ms");
		String time = timeFm.format(now);
		return time;
	}
	/**
	 * 时间格式：yyyyMMddHHmmssms
	 * @return
	 */
	public synchronized static String getCurrentTime() {
		Date now = new Date();
		SimpleDateFormat timeFm = new SimpleDateFormat("yyyyMMddHHmmssms");
		String time = timeFm.format(now);
		
		String today = dateFormat.format(now);	
		if (null == bakDate) {
			bakDate = today;
		}
		//每天开始通信的时候产生不同的交易流水号
		if (bakDate.equals(today)) {
			
		} else {
			bakDate = today;
			increaments = 1;
		}
		increaments = increaments + 1;
		
		return time + increaments;
	}
}
