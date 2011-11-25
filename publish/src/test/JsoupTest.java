package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.util.HtmlUtils;

import com.sily.publish.WordPressPost;
import com.sily.util.ReadFile;

public class JsoupTest { 

	public static void main(String[] args) {
		ReadFile rFile = new ReadFile();
		
		String content = rFile.getFileContent("d:/我的桌面/simple.html","gb2312");
		Document doc = null;
//		try {
//			doc = Jsoup.parse(new File("C:/Users/sily/Desktop/text.html"),"utf-8");
			doc = Jsoup.parse(content);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String text = doc.body().text();
		
		
		
//		System.out.println(text);
		
		Elements ediv = doc.getElementsByTag("font");
		Elements br = doc.select("br");
	
		
		ediv.unwrap();
		
		String htmlString = doc.body().html();
		htmlString = HtmlUtils.htmlUnescape(htmlString);
		htmlString = htmlString.replaceAll("<br />", "");
		System.out.println(htmlString);
		
//	
//		Iterator li = ediv.iterator();
//		System.out.println(ediv.size());
//		while(li.hasNext()){
//			Element e = (Element)li.next();
//			System.out.println(e.text());
//		}
		try {
//			WordPressPost.publishPost("使用xml-rpc发布wordpress日志",htmlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
