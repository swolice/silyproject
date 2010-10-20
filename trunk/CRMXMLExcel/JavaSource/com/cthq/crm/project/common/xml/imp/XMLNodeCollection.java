/*
 * XMLNodeCollection.java
 * 创建日期:2008/08/20
 */
package com.cthq.crm.project.common.xml.imp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cthq.crm.project.common.xml.interfaces.IXMLAttribute;


/**
 * XML数据结构的中叶子节点属性及属性数据集合类
 * 使用例子:
 * <element name="recivee" id="A00001" >A000023449 </element>
 *  XMLNodeCollection coll = new XMLNodeCollection();
 *  coll.AddNode();
 *  coll.putAttribute("name", "recivee");
 *  coll.putAttribute("id", "A00001");
 *  coll.putNodeValue("A000023449");
 * * @author 蒋峰
 */
public class XMLNodeCollection {
	//叶子节点一览
	private List nodesList = null;
	/**
	 * 
	 *
	 */
	public XMLNodeCollection( ) {
	}
	/**
	 * 设置XML叶子节点属性集合的容量
	 * @param capacity
	 */
	public XMLNodeCollection(int capacity) {
		nodesList = new ArrayList(capacity);
		AddNode();
	}
	/**
	 * 给增加叶子节点
	 */
	public void AddNode(){
		if (null == nodesList) {
			nodesList = new ArrayList();
		}
		Entry entry = new Entry();
		nodesList.add(entry);
	}
	/**
	 * 获取叶子节点的大小
	 * 如果节点元素没有叶子节点返回的数据为-1
	 * @return 叶子节点大小 
	 */
	public int getNodesSize() {
		if (null == nodesList) {
			return 0;
		}
		return nodesList.size();
	}
	/**
	 * 获取叶子节点中指定位置的叶子节点集合
	 * @param loc 属性集合定位
	 * @return 叶子节点集合
	 */
	public XMLNode getNodesAttributeMap(int loc) {
		if (loc >= nodesList.size()) {
			return null;
		}
		Entry entry = (Entry)nodesList.get(loc);
		if (null == entry) {
			return null;
		}
		return entry.xmlNode;
	}
	/**
	 * 获取叶子节点的数据
	 * @param loc 
	 * @return
	 */
	public String getNodesValue(int loc) {
		if (null == nodesList) {
			return null;
		}
		if (loc >= nodesList.size()) {
			return null;
		}
		Entry entry = (Entry)nodesList.get(loc);
		if (null == entry) {
			return null;
		}
		return entry.xmlNode.getValue() ;
	}
	/**
	 * 根据属性数值和属性名称遍历所有叶子节点的,获取叶子节点的数据
	 * 在所有叶子节点中的属性名称与属性数据组合仅有唯一
	 * @param attrValue 叶子节点的属性数据
	 * @param attrName 叶子节点的属性名称
	 * @return 叶子节点的数据
	 */
	public String SearchNodesValue(String attrName, String attrValue) {
		if (null == nodesList ) {
			return null;
		}
		if (null == attrName) {
			return null;
		}
		if (null == attrValue) {
			return null;
		}
		Iterator iter = nodesList.iterator();
		for(; iter.hasNext() ;) {
			Entry entry = (Entry)iter.next();
			String attrValueTmp = entry.xmlNode.getAttributeValue(attrName);
			if (null == attrValueTmp) {
				return null;
			}
			if (attrValue.equals(attrValueTmp)) {
				return entry.xmlNode.getValue(); 
			}
		}
		return null;
	}
	/**
	 * 
	 * @param nodeNm
	 * @return
	 */
	public String findLeafValue(String nodeNm) {
		if (null == nodesList ) {
			return null;
		}
		if (null == nodeNm) {
			return null;
		}
		Iterator iter = nodesList.iterator();
		for(; iter.hasNext() ;) {
			Entry entry = (Entry)iter.next();
			String name = entry.xmlNode.getName();
			if (nodeNm.equals(name)) {
				return entry.xmlNode.getValue(); 
			}
		}
		return null;
	}	
	/**
	 * 判定指定的叶子节点在是否存在
	 * @param nodeNm 指定叶子节点名称
	 * @return true 存在叶子节点 false 不存在叶子节点
	 */
	public boolean isExistsNodeNm(String nodeNm) {
			if (null == nodesList ) {
				return false;
			}
			if (null == nodeNm) {
				return false;
			}
			Iterator iter = nodesList.iterator();
			for(; iter.hasNext() ;) {
				Entry entry = (Entry)iter.next();
				String name = entry.xmlNode.getName();
				if (nodeNm.equals(name)) {
					return true; 
				}
			}
			return false;
	}
	/**
	 * 根据指定的叶子节点的名称获取相应节点的集合
	 * @param nodeNm 叶子节点名称
	 * @return 叶子节点集合 如果没有获取 则返回NULL
	 */
	private Entry getExistsNodeEntry(String nodeNm) {
		if (null == nodesList ) {
			return null;
		}
		if (null == nodeNm) {
			return null;
		}
		Iterator iter = nodesList.iterator();
		for(; iter.hasNext() ;) {
			Entry entry = (Entry)iter.next();
			String name = entry.xmlNode.getName();
			if (nodeNm.equals(name)) {
				return entry; 
			}
		}
		return null;
	}
	/**
	 * 将存在的叶子节点的数据进行更新
	 * @param nodeNM 指定叶子节点名称
	 * @param value
	 */
	public void updExistsNodeNm(String nodeNM, String value) {
		Entry entry = getExistsNodeEntry(nodeNM);
		if (null != entry) {
			entry.xmlNode.setValue(value);
		}
	}
	/**
	 * 设置叶子节点的属性名称与属性数据
	 * @param name
	 * @param value
	 */
	public void putAttribute(String attrName, String attrValue) {
		if ( null == nodesList) {
			return;
		}
		Entry entry = null;
		if (null != entry) {
			
		} else {
			int size = nodesList.size();
			entry = (Entry)nodesList.get(size -1);
		}
		IXMLAttribute attr = new XMLAttribute();
		attr.setProperty(attrName, attrValue);
		entry.xmlNode.AddNodeAttribute(attr);
	}
	/**
	 * 
	 * 设置叶子节点的属性名称与属性数据
	 * @param nodeNm 指定叶子名称
	 * @param attrName 叶子节点属性名称
	 * @param attrValue 叶子节点属性数据
	 */
	public void putAttribute(String nodeNm, String attrName, String attrValue) {
		if ( null == nodesList) {
			return;
		}
		Entry entry =  getExistsNodeEntry(nodeNm);
		if (null != entry) {
			
		} else {
			int size = nodesList.size();
			entry = (Entry)nodesList.get(size -1);
		}
		IXMLAttribute attr = new XMLAttribute();
		attr.setProperty(attrName, attrValue);
		entry.xmlNode.AddNodeAttribute(attr);
	}
	
