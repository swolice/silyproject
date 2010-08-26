package testjsoup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestJsoup {

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		URL url = null;
		try {
			url = new URL("http://bj.ganji.com/diannaoyingjian/");
			Document doc = Jsoup.parse(url, 3*2000);
			Elements es = doc.select("div");
//			Elements links = doc.select("div");
			Iterator<Element> iter = es.iterator();
			while(iter.hasNext()){
				Element e = iter.next();
				if(e.attr("class").equals("list")){
					Elements links = e.select("a");
					Iterator<Element> iter1 = links.iterator();
					while(iter1.hasNext()){
						Element link = iter1.next();
						String absHref = link.attr("abs:href");
						System.out.print("文本：" + link.text());
						System.out.println("===href：" + absHref);
						URL url1 = new URL(absHref);
						Document doc1 = Jsoup.parse(url1, 3*2000);
						Elements es1 = doc1.select("div");
						Iterator<Element> iter2 = es1.iterator();
						while(iter2.hasNext()){
							Element e2 = iter2.next();
							if(e2.attr("class").equals("body_left")){
								System.out.println(e2.text());
							}
						}
					}
				}
//				String relHref = link.attr("href"); // == "/"
//				String absHref = link.attr("abs:href"); //
//				System.out.println("相对：" + relHref);
//				System.out.println("绝对：" + absHref);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
