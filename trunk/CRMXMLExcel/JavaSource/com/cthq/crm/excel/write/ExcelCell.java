/*
 * ExcelCell.java
 * 创建日期:2009/07/07
 */
package com.cthq.crm.excel.write;

import java.util.ArrayList;
import java.util.List;

import jxl.CellFeatures;
import jxl.WorkbookSettings;
import jxl.biff.StringHelper;
import jxl.format.CellFormat;
import jxl.write.WritableCellFeatures;

/**
 * EXCEL文件中的单元格的对象类
 * @author 蒋峰
 */
public class ExcelCell {
	//单元格所在的起始行
	private int locRow = -1;
	//单元格所在的起始列
	private int locCol = -1;
	//单元格的数据
	private String locStr = "";
	//单元格的数据类型和格式样式
	private CellFormat  format = null;
	//单元格的特性
	private CellFeatures features = null;
	//单元特性下拉列表数据
	private List featuresList = null;
	//单元格所在的原始的行号
	private int initRow = -1;
	//单元格所在的原始的列号
	private int initCol = -1;
	private int curLocRow = -1;
	private int curLocCol = -1;
	private String curLocStr = "";
	private String initLab = "";
	/**
	 * 获取单元格所在的数据
	 * @return
	 */
	public String getCurLocStr() {
	 return this.curLocStr;
	}
	/**
	 * 设置单元格所在的数据
	 * @param _str
	 */
	public void setCurLocStr(String _str) {
		this.curLocStr = _str;
	}
	
