/*
 * Created on 2009-7-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cthq.crm.excel.write;

/**
 * 输出EXCEL文件的抽象类
 * @author 蒋峰
 */
public abstract class AbstractExcelOut implements IExcelOut {
	
	protected ExcelSTLWrite excelSTLWr = new  ExcelSTLWrite();
	/* (non-Javadoc)
	 * @see com.cthq.crm.excel.write.IExcelOut#initExcelSTL(java.lang.String, java.lang.String)
	 */
	public void initExcelSTL(String xlsTempFile, String xlsDescFile) throws Exception{
		excelSTLWr.init(xlsTempFile, xlsDescFile);
	}

	/* (non-Javadoc)
	 * @see com.cthq.crm.excel.write.IExcelOut#process()
	 */
	public void process() throws Exception{
		try {
			if (excelSTLWr.isWorkbookNull()) {
				throw new Exception("模板文件：" +getExcelTempFile() + "不存在 或 EXCEL文件模板不正确");
			}
			processExcel();
		} catch(Exception ex) {
			throw ex;
		} finally {
			excelSTLWr.dispose();
		}
	}
	protected abstract String getExcelTempFile();
	/**
	 * 生成EXCEL文件
	 * @throws Exception
	 */
	protected abstract void processExcel() throws Exception;
}
