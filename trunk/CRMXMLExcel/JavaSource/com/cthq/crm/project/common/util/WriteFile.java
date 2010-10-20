/*
 * WriteFile.java
 * 创建日期：2009/05/12
 */
package com.cthq.crm.project.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * 生成文件
 * @author 蒋峰
 */
public class WriteFile {
	
	/**
	 * 创建文件 写文件
	 * @param filePath 文件的所在路径
	 * @param fileName 文件名称
	 * @param fileCon 文件的内容
	 * @param blnAppend true 标识如果文件fileName在指定的路径下存在，则将文件打开并将写入的文件内容追加在文件的尾部
	 *  				false 创建新的文件 如果文件存在则将文件覆盖
	 * @return 如果文件创建失败 返回FALSE
	 */
	public boolean write(String filePath, String fileName, byte fileCon[], boolean blnAppend) {
		try {
			if (filePath == null || filePath.trim().length() == 0) {
				return false;
			}
			if (fileName == null || fileName.trim().length() == 0) {
				return false;
			}
			if (fileCon == null || fileCon.length == 0) {
				return false;
			}
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String strSep = System.getProperty("file.separator");
			String pathName = filePath + strSep +fileName;
			FileOutputStream out = new FileOutputStream(pathName, blnAppend);
			out.write(fileCon);
			out.flush();
			out.close();
			return true;
		} catch(Exception ex) {
			return false;
		} 
	}
	/**
	 * 创建文件 写文件
	 * @param filePath 文件的所在路径
	 * @param fileName 文件名称
	 * @param fileCon 文件的内容
	 * @param blnAppend true 标识如果文件fileName在指定的路径下存在，则将文件打开并将写入的文件内容追加在文件的尾部
	 *  				false 创建新的文件 如果文件存在则将文件覆盖
	 * @param charsetName 写文件的字符方式UTF-8或其它形式
	 * @return 如果文件创建失败 返回FALSE
	 */
	public boolean write(String filePath, String fileName, String fileCon, boolean blnAppend, String charsetName) {
		try {
			if (filePath == null || filePath.trim().length() == 0) {
				return false;
			}
			if (fileName == null || fileName.trim().length() == 0) {
				return false;
			}
			if (fileCon == null || fileCon.trim().length() == 0) {
				return false;
			}
			String strSep = System.getProperty("file.separator");
			File file = new File(filePath);
			
			if (!file.exists()) {
				file.mkdirs();
			}
			
			String pathName = filePath + strSep +fileName;
			FileOutputStream out = new FileOutputStream(pathName, blnAppend);
			OutputStreamWriter  writer;
			if (null == charsetName || charsetName.trim().length() == 0) {
				  writer = new OutputStreamWriter(out);
			} else {
				  writer = new OutputStreamWriter(out, charsetName);
			}
			
			writer.write(fileCon);
			writer.flush();
			writer.close();
			return true;
		} catch(Exception ex) {
			return false;
		}
		
	}
	
}
