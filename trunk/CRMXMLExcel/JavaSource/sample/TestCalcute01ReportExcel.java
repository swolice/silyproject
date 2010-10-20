package sample;

import java.util.List;
import java.util.Map;

import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.cthq.crm.excel.write.ExcelCell;
import com.cthq.crm.report.excel.support.AbstractReportToExcel;

public class TestCalcute01ReportExcel extends AbstractReportToExcel {
	public int bt2InitRow= -1;
	public double bt3 = 0;
	public double bt2 = 1;
	public void bodyMergeCell(String sectionNm){
		List cellStyleList = (List)rptXmlRd.getReportMap(sectionNm).get("CellStyle");
		WritableSheet sheet = excelSTLWr.getWorkSheet(0);
		try {
			String finishFlg = (String)rptXmlRd.getReportMap(sectionNm).get("IS_FINISH");
			ExcelCell bt1 = super.searchExcelCell(cellStyleList, "BT1");
			if (bt1 != null){
				if (bt2InitRow == -1) {
					bt2InitRow = bt1.getCurLocRow();
				}
//				if (finishFlg != null) {
//					sheet.mergeCells(bt1.getCurLocCol(), bt2InitRow, bt1.getCurLocCol(), bt1.getCurLocRow());
//				}
			}
			ExcelCell btc3 = super.searchExcelCell(cellStyleList, "BT3");
			
			bt3 = bt3 + Double.parseDouble(btc3.getCurLocStr());
			Map map = (Map)rptXmlRd.getReportMap(sectionNm).get("CUR_ROW_DATA_MAP");
			String style = (String)map.get("STYLE");
			if (style != null) {
				if (style.equals("1")) {
					ExcelCell bt2 = super.searchExcelCell(cellStyleList, "BT2");
					WritableCell cell = this.excelSTLWr.getWorkSheet(0).getWritableCell(bt2.getCurLocCol(), bt2.getCurLocRow());
					super.excelSTLWr.drawRect(sheet, bt2.getCurLocCol(), bt2.getCurLocRow(), bt2.getCurLocCol(), bt2.getCurLocRow(), BorderLineStyle.THIN, Colour.BLACK, Colour.YELLOW);
					
				}
				if (style.equals("2")) {
					ExcelCell bt2 = super.searchExcelCell(cellStyleList, "BT2");
					WritableCell cell = this.excelSTLWr.getWorkSheet(0).getWritableCell(bt2.getCurLocCol(), bt2.getCurLocRow());
					super.excelSTLWr.drawRect(sheet, bt2.getCurLocCol(), bt2.getCurLocRow(), bt2.getCurLocCol(), bt2.getCurLocRow(), BorderLineStyle.THIN, Colour.BLACK, Colour.BLUE);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void smallsum(String sectionNm){
		List cellStyleList01 = (List)rptXmlRd.getReportMap("body").get("CellStyle");
//		List cellStyleList02 = (List)rptXmlRd.getReportMap("bodygr01").get("CellStyle");
		WritableSheet sheet = excelSTLWr.getWorkSheet(0);
		try {
			String finishFlg = (String)rptXmlRd.getReportMap("body").get("IS_FINISH");
			ExcelCell bt1 = super.searchExcelCell(cellStyleList01, "BT1");
//			bt2InitRow = bt1.getCurLocRow();
//			ExcelCell bg1 = super.searchExcelCell(cellStyleList02, "BG1");
			sheet.mergeCells(bt1.getCurLocCol(), bt2InitRow, bt1.getCurLocCol(), bt1.getCurLocRow());
			
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
}






























