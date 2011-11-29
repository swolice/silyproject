package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sily.util.ReadFile;

public class JsoupTest { 

	public static void main(String[] args) {
		ReadFile rFile = new ReadFile();
		
		String content = rFile.getFileContent("D:/我的桌面/sss.html");
		Document doc = null;
//		try {
//			doc = Jsoup.parse(new File("C:/Users/sily/Desktop/text.html"),"utf-8");
			doc = Jsoup.parse(content);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String text = doc.body().text();
		
		Elements  es = 	doc.select("img");
		
		Element img = es.get(0);
		
		Element parent = img.parent();
		
		System.out.println(img.outerHtml());
		
		if(!"a".equals(parent)){
			Element newEle = doc.createElement("a").attr("href",img.attr("src")).appendChild(img.clone());
			img.after(newEle.outerHtml());
			img.remove();
		}
		System.out.println(doc.html());
		
//		Elements ediv = doc.getElementsByTag("font");
//		Elements br = doc.select("br");
//	
//		
//		ediv.unwrap();
//		
//		String htmlString = doc.body().html();
//		htmlString = HtmlUtils.htmlUnescape(htmlString);
//		htmlString = htmlString.replaceAll("<br />", "");
//		System.out.println(htmlString);
		
//	
//		Iterator li = ediv.iterator();
//		System.out.println(ediv.size());
//		while(li.hasNext()){
//			Element e = (Element)li.next();
//			System.out.println(e.text());
//		}
		try {
//			WordPressPost.publishPost("使用xml-rpc发布wordpress日志",htmlString);
			
//			Element element = doc.select("div[class=ennote]").first();
//			String html = element.html();
//			FileWriter fWriter = new FileWriter(new File("C:/Users/sily/Desktop/dropbox1.html"));
//			
//			fWriter.write(html);
//			
//			fWriter.flush();
//			fWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
