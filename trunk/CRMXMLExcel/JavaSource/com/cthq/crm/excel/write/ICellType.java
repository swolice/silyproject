/*
 * ICellType.java
 * 创建日期:2009/07/07
 */
package com.cthq.crm.excel.write;

/**
 * EXCEL文件的单元格的数据类型
 * @author 蒋峰
 */
public interface ICellType {
	//数值型
	public final static int NUMBER = 0;
	//字符型
	public final static int STRING = 1;
	//日期型
	public final static int DATE = 2;
}
