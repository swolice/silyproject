package util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Analyzer {
	private HashMap map = new HashMap();
	private String fileName;
	private String html = "";

	public Analyzer(String fileName) {
		super();
		this.fileName = fileName;
	}

	/**
	 * @return the map
	 */
	public HashMap getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(HashMap map) {
		this.map = map;
	}

	public void analyze() {
		try {
			InputStream in = Analyzer.class.getClassLoader().getResourceAsStream(fileName);
			Document document = new SAXReader().read(in);

			Element root = document.getRootElement();

			String domain = root.elementText("domain");
			map.put("domain", domain);

			String url = root.elementText("url");
			String charset = root.elementText("charset");
			String city = root.elementText("city");

			if (url == null || url.length() < 1)
				return;
			if (charset == null)
				charset = "utf-8";

			map.put("url", url);
			map.put("charset", charset);
			map.put("city",city);

			html = HttpUtil.get(url, null, charset);

			if (html == null || html.length() < 1) {
				return;
			}

			process(root, html);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void process(Element e, String htmlString) {
		String seg = seg(e, htmlString);

		Iterator it = e.elementIterator("value");
		while (it.hasNext()) {
			Element e1 = (Element) it.next();
			setValue(e1, seg);
		}

		it = e.elementIterator("seg");
		while (it.hasNext()) {
			Element e1 = (Element) it.next();
			process(e1, seg);
		}

	}

	public String seg(Element e, String htmlString) {
		String start = e.elementText("start");
		String end = e.elementText("end");

		int s = 0;
		if (start != null)
			s = htmlString.indexOf(start);
		if (s < 0)
			s = 0;

		int e1 = htmlString.length();
		if (end != null)
			e1 = htmlString.indexOf(end, s);
		if (e1 < s)
			e1 = htmlString.length();

		String seg = htmlString.substring(s, e1);
		return seg;
	}

	public void setValue(Element e, String htmlString) {
		String seg = seg(e, htmlString);

		String names = e.elementText("name");
		if (names == null || names.length() < 1)
			return;

		String[] ns = names.split(",");

		String reg = e.elementText("reg");
		if (reg == null || reg.length() < 1) {
			map.put(ns[0], seg);
			return;
		}

		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(seg);

		if (m.find()) {
			for (int i = 1; i <= m.groupCount(); i++) {
				if (i - 1 < ns.length) {
					map.put(ns[i - 1], m.group(i));
				} else {
					break;
				}
			}
		}
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
