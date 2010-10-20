/*
 * XMLElement.java
 * 创建日期:2008/08/20
 */
package com.cthq.crm.webservice.common.xml.imp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cthq.crm.webservice.common.xml.interfaces.IXMLAttribute;
import com.cthq.crm.webservice.common.xml.interfaces.IXMLElement;

/**
 * XML数据结构项目元素的信息
 * 例：
 * <order> <element name="test">a001</element></order>
 * <order> 元素属于本类描述的对象
 * <element> 属于叶子节点， 用IXMLNode类描述对象
 * @author 蒋峰
 */
public class XMLElement implements IXMLElement {
	
	//XML数据结构中元素项目节点名称
	private String elementName;
	
	//XML数据结构中元素项目节点属性集合
	private List attrList = null;
	
	//XML数据结构中元素中节点数据
	private String elementValue;
	
	//XML数据结构中元素中项目节点中子节点的集合
	private XMLNodeCollection leafNodeCollection = null;
	
	//XML数据结构中元素项目节点中的叶子节点
	private List nodeList = null;
	
	//元素所属父节点的位置
	private int ownParentLoc = -1;
	private int selfLoc = -1;
	
	//（从根节点的到该节点的所属父节点的路径）
	private List xmlDomParentsList;
	//本节点元素在DOM树中的路径
	private String domTreeAddress = "";
	/**
	 * 设置节点的本地路径地址
	 * @param _address
	 */
	public void setDomTreeAddress(String _address) {
		domTreeAddress = _address;
	}
	/**
	 * 获取节点的本地路径地址
	 * @return
	 */
	public String getDomTreeAddress() {
		return domTreeAddress;
	}
	/**
	 * 设置节点的节点路径 （从根节点的到该节点的所属父节点的路径）
	 * @param xpathAddr
	 */
	public void setXmlLevelParentS(List  parentsList){
		this.xmlDomParentsList = parentsList;
	}
	
	/**
	 * 获取该节点的节点的路径（从根节点的到该节点的所属父节点的路径）
	 * @return 节点路径
	 */
	public List getXmlParentsList(){
		return this.xmlDomParentsList;
	}
	
	/**
	 * 设置该节点所属父节点的位置
	 * @param loc 父节点位置
	 */
	public void setOwnParentLoc(int loc) {
		this.ownParentLoc = loc;
	}
	
	/**
	 * 获取元素所属父节点的位置
	 * @return 父节点的位置
	 */
	public int getOwnParentLoc() {
		return this.ownParentLoc;
	}
	public void setSelfLoc(int loc) {
		this.selfLoc = loc;
	}
	public int getSelfLoc() {
		return this.selfLoc;
	}
	/**
	 * 获取节点元素名称
	 */
	public String getElementName() {
		return this.elementName;
	}
	
	/* (non-Javadoc)
	 * @see com.cthq.crm.common.webservice.xml.interfaces.IXMLElement#addElement(java.lang.String)
	 */
	public void setElementName(String name) {
		this.elementName = name;
	}
	/**
	 * 设置XML节点元素的数据
	 * @param value 节点元素的数据
	 */
	public void setElementValue(String value) {
		this.elementValue = value;
	}
	/**
	 * 获取XML节点元素的数据
	 * @return 节点元素的数据
	 */
	public String getElementValue() {
		return this.elementValue;
	}
	/* (non-Javadoc)
	 * @see com.cthq.crm.common.webservice.xml.interfaces.IXMLElement#setAttribute(com.cthq.crm.common.webservice.xml.interfaces.IXMLAttribute)
	 */
	public void addAttribute(String attrName, String attrValue) {
		if (attrList == null) {
			attrList = new ArrayList();
		}
		IXMLAttribute attr =new XMLAttribute();
		attr.setProperty(attrName, attrValue);
		attrList.add(attr);
	}
	
	/**
	 * 设置节点元素项目的叶子节点的集合
	 * @param param 叶子节点的集合
	 */
	public void setElementNodeCollection(XMLNodeCollection param ) {
		this.leafNodeCollection = param;
	}
	
	/**
	 * 获取节点元素项目的叶子节点的集合
	 * @return 叶子节点集合
	 */
	public XMLNodeCollection getElementLeafNodeColl() {
		return this.leafNodeCollection;
	}
	
	/**
	 * 获取节点元素的叶子节点的个数
	 * @return 叶子节点的个数
	 */
	public int getLeadNodeCount() {
		if (null == this.leafNodeCollection) {
			return 0;
		}
		return this.leafNodeCollection.getNodesSize();
	}
	
	/**
	 * 获取节点元素属性集合
	 * @return 节点元素属性集合
	 */	
	public List getAttributes() {
		return this.attrList; 
	}
	
	/**
	 * 获取元素项目该节点的属性的个数
	 * @return 节点属性的个数
	 */
	public int attributeCount(){
		if (null == this.attrList) {
			return 0;
		}
		if (this.attrList.isEmpty()) {
			return 0;
		}
		return this.attrList.size(); 
	}
	
	/**
	 * 获取元素节点的叶子节点的个数
	 * @return 叶子节点的个数
	 */
	public int leafNodesCount() {
		if (null == this.leafNodeCollection) {
			return 0;
		}
		return this.leafNodeCollection.getNodesSize();
	}
	
	/**
	 * 获取该节点元素属性的遍历
	 * @return
	 */
	public Iterator attributeIterator(){
		if (null == this.attrList) {
			return null;
		}
		return this.attrList.iterator();
	}
	
	/**
	 * 获取节点元素的叶子节点的遍历
	 * @return
	 */
	public Iterator leafNodeIterator() {
		if (null == this.leafNodeCollection) {
			return null;
		}
		return this.leafNodeCollection.ListIterator(); 
	}
	/**
	 * 获取指定层次路径父节点的路径地址
	 * 路径格式：根节点/父节点01/父节点01/父节点。。。。
	 * @param levelCount 父节点的层次个数
	 * @return
	 */
	public String getParentTreeAddress(int levelCount) {
		if (xmlDomParentsList.size() < levelCount) {
			return "";
		}
		int size = xmlDomParentsList.size();
		StringBuffer levelPath = new StringBuffer();
		for(int i= size-1; i > size-levelCount-1;) {
			levelPath.append(xmlDomParentsList.get(i));
			--i;
			if (i > size-levelCount-1) {
				levelPath.append("/");
			}
		}
		return levelPath.toString();
	}
	/**
	 * 释放节点空间
	 *
	 */
	public void dispose() {
		//属性释放
		if (null == this.attrList) {
			
		} else {
			this.attrList.clear();
			this.attrList = null;	
		}
		//叶子节点释放
		if (null == this.leafNodeCollection) {
			
		} else {
			this.leafNodeCollection.dispose();
			this.leafNodeCollection = null;	
		}
	}
	
	public static void main(String[] args) {
	}
}
