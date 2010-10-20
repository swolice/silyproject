/*
 * IEncodeDecode.java
 * 创建日期:  2008/08/13
 */
package com.cthq.crm.webservice.encode;

/**
 * 编码集中转换的接口
 * @author 蒋峰
 * 
 */
public interface IEncodeDecode {
	/**
	 *  字节数据转换相应的编码字符集合
	 * @param data 需要转换的编码字节数据集合
	 * @return 相应的编码字符结合
	 */
	public char[] encodeFromByteToChar(byte data[]);
	
	/**
	 * 将相应的编码集合解码相应的字节集合数据
	 * @param data 编码字符集合数据
	 * @return 已转换的字节数据集合
	 */
    public byte[] decodeFromCharToByte(char data[]);
}
