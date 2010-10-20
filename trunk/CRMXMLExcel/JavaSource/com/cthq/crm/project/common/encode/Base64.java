/*
 * Base64.java
 * 创建日期:  2008/08/13
 */
package com.cthq.crm.project.common.encode;

/**
 * 知识点: Base64编码要求把3个8位字节（3*8=24）转化为4个6位的字节（4*6=24），
 * 之后在6位的前面补两个0，形成8位一个字节的形式。 
 * 该类进行Base64编码转换将任意序列的字节转换为相应的Base64编码的字符
 * 以及将Base64编码集字符转换和响应的字节(解码)
 * @author 蒋峰
 */
public class Base64 implements IEncodeDecode {
	/**
	 *Base64编码集合基本信息序列
	 */
	private static final char alphabet[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-/=".toCharArray();
	/**
	 * Base64编码集合基本字节序列标号数组
	 */
	private static byte codes[];

	static {
    	//定义256个字符字节编码
    	codes = new byte[256];
    	//初始化
    	for(int i = 0; i < 256; i++) {
    		codes[i] = -1;
    	}
    	//处理从A到Z的字节序列标号
		for(int i = 65; i <= 90; i++){
			codes[i] = (byte)(i - 65);
		}
		//处理从a到z的字节序列标号
		for(int i = 97; i <= 122; i++) {
			codes[i] = (byte)((26 + i) - 97);
		}
		//处理0到9的字节序列表号
		for(int i = 48; i <= 57; i++) {
			codes[i] = (byte)((52 + i) - 48);
		}
		//处理"-/="字节序列标号
		codes[45] = 62;
		codes[47] = 63;
    }
	
	/**
	 * 将字节数据按照Base64编码的方式转换成相应的BASE64编码的字符集合
	 * 如果传进的参数数值为NULL 方法返回的集合数据为NULL
	 * @see com.gentle.core.encode.IEncodeDecode#encodeFromByteToChar(byte[])
	 */
	public char[] encodeFromByteToChar(byte[] data) {
		if  (data == null)   {
    		return null;
    	}
    	char out[] = new char[((data.length + 2) / 3) * 4];
    	int i = 0;
    	for ( int index = 0; i < data.length; index += 4 )  {
    		boolean quad = false;
    		boolean trip = false;
    		int val = 0xff & data[i];
    		val <<= 8;
    		if ( i + 1 < data.length) {
    			val |= 0xff & data[i + 1];
    			trip = true;
    		}
    		val <<= 8;
    		if(i + 2 < data.length) {
    			val |= 0xff & data[i + 2];
    			quad = true;
    		}
    		out[index + 3] = alphabet[quad ? val & 0x3f : 64];
    		val >>= 6;
    		out[index + 2] = alphabet[trip ? val & 0x3f : 64];
    		val >>= 6;
    		out[index + 1] = alphabet[val & 0x3f];
    		val >>= 6;
    		out[index + 0] = alphabet[val & 0x3f];
    		i += 3;
    	}
    	return out;
	}

	/**
	 * 将BASE64的编码字符集合数据解码为相应的字节集合数据
	 * 如果传进的参数的数值为NULL 方法返回的集合数据为NULL
	 * @see com.gentle.core.encode.IEncodeDecode#decodeFromCharToByte(char[])
	 */
	public byte[] decodeFromCharToByte(char[] data) {
		if (data == null) { 
    		return null;
    	}
    	int len = ((data.length + 3 ) / 4) * 3;
    	if (data.length > 0 && ( '=' == data[data.length - 1]) ) {
    		len--;	
    	}
    	if (data.length > 1 && ('=' == data[data.length - 2] ) ) {
    		len--;
    	}
    	byte out[] = new byte[len];
    	int shift = 0;
    	int accum = 0;
    	int index = 0;
    	for (int ix = 0; ix < data.length; ix++) {
    		int value = codes[data[ix] & 0xff];
    		if (value >= 0) {
    			accum <<= 6;
    			shift += 6;
    			accum |= value;
    			if(shift >= 8) {
    				shift -= 8;
    				out[index++] = (byte)(accum >> shift & 0xff);
    	        }
    	    }
    	}
    	if(index != out.length){
    		return null;
    	}
    	return out;
	}
}
