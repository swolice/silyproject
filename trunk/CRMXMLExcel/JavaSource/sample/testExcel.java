package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cthq.crm.report.xml.support.ReportDataXmlToExcelFile;

public class testExcel {
	
	
	public static void main(String arg[]){
		
		ReportDataXmlToExcelFile excel = new ReportDataXmlToExcelFile();
		
		List list = new ArrayList();
		
		Map map =  new HashMap();
		map.put("STRNUM", "316992693.99");
		map.put("NAME", "sily");
		map.put("Address", "中国北京");

		List list1 = new ArrayList();
		list1.add(map);
		list1.add(map);
		list1.add(map);
		list1.add(map);
		
		//第一个SHEET
		Map map_sheet1 = new HashMap();
		map_sheet1.put("body", list1);
		map_sheet1.put("index", "2"); //操作的SHEET标号
		map_sheet1.put("tempxml","E:/myeclipseworkspace/CRMXML/JavaSource/test/example.xml");
		list.add(map_sheet1);

		
		System.out.print(
		excel.getOutExcelWithSheets("E:/myeclipseworkspace/CRMXML/JavaSource/test/output.xls", list)
		);
	}
}
