/*
 * DateUtils.java
 * 创建日期:2008/10/12
 */
package com.cthq.crm.webservice.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 获取时间
 * @author 蒋峰
 */
public final class DateTimeUtils {
	/**
	 * 根据相应的格式获取相应格式的时间
	 * @param strFormat
	 * @return
	 */
	public static String getCurrentDate(String strFormat) {
		if (null == strFormat) {
			return "";
		}
		if (strFormat.trim().length() == 0) {
			return "";
		}
		//日期时间格式化
		SimpleDateFormat simFormat = new SimpleDateFormat(strFormat);
		//取日期时间，精确到毫秒数
		Date now = new Date();
		String today = simFormat.format(now);	
		return today;
	}
	/**
	 * 获取当前的日期格式YYYYMMDDHHmmssMS
	 * @return
	 */
	public static String getCurrYMDHmsMS() {
		//日期时间格式化
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyyMMddHHmmssMS");
		//取日期时间，精确到毫秒数
		Date now = new Date();
		String today = simFormat.format(now);	
		return today;		
	}
	/**
	 * 以当前日期为起点获取前若干天的日期
	 * @param amount 输入的数据为负数 -N
	 * @return
	 */
	public static String getPrevDateYYYYMMDD(int  amount) {
		//日期时间格式化
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyyMMdd");
		//取日期时间，精确到毫秒数
		Date now = new Date();
		Calendar kk = Calendar.getInstance();
		kk.add(Calendar.DATE, amount);
		Date date = kk.getTime();
		String prevDay = simFormat.format(date);	
		return prevDay;
	}
	/**
	 * 以当前日期为起点获取后若干天的日期
	 * @param amount 输入的数据为正数 +N
	 * @return
	 */
	public static String getAfterDateYYYYMMDD(int  amount) {
		//日期时间格式化
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyyMMdd");
		//取日期时间，精确到毫秒数
		Date now = new Date();
		Calendar kk = Calendar.getInstance();
		kk.add(Calendar.DATE, amount);
		Date date = kk.getTime();
		String prevDay = simFormat.format(date);	
		return prevDay;
	}
	/**
	 * 获取当前的日期
	 * @return
	 */
	public static String getCurrDateYYYYMMDD() {
		//日期时间格式化
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyyMMdd");
		//取日期时间，精确到毫秒数
		Date now = new Date();
		String today = simFormat.format(now);	
		return today;		
	}
	/**
	 * 获取当前的时间
	 * 返回的时间是24小时制式
	 * @return
	 */
	public static String getCurrTime() {
		//日期时间格式化
		SimpleDateFormat simFormat = new SimpleDateFormat("HHmmss");
		//取日期时间，精确到毫秒数
		Date now = new Date();
		String today = simFormat.format(now);	
		return today;		
	}
	/**
	 * 获取一定格式的日期类型的数据
	 * @param strDate 日期数据
	 * @param srcDateFmt 原日期数据的格式 例如：20090910 yyyyMMdd
	 * @param desDateFmt 期望获取的目标日期格式 例如 “yyyy-MM-dd
	 * @return 如果传入的日期合法 则返回目标的日期格式数据 如果不合法则返回空数据
	 */
	public static String getFormatDateTime(String strDate, String srcDateFmt, String desDateFmt) {
		try {
			if ( !AbstractCheckUtil.isValidateDateTime(strDate, srcDateFmt)) {
				return "";
			}
			//日期时间格式化
			SimpleDateFormat dateFormat = new SimpleDateFormat(srcDateFmt);
			//取日期时间，精确到毫秒数
	        dateFormat.setLenient(false);
		    Date desDate = dateFormat.parse(strDate);
		    SimpleDateFormat dateFormat1 = new SimpleDateFormat(desDateFmt);
			return dateFormat1.format(desDate);	
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	public static void main(String args[]) {
		System.out.println(DateTimeUtils.getCurrentDate("yyyyMMddHHmmss"));
	}
}
