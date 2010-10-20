/*
 * IXMLNode.java
 * 创建日期:2008/08/20
 */
package com.cthq.crm.project.common.xml.imp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cthq.crm.project.common.xml.interfaces.IXMLAttribute;
import com.cthq.crm.project.common.xml.interfaces.IXMLNode;

/**
 * XML数据结构的中叶子节点类
 * @author 蒋峰
 */
public class XMLNode implements IXMLNode {
	
	//XML数据结构中叶子节点的名称属性
	private List nodeAttrList = null;
	
	//XML数据结构中叶子节点的数据
	private String nodeValue;
	
	//XML数据结构中叶子节点的元素名称
	private String nodeName;
	
	/* (non-Javadoc)
	 * @see com.cthq.crm.common.webservice.xml.interfaces.IXMLNode#setName(java.lang.String)
	 */
	public void AddNodeAttribute(IXMLAttribute nodeAttr) {
		if ( null == nodeAttrList ) {
			nodeAttrList = new ArrayList();
		}
		nodeAttrList.add(nodeAttr);

	}
	/**
	 * 获取叶子节点的属性集合
	 * @return 叶子节点属性集合
	 */
	public List getAttributes() {
		if (nodeAttrList == null) {
			return new ArrayList(0);
		}
		return nodeAttrList;
	}
	/* (non-Javadoc)
	 * @see com.cthq.crm.common.webservice.xml.interfaces.IXMLNode#setValue(java.lang.String)
	 */
	public void setValue(String value) {
		this.nodeValue = value;

	}

	/* (non-Javadoc)
	 * @see com.cthq.crm.common.webservice.xml.interfaces.IXMLNode#getName()
	 */
	public String getAttributeValue(String attrName) {
		if ( null == nodeAttrList) {
			return null;
		}
		Iterator iter = nodeAttrList.iterator();
		String name = null;
		String value = null;
		IXMLAttribute xmlAttr = null;
		for(;iter.hasNext();) {
			xmlAttr = (IXMLAttribute) iter.next();
			if (attrName.equals(xmlAttr.getName())) {
				return xmlAttr.getValue();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.cthq.crm.common.webservice.xml.interfaces.IXMLNode#getValue()
	 */
	public String getValue() {
		return this.nodeValue;
	}
	/**
	 * 设置叶子节点的名称
	 */
	public void setName(String name) {
		this.nodeName = name;
	}
	/**
	 * 返回叶子节点的元素名称
	 */
	public String getName() {
		return this.nodeName;
	}
	/**
	 * 返回叶子节点的遍历集合
	 * @return
	 */
	public Iterator listIterator() {
		if (null == this.nodeAttrList) {
			return null;
		}
		return this.nodeAttrList.listIterator();
	}
	/**
	 * 获取叶子节点的属性的个数
	 * @return
	 */
	public int attributeCount() {
		if (null == this.nodeAttrList) {
			return -1;
		}
		return this.nodeAttrList.size();
	}
	/**
	 * 释放叶子节点所占用的空间
	 *
	 */
	public void dispose() {
		this.nodeAttrList = null;
		this.nodeName = null;
		this.nodeValue = null;
	}
}
