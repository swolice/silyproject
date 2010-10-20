package sample.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.report.excel.support.ReportToExcel;

public class notice01 implements IReportLocic,Runnable{
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
		dataMap.put("ACCT_CODE", "TEST001");
		dataMap.put("BILL_SCORE", "1000");
		dataMap.put("NOW_DATE", "2010/07/12");
		dataMap.put("COUNT_ORDER_NUM", "1000");
		excel.pageHeadFetchData(dataMap, sidx);
		
		List dataList = new ArrayList();
		
		dataMap = new HashMap();
		dataMap.put("FRI_AREA_NAME", "测试");
		dataMap.put("ACCT_NAME_ONE", "TEST001");
		dataMap.put("T_ITEM_M_002", "1000");
		dataMap.put("T_ITEM_M_003", "1000");
		dataList.add(dataMap);
		
		dataMap = new HashMap();
		dataMap.put("FRI_AREA_NAME", "测试2");
		dataMap.put("ACCT_NAME_ONE", "TEST002");
		dataMap.put("T_ITEM_M_002", "2000");
		dataMap.put("T_ITEM_M_003", "2000");
		dataList.add(dataMap);
		
//		excel.pageBodyFecthData(dataList, sidx);
	      
		dataMap = new HashMap();
		dataMap.put("ALL_COUNT_MONEY", "100000.0908");
//		excel.pageFootFecthData(dataMap, sidx);
		
	}

	public void setExcelSTLWrite(ExcelSTLWrite _excelSTLWr, int _sidx) {
		excel.setExcelSTLWrite(_excelSTLWr);
		this.sidx = _sidx;
		excel.setExcelTempRptXml("F:/crmwork/20100308Accout/CRMXML/xmltemp/notice-01.xml");
	}

	public void setReportToExcel(ReportToExcel _excel, int _sidx) {
	}

}
