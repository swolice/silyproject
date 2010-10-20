/*
 * PropertyWrapper.java
 * 创建日期： 2009/02/28
 */
package com.cthq.crm.project.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 导入属性文件
 * @author 蒋峰
 */
public class PropertyWrapper {
	//WEB服务属性集合
	private Properties  props = new Properties();
	
	/**
	 * 导入属性文件
	 * @param _filePath 属性文件的路径
	 * @param _fileName 属性文件的名称
	 * @throws Exception
	 */
	public void loadProperty(String _filePath, String _fileName) throws Exception {
		String strfileSeparator = System.getProperty("file.separator");
		String filepath = _filePath + strfileSeparator + _fileName;
		InputStream in=new BufferedInputStream(new FileInputStream(filepath)); 
    	props.load(in);
    	in.close();
	}
	/**
	 * 导入属性文件
	 * @param _file 属性文件的路径
	 * @throws Exception
	 */
	public void loadProperty(String _file) throws Exception {
		InputStream in=new BufferedInputStream(new FileInputStream(_file)); 
    	props.load(in);
    	in.close();
	}
	/**
	 * 根据key读取value
	 * @param key
	 * @return
	 */
	 public  String readValue(String key) {
	 	if (props == null) {
	 		return null;
	 	}
	 	if (props.getProperty(key) == null) {
	 		return null;
	 	}
	 	try {
		 	return props.getProperty(key);
	 	}catch(Exception ex) {
	 		return null;
	 	}
	 	
	 }
	 
	/**
	 * 获取属性文件集合
	 * @return Returns the props.
	 */
	public Properties getProps() {
		return props;
	}
}
