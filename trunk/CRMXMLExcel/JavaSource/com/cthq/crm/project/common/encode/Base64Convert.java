/*
 * Base64Convert.java
 * 创建日期:  2008/08/13
 */
package com.cthq.crm.project.common.encode;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

/**
 * BASE64编码集数据的编码和解码
 * @author 蒋峰
 * 
 */
public class Base64Convert implements ICodeConvert {
	private final static String STRING_EMPTY = "";
	private Base64 base64 = new Base64();
	
	/**
	 * 将BASE64编码集的数据字符解码为相应的序列化的数据类型
	 * @see com.gentle.core.encode.ICodeConvert#decode(java.lang.String)
	 */
	public Serializable decode(String valString) {
		 if(valString == null) {
	    	return null;
	    }	
	    if(valString.equals(STRING_EMPTY)) {
	    	return null;
	    }
	    byte valByte[] = base64.decodeFromCharToByte(valString.toCharArray());
	    ByteArrayInputStream valByteStrm = new ByteArrayInputStream(valByte);
	    Serializable valSeri = ObjectSerialize.unzip(valByteStrm);
	    return valSeri;
	}

	/**
	 * 将序列化的数据编码为相应的BASE64编码字符集合
	 * @see com.gentle.core.encode.ICodeConvert#encode(java.io.Serializable)
	 */
	public String encode(Serializable valSeri) {
		if(valSeri == null){
			return null;
		}
		byte valZip[] = ObjectSerialize.zip(valSeri);
		char encodedChar[] = base64.encodeFromByteToChar(valZip);
		String encodeStr = String.copyValueOf(encodedChar);
		return encodeStr;
	}

}
