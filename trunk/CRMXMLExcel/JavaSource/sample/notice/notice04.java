package sample.notice;

import java.util.HashMap;
import java.util.Map;

import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.report.excel.support.ReportToExcel;

public class notice04   implements IReportLocic,Runnable{
	private boolean blnFinished=false;
	private ReportToExcel excel = new ReportToExcel();
	private int sidx =-1;
	public boolean isFinish() {
		// TODO Auto-generated method stub
		return this.blnFinished;
	}
	public void run(){
		process();
		blnFinished = true;
	}
	public void process() {
		Map dataMap = new HashMap();
		dataMap.put("ACCT_NAME", "测试");
		dataMap.put("ZIP_CODE", "TEST001");
		dataMap.put("BILL_ADDR", "1000");
		dataMap.put("NOTICE_NBR", "2010/07/12");
		dataMap.put("MONEY_TYPE", "1000");
		dataMap.put("ACCT_NAME", "蒋峰");
		excel.pageHeadFetchData(dataMap, sidx);
	}
	public void setExcelSTLWrite(ExcelSTLWrite _excelSTLWr, int _sidx) {
		excel.setExcelSTLWrite(_excelSTLWr);
		this.sidx = _sidx;
		excel.setExcelTempRptXml("F:/crmwork/20100308Accout/CRMXML/xmltemp/notice-04.xml");
		
	}
	public void setReportToExcel(ReportToExcel _excel, int _sidx) {
		// TODO Auto-generated method stub
		
	}

}
