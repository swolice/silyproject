package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.report.excel.support.ExpExcelOperator;

public class ExcelWrite {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// ReportDataXmlToExcelFile rdxtf = new ReportDataXmlToExcelFile();
		// Map map = new HashMap();
		// map.put("ADDRESS", "beijing china");
		// map.put("NAME", "xixi");
		// map.put("STRNUM", "111");
		// // String tempxml =
		// "E:/myeclipseworkspace/CRMXML/JavaSource/test/example.xml";
		// String tempxml =
		// "E:/myeclipseworkspace/CRMXML/JavaSource/test/example.xml";
		// // String tempExl = "D:/我的桌面/output.xls";
		// String tempExl = "d:/SwitchboardList.xls";
		// List list = new ArrayList();
		// list.add(map);
		// list.add(map);
		// list.add(map);
		// list.add(map);
		// list.add(map);
		// String ss =
		// rdxtf.getOutExcelWithMultiLine(tempExl,tempxml,list,null,null,10);
		//		
		// System.out.println(ss);

//		copysheetImpot();
		copysheet();
		// String aa = "#XUYAOTIHUAN#";
		// System.out.println(aa.matches(".*#.+#.*"));
		// System.out.println(aa.substring(aa.indexOf("#")+1,aa.lastIndexOf("#")));
	}

	public static void copysheet() {
		Workbook rwb = null;
		FileInputStream fis = null;
		WritableWorkbook wwb = null;
		BufferedReader bufferedReader = null;
		ExcelSTLWrite excelSTLWr = new ExcelSTLWrite();
		String outExcelFile = "d:/我的桌面/a.xls";
		String excelTempFile = "d:/我的桌面/output.xls";

		try {
			int index = 2;

			excelSTLWr.init(excelTempFile, outExcelFile);
			wwb = excelSTLWr.getGWriteBook();
			// 创建xml解析器
			WritableSheet sheet = excelSTLWr.getWorkSheet(index);
			// 删除除模板外其他的sheet
			int sheets = wwb.getNumberOfSheets();
			// 复制工作表
			// for(int i=1;i<sheets;i++){
			// wwb.copySheet(2, sheet.getName() + 1, sheets);
			// wwb.moveSheet(sheets, 0);
			int z = 1;
			String sheetName = sheet.getName() + (z++);
//
			int newindex = index + 1;
//			wwb.copySheet(index, sheetName, sheets);
			
			wwb.createSheet(sheetName, newindex);

			WritableSheet sheetNew = excelSTLWr.getWorkSheet(newindex);

			System.out.println(sheet.getRows());
			
			ExpExcelOperator.copySheet(excelSTLWr,2,excelSTLWr.getNumberOfSheets(),1);
			ExpExcelOperator.copySheet(excelSTLWr,2,excelSTLWr.getNumberOfSheets(),2);
			
			
			

//			ArrayList list = new ArrayList();
//			for (int row = 0; row < sheet.getRows(); row++) {
//				for (int col = 0; col < sheet.getColumns(); col++) {
//					list.add(excelSTLWr.getLocalCell(sheet, col, row));
//				}
//			}
			
			
			
//			for (int row = 0; row < sheet.getRows(); row++) {
//				for (int col = 0; col < sheet.getColumns(); col++) {
//					for (int i = 0; i < list.size(); i++) {
//						ExcelCell tmpCell = (ExcelCell) list.get(i);
//						ExcelCell cellClone = tmpCell.copyCell();
//						excelSTLWr.setCell(sheetNew, cellClone, ICellType.STRING);
//					}
//				}
//			}
//
//			Range rangs[] = sheet.getMergedCells();
//			for (int i = 0; i < rangs.length; i++) {
//				sheetNew.mergeCells(rangs[i].getTopLeft().getColumn(), rangs[i]
//						.getTopLeft().getRow(), rangs[i].getBottomRight()
//						.getColumn(), rangs[i].getBottomRight().getRow());
//			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			excelSTLWr.dispose();
			try {
				if (null != rwb)
					rwb.close();
				if (null != fis)
					fis.close();
				if (null != bufferedReader)
					bufferedReader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void copysheetImpot() {
		Workbook rwb = null;
		FileInputStream fis = null;
		WritableWorkbook wwb = null;
		BufferedReader bufferedReader = null;
		ExcelSTLWrite excelSTLWr = new ExcelSTLWrite();
		String outExcelFile = "d:/我的桌面/a.xls";
		String excelTempFile = "d:/我的桌面/output.xls";

		try {
			// 老方法，但是它的拷贝函数有问题，在得到拷贝的时候，文字的样式会丢失,因为它不支持分散对齐的格式
			Workbook wb = Workbook.getWorkbook(new File(excelTempFile));
//			WorkbookSettings worksetting = new WorkbookSettings();

			// 如果出现乱码，请使用下面的任一代码
//			 worksetting.setEncoding("ISO-8859-1");
//			 worksetting.setLocale(Locale.getDefault());
			// 打开一个文件的副本，并且指定数据写回到原文件
			WritableWorkbook book = Workbook.createWorkbook(new File(
					outExcelFile), wb);
			// 往工作表填入数据

			/*
			 * 此方法的效果不佳，粗黑边框有一半失效
			 */
//			 book.copySheet(2,"111", 3);
			book.importSheet("1111", 3, wb.getSheet(2));
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
			String errMSG = "导出季度报表的时候Exception[com.neusoft."
					+ "procuratorate.performance.action.assistant."
					+ "getReportContent] --- " + e.getMessage();
			String userinfo = "温馨提示：在你导出季度报表的时候出现了异常，请联系管理员检查季度报表模板是否是空文件.";
		}
	}


}
