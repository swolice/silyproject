/*
 * XmlTemplateContainer.java
 * 创建日期：2009/02/02
 */
package com.cthq.crm.webservice.xml.template.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cthq.crm.common.database.ICommonBean;
import com.cthq.crm.webservice.common.xml.imp.XMLCommonReader;
import com.cthq.crm.webservice.common.xml.imp.XMLElement;
import com.cthq.crm.webservice.common.xml.imp.XMLNodeCollection;
import com.cthq.crm.webservice.common.xml.imp.XMLNodeCollection.Entry;
import com.cthq.crm.webservice.common.xml.interfaces.IXMLAttribute;

/**
 * 解读XML模板文件与数据库数据转换存储的模板容器 
 * @author 蒋峰
 */
public class XmlTemplateContainer {
	//XML层次结构节点的数据集合
	private Map context = null;
	//XML模板文件解析器
	private XMLCommonReader xmlReader = null;
	//XML数据结构文件的层次
	private List xmlLevelList = null;
	//XML数据结构中子节点所属的父节点的位置的集合
	private Map xmlParentsLocMap = new HashMap();
	/**
	 * 创建XML数据层次结构的子节点所属父节点的位置集合
	 * @param xmlLevelLoc XML层次结构集合中的路径的位置及当前的层次路径
	 * @param ownerParentLoc 当前子节点所属父节点的位置
	 */
	private  void createXMLOwnerParentNode( int xmlLevelLoc, int ownerParentLoc ) {
		//XML层次路径
		String xmlLevelPath = (String)xmlLevelList.get(xmlLevelLoc);
		String[] xmlElememtArr = xmlLevelPath.split("/");
		//子节点所属的父节点的层次结构的路径
		String xmlLevelParentPath = xmlLevelPath.replaceAll("/" + xmlElememtArr[xmlElememtArr.length-1], "");
		List list = (List)xmlParentsLocMap.get(xmlLevelParentPath);
		if (list != null) {
			if (list.contains(String.valueOf(ownerParentLoc))) {
				return;
			}
			list.add(String.valueOf(ownerParentLoc));
		} else {
			list = new ArrayList();
			list.add(String.valueOf(ownerParentLoc));
			xmlParentsLocMap.put(xmlLevelParentPath,list);			
		}
	}
	
	/**
	 * 
	 * @param xmlObjVal 数据集合 
	 * @param xmlLevelLoc XML层次节点的集合下标
	 * @param ownerParentLoc 该层次在XML集合中所属的父节点的位置
	 * @param recordLoc 数据库检索结果的游标
	 */
	private void createXMLLevelData(Object xmlObjVal, int xmlLevelLoc, int ownerParentLoc, int recordLoc) {
		//数据集合的数据类型判断标识
		int intXmlObjType = -1;
		//XML层次节点的一览
		if (xmlLevelList == null) {
			return;
		}
		if (xmlLevelList.isEmpty()) {
			return;
		}
		//XML层次路径
		String xmlLevelPath = (String)xmlLevelList.get(xmlLevelLoc);
		//XML层次节点中的数据集合
		List list = (List)context.get(xmlLevelPath);
		if( null == list){
			list = new ArrayList();
		}
		String[] xmlElememtArr = xmlLevelPath.split("/");
		XMLElement xmlElement = new XMLElement();
		//设置节点属性
		xmlElement.setElementName(xmlElememtArr[xmlElememtArr.length-1]);
		//创建所属的XML节点元素和数据属性
		XMLNodeCollection xmlNodeColl= new XMLNodeCollection();
		xmlElement.setElementNodeCollection(xmlNodeColl);
		xmlElement.setOwnParentLoc(ownerParentLoc);
		
		//获取XML模板中的节点数据结合，根据XML模板中获取的节点的数据字段，从数据结合中组织相应的XML文件的数据节点
		XMLElement xmlPathElement = xmlReader.xmlDomElement(xmlLevelPath, 0);
		if (xmlPathElement == null) {
			return;
		}
		//设置节点属性
		setNodeProperty(xmlPathElement,xmlObjVal, recordLoc, xmlElement);
		//设置节点的所属叶子节点数据
		setNodeOWnerLeafData(xmlPathElement,xmlObjVal, recordLoc, xmlNodeColl, xmlElement);
	
		//创建分析当前的层次节点中所属的父节点的位置情况和数据的情况
		createXMLOwnerParentNode(xmlLevelLoc, ownerParentLoc);
		list.add(xmlElement);
		context.put(xmlLevelPath,list);
	}
	
