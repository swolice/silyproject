package cn.com.yumincun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * htmlparser取得一段html代码里面所有的链接地址和链接名称
 * 
 * @author chenguoyong
 * 
 */
public class Testhtmlparser{

	/**
	 * @param argsasdfa中文
	 * 
	 * @throws ParserException 
	 */
	public static int temp = 0;
	
	public static void main(String[] args) {
		List<String> list = null;
		try {
			//小说
//			list = readTextAndLinkAndTag("http://64.191.20.24/htm_data/8/0906/331888.html");
			list = readTextAndLinkAndTag("http://c1520.noipx.com/htm_data/8/0906/333414.html");
			List<String> list1 = showLink(list);
//			for (int i = 0; i < list1.size(); i++) {
//				showLink(readTextAndLinkAndTag(list1.get(i)));
//			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		
	}
	
	public static List<String> showLink(List<String> list){
		List<String> list1 = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if(!list.get(i).startsWith("http://")){
				String aa = "http://64.191.20.24/"+list.get(i);
				if(aa.endsWith("jpg")||aa.endsWith("jpeg")||aa.endsWith("jif")){
					//System.out.println(aa);
				}
				list1.add(aa);
			}else{
				if(list.get(i).endsWith("jpg")||list.get(i).endsWith("jpeg")||list.get(i).endsWith("gif")){
					//System.out.println(list.get(i));
				}
				list1.add(list.get(i));
			}
		}
		return list1;
	}
	
	public static List<String> readTextAndLinkAndTag(String str) throws ParserException{
		List<String> list = new ArrayList<String>();
		URL url = null;
		String strhtml = "";
		try {
			url = new URL(str);
			URLConnection c = url.openConnection();
			c.connect();
			InputStream is = c.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is,"gbk"));

			while (in.readLine() != null) {
				//System.out.println(in.readLine());
				strhtml += in.readLine();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String htmlcode = strhtml;
		 
		Parser parser;
        NodeList nodelist;
        parser = Parser.createParser(htmlcode, "gbk");
        NodeFilter textFilter = new NodeClassFilter(TextNode.class);
        NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
        NodeFilter titleFilter = new NodeClassFilter(TitleTag.class);
        NodeFilter imageFilter = new NodeClassFilter(ImageTag.class);
        NodeFilter inputFilter = new NodeClassFilter(InputTag.class);
        OrFilter lastFilter = new OrFilter();
        lastFilter.setPredicates(new NodeFilter[] {imageFilter,inputFilter });
        nodelist = parser.parse(lastFilter);
        //nodelist = parser.parse(null);
        Node[] nodes = nodelist.toNodeArray();
        String line = "";
        for (int i = 0; i < nodes.length; i++) {
            @SuppressWarnings("unused")
			Node node = nodes[i];
            if (node instanceof TextNode) {
                TextNode textnode = (TextNode) node;
                //line = textnode.getText();
            } else if (node instanceof LinkTag) {
                LinkTag link = (LinkTag) node;
                //line = link.getLink();
            } else if (node instanceof TitleTag) {
                TitleTag titlenode = (TitleTag) node;
                //line = titlenode.getTitle();
            } else if (node instanceof ImageTag) {
            	ImageTag imagenode = (ImageTag) node;
	        	line = imagenode.getImageURL();
	        } else if (node instanceof InputTag) {
	        	InputTag inputnode = (InputTag) node;
	        	line = inputnode.toHtml();
	        }
            
            if (isTrimEmpty(line))
                continue;
            
            list.add(line);
           System.out.println(line);
        }
        return list;
	}

	public static boolean isTrimEmpty(String astr) {
		if ((null == astr) || (astr.length() == 0)) {
			return true;
		}
		if (isBlank(astr.trim())) {
			return true;
		}
		return false;
	}

	public static boolean isBlank(String astr) {
		if ((null == astr) || (astr.length() == 0)) {
			return true;
		} else {
			return false;
		}
	}

}
