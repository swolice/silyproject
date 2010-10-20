package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Test {

	public static void main(String args[]) {
		String fileName="C:/Documents and Settings/Administrator/×ÀÃæ/usage/a.xml";
		File inputXml = new File(fileName);
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(inputXml);
			Element root = document.getRootElement();
			System.out.println(root.getName());
			System.out.println(root.elementText("a"));
			System.out.println(root.attributeValue("start"));
			System.out.println(root.element("a").getText());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

//		System.out.println(new Date(1279214273339l));
		

	}

	public static void createXml(String fileName) {
		Document document = DocumentHelper.createDocument();
		Element employees = document.addElement("employees");
		Element employee = employees.addElement("employee");
		Element name = employee.addElement("name");
		name.setText("ddvip");
		Element sex = employee.addElement("sex");
		sex.setText("m");
		Element age = employee.addElement("age");
		age.setText("29");
		try {
			Writer fileWriter = new FileWriter(fileName);
			XMLWriter xmlWriter = new XMLWriter(fileWriter);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void parserXml(String fileName) {
		File inputXml = new File(fileName);
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(inputXml);
			Element employees = document.getRootElement();
			for (Iterator i = employees.elementIterator(); i.hasNext();) {
				Element employee = (Element) i.next();
				for (Iterator j = employee.elementIterator(); j.hasNext();) {
					Element node = (Element) j.next();
					System.out.println(node.getName() + ":" + node.getText());
				}
			}
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("dom4j parserXml");
	}

}