	/**
	 * 获取单元格所在的数据
	 * @return
	 */
	public String getInitLab() {
	 return this.initLab;
	}
	/**
	 * 设置单元格所在的数据
	 * @param _str
	 */
	public void setInitLab(String _str) {
		this.initLab = _str;
	}
	public int getCurLocRow(){
		return this.curLocRow;
	}
	public void setCurLocRow(int _row) {
		this.curLocRow = _row;
	}
	public int getCurLocCol(){
		return this.curLocCol;
	}
	public void setCurLocCol(int _col) {
		this.curLocCol = _col;
	}
	/**
	 * 获取单元格所在的行
	 * @return
	 */
	public int getLocRow() {
		return this.locRow;
	}
	/**
	 * 设置单元格所在的行
	 * @param _row
	 */
	public void setLocRow(int _row) {
		this.locRow = _row;
		if (this.initRow == -1) {
			this.initRow = _row;
		}
		
	}
	/**
	 * 获取单元格所在列
	 * @return
	 */
	public int getLocCol() {
		return this.locCol;
	}
	/**
	 * 设置单元格所在的列
	 * @param _col
	 */
	public void setLocCol(int _col) {
		this.locCol = _col;
		if (this.initCol == -1) {
			this.initCol = _col;
		}
		
	}
	/**
	 * 获取原始单元格的所在列
	 * 目前用于单元格的复制
	 * @return
	 */
	public int getInitCol(){
		return this.initCol;
	}
	/**
	 * 获取原始单元格的所在行
	 * 目前用于单元格的复制
	 * @return
	 */
	public int getInitRow(){
		return this.initRow;
	}
	/**
	 * 获取单元格所在的数据
	 * @return
	 */
	public String getLocStr() {
	 return this.locStr;
	}
	/**
	 * 设置单元格所在的数据
	 * @param _str
	 */
	public void setLocStr(String _str) {
		this.locStr = _str;
	}
	/**
	 * 设置单元格的数据格式 样式
	 * @param _format
	 */
	public void setCellFormat(CellFormat _format) {
		this.format  = _format;
	}
	/**
	 * 获取单元格的数据格式、样式
	 * @return
	 */
	public CellFormat getCellFormat() {
		return this.format;
	}
	/**
	 * 设置单元格的特性
	 * @param _features
	 */
	public void setFeatures(CellFeatures _features) {
		this.features = _features;
		featuresList = getFeatureList(_features);
	}
	/**
	 * 获取单元格的特性
	 * @return
	 */
	public CellFeatures getFeatures() {
		return this.features;
	}
	public List getFeatureList() {
		return this.featuresList;
	}
	/**
	 * 单元格的复制
	 * @return
	 */
	public ExcelCell copyCell() {
		ExcelCell cell = new ExcelCell();
		cell.format = this.format;
		cell.locCol = this.locCol;
		cell.locRow = this.locRow;
		cell.locStr = this.locStr;
		cell.initCol = this.initCol;
		cell.initRow = this.initRow;
		cell.features = this.features;
		cell.featuresList = this.featuresList;
		return cell;
	}
	private List getFeatureList(CellFeatures _features) {
		if (_features == null) {
			return null;
		}
		String dataValidatStr = _features.getDataValidationList();
	    WorkbookSettings setting = new WorkbookSettings();   
	   	if (dataValidatStr == null) {
	   		return null;
	   	}
	   	//获取特性字符串
	   	byte[] byteArr = StringHelper.getBytes(dataValidatStr, setting);
	   	//分隔字符的长度
	   	int count = 0;
	   	//每个分隔字符的起点位置
	   	int loc = 0;
	   	List list = new ArrayList();
	   	//解析特性字符串 （EXCEL中的有效性的解析）
	   	for(int i = 0; i < byteArr.length; i++) {
	   		//EXCEL有效性字符的分隔符编码 在EXCEL中 “测试1,测试2” 在文件解析中获取的字符是 将“,”这个转换的 0
	   		if (byteArr[i] == 0) {
	   			if (loc ==0) {
	   				byte[] conBteArr = new byte[count-1];
	   				System.arraycopy(byteArr, 1, conBteArr, 0, count-1);
	       			String value = new String(conBteArr);
	       			if (value.length() == 0) {
	       			} else {
	       				list.add(value);
	       			}
	   			} else {
	   				byte[] con1 = new byte[count];
	   				System.arraycopy(byteArr, loc, con1, 0, count);
	       			String value = new String(con1);
						if (value.length() ==0) {
	       				
	       			} else {
	       				list.add(value);
	       			}
	   			}
	   			loc = i+1;
	   			count = 0;
	   			continue;
	   		}
	   		
	   		if (i+1 == byteArr.length) {
	   			byte[] conBteArr = new byte[count];
	   			System.arraycopy(byteArr, loc, conBteArr, 0, count);
	   			String value = new String(conBteArr);
	   			if (value.length() ==0) {
	   			} else {
	   				list.add(value);
	   			}
	   			loc = i+1;
	   		}
	   		count  = count +1;
	   	}
		return  list;
	}
	private WritableCellFeatures getCellFeatures(ExcelCell xlscell) {
		 //解析单元格的特性
       if (xlscell.getFeatures() != null) {
       	WritableCellFeatures features = new WritableCellFeatures();
       	String dataValidatStr = xlscell.getFeatures().getDataValidationList();
//       	WorkbookSettings ws = new WorkbookSettings();
        WorkbookSettings setting = new WorkbookSettings();   
//        java.util.Locale locale = new java.util.Locale("zh","CN");   
//        setting.setLocale(locale);  
//        setting.setEncoding("ISO-8859-1");   
       	if (dataValidatStr == null) {
       		return null;
       	}
//       	System.out.println(dataValidatStr);
       	//获取特性字符串
       	byte[] byteArr = StringHelper.getBytes(dataValidatStr, setting);
       	//分隔字符的长度
       	int count = 0;
       	//每个分隔字符的起点位置
       	int loc = 0;
       	List list = new ArrayList();
       	//解析特性字符串 （EXCEL中的有效性的解析）
       	for(int i = 0; i < byteArr.length; i++) {
       		//EXCEL有效性字符的分隔符编码 在EXCEL中 “测试1,测试2” 在文件解析中获取的字符是 将“,”这个转换的 0
       		if (byteArr[i] == 0) {
       			if (loc ==0) {
       				byte[] conBteArr = new byte[count-1];
       				System.arraycopy(byteArr, 1, conBteArr, 0, count-1);
           			String value = new String(conBteArr);
           			if (value.length() == 0) {
           			} else {
           				list.add(value);
           			}
       			} else {
       				byte[] con1 = new byte[count];
       				System.arraycopy(byteArr, loc, con1, 0, count);
           			String value = new String(con1);
						if (value.length() ==0) {
           				
           			} else {
           				list.add(value);
           			}
       			}
       			loc = i+1;
       			count = 0;
       			continue;
       		}
       		
       		if (i+1 == byteArr.length) {
       			byte[] conBteArr = new byte[count];
       			System.arraycopy(byteArr, loc, conBteArr, 0, count);
       			String value = new String(conBteArr);
       			if (value.length() ==0) {
       			} else {
       				list.add(value);
       			}
       			loc = i+1;
       		}
       		count  = count +1;
       	}
       	//设置单元格的的位置
       	features.setDataValidationList(list);
       	return features;
       }
       return null;
	}
	
}
