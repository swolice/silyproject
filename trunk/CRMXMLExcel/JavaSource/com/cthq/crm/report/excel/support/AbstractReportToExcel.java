package com.cthq.crm.report.excel.support;

import java.util.List;

import com.cthq.crm.excel.write.ExcelCell;
import com.cthq.crm.report.xml.support.ReportExcelXmlReader;

public abstract class AbstractReportToExcel extends ComplexReportToExcel
		implements IReportPaint {
	protected ExcelCell searchExcelCell(List cellStyleList, String varLab){
		for (int i = 0; i < cellStyleList.size(); i++) {
			ExcelCell tmpCell = (ExcelCell) cellStyleList.get(i);
			if (tmpCell.getInitLab().indexOf(varLab) > 0) {
				return tmpCell;
			}
		}	
		return null;
	}
}
