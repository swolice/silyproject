package test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.cthq.crm.project.common.xml.imp.XMLBaseReader;

public class TG {
	public static void main(String[] args) throws Exception {
		String filePath = "C:/Users/sily/Desktop/test.xls";
		TG tg = new TG();
		// jxlWriteImg.writeImg(filePath);
		
		tg.exeExcelWrite(filePath);
	}

	private void exeExcelWrite(String filePath) throws Exception {
		Workbook wb = Workbook.getWorkbook(new File(filePath));
		WritableWorkbook wwb = Workbook.createWorkbook(new File(
				"C:/Users/sily/Desktop/test1.xls"), wb);
		WritableSheet ws = wwb.getSheet(0);
		DescStru ds = new DescStru();
		Map dataMap = new HashMap();
		dataMap.put("title", "例如1：Shanghai->HKG->Malaysia-Pakistan");
		/* 左上角的标题 */
		XMLBaseReader xmlbr = ds.xmlStruRead("C:/Users/sily/Desktop/desc_stru.xml");
		
		List list_x = ds.getXmlLevelItemList(xmlbr, "excel/modular/section");
		
		
		List list = ds.getXmlLevelItemList(xmlbr, "excel/global");
		if (!list.isEmpty()) {
			Map map = (Map) list.get(0);
			ds.writeGlobal(ws, map, dataMap);
		}

		/* 内容体 */

		list = ds.getXmlLevelItemList(xmlbr, "excel/modular");
		if (!list.isEmpty()) {
			Map map = (Map) list.get(0);
			ds.writeCell(ws, map, "Theme", "实际电路段");
			
			// section
			List list1 = new ArrayList();
			Map tmpMap = new HashMap();
			tmpMap.put("key", "Shanghai Address");
			list1.add(tmpMap);
			tmpMap = new HashMap();
			tmpMap.put("key", "Shanghai Local Exchange");
			list1.add(tmpMap);
			tmpMap = new HashMap();
			tmpMap.put("key", "Shanghai ITMC");
			list1.add(tmpMap);

			exeSection(ws, ds, xmlbr, list1,map,"Provided by CMCC");
		}
		wwb.write();
		wwb.close();
	}
	
	private void exeSection(WritableSheet ws, DescStru ds, XMLBaseReader xmlbr ,List list,Map map,String title) throws Exception {
		
		List list_x = ds.getXmlLevelItemList(xmlbr, "excel/modular/section");
		Map map1 = (Map) list_x.get(0);
		
		String section_first = map1.get("section-first").toString();
		String margin_src_x = map1.get("img-x").toString();
		
		String[] sections = section_first.split(",");
		int col1, row1, col2, row2;
		col1 = Integer.parseInt(sections[0]);
		row1 = Integer.parseInt(sections[1]);
		col2 = Integer.parseInt(sections[2]);
		row2 = Integer.parseInt(sections[3]);
		
		ds.writeMergeCell(ws,  col1, row1, col2, row2, "Shanghai Address");

		int width = col2 - col1;
		int height = row2 - row1;
		
//		String[] margins = section_margin.split(",");
//		int margin_width = Integer.parseInt(margins[0]);
//		int margin_src = Integer.parseInt(margins[0]);
		
		int last_col = 0;
		int last_row = 0;
		for (int i = 0; i < list.size(); i++) {
			Map dataMap = (Map)list.get(0);
			String val = dataMap.get("key").toString();
			ds.writeMergeCell(ws,  col1, row1, col1 + 1*(i+1) + width*(i+1) + 1, row1 + height, val);
			//key:col, row, width, height, src
			last_col = col1 + 1*(i+1) + width*(i+1) + 1;
			last_row = row1 + height;
			
			dataMap.put("col", col1 + 1*(i+1) + width*(i+1) + 2 + "");
			dataMap.put("row", row1 + 2 + "");
			dataMap.put("width",width + "");
			dataMap.put("height", "0.1");
			dataMap.put("src", "C:/Users/sily/Desktop/" + margin_src_x);
			ds.writeImg(ws, dataMap);
		}
			
		int row_r = row1 - 4;
		ds.writeMergeCell(ws, col1, row_r, last_col, row_r, title);

		String supplier_margin = map.get("img-margin").toString();
		Map imgMap = new HashMap();
		imgMap.put("col", col1 + "");
		imgMap.put("row", row_r + 1 + "");
		imgMap.put("width",last_col - col1 + 1.5 + "");
		imgMap.put("height","2");
		imgMap.put("src", "C:/Users/sily/Desktop/" + supplier_margin);
		ds.writeImg(ws, imgMap);
		
		String margin_src_y = map.get("supplier-margin").toString();
		imgMap.clear();
		imgMap.put("col", last_col + 1 + "");
		imgMap.put("row", row_r + "");
		imgMap.put("width","0.1");
		imgMap.put("height",last_row + 1 + "");
		imgMap.put("src", "C:/Users/sily/Desktop/" + margin_src_y);
		ds.writeImg(ws, imgMap);
		
	}

}
