/*
 * IXMLParser.java
 * 创建日期:2008/08/20
 */
package com.cthq.crm.project.common.xml.interfaces;

import java.util.List;
import java.util.Map;


/**
 * 分析和组织CRM业务数据与长途业务WEB服务的业务XML数据结构
 * @author 蒋峰
 */
public interface IXMLParser {
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
	 * 获取XML结构数据内容
	 * @return
	 */
	public String getXMLContent();
	/**
	 * 设置发送的XML文数据
	 * @param _xmlContent
	 */
	public void setXMLContent(String _xmlContent);
	/**
	 * 检测XML结构数据
	 */
	public void chkXMLValidate();

	/**
	 * 根据写文件标识如果设定为1 将把XML数据写入文件中,创建XML数据文件的历史记录
	 * 同时删除相应旧的历史文件
	 * @param _paramMap 
	 * 		文件的名称 历史文件保留的个数  是否写文件的的标识 文件存放的路径
	 *
	 */
	public void writeXMLToFile(Map _paramMap);
	/**
	 *处理释放资源
	 */
	public void Dispose();
}
