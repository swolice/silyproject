/*
 * 创建日期： 2009-11-19
 *
 */
package report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author 吉仕军
 *	
 */
public class expExcelTemp {

	public static String sourceExcel = "D:\\我的桌面\\4008.xls";
	public static String outExcel = sourceExcel + "." +System.currentTimeMillis()+".xls";
	public static String outXml = sourceExcel + "." +System.currentTimeMillis()+".xml";
	
	public static void main(String args[]){
		outExcelTemp(1,17);
		outXmlTemp(1,17);
	}
	/**
	 * 
	 * @param startrow 起始行下标
	 * @param endrow 结束行的行号
	 */
	public static void outExcelTemp(int startrow,int endrow){
		FileInputStream fis = null;
		WritableWorkbook wwb = null;
		WritableSheet sheet = null;
		
		try {
			fis = new FileInputStream(new File(sourceExcel));
			Workbook rwb = Workbook.getWorkbook(fis);
			wwb = Workbook.createWorkbook(new File(outExcel));
			Sheet readsheet = rwb.getSheet(0);
			int tempCol = 0;
			sheet = wwb.createSheet(""+tempCol,0);
			for (int i = startrow; i < endrow; i++) {
				Cell cell = readsheet.getCell(0,i);
				if(i == startrow){
					if(cell.getType() != CellType.EMPTY){
						jxl.write.WritableCellFormat wcsB = new jxl.write.WritableCellFormat(
								cell.getCellFormat());
						Label label = new Label(tempCol,0,"序号",wcsB);
						sheet.addCell(label);
					}
					Cell cell1 = readsheet.getCell(2,i);
					if(cell1.getType() != CellType.EMPTY){
						jxl.write.WritableCellFormat wcsB1 = new jxl.write.WritableCellFormat(
								cell1.getCellFormat());
						Label label1 = new Label(tempCol,1,"%STRNUM%",wcsB1);
						sheet.addCell(label1);
					}
					tempCol++;
				}
				if(cell.getType() != CellType.EMPTY){
					jxl.write.WritableCellFormat wcsB = new jxl.write.WritableCellFormat(
							cell.getCellFormat());
					Label label = new Label(tempCol,0,cell.getContents().toString(),wcsB);
					sheet.addCell(label);
				}
				Cell cell1 = readsheet.getCell(2,i);
				if(cell1.getType() != CellType.EMPTY){
					jxl.write.WritableCellFormat wcsB1 = new jxl.write.WritableCellFormat(
							cell1.getCellFormat());
					Label label1 = new Label(tempCol,1,"%"+cell1.getContents().toString()+"%",wcsB1);
					sheet.addCell(label1);
				}
				tempCol++;
			}
			wwb.write();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != wwb)
					wwb.close();
				if (null != fis)
					fis.close();
			} catch (WriteException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public static void outXmlTemp(int startrow,int endrow){
		FileInputStream fis = null;
		WritableSheet sheet = null;
		
		try {
			fis = new FileInputStream(new File(sourceExcel));
			Workbook rwb = Workbook.getWorkbook(fis);
			Sheet readsheet = rwb.getSheet(0);
			StringBuffer sb  = new StringBuffer();
			for (int i = startrow; i < endrow; i++) {
				Cell cell = readsheet.getCell(2,i);
				if(cell.getType() != CellType.NUMBER){
					String str = cell.getContents().toString().trim();
					if("".equals(str)){
						continue;
					}
					sb.append("<"+str+">{"+str+"}</"+str+">" + "\r\n");
				}
			}
			FileWriter fw = new FileWriter(outXml);
			fw.write(sb.toString());
			fw.flush();
			fw.close();
			rwb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if (null != fis)
					fis.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	
}
