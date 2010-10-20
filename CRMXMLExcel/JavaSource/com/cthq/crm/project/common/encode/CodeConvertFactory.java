/*
 * CodeConvertFactory.java
 * 创建日期:  2008/08/13
 */
package com.cthq.crm.project.common.encode;

/**
 * 获取字符编码集的处理类
 * @author 蒋峰
 * 
 */
public class CodeConvertFactory {
	
	/**
	 * 获取处理编码集合的处理类
	 * @param typeId
	 * @return
	 */
	public static ICodeConvert getCodeConvert(String typeId) {
		//BASE64编码集的处理类型
		if ( typeId.equals( EncodeType.getBase64ID() ) ) {
			ICodeConvert codeConvert = new Base64Convert();
			return codeConvert;
		}
	   	return null;
	}
	public static void main(String[] args) {
	}
}
