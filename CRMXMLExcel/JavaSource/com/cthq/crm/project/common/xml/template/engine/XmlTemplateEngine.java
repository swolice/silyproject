/*
 * XmlTemplateEngine.java
 * 创建日期： 2008/02/19
 */
package com.cthq.crm.project.common.xml.template.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cthq.crm.project.common.xml.imp.XMLElement;

/**
 * XML生成模板的数据结构引擎
 * @author 蒋峰
 */
public class XmlTemplateEngine {

	//XML数据结构中没有子节点的节点标识 该节点拥有叶子节点
	public static final int XML_LEAF_LEVEL = 2;
	
	//XML数据结构中第二层次节点的拥有子节点的
	public static final int XML_PARENT_LEVEL = 1;
	
	//XML数据结构中第一层次的节点标识
	public static final int XML_FIRST_LEVEL = 1;
	
	//XML模板和数据库容器的集合
	private XmlTemplateContainer xmlTemplateContainer = null;
	
	//XML数据结构中叶子节点和父节点的集合
	private Map nodeBlockAttrMap = new HashMap();
	
	//XML层次节点的数据块的集合 
	private Map nodeBlockDataMap = new HashMap();
	/**
	 * 
	 */
	public XmlTemplateEngine(XmlTemplateContainer _xmlTemplateContainer) {
		super();
		this.xmlTemplateContainer = _xmlTemplateContainer;
	}
	/**
	 * 获取XML数据结构中叶子节点和父节点的集合
	 * @return
	 */
	public Map getNodeBlockAttrMap() {
		return this.nodeBlockAttrMap;
	}
	/**
	 * 获取XML层次节点的数据块的集合 
	 * @return
	 */
	public Map getNodeBlockDataMap() {
		return this.nodeBlockDataMap;
	}
	/**
	 * 解析XML模板容器中的XML层次节点的数据，同时生成XML数据层次节点的属性和数据集合块
	 * 为生成XML数据文件做好数据集合的准备工作
	 */
	public void paraseTemplate() {
		 try{
		 	//分析XML模板内容生成相应的空叶子节点数据集合
		 	analyzeXmlParentsElement();
		 	Map contextMap = xmlTemplateContainer.getXMLTemplateContext();
		 	List list = xmlTemplateContainer.getXMLLevelList();
		 	Iterator iter = list.iterator();
		 	//分析XML模板内容数据块同时在生成XML数据节点的属性标识
		 	for(;iter.hasNext();) {
		 		String xmlPath = (String)iter.next();
		 		int intLevelFlg = getXMLNodePathLevelLab(list, xmlPath);
		 		if (intLevelFlg > 0) {
		 			if (intLevelFlg == XML_LEAF_LEVEL) {
		 				if (!contextMap.containsKey(xmlPath)) {
		 					continue;
		 				}
		 			}
		 			if (intLevelFlg == XML_PARENT_LEVEL) {
		 				if (!contextMap.containsKey(xmlPath)) {
		 					continue;
		 				}
		 			}
		 			nodeBlockAttrMap.put("//" + xmlPath, String.valueOf(intLevelFlg));
		 			Object obj = contextMap.remove(xmlPath);
		 			if (obj != null) {
		 				nodeBlockDataMap.put("//" + xmlPath, obj);
		 			}
		 		}
		 	}
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
        	xmlTemplateContainer.disposes();
        }
       
	}
	/**
	 *根据生成的XML模板的内容数据 分析生成相应的XML数据层次节点的空节点数据集合 
	 *也就是在数据节点中没有叶子节点的层次结构的时候加入生成空的叶子节点
	 */
	private void analyzeXmlParentsElement() {
		//获取容器节点的数据集合
		Set xmlset = xmlTemplateContainer.getXMLParentsLocMap().keySet();
		if (xmlset.isEmpty()) {
			return;
		}
		Iterator iterator = xmlset.iterator();
		String xmlPath = "";
		Map contextMap = xmlTemplateContainer.getXMLTemplateContext();
		//分析处理XML数据集合节点并创建节点数据集合
		for(;iterator.hasNext();) {
			xmlPath = (String)iterator.next();
			String[] xmlArr = xmlPath.split("/");
			Object obj = contextMap.get(xmlPath);
			if (obj != null) {
				return;
			}
			Object objList = xmlTemplateContainer.getXMLParentsLocMap().get(xmlPath);
			if (objList instanceof List) {
				List list = (List) objList;
			    if (list.size() > 1) {
			    	List xmlElementList = new ArrayList();
			    	for(int i=0; i < list.size(); i++) {
			    		XMLElement xmlElement = new XMLElement();			    		
			    		xmlElement.setOwnParentLoc(0);
			    		xmlElement.setElementName(xmlArr[xmlArr.length-1]);
			    		xmlElementList.add(xmlElement);
			    	}
			    	xmlTemplateContainer.setXMLTempContext(xmlPath, xmlElementList);
			    }
			}
		}
	}
	/**
	 * 获取XML数据结构中节点数据块所属的标识
	 * @param _list XML数据结构层次一览
	 * @param xmlPath 当前XML数据结构层次输
	 * @return
	 */
	private int getXMLNodePathLevelLab(List _list, String xmlPath) {
		int count = 0;
		for(int i=0;i < _list.size(); i++) {
			String tempStr = (String)_list.get(i);
			int loc = tempStr.indexOf(xmlPath);
			if (loc < 0) {
				continue;
			} 
			count = count + 1;
		}
		if (count >= 2) {
			return this.XML_PARENT_LEVEL;
		}
		if (count == 1) {
			return this.XML_LEAF_LEVEL;
		}
		return count;
	}
	

}