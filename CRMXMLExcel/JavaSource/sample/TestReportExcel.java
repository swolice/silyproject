package sample;

import java.util.List;

import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.cthq.crm.excel.write.ExcelCell;
import com.cthq.crm.report.excel.support.AbstractReportToExcel;

public class TestReportExcel extends AbstractReportToExcel {
	private int bt2InitRow= -1;

	public void paintBT2(){
		String sectionNm = "bodynm";
		List cellStyleList = (List)rptXmlRd.getReportMap(sectionNm).get("CellStyle");
		for (int i = 0; i < cellStyleList.size(); i++) {
			ExcelCell tmpCell = (ExcelCell) cellStyleList.get(i);
			if (tmpCell.getInitLab().indexOf("BT2") > 0) {
				if (bt2InitRow == -1) {
					bt2InitRow = tmpCell.getCurLocRow();
				} else {
					WritableSheet sheet = excelSTLWr.getWorkSheet(0);
					try {
						String finishFlg = (String)rptXmlRd.getReportMap(sectionNm).get("FINISH");
						if (finishFlg == null) {
							sheet.mergeCells(tmpCell.getCurLocCol(), bt2InitRow, tmpCell.getCurLocCol(), tmpCell.getCurLocRow()-1);
						} else{
							sheet.mergeCells(tmpCell.getCurLocCol(), bt2InitRow, tmpCell.getCurLocCol(), tmpCell.getCurLocRow());
						}
						ExcelCell bt1 = super.searchExcelCell(cellStyleList, "BT1");
						if (bt1 != null){
							if (finishFlg == null) {
								sheet.mergeCells(bt1.getCurLocCol(), bt2InitRow, bt1.getCurLocCol(), bt1.getCurLocRow()-1);
							} else{
								sheet.mergeCells(bt1.getCurLocCol(), bt2InitRow, bt1.getCurLocCol(), bt1.getCurLocRow());
							}
						}
						bt2InitRow = tmpCell.getCurLocRow();
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
