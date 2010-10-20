package sample;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;

import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.report.excel.support.ReportToExcel;

public class ExcelBodyGroupLogic implements Runnable {
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
		excel.setExcelTempRptXml("F:/crmwork/20100308Accout/CRMXML/xmltemp/bodyGroupreport.xml");
		excel.initExcelTempStyle(sidx);
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		System.out.println("开始");
		  
		  Map _dataHeadMap = new HashMap();
		  _dataHeadMap.put("H1", "湖北省电信公司2006年12月月租费结算清单");
		  excel.pageHeadFetchData(_dataHeadMap, sidx);
//		  %STRNUM%	%CUST_DEPT_NAME%	%NEW_CUST_ID%	%NEW_CUST_NAME%	%CYCLE_TYPE_NAME%	%KDXSR%	%FKDXSR%	%ALL_RMB%	%ALL_US%
		  List list = new ArrayList();
		  
		  Map _dataMap = new HashMap();
		  _dataMap = new HashMap();
		  _dataMap.put("BH1", "中国人民银行");
		  list.add(_dataMap);
		  excel.pageBodyGroupHeadFetchData(list, sidx);
		  list.clear();
		  
		  
		  _dataMap = new HashMap();
		  _dataMap.put("BT1", "01");
		  _dataMap.put("BT2", "HT201000100901");
		  _dataMap.put("BT3", "备注01");
		  list.add(_dataMap);
		  _dataMap.put("BT1", "02");
		  _dataMap.put("BT2", "HT201000100902");
		  _dataMap.put("BT3", "备注02");
		  list.add(_dataMap);
		  list.add(_dataMap);
		  list.add(_dataMap);
		  list.add(_dataMap);
		  list.add(_dataMap);
		  excel.pageBodyFetchData(list, sidx);
		  list.clear();
		  
		  _dataMap = new HashMap();
		  _dataMap.put("BF1", "测试01");
		  _dataMap.put("BF2", "100010.11");
		  list.add(_dataMap);
		  excel.pageBodyGroupFootFetchData(list, sidx);
		  list.clear(); 
		  
		  
		  
		  _dataMap = new HashMap();
		  _dataMap = new HashMap();
		  _dataMap.put("BH1", "解放军");
		  list.add(_dataMap);
		  excel.pageBodyGroupHeadFetchData(list, sidx);
		  list.clear();
		  
		  
		  _dataMap = new HashMap();
		  _dataMap.put("BT1", "03");
		  _dataMap.put("BT2", "HT201000100903");
		  _dataMap.put("BT3", "备注03");
		  list.add(_dataMap);
		  list.add(_dataMap);
		  list.add(_dataMap);
		  list.add(_dataMap);
		  list.add(_dataMap);
		  list.add(_dataMap);
		  excel.pageBodyFetchData(list, sidx);
		  list.clear();
		  
		  _dataMap = new HashMap();
		  _dataMap.put("BF1", "测试02");
		  _dataMap.put("BF2", "100010.22");
		  list.add(_dataMap);
		  excel.pageBodyGroupFootFetchData(list, sidx);
		  list.clear(); 
		  
		  
		  
		  _dataMap = new HashMap();
		  _dataMap.put("BH1", "腐败");
		  list.add(_dataMap);
		  excel.pageBodyGroupHeadFetchData(list, sidx);
		  list.clear();
		  
		  
		  _dataMap = new HashMap();
		  _dataMap.put("BT1", "06");
		  _dataMap.put("BT2", "HT201000100906");
		  _dataMap.put("BT3", "备注06");
		  list.add(_dataMap);
		  list.add(_dataMap);
		  list.add(_dataMap);
		  list.add(_dataMap);
		  excel.pageBodyFetchData(list, sidx);
		  list.clear();
		  
		  _dataMap = new HashMap();
		  _dataMap.put("BF1", "测试06");
		  _dataMap.put("BF2", "100010.66");
		  list.add(_dataMap);
		  excel.pageBodyGroupFootFetchData(list, sidx);
		  list.clear(); 
		  
		  
		  this.blnFinished = true;
		  
		  
		  System.out.println("结束");
	}
}
