/*
 * ReadTemplateFile.java
 * 创建日期:2009/03/30
 */
package com.cthq.crm.webservice.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

/**
 * 获取模板文件的内容
 * @author 蒋峰
 */
public class ReadFile {
	//日志文件
	private   Logger syslogger = null;
	public void setLogger(Logger _logger) {
		this.syslogger = _logger;
	}
	/**
	 * 获取文件的内容
	 * @param path 模板文件的路径
	 * @param charSet 文件的字符集
	 * @return 文件内容
	 */
	public  String getFileContent(String path, String charSet) {
		File filekk = new File(path);
		return OpenReadFile(filekk, charSet).toString();
	}
	/**
	 * 获取文件的内容
	 * @param path 模板文件的路径
	 * @return 文件内容
	 */
	public  String getFileContent(String path) {
		File filekk = new File(path);
		return OpenReadFile(filekk, "").toString();
	}
	public  String getFileContent(File file, String charSet) {
		return OpenReadFile(file, charSet).toString();
	}
	/**
	 * 文件内容
	 * @param file 文件
	 * @param strCharset 文件字符集
	 * @return
	 */
	private  StringBuffer OpenReadFile(File file, String strCharset) {
		if (null != syslogger) {
			syslogger.debug("OpenReadFile(File file, String strCharset) START");
		}
		
		StringBuffer textCon = new StringBuffer();
		//String strCharset = "";
		try {
			InputStreamReader reader = null;
			if (strCharset.trim().length() == 0) {
				reader = new InputStreamReader(new FileInputStream(file));
			} else {
				reader = new InputStreamReader(new FileInputStream(file), Charset.forName(strCharset ));	
			}
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = bufferedReader.readLine();
			while (line != null) {
				try {
					String nextLine = bufferedReader.readLine();
					if (nextLine != null) {
						line += "\n";
					}	
					textCon.append(line);
					
					line = nextLine;
				} catch (Exception blExc) {
					blExc.printStackTrace();
				}
			}
			reader.close();
			bufferedReader.close();
		} catch (IOException exc) {
			if (null != syslogger) {
				syslogger.error(exc.getMessage(), exc);
			}
			
		}
		if (null != syslogger) {
			syslogger.debug("OpenReadFile(File file, String strCharset) end");
		}
		return textCon;
		
	}
}
