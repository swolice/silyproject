package sily.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author yier
 *
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
	 public  String getProperty(String key) {
	 	if (props.getProperty(key) == null) {
	 		return null;
	 	}
	 	return props.getProperty(key);
	 }
	 
}

