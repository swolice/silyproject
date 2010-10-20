/*
 * XMLAttribute.java
 * 创建日期:2008/08/20
 */
package com.cthq.crm.project.common.xml.imp;

import com.cthq.crm.project.common.xml.interfaces.IXMLAttribute;


/**
 * XML数据结构项目的元素属性类
 * @author 蒋峰
 */
public class XMLAttribute implements IXMLAttribute {
	
	//项目的属性名称
	private String attrName = null;
	
	//项目的属性数据
	private String attrValue = null;
	
	/* (non-Javadoc)
	 * @see com.cthq.crm.common.webservice.xml.interfaces.IXMLAttribute#setProperty(java.lang.String, java.lang.String)
	 */
	public void setProperty(String name, String value) {
		this.attrName = name;
		this.attrValue = value;
	}
	/**
	 * 获取属性名称
	 */
	public String getName() {
		return this.attrName;
	}
	/**
	 * 获取属性数据
	 */
	public String getValue(){
		return this.attrValue; 
	}
	public static void main(String[] args) {
	}
}
