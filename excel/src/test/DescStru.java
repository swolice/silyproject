package test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;

import com.cthq.crm.project.common.util.ReadFile;
import com.cthq.crm.project.common.xml.imp.XMLBaseReader;
import com.cthq.crm.project.common.xml.imp.XMLCommonReader;
import com.cthq.crm.project.common.xml.imp.XMLNode;
import com.cthq.crm.project.common.xml.imp.XMLNodeCollection.Entry;

public class DescStru {

	public XMLBaseReader xmlStruRead(String xmlPath) {
		XMLCommonReader xmlCB = new XMLCommonReader();

		List list = new ArrayList(3);

		list.add(0, "excel/global");
		list.add(1, "excel/modular");
		list.add(2, "excel/modular/section");

		xmlCB.setXMLLevelList(list);
		ReadFile rf = new ReadFile();
		String xmlstr = rf.getFileContent(xmlPath, "UTF-8");

		try {
			xmlCB.analysisXML(xmlstr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xmlCB;
	}

	public List getXmlLevelItemList(XMLBaseReader xmlBaseReader,
			String xmlLevelItemXPath) {
			Iterator it = xmlBaseReader.xmlDomElementLeafIterator(
					xmlLevelItemXPath, 0);
			List elementList = new ArrayList();
			if (null != it) {
				Map map = new HashMap();
				while (it.hasNext()) {
					Entry entry = (Entry) it.next();
					XMLNode xn = entry.xmlNode;
					map.put(xn.getName(), xn.getValue());
					System.out.println(xn.getName() + " : "
							+ xn.getValue().trim());
				}
				elementList.add(map);
			}
		return elementList;
	}

	/**
	 * 写入全局变量 固定位置的值
	 * 
	 * @param ws
	 *            需要写入的sheet
	 * @param map
	 *            模板的map
	 * @param dataMap
	 *            对应模板数据的map
	 */
	public void writeGlobal(WritableSheet ws, Map map, Map dataMap)
			throws Exception {
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			try {
				String[] vals = val.toString().split(",");
				int c = Integer.parseInt(vals[0]);
				int r = Integer.parseInt(vals[1]);
				writeLabel(ws, c, r, dataMap.get(key).toString());
			} catch (Exception e) {
				throw new Exception("模板结构定义不正确", e);
			}
		}
	}
	
	/**
	 * 
	 * @param ws
	 * @param map
	 * @param dataMap
	 * @throws Exception
	 */
	public void writeCell(WritableSheet ws, Map map, String key,String value)
		throws Exception {
		Object val = map.get(key);
		try {
			String[] vals = val.toString().split(",");
			int c = Integer.parseInt(vals[0]);
			int r = Integer.parseInt(vals[1]);
			writeLabel(ws, c, r, value);
		} catch (Exception e) {
			throw new Exception("模板结构定义不正确", e);
		}
	}

	private void writeLabel(WritableSheet ws, int c, int r, String value)
			throws Exception {
		try {
			Label lr = new Label(c, r, value);
			ws.addCell(lr);
		} catch (Exception e) {
			throw new Exception("添加Label的时候出错", e);
		}
	}

	/**
	 * 
	 * @param ws
	 * @param map
	 * @param dataMap
	 * @throws Exception
	 */
	public void writeMergeCell(WritableSheet ws, int col1, int row1, int col2, int row2, String value)
			throws Exception {
		try {
//			String[] coords = coord.split(",");
//
//			int col1, row1, col2, row2;
//			col1 = Integer.parseInt(coords[0]);
//			row1 = Integer.parseInt(coords[1]);
//			col2 = Integer.parseInt(coords[2]);
//			row2 = Integer.parseInt(coords[3]);

			ws.mergeCells(col1, row1, col2, row2);

			writeLabel(ws, col1, row1, value);
		} catch (Exception e) {
			throw new Exception("添加Label的时候出错", e);
		}
	}

	/**
	 * 
	 * @param ws
	 * @param map
	 *            key:col, row, width, height, src
	 * @throws Exception
	 */
	public void writeImg(WritableSheet ws, Map map) throws Exception {
		String src = map.get("src").toString();
		File imgFile = new File(src);
		// WritableImage(col, row, width, height, imgFile);
		double col, row, width, height;
		try {
			col = Double.parseDouble(map.get("col").toString());
			row = Double.parseDouble(map.get("row").toString());
			width = Double.parseDouble(map.get("width").toString());
			height = Double.parseDouble(map.get("height").toString());
			WritableImage image = new WritableImage(col, row, width, height, imgFile);
			ws.addImage(image);
		} catch (Exception e) {
			throw new Exception("写入图片到excel中时出错", e);
		}
	}
	
	
	

}
