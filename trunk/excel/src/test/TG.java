package test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.cthq.crm.project.common.xml.imp.XMLBaseReader;

public class TG {
	public static void main(String[] args) throws Exception {
		String filePath = "d:/我的桌面/test.xls";
		TG tg = new TG();
		// jxlWriteImg.writeImg(filePath);
		tg.exeExcelWrite(filePath);
	}

	private void exeExcelWrite(String filePath) throws Exception {
		Workbook wb = Workbook.getWorkbook(new File(filePath));
		WritableWorkbook wwb = Workbook.createWorkbook(new File(
				"d:/我的桌面/test1.xls"), wb);
		WritableSheet ws = wwb.getSheet(0);
		DescStru ds = new DescStru();
		Map dataMap = new HashMap();
		dataMap.put("title", "例如1：Shanghai->HKG->Malaysia-Pakistan");
		/* 左上角的标题 */
		XMLBaseReader xmlbr = ds.xmlStruRead("d:/我的桌面/desc_stru.xml");
		List list = ds.getXmlLevelItemList(xmlbr, "excel/global");
		if (!list.isEmpty()) {
			Map map = (Map) list.get(0);
			ds.writeGlobal(ws, map, dataMap);
		}

		/* 内容体 */

		list = ds.getXmlLevelItemList(xmlbr, "excel/modular");
		if (!list.isEmpty()) {
			Map map = (Map) list.get(0);
			String Theme = map.get("Theme").toString();
			ds.writeCell(ws, map, Theme, "实际电路段");
			
			// section
			
			
			List list1 = new ArrayList();
			
			
 			
			exeSection(ws, ds, xmlbr, list1);
		}
		wwb.write();
		wwb.close();
	}
	
	private void exeSection(WritableSheet ws, DescStru ds, XMLBaseReader xmlbr ,List list) throws Exception {
		
		list = ds.getXmlLevelItemList(xmlbr, "excel/modular/section");
		Map map1 = (Map) list.get(0);
		
		String section_first = map1.get("section-first").toString();
		String section_margin = map1.get("section-margin").toString();
		
		String[] sections = section_first.split(",");
		int col1, row1, col2, row2;
		col1 = Integer.parseInt(sections[0]);
		row1 = Integer.parseInt(sections[1]);
		col2 = Integer.parseInt(sections[2]);
		row2 = Integer.parseInt(sections[3]);
		
		ds.writeMergeCell(ws,  col1, row1, col2, row2, "Shanghai Address");

		int width = col2 - col1;
		int height = row2 - row1;
		
		String[] margins = section_margin.split(",");
		int margin_width = Integer.parseInt(margins[0]);
		int margin_src = Integer.parseInt(margins[0]);
		
		for (int i = 0; i < list.size(); i++) {
			Map dataMap = (Map)list.get(0);
			String val = dataMap.get("key").toString();
			ds.writeMergeCell(ws,  col1, row1, col1 + margin_width + width + 1, row1 + height, val);
		}
		
		
		
		String src = "";
		
		
	}

}
