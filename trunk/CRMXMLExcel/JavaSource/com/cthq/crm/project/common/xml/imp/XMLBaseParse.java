/*
 * XMLBaseParse.java
 * 创建日期：2008/09/20
 */
package com.cthq.crm.project.common.xml.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cthq.crm.project.common.xml.imp.XMLNodeCollection.Entry;
import com.cthq.crm.project.common.xml.interfaces.IXMLAttribute;
import com.cthq.crm.project.common.xml.interfaces.IXMLParser;
import com.cthq.crm.project.common.xml.template.engine.XmlTemplateContainer;
import com.cthq.crm.project.common.xml.template.engine.XmlTemplateEngine;

/**
 * 生成XML数据
 * 根据用户设置的数据创建XML数据结构文档字符流
 * 处理数据的方式将XML数据中的叶子节点和XML中元素项目的属性数据
 * 抽象为XML中的数据块 根据不同的数据块产生相应的XML层次结构的数据
 * @author 蒋峰
 */
public abstract class XMLBaseParse implements IXMLParser {
	
	//XML数据结构中没有子节点的节点标识 该节点拥有叶子节点
	public static final int XML_LEAF_LEVEL = 2;
	
	//XML数据结构中第二层次节点的拥有子节点的
	public static final int XML_PARENT_LEVEL = 1;
	
	//XML数据结构中第一层次的节点标识
	public static final int XML_FIRST_LEVEL = 1;
	
	//节点数据的属性和叶子节点的数据块的集合
	private Map nodeBlockDataMap = new HashMap();
	
	//每个层次节点是否是父节点还是叶子节点的集合 1-层次是父节点 2-叶子节点
	private Map nodeAttributeMap = new HashMap(); 
	
	//记录XML文档结构树中无效的节点
	private List invalidXMLDomElementList = new ArrayList();
	
	//XML文档
	private Document xmkDomDocument = null;
	
	//XML数据文件内容
	private String strXMLContent = null;
	
	//XML数据结构模板内容容器
	private XmlTemplateContainer xmlTemplateContainer = null;
	
	//XML数据模板解析引擎
	private XmlTemplateEngine xmlTempEngine = null;
	/**
	 * 
	 * @param _attrMap
	 */
	public void setNodeAttributeMap(Map _attrMap) {
		this.nodeAttributeMap = _attrMap;
	}
	/**
	 * 
	 * @param _dataMap
	 */
	public void setNodeBlockDataMap(Map _dataMap) {
		this.nodeBlockDataMap = _dataMap;
	}
	/**
	 * 
	 * @param xpath
	 * @return
	 */
	public int getElementNodeCount(String xpath) {
		if (null == nodeAttributeMap.get(xpath)) {
			return -1;
		}
		if (nodeAttributeMap.get(xpath) instanceof List) {
			 List list  =  (List)nodeAttributeMap.get(xpath);
			 if (null == list) {
			 	return -1;
			 }
			 return list.size();
		}
		return -1;
	}
	
