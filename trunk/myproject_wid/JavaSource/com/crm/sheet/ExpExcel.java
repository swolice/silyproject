package com.crm.sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.CellType;
import jxl.Range;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author sily
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ExpExcel {

	public static void main(String[] args) {
		FileOutputStream outExl = null;
		FileInputStream fis = null;
		WritableWorkbook wwb = null;
		WritableSheet sheet = null;
		try {
			fis = new FileInputStream(new File("d:/IP_xn_yw_jf.xls"));
			Workbook rwb = Workbook.getWorkbook(fis);
			wwb = Workbook.createWorkbook(new File("d:/IP_xn_yw_jf1.xls"), rwb);
			sheet = wwb.getSheet(0);
			copyRow(sheet, 8, 11);
			sheet.removeRow(8);
			copyRow(sheet, 7, 8);
			copyRow(sheet, 8, 9);
			copyRow(sheet, 9, 10);
		
			Map mapHead = new HashMap();
			mapHead.put("ORDER_NBR","20091092411313");
			mapHead.put("CUST_NAME","客户名称");
			insertHeaderData(sheet,mapHead);
			
			Map mapBody = new HashMap();
			mapBody.put("STRNUM", "1");
			mapBody.put("AREA", "2222");
			mapBody.put("SERV_NBR", "3333");
			mapBody.put("CIRCUIT_DIRECT_TYPE", "343");
			mapBody.put("LOCAL_CIR_TYPE", "111");
			mapBody.put("SPEEP_INPUT", "21321");
			mapBody.put("CIRCUIT_LOCAL_NBR", "1222");
			mapBody.put("SPEED_PE_PORT", "2222");
			mapBody.put("CONNECT_IP_CE", "212312");
			mapBody.put("CONNECT_IP_PE", "3333");
			mapBody.put("FRI_TER_USER", "123");
			mapBody.put("USER_ADDRESS", "1231");
			mapBody.put("FRI_TER_LINK_MAN", "3333");
			mapBody.put("FRI_TER_TEL", "3333");
			mapBody.put("RD", "3333");
			mapBody.put("RT_IMPORT", "3333");
			mapBody.put("RT_EXPORT", "3333");
			mapBody.put("ORDER_DATE", "3333");
			mapBody.put("ORDER_ACT_DATE", "3333");
			mapBody.put("LINE_COMMENT", "3333");
			insertRowData(sheet, mapBody, 7);
			insertRowData(sheet, mapBody, 8);
			insertRowData(sheet, mapBody, 9);
			insertRowData(sheet, mapBody, 10);
			
			Map mapFoot = new HashMap();
			mapFoot.put("ORDER_COMMENT","订单内容");
			insertFootData(sheet,mapFoot);
			
			wwb.write();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != wwb)
					wwb.close();
				if (null != outExl)
					outExl.close();
				if (null != fis)
					fis.close();
			} catch (WriteException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void copyRow(WritableSheet sheet, int sourceRow, int toRow) {
		try {
			Cell cells[] = sheet.getRow(sourceRow);
			sheet.insertRow(toRow);
			sheet.setRowView(toRow, sheet.getRowHeight(sourceRow));
			for (int i = 0; i < cells.length; i++) {
				if (cells[i].getType() == CellType.EMPTY) {
					try {
						jxl.write.WritableCellFormat wcsB = new jxl.write.WritableCellFormat();
						wcsB.setBorder(jxl.format.Border.ALL,
								jxl.format.BorderLineStyle.THIN);
						Label lb = new Label(cells[i].getColumn(), toRow, "",
								wcsB);
						sheet.addCell(lb);
					} catch (RowsExceededException e) {
						e.printStackTrace();
					} catch (WriteException e) {
						e.printStackTrace();
					}
				}
				if (cells[i].getType() == CellType.LABEL) {
					Label wc = (Label) cells[i];
					Label wc1 = (Label) wc.copyTo(cells[i].getColumn(), toRow);
					wc1.setCellFormat(wc.getCellFormat());
					sheet.addCell(wc1);
				}
			}
			Range rangs[] = sheet.getMergedCells();
			for (int i = 0; i < rangs.length; i++) {
				if (rangs[i].getTopLeft().getRow() == sourceRow) {
					Range range = sheet.mergeCells(rangs[i].getTopLeft().getColumn(), toRow,
							rangs[i].getBottomRight().getColumn(), toRow);
					Cell cell = range.getTopLeft();
					jxl.write.WritableCellFormat wcsB = new jxl.write.WritableCellFormat(cell.getCellFormat());
					wcsB.setBorder(jxl.format.Border.ALL,
							jxl.format.BorderLineStyle.THIN);
					Label lb = new Label(cell.getColumn(), cell.getRow(),cell.getContents(),
							wcsB);
					sheet.addCell(lb);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertRowData(WritableSheet sheet, Map map, int rownum) {
		Set set = map.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Cell cells[] = sheet.getRow(rownum);
			for (int i = 0; i < cells.length; i++) {
				if (cells[i].getType() == CellType.LABEL) {
					String name = cells[i].getContents().toUpperCase().trim();
					if (name.equals("%" + key + "%")) {
						Label lb = (Label) cells[i];
						String value = map.get(key) == null ? "" : (String) map
								.get(key);
						lb.setString(value);
					}
				}
			}
		}
	}

	public static void insertHeaderData(WritableSheet ws, Map headMap) {
		Label lab;
		Set set = headMap.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			for (int i = 0; i < ws.getRows(); i++) {
				for (int j = 0; j < ws.getColumns(); j++) {
					Cell cell = ws.getCell(j, i);
					String content = cell.getContents().toUpperCase()
							.toString();
					content = content.trim();
					String value = "#" + key + "#";
					if (value.equals(content)) {
						jxl.format.CellFormat cf = cell.getCellFormat();
						Object con = headMap.get(key);
						if (con != null)
							lab = new Label(cell.getColumn(), cell.getRow(),
									con.toString(), cf);
						else
							lab = new Label(cell.getColumn(), cell.getRow(),
									"", cf);
						try {
							ws.addCell(lab);
						} catch (RowsExceededException e) {
							e.printStackTrace();
						} catch (WriteException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	public static void insertFootData(WritableSheet ws, Map footMap) {
		Label lab;
		Set set = footMap.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			for (int i = ws.getRows() - 1; i > -1; i--) {
				for (int j = ws.getColumns() - 1; j >-1; j--) {
					Cell cell = ws.getCell(j, i);
					String content = cell.getContents().toUpperCase()
							.toString();
					content = content.trim();
					String value = "#" + key + "#";
					if (value.equals(content)) {
						jxl.format.CellFormat cf = cell.getCellFormat();
						Object con = footMap.get(key);
						if (con != null)
							lab = new Label(cell.getColumn(), cell.getRow(),
									con.toString(), cf);
						else
							lab = new Label(cell.getColumn(), cell.getRow(),
									"", cf);
						try {
							ws.addCell(lab);
						} catch (RowsExceededException e) {
							e.printStackTrace();
						} catch (WriteException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public static void deleteRow(WritableSheet ws, int rownum) {
		ws.removeRow(rownum);
	}
}