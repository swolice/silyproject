package cn.com.yumincun;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/** */
/**
 * 测试HTMLParser的使用.
 * 
 * @author <a href="mailto:xiexingxing1121@126.com">AmigoXie</a> Creation date:
 *         2008-1-18 - 上午11:44:22
 */
public class HtmlParseTest implements Runnable {
	/** */
	/**
	 * 入口方法.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static int init = 0;

	public static int link_int = 0;

	public static DatabaseTool dt = DatabaseTool.getInstance();

	public static String link_str = "http://c1520.noipx.com/thread0806.php?fid=20&search=&page=";

	public static URL link_url;

	public static String html_str;

	public static void main(String args[]) throws Exception {
		//readTextAndLinkAndTitle("http://c1520.noipx.com/htm_data/20/0907/337362.html");
		link_url = new URL(link_str);
		for (int i = 1; i < 10; i++) {
			String path = link_str + i;
			URL url = new URL(path);
			URLConnection conn = url.openConnection();
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

			String result = sb.toString();
			link_int = i;
			html_str = result;
			HtmlParseTest ht = new HtmlParseTest();
			Thread t = new Thread(ht);
			t.start();
			// System.out.println("main线程结束");
			// readByHtml(result);
			// readTextAndLinkAndTitle(result);
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
				if (href != null
						&& (href.startsWith("htm_data") || href
								.startsWith("read.php"))) {
					String ss = link_url.getProtocol() + "://"
							+ link_url.getHost() + "/" + href;
					String select_sql = "select list_link from text_table where list_link ='"
							+ ss + "'";
					if (dt.executeSQL1(select_sql)) {
						String sql = "insert into text_table(list_link) values('"
								+ ss + "')";
						dt.executeSQL(sql);
						readTextAndLinkAndTitle(ss);
					}
				}
			}
		}
		// System.out.print(nodelist.asString().trim());
	}

	/** */
	/**
	 * 分别读纯文本和链接.
	 * 
	 * @param result
	 *            网页的内容
	 * @throws Exception
	 */
	public static void readTextAndLinkAndTitle(String path){
		String result = path2html(path);
		Parser parser;
		NodeList nodelist;
		parser = Parser.createParser(result, "gbk");
		NodeFilter divFilter = new NodeClassFilter(Div.class);
		NodeFilter titleFilter = new NodeClassFilter(TitleTag.class);
		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { divFilter,titleFilter });
		Node[] nodes = null;
		try {
			nodelist = parser.parse(lastFilter);
			nodes = nodelist.toNodeArray();
		} catch (ParserException e1) {
			e1.printStackTrace();
		}
		
		String line = "";
		String title = "";
		for (int i = 0; i < nodes.length; i++) {
			Node node = nodes[i];
			if(node instanceof TitleTag){
				TitleTag titlenode = (TitleTag) node;
				title = titlenode.getTitle();
			}
			if (node instanceof Div) {
				Div textnode = (Div) node;
				if (textnode.getAttribute("class") != null
						&& textnode.getAttribute("class").equals("tpc_content")) {
					String s = "";
					PrintWriter out = null;
					try {
						s =  title.split(" ")[0] + (++init) + ".txt";
					} catch (Exception e) {
						System.out.println("保存文件出错！！！！！！！！！！！！！，另存文件");
						s = new Date().toLocaleString().replace(" ", "").replace("-", "").replace(":", "")
								+ (new Random().nextInt() % 100) + init
								+ ".txt";
					}
					line = textnode.getStringText()
							.replaceAll("<br />", "\r\n")
							.replace("&nbsp;", " ").replace("<br>", "\r\n");
					if(line.length()>100){
						try {
							out = new PrintWriter("L:/AREA_TRANSFER/txt/" + s);
							String sql = "insert into text_table(story) values('"
								+ line + "')";
							dt.executeSQL(sql);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							System.out.println("执行sql语句时有不合法的参数");
						}
						
						out.write(line);
						out.flush();
						out.close();
						System.out.println(s + "已创建");
					}
				}
			}
		}
	}

	public static String path2html(String path) {

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

	public void run() {
		// long l1 = System.currentTimeMillis();
		int temp = link_int;
		System.out.println("-------开-------" + temp + "-----------始---------");
		if (html_str != null) {
			try {
				readByHtml(html_str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("------结--------" + temp + "-----------束---------");
		// long l2 = System.currentTimeMillis();
		// System.out.println("时间差="+(l2 - l1));
	}
}