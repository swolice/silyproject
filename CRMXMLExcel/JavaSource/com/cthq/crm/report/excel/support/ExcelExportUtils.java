package com.cthq.crm.report.excel.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cthq.crm.report.xml.support.ReportDataXmlToExcelFile;

public class ExcelExportUtils {

	private List list = new ArrayList();
	
	private ReportDataXmlToExcelFile rdxtf = null;
	
	private  String tmpExcel;
	
	private int i = 0;
	
	/**
	 * 初始化
	 * @param tmpExcel
	 */
	public ExcelExportUtils(String tmpExcel){
		rdxtf = new ReportDataXmlToExcelFile();
		this.tmpExcel = tmpExcel;
	}
	
	/**
	 * 压入需要插入excel的数据 没有数据用null
	 * @param headData 头部数据
	 * @param footData	尾部数据
	 * @param bodyData	中间部数据 可以是list 或 ResultSetOperator
	 * @param tmpXml xml模板文件 生成临时存放数据文本文件的模板
	 * @param sheetIndex 将数据放入sheet中的下标 第一个sheet为：0
	 */
	public void putDataAndTmp(Map headData,Map footData,Object bodyData,String tmpXml,int sheetIndex){
		Map map = new HashMap();
		map.put("head", headData);
		map.put("body", bodyData);
		map.put("foot", footData);
		map.put("tempxml", tmpXml);
		map.put("index", ""+sheetIndex);
		list.add(i++,map);
	}
	/**
	 * 只有头部
	 * @param dataMap 头部或尾部数据
	 * @param tmpXml xml模板文件 生成临时存放数据文本文件的模板
	 * @param sheetIndex 将数据放入sheet中的下标 第一个sheet为:0
	 */
	public void putDataAndTmpByHead(Map dataMap,String tmpXml,int sheetIndex){
		Map map = new HashMap();
		map.put("head", dataMap);
		map.put("tempxml", tmpXml);
		map.put("index", ""+sheetIndex);
		list.add(i++,map);
	}
	/**
	 * 只有尾部
	 * @param dataMap 头部或尾部数据
	 * @param tmpXml xml模板文件 生成临时存放数据文本文件的模板
	 * @param sheetIndex 将数据放入sheet中的下标 第一个sheet为:0
	 */
	public void putDataAndTmpByFoot(Map dataMap,String tmpXml,int sheetIndex){
		Map map = new HashMap();
		map.put("foot", dataMap);
		map.put("tempxml", tmpXml);
		map.put("index", ""+sheetIndex);
		list.add(i++,map);
	}
	/**
	 * 只有body中间体数据 可以是list 或 ResultSetOperator
	 * @param bodyData 	中间部数据
	 * @param tmpXml xml模板文件 生成临时存放数据文本文件的模板
	 * @param sheetIndex 将数据放入sheet中的下标 第一个sheet为:0
	 */
	public void putDataAndTmp(Object bodyData,String tmpXml,int sheetIndex){
		Map map = new HashMap();
		map.put("body", bodyData);
		map.put("tempxml", tmpXml);
		map.put("index", ""+sheetIndex);
		list.add(i++,map);
	}
	/**
	 * 获取excel字节数组
	 * @return
	 */
	public byte[] getExcelBytes(){
		byte[] ss = rdxtf.getOutExcelBytesWithSheets(this.tmpExcel, list);
		return ss;
	}
	/**
	 * 获取excel绝对路径 使用完后，删除改文件
	 * @return excel绝对路径 
	 */
	public String getExcelFile(){
		String excel = rdxtf.getOutExcelWithSheets(this.tmpExcel, list);
		return excel;
	}
	/**
	 * 根据xml文件，形成对应excel模板的excel文件
	 * @param xmlfile xml数据文件	
	 * @param tempxml 写入excel文件的xml描述
	 * @param index 对应的xml文件的下标
	 * @return
	 */
	public byte[] getExcelBytesByDataXml(String xmlfile,String tempxml,int index){
		byte[] ss = rdxtf.getExcelBytesByDataXml(xmlfile,this.tmpExcel, tempxml,index);
		return ss;
	}
	
}
