/*
 * Created on 2009-8-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cthq.crm.order.dao.test;

import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * @author a
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ExcelReader {

	public static void main(String[] args) {
		try {
			//		构建Workbook对象, 只读Workbook对象
			//直接从本地文件创建Workbook
			//		从输入流创建Workbook
			InputStream is = new FileInputStream("E:/开发环境-文档/CRM与运维接口XML数据检测文档.xls");
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet rs = rwb.getSheet(1);
			for (int i = 1; i < 76; i++) {
				Cell c1i = rs.getCell(1, i);
				Cell c2i = rs.getCell(2, i);
				Cell c3i = rs.getCell(3, i);
				Cell c5i = rs.getCell(5, i);
				Cell c6i = rs.getCell(6, i);
				if("".equals(c2i.getContents().trim())){
					continue;
				}
				String tempStr = "<" + c2i.getContents() + " ismust=\"1\"";
				if (!"".equals(c5i.getContents().trim())) {
					tempStr += " isvalidateVal=" + c5i.getContents();
				}
				if (!"".equals(c6i.getContents().trim())) {
					tempStr += " islength=\"" + c6i.getContents() + "\"";
				}
				tempStr += ">" + c1i.getContents() + "</" + c2i.getContents() + ">";
				System.out.println(tempStr);
			}
			rwb.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
