/** 
 * 文件名：		WriteDateMultiSheet.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		Apr 17, 2010 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2010 
 * Company：		广州正道科技有限公司  
 */
package sample;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cthq.crm.report.excel.support.ExcelExportUtils;
import com.cthq.crm.report.xml.support.ReportDataXmlToExcelFile;

/** 
 * 项目名称：	账务系统 
 * 名称：	WriteDateMultiSheet 
 * 描述： 
 * 创建人：	吉仕军
 * 创建时间：	Apr 17, 2010 2:01:21 PM
 * 修改人：	
 * 修改时间：	
 * 修改备注： 
 * @version 1.0
 */
public class WriteDateMultiSheet {

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		ReportDataXmlToExcelFile rdxtf = new ReportDataXmlToExcelFile();
		Map map1 = new HashMap();
		Map headmap = new HashMap();
		headmap.put("jtbs", "中国电信标识");
		
//		map1.put("head", headmap);
		String tempxml1 =  Thread.currentThread().getContextClassLoader().getResource("test/example1.xml").getPath();
//		map1.put("tempxml", tempxml1);
		
//		Map map2 = new HashMap();
		List bodylist =  new ArrayList();
//		Map bodymap = new HashMap();
//		bodymap.put("ADDRESS", "beijing china");
//		bodymap.put("NAME", "xixi");
//		bodymap.put("STRNUM", "111");
//		bodylist.add(bodymap);
//		bodylist.add(bodymap);
//		bodylist.add(bodymap);
//		bodylist.add(bodymap);
//		bodylist.add(bodymap);
		String sql = "select * from te_cust_order_list where rownum<20000";
		
		try {
			bodylist = DatabaseTool.getInstance().executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String tempxml =  Thread.currentThread().getContextClassLoader().getResource("test/example2.xml").getPath();
		
		String tempExl =  Thread.currentThread().getContextClassLoader().getResource("test/output.xls").getPath();

		Map footmap = new HashMap();
		footmap.put("total","1111111111.00");
		ExcelExportUtils eeu = new ExcelExportUtils(tempExl);
		
		eeu.putDataAndTmpByHead(headmap, tempxml1, 0);
		eeu.putDataAndTmp(headmap,footmap,bodylist, tempxml, 2);
		//多个sheet生成
		
		System.out.println(System.currentTimeMillis());
		String ss = eeu.getExcelFile();
		System.out.println(System.currentTimeMillis());
		
		System.out.println(ss);
		
//		String xmlfile = Thread.currentThread().getContextClassLoader().getResource("test/output20100419192349201004191923502350.xml").getPath();
//		String xmlfile1 = Thread.currentThread().getContextClassLoader().getResource("test/output201004191923492349.xml").getPath();
		
		//根据xml文件写入excel 中
//		byte[] ss1 = eeu.getExcelBytesByDataXml(xmlfile1,tempxml1,0);
//		byte[] ss2 = eeu.getExcelBytesByDataXml(xmlfile,tempxml,2);
	}
}
