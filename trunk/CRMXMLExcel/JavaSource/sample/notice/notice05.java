package sample.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.report.excel.support.ReportToExcel;

public class notice05 implements IReportLocic,Runnable{
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
		dataMap.put("BILL_SCORE", "20100713");
		dataMap.put("ACCT_NAME", "测试");
		excel.pageHeadFetchData(dataMap, sidx);
		
		List dataList = new ArrayList();
		
		dataMap = new HashMap();
		dataMap.put("ORDER_NBR", "测试");
		dataMap.put("PRO_ID", "TEST001");
		dataMap.put("FRI_ADDR", "1000");
		dataMap.put("LOCAL_NBR_A", "1000");
		dataList.add(dataMap);
		
		dataMap = new HashMap();
		dataMap.put("ORDER_NBR", "测试2");
		dataMap.put("PRO_ID", "TEST002");
		dataMap.put("FRI_ADDR", "10002");
		dataMap.put("LOCAL_NBR_A", "10002");
		dataList.add(dataMap);
		
//		excel.pageBodyFecthData(dataList, sidx);
	      
		dataMap = new HashMap();
		dataMap.put("COUNT_ROWS", "100000.0908");
		dataMap.put("RMB_INTER_AMOUNT_COUNT", "100000.0908");
		dataMap.put("RMB_LOOP_AMOUT_COUNT", "100000.0908");
		dataMap.put("RMB_LOCAL_A_AMOUNT_COUNT", "100000.0908");
//		excel.pageFootFecthData(dataMap, sidx);
		
	}
	public void setExcelSTLWrite(ExcelSTLWrite _excelSTLWr, int _sidx) {
		excel.setExcelSTLWrite(_excelSTLWr);
		this.sidx = _sidx;
		excel.setExcelTempRptXml("F:/crmwork/20100308Accout/CRMXML/xmltemp/notice-05.xml");
		
	}
	public void setReportToExcel(ReportToExcel _excel, int _sidx) {
		// TODO Auto-generated method stub
		
	}
}
