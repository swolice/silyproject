package cn.com.mianshi;

import java.io.*;
import java.util.Hashtable;
import org.xml.sax.*;

public class SAXHandler extends HandlerBase {
	private Hashtable table = new Hashtable();
	private String currentElement = null;
	private String currentValue = null;

	public void setTable(Hashtable table) {
		this.table = table;
	}

	public Hashtable getTable() {
		return table;
	}

	public void startElement(String tag, AttributeList attrs)
			throws SAXException {
		currentElement = tag;
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		currentValue = new String(ch, start, length);
	}

	public void endElement(String name) throws SAXException {
		if (currentElement.equals(name))
			table.put(currentElement, currentValue);
	}
}
