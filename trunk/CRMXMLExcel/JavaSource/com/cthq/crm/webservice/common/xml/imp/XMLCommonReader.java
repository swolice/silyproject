/*
 * Created on 2008-12-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cthq.crm.webservice.common.xml.imp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMLCommonReader extends XMLBaseReader {
	List xmlLevelList = new ArrayList();
	public void setXMLLevelList(List list) {
		xmlLevelList = list;
	}
	/* (non-Javadoc)
	 * @see com.cthq.crm.webservice.common.xml.interfaces.IXMLReader#getXMLLevelList()
	 */
	public List getXMLLevelList() {
		// TODO Auto-generated method stub
		return xmlLevelList;
	}

	/* (non-Javadoc)
	 * @see com.cthq.crm.webservice.common.xml.interfaces.IXMLReader#getXMLLevelXPath(int)
	 */
	public String getXMLLevelXPath(int loc) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
	}
}
