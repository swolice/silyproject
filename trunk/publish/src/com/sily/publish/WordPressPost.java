/** 
 * 文件名：		DemoPost.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-6-29 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package com.sily.publish;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sily.util.FileType;
import com.sily.util.HtmlUtils;
import com.sily.util.ReadFile;
import com.sily.util.StringUtils;

/**
 * 名称： DemoPost 描述： 创建人： sily 创建时间： 2011-6-29 上午08:38:56 修改人： 修改时间： 修改备注：
 * 
 * @version 1.0
 */
public class WordPressPost {

	public static Logger log = Logger.getLogger("publish");

	public static void main(String[] args) {

		ReadFile rf = new ReadFile();

		// String desc = rf.getFileContent("C:/Users/sily/Desktop/test.txt",
		// "GBK");

		try {
			String desc = rf.getFileContent("d:/我的桌面/test.txt", "GBK");

			// Document doc = Jsoup.parse(desc);
			// String text = doc.body().text();
			// String excerpt = text;

			// System.out.println(excerpt);

			// publishPost("test",desc);

			// File file = new File("D:/我的桌面/clock.avi");
			// byte[] bytes = getOutExcelByteCon(file);
			// publishMedia(file.getName(),FileType.getMineType(file),bytes);

			publishPost("vim学习教程", desc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发布媒体文件， 图片，附件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String publishMedia(File file) throws Exception {
		// byte[] bytes = getOutExcelByteCon(file);
		// String mineType = FileType.getMineType(file);
		String urlString = null;
		// if (StringUtils.isNotNull(mineType)) {
		// publishMedia(file.getName(),mineType,bytes);
		urlString = SvnKitLogic.process(file);
		// }

		return urlString;
	}

	public static String publishMedia(File file, String mineType)
			throws Exception {
		byte[] bytes = getOutExcelByteCon(file);
		if (StringUtils.isNotNull(mineType)) {
			return publishMedia(file.getName(), mineType, bytes);
		} else {
			return null;
		}
	}

	/**
	 * 发布文章
	 * 
	 * @param title
	 * @param desc
	 * @throws Exception
	 */
	public static void publishPost(String title, String desc) throws Exception {
		try {
			// Set up XML-RPC connection to server
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL("http://www.swjsj.com/xmlrpc.php"));
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);

			// Set up parameters required by newPost method
			Map<String, String> post = new HashMap<String, String>();
			post.put("title", title);
			// post.put("link", "http://sily.sinaapp.com/");

			Document doc = Jsoup.parse(desc);
			
			Elements es = doc.select("table");
			Iterator<Element> it = es.iterator();
			if(it.hasNext()){
				Element e = it.next();
				e.remove();
			}
			
			post.put("description", doc.body().html());

			String text = doc.body().text();
			String excerpt = text;
			if (text.length() > 800) {
				excerpt = text.substring(0, 800);
			}
			excerpt = HtmlUtils.htmlEscape(excerpt);
			post.put("mt_excerpt", excerpt.replaceAll("\\。", "。\r\n"));// 摘要

			Object[] params = new Object[] { "1", "sily", "jishijun", post,
					Boolean.TRUE };

			// Call newPost

			String result = (String) client.execute("metaWeblog.newPost",
					params);
			log.info(" Created with blogid " + result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	private static byte[] readFromFile(String src) throws Exception {
		byte data[];
		File file = new File(src);
		InputStream in = new FileInputStream(src);
		data = new byte[(int) file.length()];
		in.read(data);
		in.close();
		return data;
	}

	/**
	 * 发布多媒体文件
	 * 
	 * @param name
	 * @param type
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String publishMedia(String name, String type, byte[] bytes)
			throws Exception {
		// Set up XML-RPC connection to server
		try {
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL("http://www.swjsj.com/xmlrpc.php"));
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);

			// Set up parameters required by newPost method
			Map<String, Object> post = new HashMap<String, Object>();
			post.put("name", name);
			post.put("type", type);
			post.put("bits", bytes);
			Object[] params = new Object[] { "1", "sily", "jishijun", post };

			// Call newPost
			Map<String, String> result = (Map<String, String>) client.execute(
					"metaWeblog.newMediaObject", params);
			System.out.println(" metaWeblog.newMediaObject url: "
					+ result.get("url"));
			log.info(" metaWeblog.newMediaObject url: " + result.get("url"));

			return result.get("url");
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * 获取excel数据的二进制数组
	 * 
	 * @return
	 */
	private static byte[] getOutExcelByteCon(File excelFile) {
		byte[] attachBytes = null;
		try {
			FileInputStream inputStream = new FileInputStream(excelFile);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			int cb = 0;
			while ((cb = inputStream.read()) != -1) {
				outStream.write(cb);
			}
			inputStream.close();
			attachBytes = outStream.toByteArray();
			outStream.close();
			return attachBytes;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
