package cn.com.yumincun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

/** */
/**
 * 测试HTMLParser的使用.
 * 
 * @author <a href="mailto:xiexingxing1121@126.com">AmigoXie</a> Creation date:
 *         2008-1-18 - 上午11:44:22
 */
public class CopyOfHtmlParseTest1 implements Runnable{
	/** */
	/**
	 * 入口方法.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static String html_str;
	
	public static int link_int = 0;

	public static DatabaseTool dt = DatabaseTool.getInstance();
	
	public static List<String> qlist = new ArrayList<String>();
	
	public static String link_str = "http://c1520.noipx.com/thread0806.php?fid=8&search=&page=";
	
	public static String save_path = "C:/WINDOWS/pic";

	public static URL link_url;
	
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		System.out.println("请输入地址(不包含页数)：");
		if(null!=br.readLine()&&!br.readLine().trim().equals("")){
			link_str = br.readLine();
		}
		System.out.println("请输入保存的路径：如C:/WINDOWS/pic/");
		if(null!=br.readLine()&&!br.readLine().trim().equals("")){
			save_path = br.readLine();
		}
		link_url = new URL(link_str);
		
		for (int i = 1; i < 10; i++) {
			String path = link_str+i;
			String result = path2html(path);
			link_int = i;
			html_str = result;
			CopyOfHtmlParseTest1 c1 = new CopyOfHtmlParseTest1();
			Thread t = new Thread(c1);
			t.start();
		}
	}

	/** */
	/**
	 * 按页面方式处理.解析标准的html页面
	 * 
	 * @param content
	 *            网页的内容
	 * @throws Exception
	 */
	public static void readByHtml(String content) throws Exception {
		Parser myParser;
		myParser = Parser.createParser(content, "gbk");
		NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { linkFilter });
		NodeList nodelist;
		nodelist = myParser.parse(lastFilter);
		Node[] nodes = nodelist.toNodeArray();
		for (int i = 0; i < nodes.length; i++) {
			Node node = nodes[i];
			if (node instanceof LinkTag) {
				LinkTag inputnode = (LinkTag) node;
				String href = inputnode.getAttribute("href");
				if (href != null && href.startsWith("htm_data")) {
					
					String ss = link_url.getProtocol()+"://"+link_url.getHost()+"/" + href;
					String select_sql = "select short_href from link_table where short_href ='"+ss+"'";
					if(dt.executeSQL1(select_sql)){
						String sql = "insert into link_table(short_href) values('"+ss+"')";
						dt.executeSQL(sql);
						readTextAndLinkAndTitle(ss);
					}
				}
			}
		}
	}

	/** */
	/**
	 * 分别读纯文本和链接.
	 * 
	 * @param result
	 *            网页的内容
	 * @throws Exception
	 */
	public static void readTextAndLinkAndTitle(String path) throws Exception {
		String result = path2html(path);
		Parser parser;
		NodeList nodelist;
		parser = Parser.createParser(result, "gbk");
		//NodeFilter divFilter = new NodeClassFilter(Div.class);
		NodeFilter inputFilter = new NodeClassFilter(InputTag.class);
		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { inputFilter });
		nodelist = parser.parse(lastFilter);
		Node[] nodes = nodelist.toNodeArray();
		// String line = "";

		for (int i = 0; i < nodes.length; i++) {
			Node node = nodes[i];
			if (node instanceof InputTag) {
				InputTag inputnode = (InputTag) node;
				if (inputnode.getAttribute("type").equals("image")) {
					String ss = inputnode.getAttribute("src");
					//qlist.add(ss);
					String select_sql = "select whole_link from link_table where whole_link ='"+ss+"'";
					
					if(dt.executeSQL1(select_sql)){
						String sql = "insert into link_table(whole_link) values('"+ss+"')";
						dt.executeSQL(sql);
						outFile1(ss);
					}
				}
			}
		}
	}

	public static String path2html(String path){
		
		URLConnection conn;
		String result = null;
		try {
			URL url = new URL(path);
			conn = url.openConnection();
			conn.setDoOutput(true);

			InputStream inputStream = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(inputStream, "gbk");
			StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(isr);
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
				sb.append("\n");
			}
			result = sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	public static void outFile() {
		for (int i = 0; i < qlist.size(); i++) {
			String url = qlist.get(0);
			try {
				URL u = new URL(url);
				URLConnection c = u.openConnection();
				c.connect();
				InputStream is = c.getInputStream();
				String filename = url.substring(url.lastIndexOf("/") + 1);
				filename = "d:\\eclipse\\downsource\\story\\"
						+ (int) Math.random() * 100 + filename;
				OutputStream os = new FileOutputStream(filename);
				int tmp = 0;
				while ((tmp = is.read()) != -1) {
					os.write(tmp);
				}
				os.close();
				is.close();
				System.out.println(filename + "添加成功;");
			} catch (IOException e) {
				e.printStackTrace();
			}
			qlist.remove(0);
		}
	}

	public static void outFile1(String url) {
		try {
			URL u = new URL(url);
			URLConnection c = u.openConnection();
			c.connect();
			InputStream is = c.getInputStream();
			String filename = url.substring(url.lastIndexOf("/") + 1);
			File file = new File(save_path);
			if(!file.exists()){
				file.mkdirs();
			}
			filename = save_path + new Date().toLocaleString().replace(" ", "").replace("-", "").replace(":", "")+""+(int)(Math.random()
					* 1000) + filename;
			OutputStream os = new FileOutputStream(filename);
			int tmp = 0;
			while ((tmp = is.read()) != -1) {
				os.write(tmp);
			}
			os.close();
			is.close();
			System.out.println(filename + "添加成功;");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long l1 = System.currentTimeMillis();
		System.out.println("-------开-------"+link_int+"-----------始---------");
		if(html_str!=null){
			try {
				readByHtml(html_str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("------结--------"+link_int+"-----------束---------");
		long l2 = System.currentTimeMillis();
		System.out.println("时间差="+(l2 - l1));
	}
}
