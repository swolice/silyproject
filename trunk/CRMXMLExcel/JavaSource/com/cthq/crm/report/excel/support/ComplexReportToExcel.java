package com.cthq.crm.report.excel.support;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import jxl.Range;
import jxl.write.WritableSheet;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.cthq.crm.excel.write.ExcelBookWrite;
import com.cthq.crm.excel.write.ExcelCell;
import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.excel.write.ICellType;
import com.cthq.crm.project.common.util.ReadFile;
import com.cthq.crm.report.xml.support.ReportExcelXmlReader;

public class ComplexReportToExcel {	
	//EXCEL操作文件
	protected ExcelSTLWrite excelSTLWr = new ExcelSTLWrite();
	//EXCEL报表模板描述文件
	protected ReportExcelXmlReader rptXmlRd = new ReportExcelXmlReader();
	//本体写数据的起始行号
	protected int bodyStaLocRow = -1;
	
	//本体写数据的结束行号
	protected int bodyEndLocRow = -1;
	//EXCEL本体单元格样式描述
	protected Range bodyRangs[] = null;
	
	/**
	 * 创建EXCEL模板文件的XML描述集合
	 * @param _xmlPath 模板文件描述集合
	 */
	private void createReportXMLReader(String _xmlPath){
		ReadFile rf = new ReadFile();
		String xmlstr = rf.getFileContent(_xmlPath, "UTF-8");
		try {
			rptXmlRd.analysisXML(xmlstr);
		} catch (Exception e) {
		}
		rptXmlRd.initReportMap();
	}
	/**
	 * 
	 * @param _sheetIdx
	 * @param reportName
	 */
	private void initReportCellStyle(int _sheetIdx, String reportName){
		WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
		bodyRangs = sheet.getMergedCells();
		//头部所占用的起始行和结束行号
		Map map = rptXmlRd.getReportMap(reportName);
		String rows = (String)map.get("Rows");
		//头部所占用的起始列和结束列号
		String cols = (String)map.get("Cols");
		List cellStyleList = (List)map.get("CellStyle");
		//初始化EXCEL的头部样式结合
		if (cellStyleList.isEmpty()) {
			if (rows == null || cols == null ){
				return;
			}
			if (rows.trim().length() == 0) {
				return;
			}
			if (cols.trim().length() == 0) {
				return;
			}
			String[] rowsArr = rows.split(",");
			String[] colsArr = cols.split(",");
			int row01 = Integer.parseInt(rowsArr[0]);
			int row02 = Integer.parseInt(rowsArr[1]);
			int col01 = Integer.parseInt(colsArr[0]);
			int col02 = Integer.parseInt(colsArr[1]);
			//获取模板的样式
			for (int row = row01; row <= row02; row++) {
				for (int col = col01; col <= col02; col++) {
					cellStyleList.add(excelSTLWr.getLocalCell(sheet, col, row));
				}
			}
		}
	}
	/**
	 * 
	 * @param _paintcode
	 * @param _sectionNm
	 * @param curDataMap
	 */
	private String processPaintcode(String _paintcode, String _sectionNm, Map curDataMap){
		try {
			Velocity.init();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ReportTemplate template = new ReportTemplate();
		VelocityContext vlcontext = new VelocityContext();
		vlcontext.put("excelRpt", this);
		vlcontext.put("curDataMap", curDataMap);
		vlcontext.put("preDataMap", rptXmlRd.getReportMap(_sectionNm).get("PRE_ROW_DATA_MAP"));
		vlcontext.put("sectionNm", _sectionNm);
		StringWriter writer = new StringWriter();
		try {
			template.setVmData(_paintcode);
			template.process();
			template.merge(vlcontext, writer);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 初始化文件操作
	 * @param tempExcel
	 * @param outExcel
	 */
	public void initExcel(String tempExcel, String outExcel) {
		try {
			excelSTLWr.init(tempExcel, outExcel);
		} catch (Exception e) {
			throw new ExcelReportException("initExcel error", e);
		}
	}
	/**
	 * 
	 * @param _excelSTLWr
	 */
	public void setExcelSTLWrite(ExcelSTLWrite _excelSTLWr) {
		this.excelSTLWr = _excelSTLWr;
	}
	/**
	 * 初始化EXCEL模板的样式
	 * @param _xmlpath   模板文件xml描述文件
	 * @param _sheetIdx  EXCEL操作的文件所在位置
	 */
	public void initTemplate(String _xmlpath, int _sheetIdx){
		createReportXMLReader(_xmlpath);
		List list=rptXmlRd.getReportSectionNameList();
		Iterator iter = list.iterator(); 
		while(iter.hasNext()) {
			String sectionNm = (String)iter.next();
			initReportCellStyle(_sheetIdx, sectionNm);
		}
	}
	/**
	 * 写入EXCEL模板的头部文件数据
	 * @param _dataMap 头部数据集合映射 KEY对应EXCEL文件中的模板属性
	 * @param sectionNm
	 * @param _sheetIdx 操作的EXCEL的Sheet位置
	 */
	public void pageHeadFetchData(Map _dataMap, String sectionNm , int _sheetIdx){
		try {
			WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
			List headCellStyleList = (List)rptXmlRd.getReportMap(sectionNm).get("CellStyle");
			Map attrMap = (Map) rptXmlRd.getReportMap(sectionNm).get("CellItem");
			//填写EXCEL模板头部的数据集合
			for (int i = 0; i < headCellStyleList.size(); i++) {
				ExcelCell tmpCell = (ExcelCell) headCellStyleList.get(i);
				ExcelCell copyCell = tmpCell.copyCell();
				String content = copyCell.getLocStr();
				if(content.matches(".*#.+#.*")){
					String key = content.substring(content.indexOf("#")+1,content.lastIndexOf("#"));
					if (_dataMap.get(key) != null) {
						String value = (String)_dataMap.get(key);
						value = Matcher.quoteReplacement(value);
						//需要特殊显示的数据，比如说金额，需要特殊显示格式
						String showStyle = ExpExcelOperator.showExcelDataStyle(attrMap,key,_dataMap);
						if(!"".equals(showStyle)){
							value = showStyle;
						}
						copyCell.setLocStr(content.replaceAll("#"+key+"#", value));
						excelSTLWr.setCell(sheet, copyCell, ICellType.STRING);
					}
				} 
			}	
		} catch(Exception ex) {
			throw new ExcelReportException("pageHeadFetchData Error" + ex.getMessage(), ex);
		}
	}
	
	/**
	 * 写入EXCEL模板的底部文件数据
	 * @param _dataMap 底部数据集合映射 KEY对应EXCEL文件中的模板属性
	 * @param sectionNm 片段名称
	 * @param _sheetIdx 操作的EXCEL的Sheet位置
	 */
	public void pageFootFetchData(Map _dataMap, String sectionNm ,int _sheetIdx){
		try {
			WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
			List footCellStyleList = (List)rptXmlRd.getReportMap(sectionNm).get("CellStyle");
			String bodyRows = (String)rptXmlRd.getReportMap(sectionNm).get("Rows");
			//头部所占用的起始列和结束列号
			String bodyCols = (String)rptXmlRd.getReportMap(sectionNm).get("Cols");
			if (bodyRows == null || bodyCols == null ){
				return;
			}
			if (bodyRows.trim().length() == 0) {
				return;
			}
			if (bodyCols.trim().length() == 0) {
				return;
			}
			String[] rows = bodyRows.split(",");
			String[] cols = bodyCols.split(",");
			int row01 = Integer.parseInt(rows[0]);
			int row02 = Integer.parseInt(rows[1]);
			int col01 = Integer.parseInt(cols[0]);
			int col02 = Integer.parseInt(cols[1]);
			//本体数据多次数据填写处理 起始行和起始列
			if (bodyEndLocRow != -1) {
				bodyStaLocRow = bodyEndLocRow;
				for (int i = 0; i < footCellStyleList.size(); i++) {
					ExcelCell tmpCell = (ExcelCell) footCellStyleList.get(i);
					tmpCell.setLocRow(bodyStaLocRow + tmpCell.getInitRow()-row01);
					
				}	
			}
			Map attrMap = (Map) rptXmlRd.getReportMap(sectionNm).get("CellItem");
			//填写数据
			for (int i = 0; i < footCellStyleList.size(); i++) {

				ExcelCell tmpCell = (ExcelCell) footCellStyleList.get(i);
				ExcelCell copyCell = tmpCell.copyCell();
				String content = copyCell.getLocStr();
				if(content.matches(".*#.+#.*")){
					String key = content.substring(content.indexOf("#")+1,content.lastIndexOf("#"));
					if (_dataMap.get(key) != null) {
						String value = (String)_dataMap.get(key);
						//需要特殊显示的数据，比如说金额，需要特殊显示格式
						String showStyle = ExpExcelOperator.showExcelDataStyle(attrMap,key,_dataMap);
						if(!"".equals(showStyle)){
							value = showStyle;
						}
						copyCell.setLocStr(content.replaceAll("#"+key+"#", value));
						excelSTLWr.setCell(sheet, copyCell, ICellType.STRING);
					}
				}
			}	
		} catch(Exception ex) {
			throw new ExcelReportException("pageFootFetchData Error" + ex.getMessage(), ex);
		}	
	}	
	
	/**
	 * 
	 * @param _dataMap
	 * @param sectionNm
	 * @param _sheetIdx
	 */
	public void pageBodyFetchData(Map _dataMap, String sectionNm, int _sheetIdx){
		try {
			WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
			List bodyCellStyleList = (List)rptXmlRd.getReportMap(sectionNm).get("CellStyle");
			//起始行位置
			int initRow = 0;
			//本体的总行数
			int bodyRowCount = 0;
			//本体的总列数
			int bodyColCount= 0;
			//各本体之间的间隔行数
			int marginRows = 0; 
			String bodyRows = (String)rptXmlRd.getReportMap(sectionNm).get("Rows");
			//头部所占用的起始列和结束列号
			String bodyCols = (String)rptXmlRd.getReportMap(sectionNm).get("Cols");
			if (bodyRows == null || bodyCols == null ){
				return;
			}
			if (bodyRows.trim().length() == 0) {
				return;
			}
			if (bodyCols.trim().length() == 0) {
				return;
			}
			String[] rows = bodyRows.split(",");
			String[] cols = bodyCols.split(",");
			int row01 = Integer.parseInt(rows[0]);
			int row02 = Integer.parseInt(rows[1]);
			int col01 = Integer.parseInt(cols[0]);
			int col02 = Integer.parseInt(cols[1]);
			
			//EXCEL本体的起始位置
			initRow=row01;
			//EXCEL本体的所占的行数
			bodyRowCount = row02 - row01+1;
			//EXCEL本体所在的列数
			bodyColCount = col02 - col01+1;
			
			//EXECL本体数据一行所在的行数
			marginRows = 0;
			
			int writeLocRow = initRow;
			bodyStaLocRow = initRow;
			//本体数据多次数据填写处理 起始行和起始列
			if (bodyEndLocRow != -1) {
				writeLocRow = bodyEndLocRow;
				bodyStaLocRow = bodyEndLocRow;
				for (int i = 0; i < bodyCellStyleList.size(); i++) {
					ExcelCell tmpCell = (ExcelCell) bodyCellStyleList.get(i);
					tmpCell.setLocRow(bodyStaLocRow);
				}	
			}
			Map attrMap = (Map) rptXmlRd.getReportMap(sectionNm).get("CellItem");
			rptXmlRd.getReportMap(sectionNm).put("PRE_ROW_DATA_MAP", new HashMap(0));
			rptXmlRd.getReportMap(sectionNm).put("CUR_ROW_DATA_MAP", new HashMap(0));
			rptXmlRd.getReportMap(sectionNm).remove("IS_FINISH");
			rptXmlRd.getReportMap(sectionNm).remove("OPER_LAST_ROW");
			rptXmlRd.getReportMap(sectionNm).remove("BODY_MARGIN_ROWS");
			int i =1;
			
			//填写EXCEL本体数据
			ExpExcelOperator.writeData(excelSTLWr,  bodyCellStyleList , _dataMap, _sheetIdx, 
					writeLocRow, bodyRowCount, bodyColCount, bodyRowCount * (i-1),  initRow,marginRows*(i-1), attrMap);
			rptXmlRd.getReportMap(sectionNm).put("CUR_ROW_DATA_MAP", _dataMap);
			if (rptXmlRd.getReportMap(sectionNm).get("paintcode") != null) {
				String paintcode = (String)rptXmlRd.getReportMap(sectionNm).get("paintcode");
				rptXmlRd.getReportMap(sectionNm).put("RowDataMap", new HashMap(0));
				rptXmlRd.getReportMap(sectionNm).put("IS_FINISH", "1");
				rptXmlRd.getReportMap(sectionNm).put("OPER_LAST_ROW", String.valueOf(writeLocRow + bodyRowCount + marginRows));
				
				this.processPaintcode(paintcode, sectionNm, _dataMap);
			}
			
			if (rptXmlRd.getReportMap(sectionNm).get("BODY_MARGIN_ROWS")!=null){
				String tmpMarginRows = (String)rptXmlRd.getReportMap(sectionNm).get("BODY_MARGIN_ROWS");
				marginRows = Integer.parseInt(tmpMarginRows);
			}
			writeLocRow = writeLocRow + bodyRowCount + marginRows;
			bodyEndLocRow = writeLocRow;
		} catch(Exception ex) {
			throw new ExcelReportException("pageBodyFetchData Error" + ex.getMessage(), ex);
		} finally {
			rptXmlRd.getReportMap(sectionNm).remove("PRE_ROW_DATA_MAP");
			rptXmlRd.getReportMap(sectionNm).remove("CUR_ROW_DATA_MAP");
			rptXmlRd.getReportMap(sectionNm).remove("IS_FINISH");
			rptXmlRd.getReportMap(sectionNm).remove("OPER_LAST_ROW");
			rptXmlRd.getReportMap(sectionNm).remove("BODY_MARGIN_ROWS");
		}
	}
	/**
	 * 
	 * @param _dataList
	 * @param sectionNm
	 * @param _sheetIdx
	 */
	public void pageBodyFetchData(List _dataList, String sectionNm, int _sheetIdx){
		try {
			WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
			List bodyCellStyleList = (List)rptXmlRd.getReportMap(sectionNm).get("CellStyle");
			//起始行位置
			int initRow = 0;
			//本体的总行数
			int bodyRowCount = 0;
			//本体的总列数
			int bodyColCount= 0;
			//各本体之间的间隔行数
			int marginRows = 0; 
			String bodyRows = (String)rptXmlRd.getReportMap(sectionNm).get("Rows");
			//头部所占用的起始列和结束列号
			String bodyCols = (String)rptXmlRd.getReportMap(sectionNm).get("Cols");
			if (bodyRows == null || bodyCols == null ){
				return;
			}
			if (bodyRows.trim().length() == 0) {
				return;
			}
			if (bodyCols.trim().length() == 0) {
				return;
			}
			String[] rows = bodyRows.split(",");
			String[] cols = bodyCols.split(",");
			int row01 = Integer.parseInt(rows[0]);
			int row02 = Integer.parseInt(rows[1]);
			int col01 = Integer.parseInt(cols[0]);
			int col02 = Integer.parseInt(cols[1]);
			
			//EXCEL本体的起始位置
			initRow=row01;
			//EXCEL本体的所占的行数
			bodyRowCount = row02 - row01+1;
			//EXCEL本体所在的列数
			bodyColCount = col02 - col01+1;
			
			//EXECL本体数据一行所在的行数
			marginRows = 0;
			
			int writeLocRow = initRow;
			bodyStaLocRow = initRow;
			//本体数据多次数据填写处理 起始行和起始列
			if (bodyEndLocRow != -1) {
				writeLocRow = bodyEndLocRow;
				bodyStaLocRow = bodyEndLocRow;
				for (int i = 0; i < bodyCellStyleList.size(); i++) {
					ExcelCell tmpCell = (ExcelCell) bodyCellStyleList.get(i);
					tmpCell.setLocRow(bodyStaLocRow);
				}	
			}
			Map attrMap = (Map) rptXmlRd.getReportMap(sectionNm).get("CellItem");
			rptXmlRd.getReportMap(sectionNm).put("PRE_ROW_DATA_MAP", new HashMap(0));
			rptXmlRd.getReportMap(sectionNm).put("CUR_ROW_DATA_MAP", new HashMap(0));
			rptXmlRd.getReportMap(sectionNm).remove("IS_FINISH");
			rptXmlRd.getReportMap(sectionNm).remove("OPER_LAST_ROW");
			rptXmlRd.getReportMap(sectionNm).remove("BODY_MARGIN_ROWS");
			//填写EXCEL本体数据
			for (int i = 1; i <= _dataList.size(); i++) {
				Map map = (Map)_dataList.get(i-1);
				ExpExcelOperator.writeData(excelSTLWr,  bodyCellStyleList , map, _sheetIdx, 
						writeLocRow, bodyRowCount, bodyColCount, bodyRowCount * (i-1),  initRow,marginRows*(i-1), attrMap);
				rptXmlRd.getReportMap(sectionNm).put("CUR_ROW_DATA_MAP", map);
				if (rptXmlRd.getReportMap(sectionNm).get("paintcode") != null) {
					String paintcode = (String)rptXmlRd.getReportMap(sectionNm).get("paintcode");
					if (i+1 > _dataList.size()){
						rptXmlRd.getReportMap(sectionNm).put("RowDataMap", new HashMap(0));
						rptXmlRd.getReportMap(sectionNm).put("IS_FINISH", "1");
					}
					rptXmlRd.getReportMap(sectionNm).put("OPER_LAST_ROW", String.valueOf(writeLocRow + bodyRowCount + marginRows));
					
					this.processPaintcode(paintcode, sectionNm, map);
				}
				
				if (rptXmlRd.getReportMap(sectionNm).get("PRE_ROW_DATA_MAP") != null){
					rptXmlRd.getReportMap(sectionNm).put("PRE_ROW_DATA_MAP", map);
				}
				
				if (rptXmlRd.getReportMap(sectionNm).get("BODY_MARGIN_ROWS")!=null){
					String tmpMarginRows = (String)rptXmlRd.getReportMap(sectionNm).get("BODY_MARGIN_ROWS");
					marginRows = Integer.parseInt(tmpMarginRows);
				}
				writeLocRow = writeLocRow + bodyRowCount + marginRows;
				if (i+1 > _dataList.size()) {
					break;
				} else {
					ExpExcelOperator.paintCellStyle(excelSTLWr, bodyCellStyleList, bodyRangs, _sheetIdx, 
							writeLocRow, bodyRowCount, i, initRow,marginRows);
				}
			}
			bodyEndLocRow = writeLocRow;
		} catch(Exception ex) {
			throw new ExcelReportException("pageBodyFetchData Error" + ex.getMessage(), ex);
		} finally {
			rptXmlRd.getReportMap(sectionNm).remove("PRE_ROW_DATA_MAP");
			rptXmlRd.getReportMap(sectionNm).remove("CUR_ROW_DATA_MAP");
			rptXmlRd.getReportMap(sectionNm).remove("IS_FINISH");
			rptXmlRd.getReportMap(sectionNm).remove("OPER_LAST_ROW");
			rptXmlRd.getReportMap(sectionNm).remove("BODY_MARGIN_ROWS");
		}
	}
	/**
	 * 构建模板本体的数据空行
	 * @param sectionNm 本体的数据集合
	 * @param _sheetIdx EXCEL模板所在的Sheet序列
	 */
	public void pageBodyPaintCell(String sectionNm, int _sheetIdx){
		try {
			WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
			List bodyCellStyleList = (List)rptXmlRd.getReportMap(sectionNm).get("CellStyle");
			//起始行位置
			int initRow = 0;
			//本体的总行数
			int bodyRowCount = 0;
			//本体的总列数
			int bodyColCount= 0;
			//各本体之间的间隔行数
			int marginRows = 0; 
			String bodyRows = (String)rptXmlRd.getReportMap(sectionNm).get("Rows");
			//头部所占用的起始列和结束列号
			String bodyCols = (String)rptXmlRd.getReportMap(sectionNm).get("Cols");
			if (bodyRows == null || bodyCols == null ){
				return;
			}
			if (bodyRows.trim().length() == 0) {
				return;
			}
			if (bodyCols.trim().length() == 0) {
				return;
			}
			String[] rows = bodyRows.split(",");
			String[] cols = bodyCols.split(",");
			int row01 = Integer.parseInt(rows[0]);
			int row02 = Integer.parseInt(rows[1]);
			int col01 = Integer.parseInt(cols[0]);
			int col02 = Integer.parseInt(cols[1]);
			
			//EXCEL本体的起始位置
			initRow=row01;
			//EXCEL本体的所占的行数
			bodyRowCount = row02 - row01+1;
			//EXCEL本体所在的列数
			bodyColCount = col02 - col01+1;
			
			//EXECL本体数据一行所在的行数
			marginRows = 0;
			
			int writeLocRow = initRow;
			bodyStaLocRow = initRow;
			//本体数据多次数据填写处理 起始行和起始列
			if (bodyEndLocRow != -1) {
				writeLocRow = bodyEndLocRow;
				bodyStaLocRow = bodyEndLocRow;
				for (int i = 0; i < bodyCellStyleList.size(); i++) {
					ExcelCell tmpCell = (ExcelCell) bodyCellStyleList.get(i);
					tmpCell.setLocRow(bodyStaLocRow);
				}	
			}
			ExpExcelOperator.paintCellStyle(excelSTLWr, bodyCellStyleList, bodyRangs, _sheetIdx, 
					writeLocRow, bodyRowCount, 1, initRow,marginRows);
		} catch(Exception ex) {
			throw new ExcelReportException("pageBodyFetchData Error" + ex.getMessage(), ex);
		}
	}
	
	
	/**
	 * 
	 * @param _dataList
	 * @param _sheetIdx
	 */
	public void pageBodyGroupHeadFetchData(List _dataList, String sectionNm, int _sheetIdx){
		WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
		List bodyGroupHeadCellStyleList = (List)rptXmlRd.getReportMap(sectionNm).get("CellStyle");
		//起始行位置
		int initRow = 0;
		//本体的总行数
		int bodyRowCount = 0;
		//本体的总列数
		int bodyColCount= 0;
		//各本体之间的间隔行数
		int marginRows = 0; 
		//EXCEL本体的起始位置
		String bgHRows = (String)rptXmlRd.getReportMap(sectionNm).get("Rows");
		//头部所占用的起始列和结束列号
		String bgHCols = (String)rptXmlRd.getReportMap(sectionNm).get("Cols");
		if (bgHRows == null || bgHCols == null ){
			return;
		}
		if (bgHRows.trim().length() == 0) {
			return;
		}
		if (bgHCols.trim().length() == 0) {
			return;
		}
		String[] rows = bgHRows.split(",");
		String[] cols = bgHCols.split(",");
		int row01 = Integer.parseInt(rows[0]);
		int row02 = Integer.parseInt(rows[1]);
		int col01 = Integer.parseInt(cols[0]);
		int col02 = Integer.parseInt(cols[1]);
		
		initRow = row01;
		//EXCEL本体的所占的行数
		bodyRowCount = row02-row01+1;
		//EXCEL本体所在的列数
		bodyColCount = col02-col01+1;
		//EXECL本体数据一行所在的行数
		marginRows = 0;
		int writeLocRow = initRow;
		bodyStaLocRow = initRow;
		//本体数据多次数据填写处理 起始行和起始列
		if (bodyEndLocRow != -1) {
			writeLocRow = bodyEndLocRow;
			bodyStaLocRow = bodyEndLocRow;
			for (int i = 0; i < bodyGroupHeadCellStyleList.size(); i++) {
				ExcelCell tmpCell = (ExcelCell) bodyGroupHeadCellStyleList.get(i);
				tmpCell.setLocRow(bodyStaLocRow + tmpCell.getInitRow()-row01);
			}	
		}
		Map attrMap = (Map) rptXmlRd.getReportMap(sectionNm).get("CellItem");
		rptXmlRd.getReportMap(sectionNm).put("PRE_ROW_DATA_MAP", new HashMap(0));
		rptXmlRd.getReportMap(sectionNm).put("CUR_ROW_DATA_MAP", new HashMap(0));
		rptXmlRd.getReportMap(sectionNm).remove("IS_FINISH");
		//填写EXCEL本体数据
		for (int i = 1; i <= _dataList.size(); i++) {
			Map map = (Map)_dataList.get(i-1);
			ExpExcelOperator.writeData(excelSTLWr,  bodyGroupHeadCellStyleList , map, _sheetIdx, 
					writeLocRow, bodyRowCount, bodyColCount, bodyRowCount * (i-1),  initRow,marginRows*(i-1), attrMap);
			writeLocRow = writeLocRow + bodyRowCount + marginRows;
			rptXmlRd.getReportMap(sectionNm).put("CUR_ROW_DATA_MAP", map);
			if (rptXmlRd.getReportMap(sectionNm).get("paintcode") != null) {
				String paintcode = (String)rptXmlRd.getReportMap(sectionNm).get("paintcode");
				if (i+1 > _dataList.size()){
					rptXmlRd.getReportMap(sectionNm).put("IS_FINISH", "1");
				}
				rptXmlRd.getReportMap(sectionNm).put("OPER_LAST_ROW", String.valueOf(writeLocRow));
				
				this.processPaintcode(paintcode, sectionNm, map);
			}
			
			if (rptXmlRd.getReportMap(sectionNm).get("PRE_ROW_DATA_MAP") != null){
				rptXmlRd.getReportMap(sectionNm).put("PRE_ROW_DATA_MAP", map);
			}
			
			if (i+1 > _dataList.size()) {
				break;
			} else {
				ExpExcelOperator.paintCellStyle(excelSTLWr, bodyGroupHeadCellStyleList, bodyRangs, _sheetIdx, 
						writeLocRow, bodyRowCount, i, initRow,marginRows);
			}
		}
		bodyEndLocRow = writeLocRow;
	}
	/**
	 * 处理本地中对应底部的数据
	 * @param _dataList 数据集合
	 * @param sectionNm 片段名称
	 * @param _sheetIdx excel模板的序列号
	 */
	public void pageBodyGroupFootFetchData(List _dataList, String sectionNm, int _sheetIdx){
		try {
			WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
			List bodyGroupFootCellStyleList = (List)rptXmlRd.getReportMap(sectionNm).get("CellStyle");
			//起始行位置
			int initRow = 0;
			//本体的总行数
			int bodyRowCount = 0;
			//本体的总列数
			int bodyColCount= 0;
			//各本体之间的间隔行数
			int marginRows = 0; 
			String bgFRows =(String)rptXmlRd.getReportMap(sectionNm).get("Rows");
			//头部所占用的起始列和结束列号
			String bgFCols =(String)rptXmlRd.getReportMap(sectionNm).get("Cols");
			if (bgFRows == null || bgFCols == null ){
				return;
			}
			if (bgFRows.trim().length() == 0) {
				return;
			}
			if (bgFCols.trim().length() == 0) {
				return;
			}
			String[] rows = bgFRows.split(",");
			String[] cols = bgFCols.split(",");
			int row01 = Integer.parseInt(rows[0]);
			int row02 = Integer.parseInt(rows[1]);
			int col01 = Integer.parseInt(cols[0]);
			int col02 = Integer.parseInt(cols[1]);
			//EXCEL本体的起始位置
			initRow = row01;
			//EXCEL本体的所占的行数
			bodyRowCount = row02-row01+1;
			//EXCEL本体所在的列数
			bodyColCount = col02-col01+1;
			
			//EXECL本体数据一行所在的行数
			marginRows = 0;
			int writeLocRow = initRow;
			bodyStaLocRow = initRow;
			//本体数据多次数据填写处理 起始行和起始列
			if (bodyEndLocRow != -1) {
				writeLocRow = bodyEndLocRow;
				bodyStaLocRow = bodyEndLocRow;
				for (int i = 0; i < bodyGroupFootCellStyleList.size(); i++) {
					ExcelCell tmpCell = (ExcelCell) bodyGroupFootCellStyleList.get(i);
					tmpCell.setLocRow(bodyStaLocRow + tmpCell.getInitRow()-row01);
					
				}	
			}
			Map attrMap = (Map) rptXmlRd.getReportMap(sectionNm).get("CellItem");
			rptXmlRd.getReportMap(sectionNm).put("PRE_ROW_DATA_MAP", new HashMap(0));
			rptXmlRd.getReportMap(sectionNm).put("CUR_ROW_DATA_MAP", new HashMap(0));
			rptXmlRd.getReportMap(sectionNm).remove("IS_FINISH");			
			
			//填写EXCEL本体数据
			for (int i = 1; i <= _dataList.size(); i++) {
				Map map = (Map)_dataList.get(i-1);
				ExpExcelOperator.writeData(excelSTLWr,  bodyGroupFootCellStyleList , map, _sheetIdx, 
						writeLocRow, bodyRowCount, bodyColCount, bodyRowCount * (i-1),  initRow,marginRows*(i-1), attrMap);
				writeLocRow = writeLocRow + bodyRowCount + marginRows;
				
				rptXmlRd.getReportMap(sectionNm).put("CUR_ROW_DATA_MAP", map);
				if (rptXmlRd.getReportMap(sectionNm).get("paintcode") != null) {
					String paintcode = (String)rptXmlRd.getReportMap(sectionNm).get("paintcode");
					if (i+1 > _dataList.size()){
						rptXmlRd.getReportMap(sectionNm).put("IS_FINISH", "1");
					}
					rptXmlRd.getReportMap(sectionNm).put("OPER_LAST_ROW", String.valueOf(writeLocRow));
					
					this.processPaintcode(paintcode, sectionNm, map);
				}
				
				if (rptXmlRd.getReportMap(sectionNm).get("PRE_ROW_DATA_MAP") != null){
					rptXmlRd.getReportMap(sectionNm).put("PRE_ROW_DATA_MAP", map);
				}
				
				if (i+1 > _dataList.size()) {
					break;
				} else {
					ExpExcelOperator.paintCellStyle(excelSTLWr, bodyGroupFootCellStyleList, bodyRangs, _sheetIdx, 
							writeLocRow, bodyRowCount, i, initRow,marginRows);
				}
			}
			bodyEndLocRow = writeLocRow;	
		} catch(Exception ex) {
			throw new ExcelReportException("pageBodyGroupFootFetchData Error" + ex.getMessage(), ex);
		} finally {
			rptXmlRd.getReportMap(sectionNm).remove("PRE_ROW_DATA_MAP");
			rptXmlRd.getReportMap(sectionNm).remove("CUR_ROW_DATA_MAP");
			rptXmlRd.getReportMap(sectionNm).remove("IS_FINISH");
			rptXmlRd.getReportMap(sectionNm).remove("OPER_LAST_ROW");
			rptXmlRd.getReportMap(sectionNm).remove("BODY_MARGIN_ROWS");
		}
	}
	/**
	 * 对模板的合并处理
	 * @param sectionNm 片段名称
	 * @param _sheetIdx excel模板文件的序列号
	 */
	public void pageReportMerges(String sectionNm, int _sheetIdx) {
		try{
			WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
			List cellStyleList = (List)rptXmlRd.getReportMap(sectionNm).get("CellStyle");
			String _merges = (String)rptXmlRd.getReportMap(sectionNm).get("Merges");
			if (_merges==null) {
				return;
			}
			if (_merges.trim().length()==0){
				return;
			}
			String rangs[] = _merges.split(";");
			for(int i=0;i<rangs.length;i++){
				String merges = rangs[i];
				int locA = merges.indexOf("[");
				int locB = merges.indexOf("]");
				merges = merges.substring(locA+1,locB);
				String[] rowsCols = merges.split(",");
				int col01 = Integer.parseInt(rowsCols[0]);
				int col02 = Integer.parseInt(rowsCols[1]);
				int row01 = Integer.parseInt(rowsCols[2]);
				int row02 = Integer.parseInt(rowsCols[3]);
				for (int j = 0; j < cellStyleList.size(); j++) {
					ExcelCell tmpCell = (ExcelCell) cellStyleList.get(j);
					if (tmpCell.getInitCol() == col01 && tmpCell.getInitRow() == row01) {
						try {
							sheet.mergeCells(col01, tmpCell.getLocRow(), col02, tmpCell.getLocRow()+row02-row01);
						} catch (Exception e) {
						}
					}
				}
			}
		} catch(Exception ex) {
			throw new ExcelReportException("pageReportMerges Error" + ex.getMessage(), ex);
		}
	}
}