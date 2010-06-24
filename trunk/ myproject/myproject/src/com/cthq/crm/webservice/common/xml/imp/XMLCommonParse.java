/*
 * Created on 2009-8-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cthq.crm.webservice.common.xml.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jiangfeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMLCommonParse extends XMLBaseParse {

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
	 * @see com.cthq.crm.webservice.common.xml.interfaces.IXMLParser#getXMLLevelXPath(int)
	 */
	public String getXMLLevelXPath(int loc) {
		// TODO Auto-generated method stub
		return (String)xmlLevelList.get(loc);
	}

	/* (non-Javadoc)
	 * @see com.cthq.crm.webservice.common.xml.interfaces.IXMLParser#chkXMLValidate()
	 */
	public void chkXMLValidate() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.cthq.crm.webservice.common.xml.interfaces.IXMLParser#writeXMLToFile(java.util.Map)
	 */
	public void writeXMLToFile(Map _paramMap) {

	}

	public static void main(String[] args) {
	}
}
