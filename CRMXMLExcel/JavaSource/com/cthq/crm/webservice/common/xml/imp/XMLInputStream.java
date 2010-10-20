/*
 * XMLInputStream.java
 *  创建日期:2008/08/28
 */
package com.cthq.crm.webservice.common.xml.imp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


/**
 * 创建XML解析的字符字节编码数据流
 * @author 蒋峰
 */
public class XMLInputStream {
	/**
	 * UTF-8编码集
	 */
	private static final String ENCODING_UTF_8 = "UTF-8";
	/**
	 *GBK编码集
	 **/
	private static final String ENCODING_GBK = "GBK";
	/**
	 * GB2132编码集
	 */
	private static final String ENCODING_GB2132 = "GB2132";
	/**
	 * GB18030编码集
	 */	
	private static final String ENCIDING_GB18030 = "GB18030";
	/**
	 * 获取UTF-8编码数据流
	 * @param strXML XML内容
	 * @return UTF-8编码数据流
	 * @throws Exception 生成数据流发生错误
	 */
	public InputStream getUTF8Stream(String strXML) throws Exception {
		return new ByteArrayInputStream(strXML.getBytes(ENCODING_UTF_8));
	}
	/**
	 * 获取GBK编码数据流
	 * @param strXML XML内容
	 * @return GBK编码数据流
	 * @throws Exception 生成数据流发生错误
	 */
	public InputStream getGBKStream(String strXML) throws Exception {
		return new ByteArrayInputStream(strXML.getBytes(ENCODING_GBK));
	}
	/**
	 * 获取GB2312编码数据流
	 * @param strXML XML内容
	 * @return GB2312编码数据流
	 * @throws Exception 生成数据流发生错误
	 */
	public InputStream getGB2312Stream(String strXML) throws Exception {
		return new ByteArrayInputStream(strXML.getBytes(ENCODING_GB2132));
	}
	/**
	 * 获取GB18030编码数据流
	 * @param strXML XML内容
	 * @return GB18030编码数据流
	 * @throws Exception 生成数据流发生错误
	 */
	public InputStream getGB18030Stream(String strXML) throws Exception {
		return new ByteArrayInputStream(strXML.getBytes(ENCIDING_GB18030));
	}	
	/**
	 * 获取XML协议报文的数据流
	 * @param xmlEncodingCode XML字符集编码
	 * @param strXML XML协议报文数据
	 * @return 协议报文数据流
	 * @throws Exception
	 */
	public InputStream getXMLEncodeStream(String xmlEncodingCode, String strXML) throws Exception {
		if (null == xmlEncodingCode || xmlEncodingCode.trim().length() == 0) {
			return getUTF8Stream(strXML);
		} else {
			 return new ByteArrayInputStream(strXML.getBytes(xmlEncodingCode));
		}
		
	}	
}
