/*
 * Created on 2009-9-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cthq.crm.project.common.util;

import java.util.Map;

/**
 * @author jiangfeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RequestUtil {
	/**
	 * 判断数据是否为NULL
	 * @param obj 参数数据
	 * @return true 数据为NULL false 数据不为NULL
	 */
	public  static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}
	/**
	 * 判断字符数据是否为NULL或空串
	 * @param obj 参数数据
	 * @return true 数据为NULL false 数据不为NULL
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			if (((String)obj).trim().length() == 0) {
			    return true;
			}
		}
		if (obj instanceof String[]) {
			if (((String[])obj)[0].trim().length() == 0) {
			    return true;
			}
		}
		return false;
	}
	/**
	 * 获取相应数据字符
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		if (isNull(obj)) {
			return null;
		}
		if (obj instanceof String) {
			return ((String)obj).trim();
		}
		if (obj instanceof String[]) {
			return ((String[])obj)[0].trim();
		}
		return null; 
	}
	/**
	 * 获取相应数据字符
	 * @param obj
	 * @return
	 */
	public static String getString(Map paramMap, String key) {
		Object obj = paramMap.get(key);
		if (isNull(obj)) {
			return null;
		}
		if (obj instanceof String) {
			return ((String)obj).trim();
		}
		if (obj instanceof String[]) {
			return ((String[])obj)[0].trim();
		}
		return null; 
	}
	/**
	 * 获取相应的字符数据集合
	 * @param obj
	 * @return
	 */
	public static String[] getStringArr(Object obj) {
		if (isNull(obj)) {
			return null;
		}
		if (obj instanceof String[]) {
			return (String[])obj;
		}
		return null;
	}
	
	public static void main(String[] args) {
	}
}
