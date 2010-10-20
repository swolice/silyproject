/*
 * Created on 2010-6-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import java.io.File;

import jxl.Workbook;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Copy {

	/**
	 * main 儊僜僢僪丅
	 * 
	 * @param args 堷悢
	 * @throws Exception 僔僗僥儉僄儔乕偑敪惗偟偨応崌
	 */
	public static void main(String[] args) throws Exception {
		
		String inFilePath  = "c:/finishBatch-test.xls";
		Workbook inBook = Workbook.getWorkbook(new File(inFilePath));
		
		File file = new File("c:/chapter42.xls");
		WritableWorkbook workbook = Workbook.createWorkbook(file, inBook);
		
		WritableSheet sheet = workbook.getSheet(0);

		// ----------------------------------------------------------
		// 僙儖偺僐僺乕
		// ----------------------------------------------------------
		// 僐僺乕尦偺僙儖傪庢摼
		WritableCell cell = sheet.getWritableCell(2, 3);
		for(int i=4;i < 10; i++) {
			WritableCell copy = cell.copyTo(2, i);
			sheet.addCell(copy);
		}
	

	
		// 廔椆張棟
		workbook.write();
		workbook.close();
		
		System.out.println("Chapter42 done!");
	}
}
