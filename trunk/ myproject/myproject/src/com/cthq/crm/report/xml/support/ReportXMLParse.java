/*
 * Created on 2009-11-13
 *
 */
package com.cthq.crm.report.xml.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cthq.crm.webservice.common.xml.imp.XMLBaseParse;

/**
 * @author sily
 *
 */
public class ReportXMLParse extends XMLBaseParse{

	
	private static final List xmlLevelList = new ArrayList(5);
	
	static {
		xmlLevelList.add(0, "root");
		xmlLevelList.add(1, "root/head");
		xmlLevelList.add(2, "root/body");
		xmlLevelList.add(3, "root/foot");
		xmlLevelList.add(4, "root/excelprop");
	}

	public String getXMLLevelXPath(int loc) {
		if (loc >= xmlLevelList.size()) {
			return "";
		}
		return (String)xmlLevelList.get(loc);
	}

	public void chkXMLValidate() {
		
	}

	public void writeXMLToFile(Map _paramMap) {
		
	}

	public List getXMLLevelList() {
		return xmlLevelList;
	}

	
}
