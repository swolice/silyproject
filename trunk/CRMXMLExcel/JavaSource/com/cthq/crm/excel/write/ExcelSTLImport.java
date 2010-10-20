/*
 * Created on 2010-6-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cthq.crm.excel.write;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableCellFeatures;

/**
 * EXCEL的文件数据导入操作组件
 * @author 蒋峰
 */
public class ExcelSTLImport {
	//EXCEL模板文档
	private Workbook gWorkBook = null;
	/**
	 * 设置所要操作的EXCEL数据文件
	 * @param xlsTempFile 数据文件
	 */
	public void init(String xlsDataFile) throws Exception{
        try   {
            WorkbookSettings setting = new WorkbookSettings();   
            //  Excel获得文件 
        	gWorkBook =  Workbook.getWorkbook(new File(xlsDataFile), setting);
        } catch(Exception ex) { 
        	gWorkBook = null;
        	throw ex;
        } finally {
        	
        }
	}
	/**
	 * 判定工作EXCEL模板是否可以操作
	 * @return
	 */
	public boolean isWorkbookNull() {
		if (gWorkBook == null) {
			return true;
		}
		return false;
	}
	/**
	 *释放模板文档
	 */
	public   void dispose()  {
		if (gWorkBook == null) {
			return;
		}
       try   {
       		gWorkBook.close();
       }catch  (Exception e)  {
       } 
	} 
	/**
	 * 获取EXCEL模板文件中的某一个SHEET
	 * @param intSheetIdx SHEET的索引
	 * @return
	 */ 
	public Sheet getWorkSheet(int intSheetIdx) {
		return gWorkBook.getSheet(intSheetIdx);
	}
	/**
	 * 定位指定SHEET下，指定某一元素在模板中所在单元格的数据
	 * @param dtSheet 工作当前数据区域
	 * @param strIden 
	 * @return 单元格类的基本信息
	 */
	public ExcelCell getLocateCell(Sheet dtSheet, String strIden) {
		Cell cell = dtSheet.findCell(strIden);
		if (cell == null) {
			ExcelCell xlscell = new ExcelCell();
			return xlscell;
		}
		return getLocalCell(dtSheet, cell.getColumn(),cell.getRow());
	}
	/**
	 * 定位指定SHEET下，指定某一列行的在模板中所在单元格的数据
	 * @param dtSheet 工作当前数据区域
	 * @param col 指定的列
	 * @param row 指定的行
	 * @return 单元格类的基本信息
	 */
	public ExcelCell getLocalCell(Sheet dtSheet, int col, int row) {
		ExcelCell xlscell = new ExcelCell();
		Cell cell = dtSheet.getCell(col,row);
		xlscell.setLocCol(cell.getColumn());
		xlscell.setLocRow(cell.getRow());
		xlscell.setLocStr(cell.getContents());
		xlscell.setCellFormat(cell.getCellFormat());
		Object object = cell.getCellFeatures();
		xlscell.setFeatures((WritableCellFeatures)object);
		return xlscell;
	}
	/**
	 * 根据指定的EXCEL中的Sheet获取相应行和列的信息。
	 * @param sheetIdx
	 * @param col
	 * @param row
	 * @return
	 */
	public String getLocalCellCon(int sheetIdx, int col, int row) {
		Sheet sheet = gWorkBook.getSheet(sheetIdx);
		Cell cell = sheet.getCell(col,row);
		return cell.getContents();
	}
	public static void main(String[] args) {
	}
}
