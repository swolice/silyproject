/*
 * IExcelOut.java
 * 创建日期：2009/07/07
 */
package com.cthq.crm.excel.write;

/**
 * @author 蒋峰
 */
public interface IExcelOut {
	/**
	 * 初始化EXCEL模板文件的副本
	 * @param xlsTempFile 模板文件
	 * @param xlsDescFile 模板文件副本
	 */
	public void initExcelSTL(String xlsTempFile, String xlsDescFile) throws Exception; 
	/**
	 * 设置生成EXCEL文件的数据对象
	 * @param obj
	 */
	public void setExcelOutRest(Object obj);
	/**
	 * 数据EXCEL文件的入口
	 * @throws Exception
	 */
	public void process() throws Exception;
}
