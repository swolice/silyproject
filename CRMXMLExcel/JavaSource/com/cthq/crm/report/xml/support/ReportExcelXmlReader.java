package com.cthq.crm.report.xml.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cthq.crm.project.common.util.ReadFile;
import com.cthq.crm.project.common.xml.imp.XMLBaseReader;
import com.cthq.crm.project.common.xml.imp.XMLElement;
import com.cthq.crm.project.common.xml.imp.XMLNode;
import com.cthq.crm.project.common.xml.imp.XMLNodeCollection.Entry;
import com.cthq.crm.project.common.xml.interfaces.IXMLAttribute;

public class ReportExcelXmlReader  extends XMLBaseReader {
	private static final List xmlLevelList = new ArrayList(6);
	private Map reportMap = new HashMap();
	private List reportSecttionNmList = new ArrayList(10);
	static {
		xmlLevelList.add(0, "report");
		xmlLevelList.add(1, "report/page");

		xmlLevelList.add(2, "report/page/head");
		
		xmlLevelList.add(3, "report/page/body");
		
		xmlLevelList.add(4, "report/page/body/bodygroup");

		xmlLevelList.add(5, "report/page/foot");
	}
	/**
	 * 设置节点属性
	 */
	public void initReportMap(){
		setReportPageMap(reportMap, getXMLLevelXPath(2)); 
		setReportPageMap(reportMap, getXMLLevelXPath(3)); 
		setReportPageMap(reportMap, getXMLLevelXPath(4)); 
		setReportPageMap(reportMap, getXMLLevelXPath(5));  
	}
	public List getReportSectionNameList() {
		return this.reportSecttionNmList;
	}
	/**
	 * 
	 * @param containerMap
	 * @param xmlLevelPath
	 */
	private void setReportPageMap(Map containerMap, String xmlLevelPath) {
		int count = super.xmlDomElementCount(xmlLevelPath);
		for(int i=0; i < count; i++){
			XMLElement xmlElement = super.xmlDomElement(xmlLevelPath, i);
			Iterator attrIter = xmlElement.attributeIterator();
			IXMLAttribute attr = null;
			while(attrIter.hasNext()){
				IXMLAttribute attr1  = (IXMLAttribute)attrIter.next();
				if (attr1.getName().equals("sectionname")){
					attr = attr1;
					break;
				}
			}
			if (attr == null) {
				continue;
			}
			Map leafNodeData = new HashMap();
			Iterator iter = xmlElement.leafNodeIterator();
			if(null != iter){
				while (iter.hasNext()) {
					Entry entry = (Entry) iter.next();
					XMLNode xn = entry.xmlNode;
					if (xn.getName().equals("CellStyle")){
						leafNodeData.put(xn.getName(),new ArrayList());
						continue;
					}	
					if (xn.getName().equals("RowDataMap")) {
						leafNodeData.put(xn.getName(), new HashMap(0));
						continue;
					}
					if (xn.getName().equals("Item")){
						if (leafNodeData.get("CellItem") == null) {
							leafNodeData.put("CellItem", new HashMap());
						}
						Map cellItemMap = (Map)leafNodeData.get("CellItem");
						Iterator iterAttr = xn.getAttributes().iterator();
						Map attrMap = new HashMap();
						for(;iterAttr.hasNext();) {
							IXMLAttribute itemAttr = (IXMLAttribute)iterAttr.next();
							if (itemAttr.getName().equals("name")){
								cellItemMap.put(itemAttr.getValue(), attrMap);
							} else {
								attrMap.put(itemAttr.getName(), itemAttr.getValue());
							}
						}
						
					}
					leafNodeData.put(xn.getName(),xn.getValue());
				}
			}	
			containerMap.put(attr.getValue(), leafNodeData);
			reportSecttionNmList.add(attr.getValue());
		}
	}
	public Map getReportMap(String reportnm){
		if (this.reportMap == null) {
			return null;
		}
		if (this.reportMap.get(reportnm) == null) {
			return null;
		}
		return (Map)this.reportMap.get(reportnm);
	}
	
	public List getXMLLevelList() {
		return xmlLevelList;
	}
	public String getXMLLevelXPath(int loc) {
		if (loc >= xmlLevelList.size()) {
			return "";
		}
		return (String) xmlLevelList.get(loc);
	}
	public static void main(String args[]) {
		ReadFile reaFile = new ReadFile();
		String xml = reaFile.getFileContent("F:/crmwork/20100308Accout/CRMXML/xmltemp/testCalcute02.xml");
		ReportExcelXmlReader xmlReader = new ReportExcelXmlReader();
		try {
			xmlReader.analysisXML(xml);
			xmlReader.initReportMap();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
