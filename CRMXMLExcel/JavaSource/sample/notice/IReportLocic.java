package sample.notice;

import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.report.excel.support.ReportToExcel;

public interface IReportLocic{
	public boolean isFinish();
	public void process();
	public void setReportToExcel(ReportToExcel _excel,int _sidx);
	public void setExcelSTLWrite(ExcelSTLWrite _excelSTLWr,int _sidx);
}
