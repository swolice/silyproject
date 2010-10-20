/*
 * XMLBaseReader.java
 * 创建日期:2008/09/20
 */
package com.cthq.crm.project.common.xml.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cthq.crm.project.common.xml.imp.XMLNodeCollection.Entry;
import com.cthq.crm.project.common.xml.interfaces.IXMLReader;
/**
 * 解析XML数据
 * 用于解析所接收到的XML数据结构的内容
 * 解析的XML内容存放在散列表中
 * 散列表的键值放入XML的层次关系
 * @author 蒋峰
 */
public abstract class XMLBaseReader implements IXMLReader {
	
	//需要解析的XML数据流
	private String strParsedXml = "";
	
	//保存解析的XML的数据的位子
	private Map xmlDataMap = new HashMap();
	
	//XML数据DOM树节点的位置集合
	private Map xmlDomElememtMap = new HashMap();
	
	/**
	 * 设置需要解析的XML数据
	 * @param strXML XML数据
	 */
	public void setParasedXML(String strXML) {
		this.strParsedXml = strXML;
	}
	/**
	 * 获取解析的XML数据
	 * @return XML数据
	 */
	public String getParsedXML() {
		return this.strParsedXml;
	}
	/**
	 * 分析XML数据
	 * @throws Exception
	 */
	public void analysisXML() throws Exception {
	   this.analysisXML(this.strParsedXml);	
	}
	/**
	 * 获取节点元素的结对路径下的唯一的位置
	 * 利用节点元素的HASHCODE来唯一确定该元素所属的实际位置
	 * @param xmlDomElement 节点元素
	 * @return 节点元素的地址路径
	 */
	private List getOwnAbsloutePath(Element xmlDomElement) {
		Element domElement = xmlDomElement;
		StringBuffer hasCodepath = new StringBuffer();
		List parentsList = new ArrayList();
		List returnList = new ArrayList(2);
		List list = null;
		String path = domElement.getPath().toString().replaceFirst("/", "");
		//保存节点的地址路径列表
		if ( null == xmlDataMap.get(path)) {
			list = new ArrayList();
			xmlDataMap.put(path,list);
		} else {
			list = (List) xmlDataMap.get(path);
		}
		while(true) {
			if (domElement.isRootElement()) {
				hasCodepath.insert(0, domElement.hashCode());
				String codePath = String.valueOf(domElement.hashCode());
				if (parentsList.contains(codePath)) {
				} else {
					parentsList.add(codePath);
				}
//				parentsList.add(String.valueOf(domElement.hashCode()));
			} else {
				hasCodepath.insert(0, "/" + domElement.hashCode());
			}
			
			domElement = domElement.getParent();
			if (null == domElement ) {
				break;
			}
			String codePath = String.valueOf(domElement.hashCode());
			if (parentsList.contains(codePath)) {
			} else {
				parentsList.add(codePath);
			}
//			parentsList.add(String.valueOf(domElement.hashCode()));
		}
		list.add(hasCodepath.toString());
		returnList.add(0, hasCodepath.toString());
		returnList.add(1, parentsList);
		return returnList;
	}
	/**
	 * 分析XML数据
	 * @param strXml 需要解析的XML数据
	 * @throws Exception
	 */
	public void analysisXML(String strXml) throws Exception{
		strParsedXml = strXml;
		SAXReader parser = new SAXReader();
		XMLInputStream inputStream = new XMLInputStream();
		xmlDataMap.clear();
		xmlDomElememtMap.clear();
		Document document = parser.read(inputStream.getUTF8Stream(strXml));
		//获取XML数据结构的层次列表
		ArrayList  list = (ArrayList)getXMLLevelList();
		Object[] obj = list.toArray();
		Arrays.sort(obj);
		Iterator iter = list.iterator();
		String currPath = "";
		//层次节点遍历
		while(iter.hasNext()) {
			currPath = (String)iter.next();
			List nodeList = document.selectNodes("//" + currPath);
			if (null == nodeList) {
				continue;
			}
			//获取节点元素的遍历列表
			Iterator nodeIterator = nodeList.iterator();
			int size = nodeList.size();
			List elmentList  = new ArrayList(size);
			//
			while(nodeIterator.hasNext()) {
				Element xmlDomElement = (Element) nodeIterator.next();
				List returnList =  getOwnAbsloutePath(xmlDomElement);
				List parentsList = (List)returnList.get(1);
				//创建节点的元素项目的集合
				XMLElement xmlElement = new XMLElement();
				xmlElement.setElementName(xmlDomElement.getName());
				xmlElement.setElementValue(xmlDomElement.getText());
				//设置节点元素的所属父节点的地址唯一标识
				if (xmlDomElement.getParent() == null) {
					xmlElement.setOwnParentLoc(xmlDomElement.hashCode());
				} else {
					xmlElement.setOwnParentLoc(xmlDomElement.getParent().hashCode());
				}
				
				//设置节点自身的位置
				xmlElement.setSelfLoc(xmlDomElement.hashCode());
				//设置节点到父节点的根节点的位置列表
				xmlElement.setXmlLevelParentS(parentsList);
				if (xmlDomElement.attributeCount() > 0) {
					//创建节点元素的属性
					Iterator attrIter = xmlDomElement.attributeIterator();
					for(; attrIter.hasNext();) {
						Attribute attr = (Attribute) attrIter.next();
						xmlElement.addAttribute(attr.getName(), attr.getValue());
					}
				}
				//创建节点的叶子节点的集合
				XMLNodeCollection xmlNodeColl = new XMLNodeCollection();
				Iterator iter12 = xmlDomElement.elementIterator();
				for ( ; iter12.hasNext(); ) {
					Element nodeDomElement = (Element)iter12.next();
					String kk = nodeDomElement.getPath();
					kk = kk.substring(1);
					if (Arrays.binarySearch(obj,kk) > 0) {
						continue;
					}
					//设置叶子节点
					xmlNodeColl.AddNode();
					xmlNodeColl.putNodeProperty(nodeDomElement.getName(),nodeDomElement.getText());
					//设置叶子节点的属性结合
					if (nodeDomElement.attributeCount() > 0) {
						Iterator attrIter = nodeDomElement.attributeIterator();
						for(; attrIter.hasNext();) {
							Attribute attr = (Attribute) attrIter.next();
							xmlNodeColl.putAttribute(attr.getName(), attr.getValue());
						}
					}
				}
				//设置节点元素的叶子节点
				if (xmlNodeColl.getNodesSize() > 0) {
					xmlElement.setElementNodeCollection(xmlNodeColl);
				}
				
				if (xmlElement.attributeCount()> 0 || xmlNodeColl.getNodesSize() > 0) {
					String keyHashCode = (String)returnList.get(0);
					xmlElement.setDomTreeAddress(keyHashCode);
					xmlDomElememtMap.put(keyHashCode, xmlElement);
//					xmlDomElememtMap.put(String.valueOf(xmlDomElement.hashCode()),xmlElement);
					elmentList.add(xmlElement);
				}
			}
		}
	}
	/**
	 * 获取某个指定XML层次路径下DOM树的元素节点的个数
	 * @param xmlPath XML层次DOM树的路径
	 * @return 节点的个数
	 */
	public int xmlDomElementCount(String xmlPath){
		if (null == this.xmlDataMap) {
			return 0;
		}
		if ( null == this.xmlDataMap.get(xmlPath)) {
			return 0;
		}
		if (this.xmlDataMap.get(xmlPath) instanceof List) {
			List list =  (List)this.xmlDataMap.get(xmlPath);
			return list.size();	
		}
		return 0;
	}
	/**
	 * 
	 * @param path
	 * @return
	 */
	private String getLastNodeAddress(String path){
//		String[] addressPath = path.split("/");
//		int len = addressPath.length;
//		if (len > 0) {
//			return addressPath[len-1];
//		}
		return path;
	}
	/**
	 * 获取某个指定XML层次路径下的DOM数中指定元素项目中属性的个数
	 * @param xmlPath XML层次DOM树的路径
	 * @param loc 层次路径中所处的位置
	 * @return 某个节点项目中属性的个数
	 */
	public int xmlDomElementAttrCount(String xmlPath, int loc) {
		if (null == this.xmlDataMap) {
			return 0;
		}
		if ( null == this.xmlDataMap.get(xmlPath)) {
			return 0;
		}
		if (this.xmlDataMap.get(xmlPath) instanceof List) {
			List list =  (List)this.xmlDataMap.get(xmlPath);
			if (loc >= list.size()) {
				return 0;
			}
			String xPath= (String)list.get(loc);
			String nodeAddress = getLastNodeAddress(xPath);
			if (null == xmlDomElememtMap.get(nodeAddress)) {
				return 0;
			}
			XMLElement xmlElement = (XMLElement)xmlDomElememtMap.get(nodeAddress);
			return xmlElement.attributeCount();
		}
		return 0;
	}
	/**
	 * 获取某个指定XML层次路径下的DOM数中指定元素项目中叶子节点的个数
	 * @param xmlPath XML层次DOM树的路径
	 * @param loc 层次路径中所处的位置
	 * @return 某个节点项目中叶子节点的个数
	 */
	public int xmlDomElementLeafCount(String xmlPath, int loc) {
		if (null == this.xmlDataMap) {
			return 0;
		}
		if ( null == this.xmlDataMap.get(xmlPath)) {
			return 0;
		}
		if (this.xmlDataMap.get(xmlPath) instanceof List) {
			List list =  (List)this.xmlDataMap.get(xmlPath);
			if (loc >= list.size()) {
				return 0;
			}
			String xPath= (String)list.get(loc);
			String nodeAddress = getLastNodeAddress(xPath);
			if (null == xmlDomElememtMap.get(nodeAddress)) {
				return 0;
			}
			XMLElement xmlElement = (XMLElement)xmlDomElememtMap.get(nodeAddress);
			return xmlElement.getLeadNodeCount();
		}
		return 0;		
	}
	/**
	 * 获取某个指定XML层次路径下的DOM数中指定元素项目中叶子节点的遍历起点
	 * 如果节点元素不存在等时返回NULL
	 * 使用时需要进行NULL的判断
	 * @param xmlPath XML层次DOM树的路径
	 * @param loc loc 层次路径中所处的位置 
	 * @return 返回节点所属叶子节点的遍历起点
	 */
	public Iterator xmlDomElementLeafIterator(String xmlPath, int loc) {
		if (null == this.xmlDataMap) {
			return null;
		}
		if ( null == this.xmlDataMap.get(xmlPath)) {
			return null;
		}
		if (this.xmlDataMap.get(xmlPath) instanceof List) {
			List list =  (List)this.xmlDataMap.get(xmlPath);
			if (loc >= list.size()) {
				return null;
			}
			String xPath= (String)list.get(loc);
			String nodeAddress = getLastNodeAddress(xPath);
			if (null == xmlDomElememtMap.get(nodeAddress)) {
				return null;
			}
			XMLElement xmlElement = (XMLElement)xmlDomElememtMap.get(nodeAddress);
			return xmlElement.leafNodeIterator();
		}
		return null;
	}
	
