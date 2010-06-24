/*
 * Created on 2008-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cthq.crm.webservice.common.xml.interfaces;

import java.util.List;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IXMLReader {
	/**
	 * 获取XML数据结构中的层次目录一览
	 * @return
	 */
	public  List getXMLLevelList();
	/**
	 * 获取XML层次结构目录
	 * @param loc 层次目录位置
	 * @return
	 */
	public  String getXMLLevelXPath(int loc);	
	/**
	 * 设置需要解析的XML数据
	 * @param strXML XML数据
	 */
	public void setParasedXML(String strXML);
	
	/**
	 * 分析XML数据
	 * @throws Exception
	 */
	public void analysisXML() throws Exception;
	/**
	 * 分析XML数据
	 * @param strXml 需要解析的XML数据
	 * @throws Exception
	 */
	public void analysisXML(String strXml) throws Exception;
	/**
	 *释放空间
	 *
	 */
	public void dispose();
}
