/*
 * ICodeConvert.java
 * 创建日期:  2008/08/13
 */
package com.cthq.crm.webservice.encode;

import java.io.Serializable;

/**
 * 字节数据编码解码接口
 * @author 蒋峰
 *
 */
public interface ICodeConvert {
	/**
	 * 编码字符集数据解码反序列化相应的数据类型
	 * @param valString 编码字符结合数据
	 * @return 反序列化数据类型
	 */
	public  Serializable decode(String valString);
	/**
	 * 序列化类型的数据转换相应编码集合的字符数据
	 * @param valSeri 序列化类型
	 * @return 应编码集合的字符数据
	 */
	public  String encode(Serializable valSeri);
}
