/*
 * Base64.java
 * 创建日期:  2008/08/13
 */
package com.cthq.crm.webservice.encode;

/**
 * 字符编码集的标识集合类
 * @author 蒋峰
 */
public class EncodeType {
	/**
	 * 字符编码的标识
	 */
	private static final String BASE64 = "BASE64";
	/**
	 * 读取BASE64编码的标识
	 * @return 标识
	 */
	public static final String getBase64ID() {
		return BASE64;
	}
}
