package sample;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cthq.crm.report.excel.support.ExcelExportUtils;

public class ExpDateFormat {

	public static void main(String[] args) {
		String exceltmp = "d:/我的桌面/test.xls";
		String xmltmp = "d:/我的桌面/testxml.xml";
		ExcelExportUtils eeu = new ExcelExportUtils(exceltmp);
		DatabaseTool dt  =  DatabaseTool.getInstance();
		String sql = 
			"select * " +
			"  from ti_order_list " + 
			" where order_nbr in ('JT201000004960', 'JT201000004961', 'JT201000004967')";

		List list = new ArrayList();
		
		try {
			list = dt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Map dataMap = (Map)list.get(0);
		eeu.putDataAndTmpByHead(dataMap, xmltmp, 0);
		
		String filepath = eeu.getExcelFile();
		
		System.out.println(filepath);
	}
}
