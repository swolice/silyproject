/*
 * CheckUtil.java
 * 创建日期:2008/08/20
 * 
 */
package com.cthq.crm.webservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * XML数据检测常用算法
 * 数据NULL或空判断
 * 日期合法性检测
 * 金额合法性检测
 * 数字合法性检测
 * @author 蒋峰
 */
public abstract class AbstractCheckUtil {
	/**
	 * 判断数据是否为NULL 或 空字符
	 * @param value 输入数据
	 * @return TRUE 数据为空 FALSE 数据不为空
	 */
	public static boolean isNullEmpty(Object value) {
		if (null == value) {
			return true;
		}
		if (value.toString().trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断数据数据是否为空
	 * @param value 输入数据
	 * @return TRUE 数据不为空 FALSE 数据为空
	 */
	public static boolean isNotNullEmpty(Object value) {
		if (null == value) {
			return false;
		}
		if (value.toString().trim().length() == 0) {
			return false;
		}
		return true;
	}
	/**
	 * 获取数据的实际长度
	 * @param value 输入的数据
	 * @return 数据的长度
	 */
	public static int getValLentgh(String value) {
		if (null == value) {
			return -1;
		}
		if (value.trim().length() == 0) {
			return 0;
		}
		return value.getBytes().length;
	}
	/**
	 * 数字类型检测 包括小数点的数据
	 * @param value 输入的数据
	 * @return TRUE 合法 FALSE 不合法
	 */
	public static boolean isNumber(String value) {
		String regEX = "^(-?\\d+)(\\.\\d*)*$";
		if (isNullEmpty(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile(regEX);
		Matcher match = pattern.matcher(value);
		return match.find();
	}
	/**
	 * 金额的合法性的检测
	 * @param value 输入的金额
	 * @return  TRUE 输入金额合法 FALSE 输入金额不合法
	 */
	public static boolean isPayment(String value) {
		String regEX = "^(-?\\d+)(\\.\\d*)*$";
		if (isNullEmpty(value)) {
			return false;
		}
		value = value.replaceAll(",","");
		Pattern pattern = Pattern.compile(regEX);
		Matcher match = pattern.matcher(value);
		return match.find();
	}
	/**
	 * 日期合法性检测
	 * 日期的格式(YYYY-MM-DD)
	 * @param value 输入的日期
	 * @return TRUE 日期合法 FALSE 日期不合法
	 */
	public static boolean isvalidateDate(String value) {
		if (null == value) {
			return false;
		}
		String regEX = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-";
		regEX = regEX + "(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-";
		regEX = regEX + "(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-";
		regEX = regEX + "(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";
		Pattern pattern = Pattern.compile(regEX);
		Matcher match = pattern.matcher(value);
		return match.find();
	}
	/**
	 * 日期输入格式 yyyy-MM-dd ,yyyyMMddHHmmss ...
	 * 日期合法性的检测
	 * @param value 输入的日期
	 * @param format 日期的格式
	 * @return  TRUE 日期合法 FALSE 日期不合法
	 */
	public static boolean isValidateDateTime(String value, String format) {
	    try {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	        dateFormat.setLenient(false);
	        dateFormat.parse(value);
	     } catch (ParseException e) {
	        return false;
	     } catch (IllegalArgumentException e) {
	        return false;
	     }  catch (Exception e) {
	     	return false;
	     }
	      return true;
	}

}
