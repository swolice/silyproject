package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.report.excel.support.ExcelReportException;
import com.cthq.crm.report.excel.support.ReportToExcel;

public class bodyGPTest {
  public static void main(String args[]) {
	  ExcelSTLWrite excelSTLWr = new ExcelSTLWrite();
	  try {
			excelSTLWr.init("G:\\tmp\\bodygroup.xls", "G:\\tmp\\bodygroupTest.xls");
		} catch (Exception e) {
			throw new ExcelReportException("initExcel error", e);
		}
	  ExcelBodyGroupLogic logic1 = new ExcelBodyGroupLogic();
	  logic1.setExcelSTLWrite(excelSTLWr, 0);
	  logic1.run();
//	  ThreadServerPool thread = new ThreadServerPool();
//	  thread.run(logic1);
//	  thread.run(logic2);
//	  
	  while(true) {
		  if ( logic1.blnFinished ) {			  
			  break;
		  }
		 /// System.out.println("11111111111111111");
	  }
	  excelSTLWr.dispose();
//	  for(int i=0;i<10; i++) {
//			TestWorkRun workrun = new TestWorkRun("A0"+i);
//			thread.run(workrun);
//		}
//		Timer busTrigerTimer = new Timer(true);
//		//定时器出发逻辑池 定时器 一秒中循环处理
//		BussinessTrigerPoolTask bussTimerTrigerTask = new BussinessTrigerPoolTask();
//		busTrigerTimer.schedule(bussTimerTrigerTask, 0 , 3 * 1000);
//		try {
//			Thread.sleep(1000*30);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////	  Map _dataMap = new HashMap();
//	  _dataMap.put("YSNY", "201009");
//	  _dataMap.put("DYRQ", "test");
//	  excel.pageHeadFetchData(_dataMap, 0);
////	  %STRNUM%	%CUST_DEPT_NAME%	%NEW_CUST_ID%	%NEW_CUST_NAME%	%CYCLE_TYPE_NAME%	%KDXSR%	%FKDXSR%	%ALL_RMB%	%ALL_US%
//	  List list = new ArrayList();
//	  
//	  _dataMap = new HashMap();
//	  _dataMap.put("CUST_DEPT_NAME", "测试1");
//	  _dataMap.put("NEW_CUST_ID", "JT2010001");
//	  _dataMap.put("NEW_CUST_ID1", "JT2010001-01");
//	  list.add(_dataMap);
//	  
//	   _dataMap = new HashMap();
//	  _dataMap.put("CUST_DEPT_NAME", "测试1");
//	  _dataMap.put("NEW_CUST_ID", "JT2010002");
//	  _dataMap.put("NEW_CUST_ID1", "JT2010002-01");
//	  list.add(_dataMap);
//	  excel.pageBodyFecthData(list, 0);
//	  
//	  list.clear();
//	  
////	  _dataMap = new HashMap();
////	  _dataMap.put("CUST_DEPT_NAME", "测试2");
////	  _dataMap.put("NEW_CUST_ID", "JT2010003");
////	  list.add(_dataMap);
////	  
////	   _dataMap = new HashMap();
////	  _dataMap.put("CUST_DEPT_NAME", "测试2");
////	  _dataMap.put("NEW_CUST_ID", "JT2010004");
////	  list.add(_dataMap);
////	  excel.pageBodyFecthData(list, 0);
//	  
//	  Map _footMap = new HashMap();
//	  _footMap.put("KDXSR", "1000");
//	  _footMap.put("FKDXSR", "1001");
//	  _footMap.put("ALL_RMB", "1002");
//	  _footMap.put("ALL_US", "1003");
//	  excel.pageFootFecthData(_footMap, 0);
//	  
//	  
//	  excel.reset();
//	   _dataMap = new HashMap();
//	  _dataMap.put("YSNY", "201009");
//	  _dataMap.put("DYRQ", "test");
//	  excel.pageHeadFetchData(_dataMap, 1);
//	  list = new ArrayList();
//	  
//	  _dataMap = new HashMap();
//	  _dataMap.put("CUST_DEPT_NAME", "测试1");
//	  _dataMap.put("NEW_CUST_ID", "JT2010001");
//	  _dataMap.put("NEW_CUST_ID1", "JT2010001-01");
//	  list.add(_dataMap);
//	  
//	   _dataMap = new HashMap();
//	  _dataMap.put("CUST_DEPT_NAME", "测试1");
//	  _dataMap.put("NEW_CUST_ID", "JT2010002");
//	  _dataMap.put("NEW_CUST_ID1", "JT2010002-01");
//	  list.add(_dataMap);
//	  excel.pageBodyFecthData(list, 1);
//	  
//	  list.clear();
//	  
//	   _footMap = new HashMap();
//	  _footMap.put("KDXSR", "1000");
//	  _footMap.put("FKDXSR", "1001");
//	  _footMap.put("ALL_RMB", "1002");
//	  _footMap.put("ALL_US", "1003");
//	  excel.pageFootFecthData(_footMap, 1);
	  
//	  excel.dispose();
  }
}
