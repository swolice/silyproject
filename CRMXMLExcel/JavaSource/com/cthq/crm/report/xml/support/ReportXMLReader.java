/*
 * Created on 2009-11-13
 *	
 */
package com.cthq.crm.report.xml.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cthq.crm.project.common.xml.imp.XMLAttribute;
import com.cthq.crm.project.common.xml.imp.XMLBaseReader;
import com.cthq.crm.project.common.xml.imp.XMLElement;
import com.cthq.crm.project.common.xml.imp.XMLNode;
import com.cthq.crm.project.common.xml.imp.XMLNodeCollection;
import com.cthq.crm.project.common.xml.imp.XMLNodeCollection.Entry;

/**
 * @author sily
 *
 */
public class ReportXMLReader extends XMLBaseReader {
	
	//private String errorDesc = null;
	
	private static final List xmlLevelList = new ArrayList(5);
	private  Map headMap = new HashMap();
	private  Map bodyMap = new HashMap();
	private  Map footMap = new HashMap();
	private  Map excelPropMap = new HashMap();
	private Map bodyAttrMap = new HashMap();
	static {
		xmlLevelList.add(0, "root");
		xmlLevelList.add(1, "root/head");
		xmlLevelList.add(2, "root/body");
		xmlLevelList.add(3, "root/foot");
		xmlLevelList.add(4, "root/excelprop");
	}
	
	public List getXMLLevelList() {
		return xmlLevelList;
	}
	public void intReportMap(){
		headMap = getXmlLevelItemMap("root/head", 0);
		bodyMap = getXmlLevelItemMap("root/body", 0);
		footMap = getXmlLevelItemMap("root/foot", 0);
		excelPropMap = getXmlLevelItemMap("root/excelprop", 0);
		bodyAttrMap = getXmlLevelAttrMap("root/body", 0);
	}
	public String getHeadData(String _prop) {
		return (String)headMap.get(_prop);
	}
	public String getBodyData(String _prop) {
		return (String)bodyMap.get(_prop);
	}
	public String getFootData(String _prop) {
		return (String)footMap.get(_prop);
	}
	
	public String getexcelPropData(String _prop) {
		return (String)excelPropMap.get(_prop);
	}
	/**
	 * 指定项目元素的定位数据loc
	 * 
	 * @return 指定项目下叶子节点元素集合 如:<CustOrder><EventType>3</EventType>
	 *         </CustOrder> xmlNode.nodeName 元素名称(EventType);xmlNode.nodeValue
	 *         值(3);
	 */
	public Map getXmlLevelItemAttrMap(String xmlLevelItemXPath) {
		Map leafNodeData = new HashMap();
		Iterator iter = super.xmlDomElementIterator(xmlLevelItemXPath);
		if (null == iter) {
			//错误处理
			//return
		} else {
			//遍历叶子节点
			for (; iter.hasNext();) {
				XMLElement xmlElement = (XMLElement) iter.next();
				XMLNodeCollection nodeCollection = xmlElement
						.getElementLeafNodeColl();
				for (int i = 0; i < nodeCollection.getNodesSize(); i++) {
					XMLNode node = nodeCollection.getNodesAttributeMap(i);
					leafNodeData.put(node.getName(), node.getValue());

				}
			}
		}
		return leafNodeData;
	}

	/**
	 * 指定项目元素的叶子节点
	 * @param xmlPath XML层次DOM树的路径
	 * @param loc loc 层次路径中所处的位置 
	 */
	public Map getXmlLevelItemMap(String xmlLevelItemXPath,int loc) {
		Map leafNodeData = new HashMap();
		Iterator iter = super.xmlDomElementLeafIterator(xmlLevelItemXPath,loc);
		if(null != iter){
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				XMLNode xn = entry.xmlNode;
				leafNodeData.put(xn.getName(),xn.getValue());
			}
		}
		return leafNodeData;
	}
	/**
	 * 获取节点的属性
	 * @param xmlLevelItemXPath
	 * @param loc
	 * @return
	 */
	public Map getXmlLevelAttrMap(String xmlLevelItemXPath,int loc) {
		Map leafNodeData = new HashMap();
		Iterator iter = super.xmlDomElementLeafIterator(xmlLevelItemXPath,loc);
		if(null != iter){
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				XMLNode xn = entry.xmlNode;
				if(xn.getAttributes().size()>0){
					List list = xn.getAttributes();
					Map attrMap = new HashMap();
					for (int i = 0; i < list.size(); i++) {
						XMLAttribute xmlattr = (XMLAttribute)list.get(i);
						attrMap.put(xmlattr.getName(),xmlattr.getValue());
					}
					leafNodeData.put(xn.getName(),attrMap);
				}
			}
		}
		return leafNodeData;
	}
	
	public String getXMLLevelXPath(int loc) {
		if (loc >= xmlLevelList.size()) {
			return "";
		}
		return (String) xmlLevelList.get(loc);
	}
	public Map getBodyAttrMap() {
		return bodyAttrMap;
	}
}
