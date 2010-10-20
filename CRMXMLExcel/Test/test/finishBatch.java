/*
 * Created on 2010-6-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cthq.crm.report.xml.support.ReportDataXmlToExcelFile;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class finishBatch {

	public static void main(String[] args) {
		ReportDataXmlToExcelFile excel = new ReportDataXmlToExcelFile();
		
		List list = new ArrayList();
		
		Map map =  new HashMap();
		map.put("A1", "316992693.99");
		map.put("A2", "22");
		map.put("A3", "44");

		List list1 = new ArrayList();
		list1.add(map);
		list1.add(map);
		list1.add(map);
		list1.add(map);
		
		//第一个SHEET
		Map map_sheet1 = new HashMap();
		map_sheet1.put("body", list1);
		map_sheet1.put("index", "0"); //操作的SHEET标号
		map_sheet1.put("tempxml","F:/crmwork/20100325/CRMProject/CRMXMLNEW/test/test/default.xml");
		list.add(map_sheet1);

		
		System.out.print(
		excel.getOutExcelWithSheets("F:/crmwork/20100325/CRMProject/CRMXMLNEW/test/test/finishBatch-test.xls", list)
		);
	}
}
