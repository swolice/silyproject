package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.report.excel.support.ExcelReportException;
import com.cthq.crm.report.excel.support.ReportToExcel;

public class payoutNotice {
	public static void main(String args[]) {
		ExcelSTLWrite excelSTLWr = new ExcelSTLWrite();
		  try {
				excelSTLWr.init("G:\\tmp\\payoutNotice.xls", "G:\\tmp\\payoutNoticetest.xls");
			} catch (Exception e) {
				throw new ExcelReportException("initExcel error", e);
			}
			ReportToExcel excel = new ReportToExcel();
			excel.setExcelSTLWrite(excelSTLWr);
			excel.setExcelTempRptXml("F:\\crmwork\\20100308Accout\\CRMXML\\xmltemp\\payoutNotice.xml");
			Map dataMap = new HashMap();
			dataMap.put("ACCT_NAME", "测试");
			dataMap.put("ACCT_CODE", "TEST001");
			dataMap.put("BILL_SCORE", "1000");
			dataMap.put("NOW_DATE", "2010/07/12");
			dataMap.put("COUNT_ORDER_NUM", "1000");
			excel.pageHeadFetchData(dataMap, 0);
			
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
			
			excel.pageBodyFetchData(dataList, 0);
			
			
			
		      
			dataMap = new HashMap();
			dataMap.put("ALL_COUNT_MONEY", "100000.0908");
			excel.pageFootFetchData(dataMap, 0);
			
			excel.dispose();
	}
}
