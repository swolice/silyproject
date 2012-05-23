package test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DelTable {

	public static void main(String[] args) {
		Document doc = null;
		try {
			doc = Jsoup.parse(new File("d:/我的桌面/test.html"),"UTF-8");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Elements es = doc.select("table");
		Iterator<Element> it = es.iterator();
		if(it.hasNext()){
			Element e = it.next();
			e.remove();
		}
		System.out.println( doc.body().html());
		
		
		
	}
}