	/**
	 * 设置叶子节点的数据
	 * @param value
	 */
	public void putNodeValue(String value){
		if ( null == nodesList) {
			return;
		}
		if (nodesList.isEmpty()) {
			return;
		}
		
		int size = nodesList.size();
		Entry entry = (Entry)nodesList.get(size -1);
		entry.xmlNode.setValue(value);
	}
	/**
	 * 设置叶子节点的名称
	 * @param name 叶子节点的名称
	 */
	public void putNodeName(String name) {
		if ( null == nodesList) {
			return;
		}
		if (nodesList.isEmpty()) {
			return;
		}
		int size = nodesList.size();
		Entry entry = (Entry)nodesList.get(size -1);
		entry.xmlNode.setName(name);
	}
	/**
	 * 设置叶子节点的名称和数据
	 * @param name 叶子节点的名称
	 * @param value 叶子节点的数据
	 */
	public void putNodeProperty(String name, String value) {
		if ( null == nodesList) {
			return;
		}
		if (nodesList.isEmpty()) {
			return;
		}
		//判定在叶子节点的集合中是否有节点存在 如果存在则叶子节点的数据将被替换
		Entry entry = getExistsNodeEntry(name);
//		if (null != entry) {
//			entry.xmlNode.setValue(value);
//		} else {
			int size = nodesList.size();
			entry = (Entry)nodesList.get(size -1);
			entry.xmlNode.setName(name);
			entry.xmlNode.setValue(value);
//		}

	}
	/**
	 * 释放叶子节点占用的系统资源
	 *
	 */
	public void dispose() {
		if (null == nodesList) {
			return;
		}
		if (nodesList.isEmpty()) {
			return;
		}
		Iterator iter =  nodesList.iterator();
		for(;iter.hasNext();) {
			Entry entry = (Entry) iter.next();
			entry.xmlNode.dispose();
			entry = null;
		}
	}
	/**
	 * 判断叶子节点是否存在数据
	 * @return true 有叶子节点存在 false 没有叶子节点存在
	 */
	public boolean isEmpty() {
		if (null == this.nodesList) {
			return true;
		}
		return this.nodesList.isEmpty();
	}
	/**
	 * 返回叶子节点的遍历根部
	 * @return
	 */
	public Iterator ListIterator() {
		if (null == this.nodesList) {
			return null;
		}
		return this.nodesList.listIterator();
	}
	/**
	 * 叶子节点的属性结合表示
	 * @author 蒋峰
	 */
	public class Entry {
		public XMLNode xmlNode = new XMLNode();
	}
	
}
