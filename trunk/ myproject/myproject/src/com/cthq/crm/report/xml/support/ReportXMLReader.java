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

import com.cthq.crm.webservice.common.xml.imp.XMLBaseReader;
import com.cthq.crm.webservice.common.xml.imp.XMLElement;
import com.cthq.crm.webservice.common.xml.imp.XMLNode;
import com.cthq.crm.webservice.common.xml.imp.XMLNodeCollection;
import com.cthq.crm.webservice.common.xml.imp.XMLNodeCollection.Entry;

/**
 * @author sily
 *
 */
public class ReportXMLReader extends XMLBaseReader {
	
	//private String errorDesc = null;
	
	private static final List xmlLevelList = new ArrayList(5);
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

	/**
	 * 指定项目元素的定位数据loc
	 * 
	 * @return 指定项目下叶子节点元素集合 如:<CustOrder><EventType>3 </EventType>
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
			return leafNodeData;
		}else{
			return null;
		}
	}
	
	
	
	public String getXMLLevelXPath(int loc) {
		if (loc >= xmlLevelList.size()) {
			return "";
		}
		return (String) xmlLevelList.get(loc);
	}


}
