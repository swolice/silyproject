package cn.com.sily;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLParser { 
	//Éú³É
	public void generateDocument() {
		Document document = DocumentHelper.createDocument();
		Element catalogElement = document.addElement("catalog");
//		catalogElement.addComment("An XML Catalog");
//		catalogElement.addProcessingInstruction("target", "simx");
		Element journalElement = catalogElement.addElement("journal");
		journalElement.addAttribute("title", "XML Zone");
		journalElement.addAttribute("publisher", "IBM developerWorks");
		Element articleElement = journalElement.addElement("article");
		articleElement.addAttribute("level", "Intermediate");
		articleElement.addAttribute("date", "December-2001");
		Element titleElement = articleElement.addElement("title");
		titleElement.setText("Java configuration with XML Schema");
		Element authorElement = articleElement.addElement("author");
		Element firstNameElement = authorElement.addElement("firstname");
		firstNameElement.setText("Marcello");
		Element lastNameElement = authorElement.addElement("lastname");
		lastNameElement.setText("Vitaletti");
		File file = new File("c:/catalog");
		if (!file.exists()) {
			file.mkdir();
		}
		//document.addDocType("catalog", null, "file://c:/Dtds/catalog.dtd");
		try {
			XMLWriter output = new XMLWriter(new FileWriter(new File(
					"c:/catalog/catalog.xml")));
			output.write(document);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	//ÐÞ¸Ä
	public void modifyDocument(File inputXml) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new FileInputStream(new File("c:/catalog/catalog.xml")));
			List list = document.selectNodes("//article/@level");
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attribute = (Attribute) iter.next();
				if (attribute.getValue().equals("Intermediate"))
					attribute.setValue("Introductory");
			}

			list = document.selectNodes("//article/@date");
			iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attribute = (Attribute) iter.next();
				if (attribute.getValue().equals("December-2001"))
					attribute.setValue("October-2002");
			}
			list = document.selectNodes("//article");
			iter = list.iterator();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				Iterator iterator = element.elementIterator("title");
				while (iterator.hasNext()) {
					Element titleElement = (Element) iterator.next();
					if (titleElement.getText().equals(
							"Java configuration with XML Schema"))
						titleElement
								.setText("Create flexible and extensible XML schema");
				}
			}
			list = document.selectNodes("//article/author");
			iter = list.iterator();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				Iterator iterator = element.elementIterator("firstname");
				while (iterator.hasNext()) {
					Element firstNameElement = (Element) iterator.next();
					if (firstNameElement.getText().equals("Marcello"))
						firstNameElement.setText("Ayesha");
				}
			}
			list = document.selectNodes("//article/author");
			iter = list.iterator();
			while (iter.hasNext()) {
				Element element = (Element) iter.next();
				Iterator iterator = element.elementIterator("lastname");
				while (iterator.hasNext()) {
					Element lastNameElement = (Element) iterator.next();
					if (lastNameElement.getText().equals("Vitaletti"))
						lastNameElement.setText("Malik");
				}
			}
			File file = new File("c:/catalog");
			if (!file.exists()) {
				file.mkdir();
			}
			XMLWriter output = new XMLWriter(new FileWriter(new File(
					"c:/catalog/catalog-modified.xml")));
			output.write(document);
			output.close();
		}

		catch (DocumentException e) {
			
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] argv) {
		XMLParser dom4j = new XMLParser();
		dom4j.generateDocument();
		dom4j.modifyDocument(new File("c:/catalog/catalog.xml"));
	}

}
