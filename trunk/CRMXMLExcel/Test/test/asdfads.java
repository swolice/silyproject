/*
 * Created on 2010-6-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import jxl.write.WritableSheet;

import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.excel.write.ICellType;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class asdfads {

	public static void main(String[] args) {
//		finishBatch-test.xls
		ExcelSTLWrite excel = new ExcelSTLWrite();
		try {
			excel.init("G:\\tmp\\finishBatch-test.xls" , "G:\\tmp\\finishBatch-test1.xls");

			WritableSheet sheet = excel.getWorkSheet(0);
			excel.setCell(sheet, 1,1,"%坎坎坷坷1%",ICellType.STRING);
			excel.setCell(sheet, 2,1,"%坎坎坷坷2%",ICellType.STRING);
			excel.setCell(sheet, 3,1,"%坎坎坷坷3%",ICellType.STRING);
			excel.setCell(sheet, 4,1,"%坎坎坷坷4%",ICellType.STRING);
			excel.setCell(sheet, 5,1,"%坎坎坷坷5%",ICellType.STRING);
			excel.setCell(sheet, 6,1,"%坎坎坷坷6%",ICellType.STRING);
			excel.dispose();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
