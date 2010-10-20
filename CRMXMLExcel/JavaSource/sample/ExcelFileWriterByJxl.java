package sample;
import java.util.ArrayList;

import jxl.Range;
import jxl.write.WritableSheet;

import com.cthq.crm.excel.write.ExcelCell;
import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.excel.write.ICellType;

/**
 * JXL を使用してエクセルファイルを書き込むサンプルです。
 * 
 * [必要ライブラリ]
 * jxl.jar download ⇒ http://jexcelapi.sourceforge.net/
 * 
 */
public class ExcelFileWriterByJxl {

    /**
     * メインメソッド
     * @param args
     */
    public static void main(String[] args) {
        ExcelFileWriterByJxl writer = new ExcelFileWriterByJxl();
        writer.write();
    }

    /**
     * エクセルファイルを書き込みます。
     * 
     */
    public void write() {
    	ExcelSTLWrite excelSTLWr = new  ExcelSTLWrite();
        try {
        	
			String xlsTempFile = "d:/我的桌面/output.xls";
			String xlsDescFile = "d:/我的桌面/a.xls";
			excelSTLWr.init(xlsTempFile, xlsDescFile);
            WritableSheet sheet = excelSTLWr.getWorkSheet(2);
            Range[] range = sheet.getMergedCells();
  //          SheetRangeImpl kk = (SheetRangeImpl)range[0];
//            int len = range.length;
//            ArrayList rangelist = new ArrayList();
//            for(int i=0; i< len; i++) {
//            	int row0 =range[i].getBottomRight().getRow();
//            	int row1 = range[i].getTopLeft().getRow();
//            	if (row0 >5 && row1 > 5) {
//            		rangelist.add(range[i]);
//            	}
//            }
//            sheet.getCell(0.
            ArrayList list = new ArrayList();
    		for(int row=2; row <= 2+0; row++) {
    			for(int col=0;col<=3; col++) {
    					list.add(excelSTLWr.getLocalCell(sheet, col, row));
    			}
    		}
    		int count = 0;
    		for(int row=3; row <=5; row++) {
    			count = count + 1; 
    			for(int col =0; col <= 3; col++) {
    				for(int i=0;i<list.size(); i++) {
        				ExcelCell tmpCell = (ExcelCell)list.get(i);
        				if (tmpCell.getLocCol() == col && tmpCell.getLocRow()+count==row) {
        					ExcelCell cell = tmpCell.copyCell();
        					cell.setLocRow(row);
            				excelSTLWr.setCell(sheet, cell, ICellType.STRING);
        				}
        			}
    			}
    		
    		}
//    		sheet.setMergedCells(range);
//    		int size = rangelist.size();
//    		for(int i=0; i < size; i++) {
//    			Range ranget=(Range)rangelist.get(i);
//    			int arg0 = ranget.getTopLeft().getColumn();
//    			int arg1 = ranget.getTopLeft().getRow()+35;
//    			int arg2 = ranget.getBottomRight().getColumn();
//    			int arg3 = ranget.getBottomRight().getRow()+35;
//    			sheet.mergeCells(arg0, arg1, arg2, arg3);
//    		}
    		//sheet.mergeCells(8, 37, 8, 40);
//    		sheet.mergeCells(1, 36, 1, 61);
    		
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	 excelSTLWr.dispose();
//            if (gWriteBook != null) {
//                try {
//                    // 必ず finally で close します。
//                	gWriteBook.close();
//                } catch (Exception e) {
//                }
//            }
        }
    }
}
