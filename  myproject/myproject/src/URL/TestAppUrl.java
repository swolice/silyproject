package URL;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * 项目名称：
 * 名称：	TestAppUrl 
 * 描述： 	有时候需要通过一个链接地址去调用程序，如：在地址栏中输入地址，访问该地址
 * 			以下的程序可实现同样的效果 ，触发链接
 * 创建人：	吉仕军
 * 创建时间：	Aug 26, 2010 4:15:40 PM
 * 修改人：	
 * 修改时间：	
 * 修改备注： 
 * @version 1.0
 */
public class TestAppUrl {

	public static void main(String[] args) {
		String str = "http://172.16.1.251:9080/CRM-DEP/productAcctInfo.crm?PRO_ID=80008888";
		try {
			URL url = new URL(str);
			URLConnection conn = url.openConnection();
//			conn.connect();打开通信链接 并未打开链接读取数据
			conn.getInputStream();//真正的打开链接，读取数据 ，获取输入流 相当于在地址栏访问
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