	/**
	 * 更新XML层次叶子节点中的数据
	 * @param xmlObjVal 数据集合 
	 * @param xmlLevelLoc XML层次节点的集合下标
	 * @param ownerParentLoc 该层次在XML集合中所属的父节点的位置
	 * @param recordLoc 数据库检索结果的游标
	 */
	private void alterXMLLevelData(Object xmlObjVal, int xmlLevelLoc, int ownerParentLoc, int recordLoc, int _nodeCollIndex) {
		//数据集合的数据类型判断标识
		int intXmlObjType = -1;
		//XML层次节点的一览
		if (xmlLevelList == null) {
			return;
		}
		if (xmlLevelList.isEmpty()) {
			return;
		}
		//XML层次路径
		String xmlLevelPath = (String)xmlLevelList.get(xmlLevelLoc);
		//XML层次节点中的数据集合
		List list = (List)context.get(xmlLevelPath);
		if( null == list){
			return;
		}
		if (_nodeCollIndex > list.size()) {
			return;
		}
		String[] xmlElememtArr = xmlLevelPath.split("/");
		XMLElement xmlElement = (XMLElement)list.get(_nodeCollIndex);
		//创建所属的XML节点元素和数据属性
		XMLNodeCollection xmlNodeColl= xmlElement.getElementLeafNodeColl();
		//设置节点的所属叶子节点数据
		updNodeOwnerLeafData(xmlObjVal, recordLoc, xmlNodeColl, xmlElement);
	}
	
	private void updNodeOwnerLeafData(	
			final Object xmlObjVal,
			final int recordLoc,
			XMLNodeCollection xmlNodeColl, 
			XMLElement xmlElement) {
		
		Iterator iter =  xmlElement.getElementLeafNodeColl().ListIterator();
		for(;iter.hasNext();) {
			Entry entry = (Entry) iter.next();
			String field = entry.xmlNode.getName();
			String con = getString(xmlObjVal, recordLoc, field);
			if (con.trim().equals("")) {				
			} else {
				entry.xmlNode.setValue(con);
			}
			
		}
	}
	/**
	 * 设置XML数据节点所拥有的叶子节点的数据和叶子节点的属性集合
	 * 
	 * @param xmlPathElement  XML模板中的叶子节点的数据集合
	 * @param xmlObjVal  业务数据集合（数据库查询的数据）
	 * @param recordLoc  数据库数据查询集合中的游标的位置
	 * @param xmlNodeColl  XML数据的节点的属性的集合
	 * @param xmlElement  XML层次结构树中某一节点的数据集合
	 */
	private void setNodeOWnerLeafData(
		final XMLElement xmlPathElement, 
		final Object xmlObjVal,
		final int recordLoc,
		XMLNodeCollection xmlNodeColl, 
		XMLElement xmlElement ) {
		
		//获取XML模板中叶子节点的数据块
		Iterator iterNode  = xmlPathElement.leafNodeIterator();	
		if (null == iterNode) {
			return;
		}
		String con = "";
		int locA = -1;
		int locB = -1;
		String locAStr = "";
		String locBStr = "";
		//根据XML模板数据节点组织实际的XML数据块的集合
		for(;iterNode.hasNext();) {
			Entry entry = (Entry)iterNode.next();
			String key = entry.xmlNode.getValue();
			Iterator nodeAttrIter = entry.xmlNode.listIterator();
			String field = "";
			locAStr = "";
			locBStr = "";
			con = key;
			locA = key.indexOf("{");
			locB = key.indexOf("}");
			
			//分析和解析XML数据块中的数据变量{AAAA}
			if ( (locA >= 0 ) && (locB >=0 )) {
				field = key.substring(locA+1,locB);
				//在XML模板中没有相应模板替换变量的时候默认模板中获取的数据作为相应的XML数据
				con = getString(xmlObjVal, recordLoc, field);
				con = locAStr + con + locBStr;
			}
			//设置数据节点数据
			xmlNodeColl.AddNode();
			
			//设置叶子节点的
			xmlNodeColl.putNodeProperty(entry.xmlNode.getName(), con);
			
			//设置叶子节点的属性数据
			if (nodeAttrIter != null) {
				for(;nodeAttrIter.hasNext();) {
					IXMLAttribute xmlAttr = (IXMLAttribute) nodeAttrIter.next();
					//属性模板中节点的参数数据变量
					String value = xmlAttr.getValue();
					String attrVal = value;
					locA = value.indexOf("{");
					locB = value.indexOf("}");
					if ( (locA >= 0 ) && (locB >=0 )) {
						field = value.substring(locA+1,locB);
						attrVal = getString(xmlObjVal, recordLoc, field);		
					}  
					
					if (attrVal == null || attrVal.trim().length() == 0) {							
					} else {
						xmlNodeColl.putAttribute(xmlAttr.getName(), attrVal);
					}
				}
			} 
				
		}	
	}
	
