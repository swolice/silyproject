/** 
 * 文件名：		DemoPost.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-6-29 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.sily.util.ReadFile;

/**
 * 名称： DemoPost 
 * 描述： 创建人： sily 
 * 创建时间： 2011-6-29 上午08:38:56 
 * 修改人：
 *  修改时间： 
 *  修改备注：
 * 
 * @version 1.0
 */
public class WpTest {
	
	public static Logger log = Logger.getLogger("publish");

	public static void main(String[] args){

		ReadFile rf =  new ReadFile();
		 
		String desc = rf.getFileContent("C:/Users/sily/Desktop/test.txt", "GBK");
		
		try {
//			String desc = new String(readFromFile("C:/Users/sily/Desktop/test.txt"));
			publishPost("title",desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void publishPost(String title,String desc) throws Exception {
		// Set up XML-RPC connection to server
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(
				"http://sily.sinaapp.com/xmlrpc.php"));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);

		// Set up parameters required by newPost method
		Map<String, String> post = new HashMap<String, String>();
		post.put("title", title);
//		post.put("link", "http://sily.sinaapp.com/");
		post.put("description",desc);
		Object[] params = new Object[] { "1", "sily", "jishijun", post,
				Boolean.TRUE };

		// Call newPost
		String result = (String) client.execute("metaWeblog.newPost", params);
		log.info(" Created with blogid " + result);
	}

	
	private static byte[] readFromFile(String src) throws Exception {
        byte data[];
        File file = new File(src);
        InputStream in = new FileInputStream(src);
        data = new byte[(int)file.length()];
        in.read(data);
        in.close();
        return data;
    }
	
}