	/**
	 * 获取某个指定XML层次路径下的DOM数中指定元素项目中节点属性的遍历起点
	 * 如果节点元素不存在等时返回NULL
	 * 使用时需要进行NULL的判断
	 * @param xmlPath XML层次DOM树的路径
	 * @param loc 层次路径中所处的位置 
	 * @return 返回节点属性的遍历起点
	 */
	public Iterator xmlDomElementAttrIterator(String xmlPath, int loc) {
		if (null == this.xmlDataMap) {
			return null;
		}
		if ( null == this.xmlDataMap.get(xmlPath)) {
			return null;
		}
		if (this.xmlDataMap.get(xmlPath) instanceof List) {
			List list =  (List)this.xmlDataMap.get(xmlPath);
			if (loc >= list.size()) {
				return null;
			}
			String xPath= (String)list.get(loc);
			String nodeAddress = getLastNodeAddress(xPath);
			if (null == xmlDomElememtMap.get(nodeAddress)) {
				return null;
			}
			XMLElement xmlElement = (XMLElement)xmlDomElememtMap.get(nodeAddress);
			return xmlElement.attributeIterator();
		}
		return null;		
	}
	/**
	 * 获取某个指定XML层次路径下的DOM数中指定元素项目中节点属性的遍历起点
	 * 如果节点元素不存在等时返回NULL
	 * 使用时需要进行NULL的判断
	 * @param xmlPath XML层次DOM树的路径
	 * @return 返回节点属性的遍历起点
	 */
	public Iterator xmlDomElementIterator(String xmlPath) {
		if (null == this.xmlDataMap) {
			return null;
		}
		if ( null == this.xmlDataMap.get(xmlPath)) {
			return null;
		}
		if (this.xmlDataMap.get(xmlPath) instanceof List) {
			List list =  (List)this.xmlDataMap.get(xmlPath);
			List elementList = new ArrayList();
			Iterator iterator = list.iterator();
			while(iterator.hasNext()) {
				String xpath = (String)iterator.next();
				String nodeAddress = getLastNodeAddress(xpath);
				if (null != xmlDomElememtMap.get(nodeAddress)) {
					elementList.add(xmlDomElememtMap.get(nodeAddress));
				}
			}
			return elementList.iterator();
		}
		return null;		
	}
	/**
	 * 获取某个指定XML层次路径下的DOM数中指定元素项目中节点元素
	 * 如果节点元素不存在等时返回NULL
	 * 使用时需要进行NULL的判断 
	 * @param xmlPath XML层次DOM树的路径
	 * @param loc 层次路径中所处的位置
	 * @return 指定层次路径的节点元素
	 */
	public XMLElement xmlDomElement(String xmlPath, int loc){
		if (null == this.xmlDataMap) {
			return null;
		}
		if ( null == this.xmlDataMap.get(xmlPath)) {
			return null;
		}
		if (this.xmlDataMap.get(xmlPath) instanceof List) {
			List list =  (List)this.xmlDataMap.get(xmlPath);
			if (loc >= list.size()) {
				return null;
			}
			String xPath= (String)list.get(loc);
			String nodeAddress = getLastNodeAddress(xPath);
			if (null == xmlDomElememtMap.get(nodeAddress)) {
				return null;
			}
			XMLElement xmlElement = (XMLElement)xmlDomElememtMap.get(nodeAddress);			
			return xmlElement;
		}
		return null;
	}
	/**
	 * 获取某个节点到根节点所属的节点列表
	 * 列表中的元素排列位置
	 * 本节点 - 》 父节点 -》 父节点 -》...-》根节点
	 * @param xmlElement
	 * @return
	 */
	public List getParentElementList(XMLElement xmlElement) {
		List parentList  = new ArrayList();
		List list = xmlElement.getXmlParentsList();
		if (null == list) {
			return parentList;
		}
		Iterator iterator = list.iterator();
		while(iterator.hasNext()) {
			String key = (String) iterator.next();
			if (null != xmlDomElememtMap.get(key)) {
				parentList.add(xmlDomElememtMap.get(key));
			} else {
				XMLElement xmlElement1 = new XMLElement();
				xmlElement.setSelfLoc(Integer.parseInt(key));
				parentList.add(xmlElement1);
			}
		}
		return parentList;
	}
	/**
	 * 获取XML数据中的节点DOM树的路径列表
	 * 列表中对路径已经排序
	 * @return
	 */
	public List getXmlDomTreePath() {
		Set keySet = this.xmlDataMap.keySet();
		Iterator iterator = keySet.iterator();
		List list = new ArrayList();
		while(iterator.hasNext()) {
			list.add(iterator.next());
		}
		Object[] obj = list.toArray();
		Arrays.sort(obj);
		return Arrays.asList(obj);
	}
	/**
	 * 根据xml数据结构树中的节点位子获取元素
	 * @param xmlDomNodeAddress 节点元素的位置地址号
	 */
	public XMLElement getXmlElement(String xmlDomNodeAddress) {
		XMLElement xmlElement = null;
		if (null != xmlDomElememtMap.get(xmlDomNodeAddress)) {
			if (xmlDomElememtMap.get(xmlDomNodeAddress) instanceof XMLElement) {
				xmlElement = (XMLElement) xmlDomElememtMap.get(xmlDomNodeAddress);
			}
		}
		return xmlElement;
	}
	/**
	 * 获取指定父节点中，同属于该父节点的子节点的序列位置
	 * @param parentsPath 父节点的XML层次路径
	 * @param childPath 改父节点所属的子节点的路径
	 * @param intParentLoc 父节点所在的位置
	 * @return 返回所属父节点的子节点的位置序列 数组 子节点为空 返回的节点序列数组为空
	 */
	public int[] getOneParentsElement(String parentsPath, String childPath, int intParentLoc){
		int[] intChildLocArr = new int[0];
		if (null == xmlDataMap) {
			return intChildLocArr;
		}
		if (!(xmlDataMap.get(parentsPath) instanceof List)) {
			return intChildLocArr;
		}
		List parentsList = (List) xmlDataMap.get(parentsPath);
		String parentsPathAdd = (String)parentsList.get(intParentLoc);
		if (!(xmlDataMap.get(childPath) instanceof List)) {
			return intChildLocArr;
		}
		List childsList = (List) xmlDataMap.get(childPath);
		Iterator iterator = childsList.iterator();
		int loc = -1;
		List childLocList = new ArrayList();
		while(iterator.hasNext()) {
			String address = (String)iterator.next();
			++loc;
			if (address.indexOf(parentsPathAdd) >= 0 ) {
				childLocList.add(String.valueOf(loc));
			}
		}
		if (childLocList.isEmpty()) {
			return intChildLocArr;
		}
		intChildLocArr = new int[childLocList.size()];
		for(int i = 0; i < childLocList.size(); i++) {
			intChildLocArr[i] = Integer.parseInt((String)childLocList.get(i));
		}
		return intChildLocArr;
	}
	/**
	 * 释放内存空间
	 */
	public void dispose() {
		
		if (null != xmlDataMap) {
			xmlDataMap.clear();
			xmlDataMap = null;
		}
		
		if (null != this.xmlDomElememtMap ) {
//			Iterator iter = xmlDomElememtMap.entrySet().iterator();
			Collection set = xmlDomElememtMap.values();
			Iterator iter = set.iterator();
			for(;iter.hasNext();) {
				XMLElement elem = (XMLElement)iter.next();
				elem.dispose();
				elem = null;
			}
			this.xmlDomElememtMap.clear();
			this.xmlDomElememtMap = null;
		}
	}
	/**
	 * 获取某个指定XML层次路径下的DOM数中指定元素项目中叶子节点对应的数据
	 * 如果节点元素不存在等时返回NULL
	 * 使用时需要进行NULL的判断
	 * @param xmlPath   XML层次DOM树的路径
	 * @param leafNodeName 页子节点名称
	 * @param loc  层次路径中所处的位置 
	 * @return 叶子节点数据
	 */
	public String getXmlDomElementLeafNodeValue(String xmlPath, String leafNodeName, int loc) {
		if (null == this.xmlDataMap) {
			return null;
		}
		if ( null == this.xmlDataMap.get(xmlPath)) {
			return null;
		}
		if (this.xmlDataMap.get(xmlPath) instanceof List) {
			List list =  (List)this.xmlDataMap.get(xmlPath);
			if (loc >= list.size()) {
				return null;
			}
			String xPath= (String)list.get(loc);
			String nodeAddress = getLastNodeAddress(xPath);
			if (null == xmlDomElememtMap.get(nodeAddress)) {
				return null;
			}
			XMLElement xmlElement = (XMLElement)xmlDomElememtMap.get(nodeAddress);
			Iterator iterator = xmlElement.leafNodeIterator();
			if (null != iterator) {
				while(iterator.hasNext()) {
					Entry entry = (Entry) iterator.next();
					if (entry.xmlNode.getName().equals(leafNodeName)) {
						return entry.xmlNode.getValue();
					}
				}
			}
			
		}
		return null;
	}
	/*
	 * 
	 */
	public Iterator xmlDomElementLeafNodeAttr(String xmlPath, String leafNodeName, int loc) {
		if (null == this.xmlDataMap) {
			return null;
		}
		if ( null == this.xmlDataMap.get(xmlPath)) {
			return null;
		}
		if (this.xmlDataMap.get(xmlPath) instanceof List) {
			List list =  (List)this.xmlDataMap.get(xmlPath);
			if (loc >= list.size()) {
				return null;
			}
			String xPath= (String)list.get(loc);
			String nodeAddress = getLastNodeAddress(xPath);
			if (null == xmlDomElememtMap.get(nodeAddress)) {
				return null;
			}
			XMLElement xmlElement = (XMLElement)xmlDomElememtMap.get(nodeAddress);
			Iterator iterator = xmlElement.leafNodeIterator();
			if (null != iterator) {
				while(iterator.hasNext()) {
					Entry entry = (Entry) iterator.next();
					if (entry.xmlNode.getName().equals(leafNodeName)) {
						return entry.xmlNode.getAttributes().listIterator();
					}
				}
			}
		}
		return null;		
	}


}	