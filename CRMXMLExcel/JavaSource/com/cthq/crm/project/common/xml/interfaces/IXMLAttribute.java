/*
 * IXMLAttribute.java
 * 创建日期:2008/08/20
 */
package com.cthq.crm.project.common.xml.interfaces;

/**
 * XML结构数据中节点元素属性类
 * @author 蒋峰
 */
public interface IXMLAttribute {
	/**
	 * 设置节点元素属性
 	 * @param name 属性名称
	 * @param value 属性数据
	 */
	public void setProperty(String name, String value);
	/**
	 * 获取节点属性名称
	 * @return 节点属性名称
	 */
	public String getName();
	/**
	 * 获取节点属性数据
	 * @return 节点属性数据
	 */
	public String getValue();
}
