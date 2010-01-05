/*
 * Created on 2009-11-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.crm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

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
public class ExpExcel1 {

	public static void main(String[] args) {

		FileOutputStream outExl = null;
		FileInputStream fis = null;
		WritableWorkbook wwb = null;

		try {
			outExl = new FileOutputStream("d:/data_excel.xls");
			fis = new FileInputStream(new File("d:/yy_adsl_quote.xls"));
			Workbook rwb = Workbook.getWorkbook(fis);
			wwb = Workbook.createWorkbook(outExl, rwb);
			while (wwb.getNumberOfSheets() > 1) {
				wwb.removeSheet(1);
			}
			for (int i = 1; i < 11; i++) {
				wwb.copySheet(0, "报价单" + i, i);
			}
			for (int k = 0; k < wwb.getNumberOfSheets(); k++) {
				WritableSheet ws = wwb.getSheet(k);
				for (int i = 6; i < 10006; i++) {
					for (int j = 0; j < 20; j++) {
						Label lable1 = new Label(j, i, "我本布衣，躬耕于南阳" + i * j
								+ "我本布衣，躬耕于南阳");
						try {
							ws.addCell(lable1);
						} catch (RowsExceededException e1) {
							e1.printStackTrace();
						} catch (WriteException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			System.out.println("---");
			wwb.write();
			System.out.println("---");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				wwb.close();
				System.out.println("wwb");
				outExl.close();
				System.out.println("outExl");
				fis.close();
				System.out.println("fis");
			} catch (WriteException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public static void expCsv() throws IOException {
		Writer write = new FileWriter("d:/data_excel_csv.csv");
		String str = null;
		for (int i = 6; i < 50006; i++) {
			for (int j = 0; j < 20; j++) {
				if (j == 19) {
					str = "我本布衣，躬耕于南阳" + i * j + "我本布衣，躬耕于南阳 + \r\n";
				} else {
					str = "我本布衣，躬耕于南阳" + i * j + "我本布衣，躬耕于南阳,";
				}
				write.write(str);
			}
		}
	}

	public static void test() {
		FileOutputStream outExl = null;
		FileInputStream fis = null;
		WritableWorkbook wwb = null;

		try {
			outExl = new FileOutputStream("d:/data_excel.xls");
			fis = new FileInputStream(new File("d:/yy_adsl_quote.xls"));
			Workbook rwb = Workbook.getWorkbook(fis);
			wwb = Workbook.createWorkbook(outExl, rwb);
			while (wwb.getNumberOfSheets() > 1) {
				wwb.removeSheet(1);
			}
			for (int i = 1; i < 10; i++) {
				wwb.copySheet(0, "报价单" + i, i);
			}
			//			long a = System.currentTimeMillis();
			for (int k = 0; k < wwb.getNumberOfSheets(); k++) {
				WritableSheet ws = wwb.getSheet(k);
				for (int i = 6; i < 10006; i++) {
					for (int j = 0; j < 20; j++) {
						Label lable1 = new Label(j, i, "我本布衣，躬耕于南阳" + i * j
								+ "我本布衣，躬耕于南阳");
						try {
							ws.addCell(lable1);
						} catch (RowsExceededException e1) {
							e1.printStackTrace();
						} catch (WriteException e1) {
							e1.printStackTrace();
						}
					}
					System.out.println(k + "---" + i);
				}
			}

			wwb.write();
			//			long b = System.currentTimeMillis();
			//			System.out.println(b - a);
			//			System.out.println("wwb");
			try {
				wwb.close();
				System.out.println("wwb");
				outExl.close();
				System.out.println("outExl");
				fis.close();
				System.out.println("fis");
			} catch (WriteException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void expXls() {
		FileOutputStream outExl = null;
		FileInputStream fis = null;
		WritableWorkbook wwb = null;

		try {
			outExl = new FileOutputStream("d:/data_excel.xls");
			fis = new FileInputStream(new File("d:/yy_adsl_quote.xls"));
			Workbook rwb = Workbook.getWorkbook(fis);
			wwb = Workbook.createWorkbook(outExl, rwb);
			while (wwb.getNumberOfSheets() > 1) {
				wwb.removeSheet(1);
			}
			for (int i = 1; i < 10; i++) {
				wwb.copySheet(0, "报价单" + i, i);
			}
			long a = System.currentTimeMillis();
			for (int k = 0; k < wwb.getNumberOfSheets(); k++) {
				WritableSheet ws = wwb.getSheet(k);
				for (int i = 6; i < 10000; i++) {
					for (int j = 0; j < 20; j++) {
						Label lable1 = new Label(j, i, "我本布衣，躬耕于南阳" + i * j
								+ "我本布衣，躬耕于南阳");
						try {
							ws.addCell(lable1);
						} catch (RowsExceededException e1) {
							e1.printStackTrace();
						} catch (WriteException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			wwb.write();
			long b = System.currentTimeMillis();
			System.out.println(b - a);
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

}
