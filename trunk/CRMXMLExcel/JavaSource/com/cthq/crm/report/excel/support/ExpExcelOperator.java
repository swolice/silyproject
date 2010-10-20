/*
 * ExpExcelOperator.java
 * 
 */
package com.cthq.crm.report.excel.support;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.CellType;
import jxl.Range;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.cthq.crm.excel.write.ExcelCell;
import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.excel.write.ICellType;

/**
 * @author 吉仕军
 * 操作excel文件
 * 修改日期：2010年7月7日
 * 修改内容：修改了显示数字时显示科学计数法
 */
public class ExpExcelOperator {
	
	private static DecimalFormat df = new DecimalFormat("##0.00");
	private static Pattern pat = Pattern.compile("^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$");
	/**
	 * 获取单元格样式
	 * 预定义的一些字体和格式，同一个Excel中最好不要有太多格式  
	 */
	public static WritableCellFormat getCellFormat(String flag){
		//字号:12,字体：ARIAL 样式：粗体，无下滑线，颜色：黑色
		if("1".equals(flag)){
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, 
				    false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.DARK_TEAL);    
				 WritableCellFormat headerFormat = new WritableCellFormat (headerFont);    
				 return headerFormat;
		}
		//字号:10,字体：ARIAL 样式：无粗体，无下滑线，颜色：红色
		if("2".equals(flag)){
			 WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD,
				     false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.DARK_PURPLE);    
				 WritableCellFormat titleFormat = new WritableCellFormat (titleFont);   
				 return titleFormat;
		}
		//字号:10,字体：ARIAL 样式：无粗体，无下滑线，颜色：白色
		if("3".equals(flag)){
			 WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, 
				    false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);    
				 WritableCellFormat detFormat = new WritableCellFormat (detFont);   
				 return detFormat;
		}
		///字号:10,字体：ARIAL 样式：无粗体，无下滑线，颜色：白色  数字格式：0.00000
		if("4".equals(flag)){
			 WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, 
				    false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);    
			WritableCellFormat detFormat = new WritableCellFormat (detFont);   
			 NumberFormat nf=new NumberFormat("0.00000"); //用于Number的格式   
			 WritableCellFormat priceFormat = new WritableCellFormat (detFont, nf);    
			 return priceFormat;
		}
		//字号:10,字体：ARIAL 样式：无粗体，无下滑线，颜色：白色  日期格式：yyyy-MM-dd
		if("5".equals(flag)){
			 WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, 
				    false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);    
			WritableCellFormat detFormat = new WritableCellFormat (detFont);   
			 DateFormat df=new DateFormat("yyyy-MM-dd");//用于日期的   
			 WritableCellFormat dateFormat = new WritableCellFormat (detFont, df); 
			 return dateFormat;
		}
		return null;
	}
	

	/**
	 * 复制一行
	 * @param sheet 复制行的sheet
	 * @param sourceRow 需要复制的行下标
	 * @param toRow 复制到哪一行
	 */
	public static void copyRow(WritableSheet sheet, int sourceRow, int toRow) {
		try {
			Cell cells[] = sheet.getRow(sourceRow);
			//插入一个空白行
			sheet.insertRow(toRow);
			//设置行高
			//sheet.setRowView(toRow, sheet.getRowHeight(sourceRow));
			for (int i = 0; i < cells.length; i++) {
				//单元格的类型为EMPTY，添加一个内容为“”的LABEL
				if (cells[i].getType() == CellType.EMPTY) {
					try {
						CellFormat cf = cells[i].getCellFormat();
						BorderLineStyle bls = cf.getBorderLine(Border.LEFT);
						String s = bls.getDescription();
						jxl.write.WritableCellFormat wcsB = new jxl.write.WritableCellFormat(
								cf);
						//如果存在thin边框，则在空的单元格也添加边框
						if(s.equals("thin")){
							wcsB.setBorder(jxl.format.Border.ALL,
									jxl.format.BorderLineStyle.THIN);
						}
						Label lb = new Label(cells[i].getColumn(), toRow, "",
								wcsB);
						sheet.addCell(lb);
					} catch (RowsExceededException e) {
					} catch (WriteException e) {
					}
				}
				//单元格类型为LABEL
				if (cells[i].getType() == CellType.LABEL) {
					Label wc = (Label) cells[i];
					Label wc1 = (Label) wc.copyTo(cells[i].getColumn(), toRow);
					wc1.setCellFormat(wc.getCellFormat());
					sheet.addCell(wc1);
				}
			}
			//获取sheet中所有的合并单元格
			Range rangs[] = sheet.getMergedCells();
			for (int i = 0; i < rangs.length; i++) {
				//复制行中正好有和并列单元格，则合并复制后的行
				if (rangs[i].getTopLeft().getRow() == sourceRow) {
					Range range = sheet.mergeCells(rangs[i].getTopLeft()
							.getColumn(), toRow, rangs[i].getBottomRight()
							.getColumn(), toRow);
					//获取合并单元格的区域的左上单元格
					Cell cell = range.getTopLeft();
					CellFormat cf = cell.getCellFormat();
					BorderLineStyle bls = cf.getBorderLine(Border.LEFT);
					String s = bls.getDescription();
					jxl.write.WritableCellFormat wcsB = new jxl.write.WritableCellFormat(
							cf);
//					如果存在thin边框，则在空的单元格也添加边框
					if(s.equals("thin")){
						wcsB.setBorder(jxl.format.Border.ALL,
								jxl.format.BorderLineStyle.THIN);
					}
					Label lb = new Label(cell.getColumn(), cell.getRow(), cell
							.getContents(), wcsB);
					sheet.addCell(lb);
				}
			}
		} catch (Exception e) {
		}
	}
	/**
	 * 填写EXCEL本体BODY的数据
	 * @param excelSTLWr EXCEL文档
	 * @param cellStyleList EXCEL模板数据样式风格集合
	 * @param dataMap 本体BODY的一行数据集合
	 * @param sheetLoc 当前操作的Excel的Sheet
	 * @param writeLocRow 当前写本体数据的起始行号
	 * @param rows 一个本体行占用EXCEL中的行数
	 * @param cols 一个本体行占用的列数
	 * @param stepRows 当前本体行在当前的EXCEL的本体的行数 
	 * @param initRow 本体所在的EXCEL中的最初行号
	 * @param attrMap 记录列的显示信息， 比如格式化数据显示
	 */
	public static void writeData(
			ExcelSTLWrite excelSTLWr, List cellStyleList,  Map dataMap, 
			int sheetLoc , int writeLocRow, 
			int rows, int cols ,
			int stepRows, int  initRow,  int marginRows,Map attrMap){
		try {
			WritableSheet sheet = excelSTLWr.getWorkSheet(sheetLoc);
			for (int row = writeLocRow; row < writeLocRow + rows; row++) {
				for (int col = 0; col < cols; col++) {
					for (int i = 0; i < cellStyleList.size(); i++) {
						ExcelCell tmpCell = (ExcelCell) cellStyleList.get(i);
						if ( tmpCell.getLocCol() == col
								&& tmpCell.getLocRow()+ stepRows + marginRows == row 
								&& tmpCell.getLocRow() >= initRow) {
							
							ExcelCell copyCell = tmpCell.copyCell();
							copyCell.setLocRow(row);
							tmpCell.setCurLocRow(row);
							tmpCell.setCurLocCol(copyCell.getLocCol());
							
							String value = copyCell.getLocStr();
							if (value == null || value.trim().length() == 0) {
//								copyCell.setLocStr("");
							} else {
								if(value.matches(".*%.+%.*")){
									String key = value.substring(value.indexOf("%")+1,value.lastIndexOf("%"));
									if (dataMap.get(key) == null) {
										copyCell.setLocStr("");
									} else {
										Object con = dataMap.get(key);
										String map_value = con.toString();
										if(pat.matcher(map_value).matches()&&!(con instanceof String)){
											//df有效精度为14位，超过14位数值显示不正确
											map_value = df.format(con);
										}
										tmpCell.setCurLocStr(map_value);
										//需要特殊显示的数据，比如说金额，需要特殊显示格式
										String showStyle = showExcelDataStyle(attrMap,key,dataMap);
										if(!"".equals(showStyle)){
											map_value = showStyle;
										}
										map_value = Matcher.quoteReplacement(map_value);
//										String map_value =(String)dataMap.get(key);	
										copyCell.setLocStr(value.replaceAll("%"+key+"%", map_value.trim()));	
									}
								}
							}
							
							excelSTLWr.setCell(sheet, copyCell, ICellType.STRING);
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}
	
	public static String showExcelDataStyle(Map attrMap,String key,Map dataMap){
		if (attrMap == null) {
			return "";
		}
		if(null == attrMap.get(key)){
			return "";
		}
		Map key_value_map = (Map)attrMap.get(key);
		if(null!=key_value_map.get("numberStyle")){
			String value = key_value_map.get("numberStyle").toString();
			DecimalFormat df = new DecimalFormat(value);
			double dd = getDoubleValue(dataMap,key);
			String showNum = df.format(dd);
			return showNum;
		}
		return ""; 
	}
	
	
	/**
	 * 绘制一个本体块的样式
	 * @param excelSTLWr  EXCEL文档
	 * @param cellStyleList EXCEL模板数据样式风格集合	 
	 * @param rangs  EXCEL模板的合并描述
	 * @param sheetLoc 当前操作的Excel的Sheet
	 * @param writeLocRow 当前写本体数据的起始行号
	 * @param rows 一个本体行占用EXCEL中的行数
	 * @param stepRows 当前本体行在当前的EXCEL的本体的行数 
	 * @param initRow 本体所在的EXCEL中的最初行号 
	 * @param marginRows 循环体中的间距
	 */
	public static void paintCellStyle(
			ExcelSTLWrite excelSTLWr, List cellStyleList, 
			Range rangs[], int sheetLoc ,
			int writeLocRow, int rows,
			int stepRows,  int initRow, int marginRows){
		try {
			WritableSheet sheet = excelSTLWr.getWorkSheet(sheetLoc);
			int count = 0; 
			
			//插入间隔空白行
			for (int i = 0; i < rows + marginRows; i++) {
				excelSTLWr.insertRow(sheet, writeLocRow+i-marginRows);
			}
			for (int i = 0; i < rangs.length; i++) {
				int rangLeftRow = rangs[i].getTopLeft().getRow();
				int rangLeftCol = rangs[i].getTopLeft().getColumn();
				int rangRightRow = rangs[i].getBottomRight().getRow();
				int rangRightCol = rangs[i].getBottomRight().getColumn();
				for (int j = 0; j < cellStyleList.size(); j++) {
					ExcelCell tmpCell = (ExcelCell) cellStyleList.get(j);
				
					if (tmpCell.getLocCol() == rangLeftCol 
							&& rangLeftRow == tmpCell.getLocRow()
							&& tmpCell.getLocRow() >= initRow) {
							sheet.mergeCells(rangLeftCol, 
									rangLeftRow + rows*stepRows +marginRows*stepRows , rangRightCol, 
									rangRightRow + rows*stepRows+marginRows*stepRows);
							break;
					}	
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}
	public static void copyRows(ExcelSTLWrite excelSTLWr,int sheetLoc ,
			int sourceRow, int rows, int cols ,
			int margin,Map dataMap, List list1,Map attrMap) {
		
		int targetRow =  sourceRow + rows + margin;
		try {
			WritableSheet sheet = excelSTLWr.getWorkSheet(sheetLoc);
			ArrayList list = new ArrayList();
			//插入间隔空白行
			for (int i = 0; i < margin + rows; i++) {
				excelSTLWr.insertRow(sheet, sourceRow + rows + i);
			}
			for (int row = sourceRow; row < sourceRow + rows; row++) {
				for (int col = 0; col < cols; col++) {
					list.add(excelSTLWr.getLocalCell(sheet, col, row));
				}
			}
			for (int row = targetRow; row < targetRow+rows; row++) {
				for (int col = 0; col < cols; col++) {
					for (int i = 0; i < list.size(); i++) {
						ExcelCell tmpCell = (ExcelCell) list.get(i);
						if (tmpCell.getLocCol() == col
								&& tmpCell.getLocRow() + rows + margin == row) {
							ExcelCell copyCell = tmpCell.copyCell();
							String value = copyCell.getLocStr();
							if (value == null || value.trim().length() == 0) {
//								copyCell.setLocStr("");
							} else {
								if(value.matches(".*%.+%.*")){
									String key = value.substring(value.indexOf("%")+1,value.lastIndexOf("%"));
									if (dataMap.get(key) == null) {
//										copyCell.setLocStr("");
									} else {
										Object con = dataMap.get(key);
										String map_value = con.toString();
										if(pat.matcher(map_value).matches()&&!(con instanceof String)){
											//df有效精度为14位，超过14位数值显示不正确
											map_value = df.format(con);
										}
										//需要特殊显示的数据，比如说金额，需要特殊显示格式
										String showStyle = showExcelDataStyle(attrMap,key,dataMap);
										if(!"".equals(showStyle)){
											map_value = showStyle;
										}
										map_value = Matcher.quoteReplacement(map_value);
//										String map_value =(String)dataMap.get(key);	
										copyCell.setLocStr(value.replaceAll("%"+key+"%", map_value.trim()));	
									}
								}
							}
							tmpCell.setLocRow(row);
							excelSTLWr.setCell(sheet, tmpCell, ICellType.STRING);
							excelSTLWr.setCell(sheet, copyCell, ICellType.STRING);
						}
					}
				}

			}

			Range rangs[] = sheet.getMergedCells();
			for (int i = 0; i < rangs.length; i++) {
				if ((rangs[i].getTopLeft().getRow() <= sourceRow + rows)
						&& rangs[i].getTopLeft().getRow() >= sourceRow
						&& rangs[i].getBottomRight().getColumn() >= 0
						&& rangs[i].getBottomRight().getColumn() < cols) {
					sheet.mergeCells(rangs[i].getTopLeft().getColumn(),
							rangs[i].getTopLeft().getRow() + rows + margin, rangs[i]
									.getBottomRight().getColumn(), rangs[i]
									.getBottomRight().getRow() + rows + margin);
				}
			}

		} catch (Exception e) {

		} 
	}
	
	
	/**
	 * 插入中部数据
	 * @param sheet 插入数据的sheet
	 * @param map 插入数据的键值对
	 * @param rownum 插入数据的行下标
	 */
	public static void insertRowData(WritableSheet sheet, Map map, int rownum) {
		Set set = map.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Cell cells[] = sheet.getRow(rownum);
			for (int i = 0; i < cells.length; i++) {
				if (cells[i].getType() == CellType.LABEL) {
					String name = cells[i].getContents().toUpperCase().trim();
					if (name.equalsIgnoreCase("%" + key + "%")) {
						Label lb = (Label) cells[i];
						String value = map.get(key) == null ? "" : (String) map
								.get(key);
						lb.setString(value);
					}
				}
			}
		}
	}

	/**
	 * 插入excel头部数据
	 * @param ws 需要插入数据的sheet
	 * @param headMap 头部数据键值对
	 */
	public static void insertHeaderData(WritableSheet ws, Map headMap,Map attrMap) {
		Label lab;
		for (int i = 0; i < ws.getRows(); i++) {
			for (int j = 0; j < ws.getColumns(); j++) {
				Cell cell = ws.getCell(j, i);
				String content = cell.getContents().toUpperCase()
						.toString();
				content = content.trim();
				if(content.matches(".*#.+#.*")){
					String key = content.substring(content.indexOf("#")+1,content.lastIndexOf("#"));
					jxl.format.CellFormat cf = cell.getCellFormat();
					Object con = headMap.get(key);
					if (con != null){
						String value = con.toString();
						value = Matcher.quoteReplacement(value);
						if(pat.matcher(value).matches()&&!(con instanceof String)){
							//df有效精度为14位，超过14位数值显示不正确
							value = df.format(con);
						}
						//需要特殊显示的数据，比如说金额，需要特殊显示格式
						String showStyle = showExcelDataStyle(attrMap,key,headMap);
						if(!"".equals(showStyle)){
							value = showStyle;
						}
						lab = new Label(cell.getColumn(), cell.getRow(),
								content.replaceAll("#"+key+"#", value.trim()), cf);
						try {
							ws.addCell(lab);
						} catch (RowsExceededException e) {

						} catch (WriteException e) {
						
						}
					}
				}
			}
		}
	}

	/**
	 * 插入excel尾部数据
	 * @param ws 需要插入数据的sheet
	 * @param headMap 尾部数据键值对
	 */
	public static void insertFootData(WritableSheet ws, Map footMap,Map attrMap) {
		
		Label lab;
		for (int i = 0; i < ws.getRows(); i++) {
			for (int j = 0; j < ws.getColumns(); j++) {
				Cell cell = ws.getCell(j, i);
				String content = cell.getContents().toUpperCase()
						.toString();
				content = content.trim();
				if(content.matches(".*#.+#.*")){
					String key = content.substring(content.indexOf("#")+1,content.lastIndexOf("#"));
					jxl.format.CellFormat cf = cell.getCellFormat();
					Object con = footMap.get(key);
					if (con != null){
						String value = con.toString();
						value = Matcher.quoteReplacement(value);
						if(pat.matcher(value).matches()&&!(con instanceof String)){
							value = df.format(con);
						}
						//需要特殊显示的数据，比如说金额，需要特殊显示格式
						String showStyle = showExcelDataStyle(attrMap,key,footMap);
						if(!"".equals(showStyle)){
							value = showStyle;
						}
						lab = new Label(cell.getColumn(), cell.getRow(),
								content.replaceAll("#"+key+"#", value.trim()), cf);
						try {
							ws.addCell(lab);
						} catch (RowsExceededException e) {

						} catch (WriteException e) {
						
						}
					}
				}
			}
		}
	}
	/**
	 * 获取double数值
	 * @param map
	 * @param key
	 * @return
	 */
	private static double getDoubleValue(Map map,String key){
		if(null == map.get(key)){
			return 0.0;
		}
		if(map.get(key) instanceof Double){
			return (Double)map.get(key); 
		}
		if(map.get(key) instanceof BigDecimal){
			return ((BigDecimal)map.get(key)).doubleValue(); 
		}
		if(map.get(key) instanceof String){
			return Double.parseDouble((String)map.get(key));
		}
		return 0.0;
	}

	/**
	 * 复制一个sheet
	 */
	public static void copySheet(ExcelSTLWrite excelSTLWr,int sourceIndex,int targetIndex,int suffix) throws Exception{
		
		WritableSheet sheet = excelSTLWr.getWorkSheet(sourceIndex);
		
		String sheetName = sheet.getName()+"_"+suffix;

		WritableSheet sheetNew = excelSTLWr.getNewSheet(sheetName,targetIndex);
		
		ArrayList list = new ArrayList();
		for (int row = 0; row < sheet.getRows(); row++) {
			for (int col = 0; col < sheet.getColumns(); col++) {
				list.add(excelSTLWr.getLocalCell(sheet, col, row));
			}
		}
		
		for (int row = 0; row < sheet.getRows(); row++) {
			for (int col = 0; col < sheet.getColumns(); col++) {
				for (int i = 0; i < list.size(); i++) {
					ExcelCell tmpCell = (ExcelCell) list.get(i);
					ExcelCell cellClone = tmpCell.copyCell();
					excelSTLWr.setCell(sheetNew, cellClone, ICellType.STRING);
				}
			}
		}

		Range rangs[] = sheet.getMergedCells();
		for (int i = 0; i < rangs.length; i++) {
			sheetNew.mergeCells(rangs[i].getTopLeft().getColumn(), rangs[i]
					.getTopLeft().getRow(), rangs[i].getBottomRight()
					.getColumn(), rangs[i].getBottomRight().getRow());
		}
	}
	
	/**
	 * 删除某一行
	 * @param ws
	 * @param rownum
	 */
	public static void deleteRow(WritableSheet ws, int rownum) {
		ws.removeRow(rownum);
	}
	/**
	 *  删除多行
	 * @param ws 
	 * @param startRow 开始行下标
	 * @param endRow 终止行下标
	 */
	public static void removeRangeRows(WritableSheet ws, int startRow ,int endRow) {
		if (startRow > endRow) {
			return;
		}
		for(int i=startRow; i <= endRow; i++) {
			ws.removeRow(startRow);
		}
	}
	
	public static void removeSheet(String filePath,String sheetIndex){
		WritableWorkbook writeBook = null;
		Workbook workbook = null;
		try {
			workbook =  Workbook.getWorkbook(new File(filePath));
			if(workbook.getSheets().length>1){
				writeBook  =  Workbook.createWorkbook(new File(filePath),workbook);
				
				int flag = 0 ;
				String[] sheetIndexs = sheetIndex.split(",");
				for(int i = 0 ; i < sheetIndexs.length ; i ++ ){
					if(writeBook.getNumberOfSheets() > Integer.parseInt(sheetIndexs[i])){
						writeBook.removeSheet(Integer.parseInt(sheetIndexs[i])+flag);
						flag ++;
					}
				}
				
				
				
				writeBook.write();
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null != workbook){
				workbook.close();
			}
			if(null != writeBook){
				try {
					writeBook.close();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}