	/**
	 * 创建XML数据结构的节点数据和叶子节点的数据
	 * 节点数据集合中包含叶子节点和节点数据的属性
	 * 叶子节点的数据和叶子节点的属性
	 * @param belogPath 节点数据所在的XML层次路径
	 * @param list 节点数据结合或叶子节点数据集合
	 * @param intLevelFlg 数据块节点在XML结构数据中的层次标识
	 */
	public void createNodeData(String belogPath, List list, int intLevelFlg) {
		if (null == list) {
			return;
		}
		nodeAttributeMap.put("//" + belogPath, String.valueOf(intLevelFlg));
		nodeBlockDataMap.put("//" + belogPath, list);
	}
	/**
	 * 
	 * @param xpath
	 * @return
	 */
	private boolean isExistLeaf(String xpath) {
		Set keySet = nodeAttributeMap.keySet();
		Iterator iterator = keySet.iterator();
		while(iterator.hasNext()) {
			String key = (String)iterator.next();
			if (key.indexOf(xpath)>=0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 创建XML内容的基本的节点骨架
	 * @param document dom4j的字符文档流
	 * @param xmlLevelList XML节点的层次关系列表
	 */
	private void createXMLNodeSkeleton(Document document, List xmlLevelList){
		Iterator iter = xmlLevelList.listIterator();
	    for(;iter.hasNext();) {
	    	String xmlLevelElementNm = (String)iter.next();
	    	//XML节点层次分解
	    	StringTokenizer token = new StringTokenizer(xmlLevelElementNm, "/");
	    	String currentPath = "";
	    	String parentsPath = "";
	    	int kk1 =0;
	    	//XML节点层次遍历
	    	while(token.hasMoreTokens()){
	    		//XML节点层次名称
	    		String elementNm = token.nextToken();
	    		//根节点
	    		if ("".equals(currentPath)) {
	    			currentPath = "//" +elementNm;
	    			//如果XML文档流中没有一个叶子节点存在，侧创建根节点
	    			if (document.nodeCount() == 0) {
	    				document.addElement(elementNm);
	    				continue;
	    			}
	    			//创建根节点
	    			List list21 = (List)document.selectNodes(elementNm);
	    			if (null == list21 || list21.isEmpty()) {
	    				document.addElement(elementNm);
	    			}
	    			continue;
	    		}
	    		//父节点
	    		parentsPath = currentPath;
	    		//当前节点的层次
	    		currentPath = currentPath + "/" + elementNm;
	    		//获取当前层次节点的所属的节点一览序列
				List currentNodeList = (List)document.selectNodes(currentPath);
				//节点元素父节点的位置
	    		int loc = -1;
	    		//叶子节点的所属父节点的位置
	    		int leafLoc = -1;
				//判断当前层次节点所属节点是否为空
				if (null == currentNodeList || currentNodeList.isEmpty()) {
	    			//父节点层次的所属节点的一览序列
					List parentsNodeList = (List)document.selectNodes(parentsPath);
	    		    Iterator parNodeIter = parentsNodeList.listIterator();  
	    			//遍历父节点的所属节点
	    		    for(;parNodeIter.hasNext();) {
	    				Element parentsElement = (Element)parNodeIter.next();
    					//获取当前节点的所属节点的一览
	    				//List curPathLeafNodesList = (List) nodeBlockDataMap.get(currentPath);
	    				//获取节点元素的是否拥有叶子节点的判断
	    				String leafNodeFlg = (String) nodeAttributeMap.get(currentPath);
	    				//当前节点没有叶子节点
	    				if (null == leafNodeFlg) {
	    					if (isExistLeaf(currentPath)) {
	    						parentsElement.addElement(elementNm);
	    					}
    					 	continue;
    					}
	    				int intFlg = Integer.parseInt(leafNodeFlg);
	    				
//	    				if (XML_LEAF_LEVEL == intFlg) {
//	    					++leafLoc;
//	    					int size = getOneBranchNodeCount(currentPath, leafLoc); 
//	    					if (size >0 ) {
//	    						intFlg = XML_PARENT_LEVEL;
//	    					}
//	    					--leafLoc;
//	    				}
	    		
	    				//某个父节点下有多个同属一个子节点的数据集合
	    				// 例如： 在层次节点中有A层次路径 在A层次中有所属B层次路径 在某一个A层次路径中B路径存在多个	    				
	    				if (XML_PARENT_LEVEL == intFlg || XML_FIRST_LEVEL == intFlg) {
	    					//当前父节点的序号													
							++loc;							
							int size = getOneBranchNodeCount(currentPath, loc); 
							ArrayList xmlDomEleList = new ArrayList();
							for(int i = 0; i < size ; i++) {
								xmlDomEleList.add(parentsElement.addElement(elementNm));
					 		}
							if (size > 0) {
								//创建节点元素
								createNodeElementOneBranch(currentPath, loc, xmlDomEleList);	
							}
							xmlDomEleList.clear();
							continue;
	    				}
	    				
	    				//在某个父节点中存没有子节点仅仅有叶子节点	
	    				if (XML_LEAF_LEVEL == intFlg) {
	    					//当前父节点的序号
	    					++leafLoc;
	    					int size = getOneBranchNodeCount(currentPath, leafLoc); 
	    					if (size > 0) {
	    						ArrayList xmlDomEleList = new ArrayList();
								for(int i = 0; i < size ; i++) {
									xmlDomEleList.add(parentsElement.addElement(elementNm));
						 		}
								if (size > 0) {
									//创建节点元素
									createNodeElementOneBranch(currentPath, leafLoc, xmlDomEleList);	
								}
								xmlDomEleList.clear();
	    					} else {
	    						Element xmlDomNodeElement = parentsElement.addElement(elementNm);
		    					//创建叶子节点元素
		    					createNodeElementTwoBranch(xmlDomNodeElement, currentPath, leafLoc);
	    					}
	    					
	    				}
	    				
	    			}	 
	    		}     			
	    	}	
	    }
	}
	/**
	 * 获取指定位置的节点元素
	 * @param key XML数据结构的层次路径
	 * @param parentsLoc 子节点所属父子节点的位置
	 * @return 节点元素
	 */
	private XMLElement getTwoBranchNodeElement(String key, int parentsLoc) {
		int count = -1;
		if (nodeBlockDataMap.get(key) == null) {
			return null;
		}
		if (!(nodeBlockDataMap.get(key) instanceof List)) {
			return null;
		}
		//XML结构层次中节点的数据集合
		ArrayList nodeElementList =  (ArrayList) nodeBlockDataMap.get(key);
		if (nodeElementList == null) {
			return null;
		}
		//遍历查询出当前层次节点集合中所属父节点的数据
		for(int i = 0; i< nodeElementList.size(); i++) {
			XMLElement xmlElement = (XMLElement) nodeElementList.get(i);
			if ( parentsLoc == xmlElement.getOwnParentLoc()) {
				xmlElement.setOwnParentLoc(-1); 
				return xmlElement;
			}
		}		
		return null;
		
	}
	/**
	 * 创建叶子节点分支的处理
	 * @param document XML文档
	 * @param currXmlPath XML数据结构层次路径
	 * @param leafLoc 叶子节点在XML层次结构中的父节点位子
	 */
	private void createNodeElementTwoBranch(Element xmlDomNodeElement, String currXmlPath, int leafLoc) {
		if (nodeBlockDataMap.get(currXmlPath) == null) {
			return;
		}
		if (!(nodeBlockDataMap.get(currXmlPath) instanceof List)) {
			return;
		}
		//获取XML层次结构中的数据集合
		ArrayList nodeElementList =  (ArrayList) nodeBlockDataMap.get(currXmlPath);
		if (null == nodeElementList || nodeElementList.isEmpty()) {
			invalidXMLDomElementList.add(xmlDomNodeElement);
			return;
		}
		//根据父节点路径下中当前节点的层次currXmlPath所属的约束集合
		XMLElement xmlElement = getTwoBranchNodeElement(currXmlPath, leafLoc);
		//如果父节点中获取当前子节点的数据集合为空则需要删除相应的子节点
		if (null == xmlElement) {
			invalidXMLDomElementList.add(xmlDomNodeElement);
			return;
		}
		
		XMLNodeCollection xmlNodeColl = xmlElement.getElementLeafNodeColl() ;
	
		//节点属性一览
		List attrList = xmlElement.getAttributes();
		//节点属性的组织
		if (null == attrList || attrList.isEmpty()) {
			
		} else {
			//创建该节点的属性集合
			Iterator attrIter = attrList.iterator();
			while(attrIter.hasNext()) {
				IXMLAttribute xmlAttr = (IXMLAttribute) attrIter.next();
				xmlDomNodeElement.addAttribute(xmlAttr.getName(),xmlAttr.getValue());
			}
		}
		//创建该节点所属的叶子节点集合
		if (null == xmlNodeColl || xmlNodeColl.isEmpty()) {
			
		} else {
			createLeafNode(xmlDomNodeElement, xmlNodeColl);
		}
		xmlElement.dispose();
		
	}
	/**
	 * 获取在某个父节点下当前子节点相应个数
	 * @param currXMLPath   XML数据结构层次路径
	 * @param parentsLoc 父子节点的位置标识
	 * @return 个数
	 */
	private int getOneBranchNodeCount(String currXMLPath, int parentsLoc) {
		int count = 0;
		if (nodeBlockDataMap.get(currXMLPath) == null) {
			return 0;
		}
		if (!(nodeBlockDataMap.get(currXMLPath) instanceof List)) {
			return 0;
		}
		//
		ArrayList nodeElementList =  (ArrayList) nodeBlockDataMap.get(currXMLPath);
		for(int i=0; i< nodeElementList.size(); i++) {
			XMLElement xmlElement = (XMLElement) nodeElementList.get(i);
			if ( parentsLoc == xmlElement.getOwnParentLoc()) {
				++count;
			}
		}		
		return count;
	}	
	/**
	 * 获取父节点中所拥有的当前子节点数据集合
	 * @param currXMLPath 当前子节点的XML数据结构的层次路径
	 * @param flg 父子节点的位置
	 * @return 节点元素
	 */
	private XMLElement getOneBranchNodeElement(String currXMLPath, int parentsLoc) {
		int count = -1;
		if (nodeBlockDataMap.get(currXMLPath) == null) {
			return null;
		}
		if (!(nodeBlockDataMap.get(currXMLPath) instanceof List)) {
			return null;
		}
		//遍历查询出归属于某个父节点的子节点的集合。
		//A/B/C
		ArrayList nodeElementList =  (ArrayList) nodeBlockDataMap.get(currXMLPath);
		for(int i=0; i< nodeElementList.size(); i++) {
			XMLElement xmlElement = (XMLElement) nodeElementList.get(i);
			if ( parentsLoc == xmlElement.getOwnParentLoc()) {
				xmlElement.setOwnParentLoc(-1); 
				return xmlElement;
			}
		}		
		return null;
		
	}
	/**
	 * 创建节点的元素和所属叶子节点的属性
	 * @param currXMLPath XML数据结构的层次路径
	 * @param loc 父子节点的位置
	 * @param xmlDomNodeLists 父子节点的列表
	 */
	private void createNodeElementOneBranch(String currXMLPath, int loc,List xmlDomNodeLists) {
		if (nodeBlockDataMap.get(currXMLPath) == null) {
			return;
		}
		if (!(nodeBlockDataMap.get(currXMLPath) instanceof List)) {
			return;
		}		
		ArrayList nodeElementList =  (ArrayList) nodeBlockDataMap.get(currXMLPath);
		if (null == nodeElementList) {
			return;
		}
		Iterator xnlDomNodeIter = xmlDomNodeLists.iterator();
		while(xnlDomNodeIter.hasNext()) {
			//获取节点元素
			Element xmlDomNodeElement  = (Element)xnlDomNodeIter.next();
			
			XMLElement xmlElement = getOneBranchNodeElement(currXMLPath, loc);
			if (null == xmlElement) {
				invalidXMLDomElementList.add(xmlDomNodeElement);
				continue;
			}
			
			List attrList = xmlElement.getAttributes();
			//节点属性的创建
			if (null == attrList || attrList.isEmpty()) {
				
			} else {
				Iterator attrIter = attrList.iterator();
				while(attrIter.hasNext()) {
					IXMLAttribute xmlAttr = (IXMLAttribute) attrIter.next();
					xmlDomNodeElement.addAttribute(xmlAttr.getName(),xmlAttr.getValue());
				}
			}
			//该节点的叶子节点的集合同时创建叶子节点字符流
			XMLNodeCollection xmlNodeColl = xmlElement.getElementLeafNodeColl();
			if (null == xmlNodeColl || xmlNodeColl.isEmpty()) {
				
			} else {
				createLeafNode(xmlDomNodeElement, xmlNodeColl);
			}
			xmlElement.dispose();
			nodeElementList.remove(xmlElement);
		}
	}
	/**
	 * 创建叶子节点的XML文档
	 * @param xmlDomNodeElement 叶子节点所属的父节点元素
	 * @param xmlNodeColl 叶子节点的集合
	 */
	private void createLeafNode(Element xmlDomNodeElement, XMLNodeCollection xmlNodeColl) {
		Iterator xmlNodeIter = xmlNodeColl.ListIterator();
		if (null == xmlNodeIter) {
			return;
		}
		while(xmlNodeIter.hasNext()) {
			Entry entry = (Entry) xmlNodeIter.next();
			XMLNode xmlNode = entry.xmlNode;
			//System.out.println(xmlNode.getName());
			Element leafNodeDomElement =  xmlDomNodeElement.addElement(xmlNode.getName());
			leafNodeDomElement.setText(xmlNode.getValue());
			Iterator leafAttrIter = xmlNode.listIterator();
			if (null == leafAttrIter) {
				continue;
			} else{
				while(leafAttrIter.hasNext()) {
					IXMLAttribute nodeAttr = (IXMLAttribute)leafAttrIter.next();
					leafNodeDomElement.addAttribute(nodeAttr.getName(), nodeAttr.getValue());
				}
			}
			xmlNode.dispose();
		}
		xmlNodeColl.dispose();
	}
	/**
	 * 删除XML中结构树中没有用的节点叶子节点和所属节点
	 * @param document XML文档
	 */
	private void crearInvalidDomElement(Document document ) {
		
		Iterator iterator = this.invalidXMLDomElementList.iterator();
		for(;iterator.hasNext();){
			Element xmlDomNodeElement = (Element)iterator.next();
			if (null == xmlDomNodeElement) {
				continue;
			}
			if (null == xmlDomNodeElement.getParent()) {
				continue;
			}
			xmlDomNodeElement.getParent().remove(xmlDomNodeElement);
			
		}
		
	}
	
	/**
	 * 根据XPATH路径获取相应的节点元素集合
	 * @param belogPath XML XPATH路径层次
	 * @return 节点数据集合
	 */
	public List getNodeElementList(String belogPath) {
		if (null == nodeBlockDataMap) {
			return null;
		}
		if (nodeBlockDataMap.get(belogPath) instanceof List) {
			return (List) nodeBlockDataMap.get(belogPath);
		}
		return null;
	}
	
	/**
	 * 获取XML数据结构文档字符流
	 * @return XML数据字符流
	 */
	public String getXMLContent() {
		if (null != strXMLContent && !strXMLContent.equals("")) {
			return strXMLContent;
		}
		Document document = DocumentHelper.createDocument( );
	    List list = getXMLLevelList();
	    if (null == list || list.isEmpty()) {
	    	return null;
	    }
	    createXMLNodeSkeleton(document, list);
	    crearInvalidDomElement(document);
	    xmkDomDocument = document;
	    strXMLContent = document.asXML();
	   
	    return strXMLContent;
	} 
	
	/**
	 * 获取XML数据结构的文档字符XML串流
	 * @param _encoding XML文档中的字符集编码 GBk GB2312 UTF-8
	 * @return
	 */
	public String getXMLContent(String _encoding) {
		Document document = DocumentHelper.createDocument( );
	    List list = getXMLLevelList();
	    if (null == list || list.isEmpty()) {
	    	return null;
	    }
	    document.setXMLEncoding(_encoding);
	    createXMLNodeSkeleton(document, list);
	    crearInvalidDomElement(document);
	    xmkDomDocument = document;
	    strXMLContent = document.asXML();
	    return strXMLContent;		
	}
	/**
	 * 清空当前XML的XML数据方式
	 */
	public void clearXMLContent()  {
		strXMLContent = null;
	}
	/**
	 * 设置需要发送的XML数据
	 * @param _xmlContent
	 */
	public void setXMLContent(String _xmlContent) {
		strXMLContent = _xmlContent;
	}
	/**
	 * 获取XML文档结构
	 * @return
	 */
	public Document getDocument() {
		return xmkDomDocument;
	}
	/**
	 * 获取相应的XML文档的XML数据内容
	 * @param xPath 指定的路径
	 * @param srcLoc 在XML串中的所在层次位置
	 * @return
	 */
	public String getXMLContent(String xPath, int srcLoc) {
		if (null == xmkDomDocument) {
			return "";
		}
		try {
			//元节点的一览
			List srcDocList = xmkDomDocument.selectNodes(xPath);
			//元节点的元素
			Element xmlSrcDomElement = (Element) srcDocList.get(srcLoc);
			xmlSrcDomElement.setDocument(xmkDomDocument);
			return xmlSrcDomElement.asXML();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	/**
	 * 获取相应的XML文档的XML数据内容 一个完整的XML字串
	 * @param xPath 指定的路径
	 * @param srcLoc 在XML串中的所在层次位置
	 * @return
	 */
	public String getSpecCompleteXmlContent(String xPath, int srcLoc) {
		getXMLContent();
		if (null == xmkDomDocument) {
			return "";
		}
		try {
			
			//元节点的一览
			List srcDocList = xmkDomDocument.selectNodes(xPath);
			//元节点的元素
			Element xmlSrcDomElement = (Element) srcDocList.get(srcLoc);
			xmlSrcDomElement.setDocument(xmkDomDocument);
			Document document = DocumentHelper.createDocument();
			return document.asXML() + xmlSrcDomElement.asXML();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "";
		
	}
	/**
	 * 创建XML层次数据
	 * @param _param XML数据模板内容集合
	 */
	public void createXMLLevelData(XmlTemplateContainer _param) {
		XmlTemplateEngine xmlTempEngine = new XmlTemplateEngine(_param);
		xmlTempEngine.paraseTemplate();
		this.setNodeAttributeMap(xmlTempEngine.getNodeBlockAttrMap());
		this.setNodeBlockDataMap(xmlTempEngine.getNodeBlockDataMap());		
	}
//	/**
//	 * 释放内存空间
//	 */
//	public void Dispose() {
//		if (null != nodeBlockDataMap) {
//			nodeBlockDataMap.clear();
//			nodeBlockDataMap = null;
//		}
//		if (null != nodeAttributeMap) {
//			nodeAttributeMap.clear();
//			nodeAttributeMap = null;
//		}		
//		if (null != invalidXMLDomElementList) {
//			invalidXMLDomElementList.clear();
//			invalidXMLDomElementList = null;
//		}
//	}
	
	/**
	 * 释放内存空间
	 */
	public void Dispose() {
		if (null != nodeBlockDataMap) {
//			XML结构层次中节点的数据集合
			
//			Set set = nodeBlockDataMap.entrySet();
			Collection set = nodeBlockDataMap.values();
			Iterator iter = set.iterator();
			for(;iter.hasNext();) {
				ArrayList nodeElementList =  (ArrayList) iter.next();
				//遍历查询出当前层次节点集合中所属父节点的数据
				for(int i = 0; i< nodeElementList.size(); i++) {
					XMLElement xmlElement = (XMLElement) nodeElementList.get(i);
					xmlElement.dispose();
				}
				nodeElementList = null;
			}
			nodeBlockDataMap.clear();
			nodeBlockDataMap = null;
		}
		if (null != nodeAttributeMap) {
			nodeAttributeMap.clear();
			nodeAttributeMap = null;
		}		
		if (null != invalidXMLDomElementList) {
			Iterator iterator = this.invalidXMLDomElementList.iterator();
			for(;iterator.hasNext();){
				Element xmlDomNodeElement = (Element)iterator.next();
				xmlDomNodeElement = null;
			}
			invalidXMLDomElementList.clear();
			invalidXMLDomElementList = null;
		}
	}
}
