package cn.com.mianshi;

import java.io.*;

public class DomTest {
	private String inFile = "c:/people.xml";
	private String outFile = "c:/people.xml";

	public static void main(String args[]) {
		new DomTest();
	}

	public DomTest() {
		try {
			javax.xml.parsers.DocumentBuilder builder =

			javax.xml.parsers.DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			org.w3c.dom.Document doc = builder.newDocument();
			org.w3c.dom.Element root = doc.createElement("老师");
			org.w3c.dom.Element wang = doc.createElement("王");
			org.w3c.dom.Element liu = doc.createElement("刘");
			wang.appendChild(doc.createTextNode("我是王老师"));
			root.appendChild(wang);
			doc.appendChild(root);
			javax.xml.transform.Transformer transformer = javax.xml.transform.TransformerFactory
					.newInstance().newTransformer();
			transformer.setOutputProperty(
					javax.xml.transform.OutputKeys.ENCODING, "gb2312");
			transformer.setOutputProperty(
					javax.xml.transform.OutputKeys.INDENT, "yes");

			transformer.transform(new javax.xml.transform.dom.DOMSource(doc),
					new javax.xml.transform.stream.StreamResult(outFile));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