	/**
	 * 设置节点的属性数据
	 * @param xmlPathElement XML模板的节点属性集合
	 * @param xmlObjVal 数据库查询的集合
	 * @param recordLoc 数据库查询结果的当前游标
	 * @param xmlElement XML层次结构树中的数据集合
	 */
	private void setNodeProperty(
		final XMLElement xmlPathElement,		
		final Object xmlObjVal,
		final int recordLoc, 
		XMLElement xmlElement) {
		
		//获取节点数据属性元素给属性变量赋值
		Iterator xmpPathElemAttrIter = xmlPathElement.attributeIterator();
		if (xmpPathElemAttrIter != null) {
			for(;xmpPathElemAttrIter.hasNext();) {
				IXMLAttribute xmlAttr = (IXMLAttribute) xmpPathElemAttrIter.next();
				//属性模板中节点的参数数据变量
				String value = xmlAttr.getValue();
				String attrVal = value;
				int locA = value.indexOf("{");
				int locB = value.indexOf("}");
				if ( (locA >= 0 ) && (locB >=0 )) {
					String  field = value.substring(locA+1,locB);
					attrVal = getString(xmlObjVal, recordLoc, field);
				}
				
				if (attrVal == null || attrVal.trim().length() == 0) {
					
				} else {
					xmlElement.addAttribute(xmlAttr.getName(), attrVal);
				}
				
			}
		}
	}
	
	private String getString(	
		final Object xmlObjVal,
		final int recordLoc, 
		final String field) {
		
		//数据库查询的集合
		ICommonBean bean = null;
		if (xmlObjVal instanceof ICommonBean) {
			bean = (ICommonBean)xmlObjVal;
			try {
				Object obj = bean.getObject(recordLoc, field);
				if (obj == null) {
					return "";
				}
				return obj.toString();
			} catch(Exception ex) {
				
			}
			return "";
		}
		
		//XML数据块映射的集合
		Map valueMap = null;
		if (xmlObjVal instanceof Map) {
			valueMap = (Map)xmlObjVal;
			Object obj = valueMap.get(field);
			if (obj == null) {
				return "";
			}
			return obj.toString();
		}
		
		return "";
	}
	
	
	/**
	 * 
	 */
	public XmlTemplateContainer() {
		super();
		context = new HashMap();
	}
	/**
	 * 获取XML的层次节点数据集合
	 * @return
	 */
	public Map getXMLTemplateContext() {
		return this.context;
	}
	public void setXMLTempContext(String xmlPath, List _param) {
		this.context.put(xmlPath, _param);
	}
	protected Map getXMLParentsLocMap() {
		return this.xmlParentsLocMap;
	}
	/**
	 * 设置XML模板文件的层次数据一览
	 * @param _list 层次节点数据一览
	 */
	public void setXMLLevelList(List _list) {
		this.xmlLevelList = _list;
	}
	public List getXMLLevelList() {
		return this.xmlLevelList;
	}
	/**
	 * 设置XML模板文件解析器
	 * @param xmlTemplatePath XML模板文件的路径
	 * @param templateFile XML模板文件名称
	 * @throws Exception
	 */
	public void setXMLReader(
		String xmlTemplatePath, 
		String templateFile) throws Exception {
			if (null == templateFile || templateFile.trim().length() == 0 ) {
				return;
			}
			if (null == xmlTemplatePath || xmlTemplatePath.trim().length() == 0) {
				return;
			}
			String strSep = System.getProperty("file.separator");
			String xmlTemplateCon = ReadTemplateFile.getFileContent(xmlTemplatePath + strSep + templateFile, "UTF-8");
			xmlReader = new XMLCommonReader();
			xmlReader.setXMLLevelList(this.xmlLevelList);
			xmlReader.analysisXML(xmlTemplateCon);
	}
	
	/**
	 * 设置XML模板文件解析器
	 * @param xmlTemplatePath XML模板文件的路径
	 * @param templateFile XML模板文件名称
	 * @throws Exception
	 */
	public void setXMLReader(String xmlVMFile) throws Exception {
			if (null == xmlVMFile || xmlVMFile.trim().length() == 0 ) {
				return;
			}
			String strSep = System.getProperty("file.separator");
			String xmlTemplateCon = ReadTemplateFile.getFileContent(xmlVMFile, "UTF-8");
			xmlReader = new XMLCommonReader();
			xmlReader.setXMLLevelList(this.xmlLevelList);
			xmlReader.analysisXML(xmlTemplateCon);
	}
	
