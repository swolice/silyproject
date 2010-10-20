package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtil {

	public static void addElement(Element src, Element e, int ftype) {
		if (ftype == 0) {
			src.elements().add(0, e);
		} else {
			try {
				Element parent = src.getParent();
				List<Element> list = parent.elements();

				for (int i = 0; i < list.size(); i++) {
					Element tmp = list.get(i);
					if (src.equals(tmp)) {
						list.add(i+1, e);
						break;
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public static Element parse(String xml) {
		try {
			InputStream in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			Document document = new SAXReader().read(in);

			Element root = document.getRootElement();
			return root;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}
	
	public static Document createDocument(){
		Document document = DocumentHelper.createDocument();
		return document;
	}
	
	public static Element createElment(String name){
		Element element=DocumentHelper.createElement(name);
		return element;
	}
	
	
	public static String toString(Element element){
		try {
			Writer stringWriter = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(stringWriter);
			xmlWriter.write(element);
			xmlWriter.close();
			
			return stringWriter.toString();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	

}
