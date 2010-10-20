package test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.dom4j.CDATA;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import util.XmlUtil;

public class TestXml {
	
	public static void main(String[] args) throws Exception{
		Document document = DocumentHelper.createDocument();
		Element employees = document.addElement("employees");
		Element employee = employees.addElement("employee");
		Element name = employee.addElement("name");
		name.setText("ddvip");
		Element sex = employee.addElement("sex");
		sex.setText("m");
		Element age = employee.addElement("age");
		age.setText("29");
		
		
		Element t1=DocumentHelper.createElement("zz");
		t1.addAttribute("ID","zzz");
		t1.addAttribute("t","aaa");
		t1.addCDATA("aaa");
		
//		employee.add(t1);
		
		employee.elements().add(0,t1);
		
		
//		t1.s
		
		System.out.println(XmlUtil.toString((Element)document.getRootElement()));
		Element e1=document.elementByID("zzz");
		e1.addAttribute("t","bbb");
//		e1.remove((CDATA)e1.getData());
		System.out.println("class:"+e1.getData());
		e1.clearContent();
		e1.addCDATA("zzz");
		if (e1==null){
			System.out.println("null");
			
		}else{
		System.out.println(e1.getText());
		System.out.println(e1.attributeValue("ID"));
		System.out.println(e1.attributeValue("id"));
		}
		
		
		System.out.println(XmlUtil.toString(e1));
		
		System.out.println(XmlUtil.toString(employee));
		System.out.println(XmlUtil.toString((Element)document.getRootElement()));
		
		try {
			Writer fileWriter = new FileWriter("d:/aa.xml");
			XMLWriter xmlWriter = new XMLWriter(fileWriter);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
	}

}
