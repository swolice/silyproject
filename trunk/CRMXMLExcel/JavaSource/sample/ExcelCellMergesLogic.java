package sample;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;

import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.report.excel.support.ReportToExcel;

public class ExcelCellMergesLogic implements Runnable {
	private ReportToExcel excel = new ReportToExcel();
	private int sidx =-1;
	public boolean blnFinished = false;
	public void setReportToExcel(ReportToExcel _excel,int _sidx) {
		this.excel = _excel;
		this.sidx = _sidx;
	}
	public void setExcelSTLWrite(ExcelSTLWrite _excelSTLWr,int _sidx) {
		excel.setExcelSTLWrite(_excelSTLWr);
		this.sidx = _sidx;
		excel.setExcelTempRptXml("F:/crmwork/20100308Accout/CRMXML/xmltemp/testreport.xml");
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		System.out.println("开始");
		  
		  Map _dataHeadMap = new HashMap();
		  _dataHeadMap.put("YSNY", "201009");
		  _dataHeadMap.put("DYRQ", "test");
		  excel.pageHeadFetchData(_dataHeadMap, sidx);
//		  %STRNUM%	%CUST_DEPT_NAME%	%NEW_CUST_ID%	%NEW_CUST_NAME%	%CYCLE_TYPE_NAME%	%KDXSR%	%FKDXSR%	%ALL_RMB%	%ALL_US%
		  List list = new ArrayList();
		  
		  Map _dataMap = new HashMap();
		  _dataMap = new HashMap();
		  _dataMap.put("CUST_DEPT_NAME", "测试1" +sidx);
		  _dataMap.put("NEW_CUST_ID", "JT2010001");
		  _dataMap.put("NEW_CUST_ID1", "JT2010001-01");
		  list.add(_dataMap);
		  
		   _dataMap = new HashMap();
		  _dataMap.put("CUST_DEPT_NAME", "测试1"+sidx);
		  _dataMap.put("NEW_CUST_ID", "JT2010002");
		  _dataMap.put("NEW_CUST_ID1", "JT2010002-01");
		  list.add(_dataMap);
		  excel.pageBodyFetchData(list, sidx);
		  excel.PageBodyColsMerges(sidx, "1");
//		  
		  list.clear();
//		  excel.pageHeadFetchData(_dataHeadMap, sidx);
		  
//		  
		  _dataMap = new HashMap();
		  _dataMap.put("CUST_DEPT_NAME", "测试2");
		  _dataMap.put("NEW_CUST_ID", "JT2010003");
		  list.add(_dataMap);
		  
		   _dataMap = new HashMap();
		  _dataMap.put("CUST_DEPT_NAME", "测试2");
		  _dataMap.put("NEW_CUST_ID", "JT2010004");
		  list.add(_dataMap);
		  excel.pageBodyFetchData(list, sidx);
		  excel.PageBodyColsMerges(sidx, "1");
		  
		  Map _footMap = new HashMap();
		  _footMap.put("KDXSR", "1000");
		  _footMap.put("FKDXSR", "1001");
		  _footMap.put("ALL_RMB", "1002");
		  _footMap.put("ALL_US", "1003");
		  excel.pageFootFetchData(_footMap, sidx);
		  this.blnFinished = true;
		  
		  
		  System.out.println("结束");
	}
}
