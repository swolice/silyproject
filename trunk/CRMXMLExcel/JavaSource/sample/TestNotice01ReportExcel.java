package sample;

import java.util.List;

import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.cthq.crm.excel.write.ExcelCell;
import com.cthq.crm.report.excel.support.AbstractReportToExcel;

public class TestNotice01ReportExcel extends AbstractReportToExcel {
	private int bt2InitRow= -1;
	public void output1(){
		System.out.println("成功1111111111111111");
//		WritableSheet sheet = excelSTLWr.getWorkSheet(0);
//		sheet.mergeCells(col1, row1, col2, row2)
	}
	public void paintBody(String sectionNm){
////		System.out.println("2323");
////		String sectionNm = "bodynm";
//		WritableSheet sheet = excelSTLWr.getWorkSheet(0);
//		String lastRow = (String)rptXmlRd.getReportMap(sectionNm).get("OPER_LAST_ROW");
//		if (lastRow == null) {
//			return;
//		}
//		rptXmlRd.getReportMap(sectionNm).put("BODY_MARGIN_ROWS", "1");
	}
}






























