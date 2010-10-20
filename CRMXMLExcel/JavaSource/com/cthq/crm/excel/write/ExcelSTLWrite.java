/*
 * ExcelSTLWrite.java
 * 创建日期:20009/07/07
 */ 
package com.cthq.crm.excel.write;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.CellFeatures;
import jxl.CellType;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 输出EXCEL文件的数据的基础操作类
 * @author 蒋峰
 */
public class ExcelSTLWrite {
	//EXCEL模板文档
	private Workbook gWorkBook = null;
	//EXCEL模板副本文档（写数据文件的模板）
	private WritableWorkbook gWriteBook = null; 
	/**
	 * 设置所要操作的EXCEL文档
	 * @param xlsTempFile 模板文档(参考模板)
	 * @param xlsDescFile 模板副本文档（需要生成EXCEL数据文件）
	 */
	public void init(String xlsTempFile, String xlsDescFile) throws Exception{
        try   {
            WorkbookSettings setting = new WorkbookSettings();   
            //  Excel获得文件 
        	gWorkBook =  Workbook.getWorkbook(new File(xlsTempFile));
            //  打开一个文件的副本，并且指定数据写回到原文件 
        	gWriteBook  =  Workbook.createWorkbook( new  File(xlsDescFile), gWorkBook);
        } catch(Exception ex) { 
        	gWorkBook = null;
        	gWriteBook = null;
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
		if (gWriteBook == null) {
			return true;
		}
		return false;
	}
	/**
	 *释放模板文档
	 */
	public   void dispose()  {
		if (gWriteBook == null) {
			return;
		}
       try   {
        	gWriteBook.write();
        	gWriteBook.close();
       }catch  (Exception e)  {
       } 
       try {
    	   System.gc();
       } catch(Exception ex){
    	   
       }
	} 
	/**
	 * 获取EXCEL模板文件中的某一个SHEET
	 * @param intSheetIdx SHEET的索引
	 * @return
	 */ 
	public  WritableSheet getWorkSheet(int intSheetIdx) {
		return gWriteBook.getSheet(intSheetIdx);
	}
	/**
	 * 定位指定SHEET下，指定某一元素在模板中所在单元格的数据
	 * @param dtSheet 工作当前数据区域
	 * @param strIden 
	 * @return 单元格类的基本信息
	 */
	public synchronized ExcelCell getLocateCell(WritableSheet dtSheet, String strIden) {
		Cell cell = dtSheet.findCell(strIden);
		return getLocalCell(dtSheet, cell.getColumn(),cell.getRow());
	}
	/**
	 * 定位指定SHEET下，指定某一列行的在模板中所在单元格的数据
	 * @param dtSheet 工作当前数据区域
	 * @param col 指定的列
	 * @param row 指定的行
	 * @return 单元格类的基本信息
	 */
	public synchronized ExcelCell getLocalCell(WritableSheet dtSheet, int col, int row) {
		ExcelCell xlscell = new ExcelCell();
		Cell cell = dtSheet.getCell(col,row);
		xlscell.setLocCol(cell.getColumn());
		xlscell.setLocRow(cell.getRow());
		xlscell.setLocStr(cell.getContents());
		xlscell.setInitLab(cell.getContents());
		xlscell.setCellFormat(cell.getCellFormat());
		Object object = cell.getCellFeatures();
		xlscell.setFeatures((WritableCellFeatures)object);
		return xlscell;
	}
	/**
	 *  设置工作区域中的数据
     * @param dataSheet dtSheet : 工作表 
     * @param col int : 列 
     * @param row int : 行 
     * @param str String : 字符 
	 * @param intType
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public synchronized void setCell(WritableSheet dtSheet, ExcelCell xlscell, int intType )throws RowsExceededException, WriteException  {
		
		if (intType == ICellType.NUMBER) {
			modiNumCell(dtSheet, xlscell.getLocCol(), xlscell.getLocRow(), 
					Double.parseDouble(xlscell.getLocStr()), xlscell.getCellFormat(), xlscell);
			return;
		}
		
		if (intType == ICellType.STRING) {
			modiStrCell(dtSheet,  xlscell.getLocCol(), xlscell.getLocRow(), 
					xlscell.getLocStr(), xlscell.getCellFormat(), xlscell);
		}
	}
	/**
	 * 删除指定工作区域中指定的行。
	 * @param dtSheet 工作区域
	 * @param deleteRow 删除的行数
	 */
	public synchronized void removeRow(WritableSheet dtSheet, int deleteRow) {
		dtSheet.removeRow(deleteRow);
	}
	/**
	 * 删除指定工作区域中的指定范围行数，
	 * 
	 * @param dtSheet 工作区域
	 * @param intFromRow 删除指定行的起始位置
	 * @param intToRow 删除指定行的终止位置
	 */
	public synchronized void removeRangeRows(WritableSheet dtSheet, int intFromRow, int intToRow) {
		if (intFromRow > intToRow) {
			return;
		}
		for(int i=intFromRow; i < intToRow; i++) {
			dtSheet.removeRow(intFromRow);
		}
	}
	/**
	 * 
     * @param dataSheet dtSheet : 工作表 
     * @param col int : 列 
     * @param row int : 行 
     * @param str String : 字符 
	 * @param intType
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public synchronized void setCell(WritableSheet dtSheet, int col, int row, String str, int intType )throws RowsExceededException, WriteException  {
		if (intType == ICellType.NUMBER) {
			modiNumCell(dtSheet, col, row, Double.parseDouble(str), null, null);
			return;
		}
		if (intType == ICellType.STRING) {
			modiStrCell(dtSheet, col, row, str, null, null);
		}
		
	}
	private WritableCellFeatures getCellFeatures(ExcelCell xlscell) {
		 //解析单元格的特性
		if (xlscell.getFeatureList() == null ) {
			return null;
		}
        if (!xlscell.getFeatureList().isEmpty()) {
        	WritableCellFeatures features = new WritableCellFeatures();
        	List angerlist = new ArrayList();
        	features.setDataValidationList(xlscell.getFeatureList());
        	return features;
        }
        return null;
	}
	/** 
     * 修改字符单元格的值 
     * @param dataSheet WritableSheet : 工作表 
     * @param col int : 列 
     * @param row int : 行 
     * @param str String : 字符 
     * @param format CellFormat : 单元格的样式 
     * @throws RowsExceededException 
     * @throws WriteException 
     */  
    private void modiStrCell(WritableSheet dataSheet, int col, int row, String str, 
    		CellFormat format, ExcelCell xlscell) throws RowsExceededException, WriteException  {  
        // 获得单元格对象  
        WritableCell cell = dataSheet.getWritableCell(col, row);  
        // 判断单元格的类型, 做出相应的转化  
        if (cell.getType() == CellType.EMPTY)  {  
        	if (format == null || xlscell == null) {
        		Label lbl = new Label(col, row, str);  
                if(null != format) {  
                    lbl.setCellFormat(format);  
                } else {  
                }  
            	dataSheet.addCell(lbl);  
        	} else {
        		if (xlscell.getFeatureList() == null ) {
                    Label lbl = new Label(col, row, str);  
                    if(null != format) {  
                        lbl.setCellFormat(format);  
                    } else {  
                    }  
                	dataSheet.addCell(lbl);  
                } else {
                  	int col_1 = xlscell.getInitCol();
                	int row_1 = xlscell.getInitRow();
                	int col_2 = xlscell.getLocCol();
                	int row_2 = xlscell.getLocRow();
                	//获取原始单元格
                	WritableCell cell_1 = dataSheet.getWritableCell(col_1, row_1);
            		//复制单元格
                	WritableCell copy_2 = cell_1.copyTo(col_2, row_2);
            		dataSheet.addCell(copy_2);  
                }
        	}
        } else if (cell.getType() == CellType.LABEL) {  
        	if (format == null || xlscell == null) {
        		Label lbl = (Label)cell;  
                lbl.setString(str); 
        	} else {
        		if (xlscell.getFeatureList() == null ) {
            		Label lbl = (Label)cell;  
                    lbl.setString(str); 
                } else {
                  	int col_1 = xlscell.getInitCol();
                	int row_1 = xlscell.getInitRow();
                	int col_2 = xlscell.getLocCol();
                	int row_2 = xlscell.getLocRow();
                	//获取原始单元格
                	WritableCell cell_1 = dataSheet.getWritableCell(col_1, row_1);
                	//复制单元格
            		WritableCell copy_2 = cell_1.copyTo(col_2, row_2);
            		//填写数据
            		((Label)copy_2).setString(str);
            		dataSheet.addCell(copy_2);  
                }
        	}
        } else if (cell.getType() == CellType.NUMBER) {  
            // 数字单元格修改  
            Number n1 = (Number)cell; 
            n1.setValue(Double.valueOf(str).intValue());
            WritableCellFeatures features =  getCellFeatures(xlscell);
            if (features != null) {
            	n1.setCellFeatures(features);
            }
        }  
    }  
    /**
     * 在工作区域中插入一行
     * @param _dtsheet
     * @param locRow
     */
    public void insertRow(WritableSheet _dtsheet, int locRow) {
    	_dtsheet.insertRow(locRow);
    }
    /** 
     * 修改数字单元格的值 
     * @param dataSheet WritableSheet : 工作表 
     * @param col int : 列 
     * @param row int : 行 
     * @param num double : 数值 
     * @param format CellFormat : 单元格的样式 
     * @throws RowsExceededException 
     * @throws WriteException 
     */  
    private void modiNumCell(WritableSheet dataSheet, int col, int row,
    		double num, CellFormat format, ExcelCell xlscell) 
    	throws RowsExceededException, WriteException {  
        
    	// 获得单元格对象  
        WritableCell cell = dataSheet.getWritableCell(col, row);  
        // 判断单元格的类型, 做出相应的转化  
        if (cell.getType() == CellType.EMPTY) {  
            Number lbl = new Number(col, row, num);  
            if(null != format) {  
                lbl.setCellFormat(format);  
            } else {  
                lbl.setCellFormat(cell.getCellFormat());  
            }  
            WritableCellFeatures features = getCellFeatures(xlscell);
            if (features != null) {
            	lbl.setCellFeatures(features);
            }
            dataSheet.addCell(lbl);  
        } else if (cell.getType() == CellType.NUMBER) {  
            // 数字单元格修改  
            Number lbl = (Number)cell;  
            lbl.setValue(num);  
            WritableCellFeatures features = getCellFeatures(xlscell);
            if (features != null) {
            	lbl.setCellFeatures(features);
            }
        } else if (cell.getType() == CellType.LABEL)  {  
            Label lbl = (Label)cell;  
            lbl.setString(String.valueOf(num));  
            WritableCellFeatures features = getCellFeatures(xlscell);
            if (features != null) {
            	lbl.setCellFeatures(features);
            }
        }
        
    }    

   
    /**
     * 
     * @param sheet
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param style
     * @param BorderColor
     * @param bgColor
     * @throws WriteException
     */
    public static void drawRect(WritableSheet sheet, int x1, int y1, int x2, int y2,
    		BorderLineStyle style,Colour BorderColor, Colour bgColor) 
    		throws WriteException {
    	   
    	for(int x = x1; x <= x2; x++){
    	    for(int y = y1; y <= y2; y++) {
	    	     WritableCellFormat alignStyle = new WritableCellFormat(); //单元格样式
	    	     alignStyle.setAlignment(Alignment.CENTRE);   //设置对齐方式
	    	     alignStyle.setVerticalAlignment(VerticalAlignment.CENTRE);//设置对齐方式
	    	     //画上
	    	      alignStyle.setBorder(Border.TOP, style, BorderColor);//设置边框的颜色和样式
	    	      //画左
	    	      alignStyle.setBorder(Border.LEFT, style, BorderColor);//设置边框的颜色和样式
	    	      //画右
	    	      alignStyle.setBorder(Border.RIGHT, style, BorderColor);//设置边框的颜色和样式
	    	      //画下
	    	      alignStyle.setBorder(Border.BOTTOM, style, BorderColor);//设置边框的颜色和样式
	    	     if(bgColor != null)
	    	      alignStyle.setBackground(bgColor); //背静色
	    	     Cell cell = sheet.getCell(x, y);
	    	     if (cell instanceof Label) {
	    	     	Label mergelabel = (Label) cell;
	    	     	mergelabel.setCellFormat(alignStyle);
	    	     } else {
	    	     	Label mergelabel = new Label(x, y, cell.getContents(), alignStyle);
	    	     	sheet.addCell(mergelabel);
	    	     }
    	    }
    	}
   }
    
	public WritableWorkbook getGWriteBook() {
		return gWriteBook;
	}
	/**
	 * 创建一个空白的sheet
	 * @param sheetName sheet的名字
	 * @param sheetIndex sheet的下标
	 * @return 返回一个可写的sheet
	 */
	public WritableSheet getNewSheet(String sheetName,int sheetIndex){
		gWriteBook.createSheet(sheetName, sheetIndex);

		WritableSheet sheetNew = this.getWorkSheet(sheetIndex);
		
		return sheetNew;
	}
	
	/**
	 * 返回sheet的个数
	 * @return
	 */
	public int getNumberOfSheets(){
		return gWriteBook.getNumberOfSheets();
	}
	
	
    	   
}
