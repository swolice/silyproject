package cn.com.mianshi;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.Locator;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

class TestSAX extends DefaultHandler {
	private StringBuffer buf;
	
	private int index = 1;
	
	public TestSAX() {
		super();
	}

	public void setDocumentLocator(Locator locator) {
	}

	public void startDocument() throws SAXException {
		buf = new StringBuffer();
		System.out.println("*******开始解析文档*******");
	}

	public void endDocument() throws SAXException {
		System.out.println("*******文档解析结束*******");
		
	}

	public void startPrefixMapping(String prefix, String uri) {
		System.out.println("\n前缀映射: " + prefix + " 开始!" + " 它的URI是:" + uri);
	}

	public void endPrefixMapping(String prefix) {
		System.out.println("\n前缀映射: " + prefix + " 结束!");
	}

	public void processingInstruction(String target, String instruction)
			throws SAXException {
	}

	public void ignorableWhitespace(char[] chars, int start, int length)
			throws SAXException {
	}

	public void skippedEntity(String name) throws SAXException {
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {
		//System.out.println("*******开始解析元素*******");
		//System.out.println("元素名" + qName);
		//System.out.println("元素值" + localName);
		if("title".equals(qName)){
			System.out.print("本书<<");
		}
		
		if("chapter".equals(qName)){
			System.out.print("第"+atts.getValue("number")+"章   "+atts.getValue("title"));
			index = 1;
		}
		if("topic".equals(qName)){
			System.out.print("第"+(index++)+"节   "+atts.getValue("name"));
		}
		
//		for (int i = 0; i < atts.getLength(); i++) {
//			System.out.println("元素名" + atts.getQName(i) + "属性值"
//					+ atts.getValue(i));
//		}
	}

	public void endElement(String namespaceURI, String localName,
			String fullName) throws SAXException {
		if("title".equals(fullName)){
			  System.out.println(">>的目录");
		}
		//System.out.println("******元素解析结束********"+fullName);
	}

	public void characters(char[] chars, int start, int length)
			throws SAXException {
		// 将元素内容累加到StringBuffer中
		System.out.print(new String(chars,start,length));
		buf.append(chars, start, length);
	}

	public static void main(String args[]) {
		try {

			SAXParserFactory sf = SAXParserFactory.newInstance();
			SAXParser sp = sf.newSAXParser();
			TestSAX testsax = new TestSAX();
			sp.parse(new InputSource(
					"content.xml"),
					testsax);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