	/**
	 * 根据数据库中查询的数据集合生成相应的XML数据节点集合
	 * @param bean 数据库查询结果
	 * @param xmlLevelLoc XML层次节点的集合下标
	 * @param ownerParentLoc 该层次在XML集合中所属的父节点的位置
	 * @param recordLoc 数据库检索结果的游标
	 */
	public void setXMLLevelData(ICommonBean bean, int xmlLevelLoc, int ownerParentLoc, int recordLoc) {
		createXMLLevelData(bean, xmlLevelLoc, ownerParentLoc, recordLoc);		
	}
	/**
	 * 根据映射集合中的数据集合生成相应的XML数据节点集合
	 * @param valueMap XML中指定的映射数据集合
	 * @param xmlLevelLoc XML层次节点的集合下标
	 * @param ownerParentLoc 该层次在XML集合中所属的父节点的位置
	 */
	public void setXMLLevelData(Map valueMap, int xmlLevelLoc, int ownerParentLoc) {
		createXMLLevelData(valueMap, xmlLevelLoc, ownerParentLoc, 0);;	
	}
	/**
	 * 
	 * @param valueMap
	 * @param xmlLevelLoc
	 * @param ownerParentLoc
	 * @param _nodeCollIndex
	 */
	public void updateXMLLevelData(Map valueMap, int xmlLevelLoc, int ownerParentLoc, int _nodeCollIndex) {
		
		alterXMLLevelData(valueMap, xmlLevelLoc, ownerParentLoc, 0, _nodeCollIndex);
	
	}
	/**
	 * 
	 * @param bean
	 * @param xmlLevelLoc
	 * @param ownerParentLoc
	 * @param recordLoc
	 * @param _nodeCollIndex
	 */
	public void updateXMLLevelData(ICommonBean bean, int xmlLevelLoc, int ownerParentLoc, int recordLoc, int _nodeCollIndex) {
		
		alterXMLLevelData(bean, xmlLevelLoc, ownerParentLoc, 0, _nodeCollIndex);
	
	}
	
	/**
	 * 创建特殊的节点的层次的数据集合
	 * @param xmlLevelLoc 层次节点的位置
	 * @param ownerParentLoc 目前创建节点数据所属的父节点的位置
	 * @return 节点的元素集合
	 */
	public  XMLElement  createSpecialXMLElement(int xmlLevelLoc, int ownerParentLoc) {
		//XML层次节点的一览
		if (xmlLevelList == null) {
			return null;
		}
		if (xmlLevelList.isEmpty()) {
			return null;
		}
		//XML层次路径
		String xmlLevelPath = (String)xmlLevelList.get(xmlLevelLoc);
		//XML层次节点中的数据集合
		List list = (List)context.get(xmlLevelPath);
		if( null == list){
			list = new ArrayList();
		}
		String[] xmlElememtArr = xmlLevelPath.split("/");
		XMLElement xmlElement = new XMLElement();
		//设置节点属性
		xmlElement.setElementName(xmlElememtArr[xmlElememtArr.length-1]);
		//创建所属的XML节点元素和数据属性
		XMLNodeCollection xmlNodeColl= new XMLNodeCollection();
		xmlElement.setElementNodeCollection(xmlNodeColl);
		xmlElement.setOwnParentLoc(ownerParentLoc);
		//创建分析当前的层次节点中所属的父节点的位置情况和数据的情况
		createXMLOwnerParentNode(xmlLevelLoc, ownerParentLoc);
		list.add(xmlElement);
		context.put(xmlLevelPath,list);
		return  xmlElement;
	}
	
	public void disposes() {
		//XML层次结构节点的数据集合
		if (!context.isEmpty()) {
			Collection set = xmlParentsLocMap.values();
			Iterator iter = set.iterator();
			for(;iter.hasNext();) {
				List list  = (List)iter.next();
				list.clear();
				list = null;
			}
		}
		context.clear();
		context = null;
		//XML模板文件解析器
		xmlReader = null;
		//XML数据结构中子节点所属的父节点的位置的集合
		if (!xmlParentsLocMap.isEmpty()) {
			Collection set = xmlParentsLocMap.values();
			Iterator iter = set.iterator();
			for(;iter.hasNext();) {
				List list  = (List)iter.next();
				list.clear();
				list = null;
			}
		}
		xmlParentsLocMap.clear();
		xmlParentsLocMap = null;
	}
	
}
