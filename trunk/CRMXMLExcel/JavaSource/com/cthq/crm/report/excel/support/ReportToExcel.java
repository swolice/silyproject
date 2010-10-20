package com.cthq.crm.report.excel.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Range;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.cthq.crm.excel.write.ExcelCell;
import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.excel.write.ICellType;
import com.cthq.crm.project.common.util.ReadFile;
import com.cthq.crm.report.xml.support.ReportXMLReader;
/**
 * 
 * @author 蒋峰
 *
 */
public class ReportToExcel {
	//EXCEL输出模板
	private String reportxml = "";
	
	//EXCEL操作文件
	private ExcelSTLWrite excelSTLWr = new ExcelSTLWrite();
	
	//EXCEL模板文件的头部样式描述集合
	private List headCellStyleList = new ArrayList();
	
	//EXCEL模板文件本体样式描述集合
	private List bodyCellStyleList = new ArrayList();
	//
	private List bodyGroupHeadCellStyleList  = new ArrayList();
	//EXCEL本体单元格样式描述
	private Range bodyGHRangs[] = null;
	
	private List bodyGroupFootCellStyleList  = new ArrayList();
	//EXCEL本体单元格样式描述
	private Range bodyGFRangs[] = null;
	
	
	//EXCEL本体单元格样式描述
	private Range bodyRangs[] = null;
	
	private List bodyGroupCellStyleList = new ArrayList();
	
	//EXCEL模板文件底部样式描述集合
	private List footCellStyleList = new ArrayList();
	
	//EXCEL报表模板描述文件
	private ReportXMLReader rptXmlRd = new ReportXMLReader();
	
	//本体写数据的起始行号
	private int bodyStaLocRow = -1;
	
	//本体写数据的结束行号
	private int bodyEndLocRow = -1;

	/**
	 * 创建EXCEL模板文件的XML描述集合
	 * @param _xmlPath 模板文件描述集合
	 */
	private void createReportXMLReader(String _xmlPath){
		ReadFile rf = new ReadFile();
		String xmlstr = rf.getFileContent(_xmlPath, "UTF-8");
		reportxml = xmlstr;
		try {
			rptXmlRd.analysisXML(xmlstr);
		} catch (Exception e) {
		}
		rptXmlRd.intReportMap();
	}
	public void reset(){
		bodyStaLocRow = -1;
		bodyEndLocRow = -1;
		bodyRangs = null;
		headCellStyleList.clear();
		bodyCellStyleList.clear(); 
		bodyGroupFootCellStyleList.clear();
		bodyGroupHeadCellStyleList.clear();
		footCellStyleList.clear();
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
	public void setExcelSTLWrite(ExcelSTLWrite _excelSTLWr) {
		this.excelSTLWr = _excelSTLWr;
	}
	/**
	 * 初始化EXCEL模板的样式
	 * @param _sheetIdx EXCEL操作的文件所在位置
	 */
	public void initExcelTempStyle(int _sheetIdx){
		this.initExcelHeadStyle(_sheetIdx);
		this.initExcelBodyStyle(_sheetIdx);
		this.initExcelBodyGroupStyle(_sheetIdx,"head");
		this.initExcelBodyGroupStyle(_sheetIdx,"foot");
		this.intExcelFootStyle(_sheetIdx);
		
	}
	/**
	 * 初始化EXCEL模板的头部样式
	 * @param _sheetIdx
	 */
	private void initExcelHeadStyle(int _sheetIdx) {
		WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
		//头部所占用的起始行和结束行号
		String headRows = rptXmlRd.getexcelPropData("headRows");
		//头部所占用的起始列和结束列号
		String headCols = rptXmlRd.getexcelPropData("headCols");
		//初始化EXCEL的头部样式结合
		if (headCellStyleList.isEmpty()) {
			if (headRows == null || headCols == null ){
				return;
			}
			if (headRows.trim().length() == 0) {
				return;
			}
			if (headCols.trim().length() == 0) {
				return;
			}
			String[] rows = headRows.split(",");
			String[] cols = headCols.split(",");
			int row01 = Integer.parseInt(rows[0]);
			int row02 = Integer.parseInt(rows[1]);
			int col01 = Integer.parseInt(cols[0]);
			int col02 = Integer.parseInt(cols[1]);
			//获取模板的样式
			for (int row = row01; row <= row02; row++) {
				for (int col = col01; col <= col02; col++) {
					headCellStyleList.add(excelSTLWr.getLocalCell(sheet, col, row));
				}
			}
		}
	
	}
	/**
	 * 初始化EXCEL模板的本体样式
	 * @param _sheetIdx
	 */
	private void initExcelBodyStyle(int _sheetIdx){
		WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
		//起始行位置
		int initRow = 0;
		//本体的总行数
		int bodyRowCount = 0;
		//本体的总列数
		int bodyColCount= 0;
		//各本体之间的间隔行数
		int marginRows = 0; 
		
		String bodyRows = rptXmlRd.getexcelPropData("bodyRows");
		//头部所占用的起始列和结束列号
		String bodyCols = rptXmlRd.getexcelPropData("bodyCols");
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
//		
//		//EXCEL本体的起始位置
//		initRow=Integer.parseInt(rptXmlRd.getexcelPropData("sourceRow"));
//		//EXCEL本体的所占的行数
//		bodyRowCount = Integer.parseInt(rptXmlRd.getexcelPropData("bodyRows"));
//		//EXCEL本体所在的列数
//		bodyColCount = Integer.parseInt(rptXmlRd.getexcelPropData("bodyCols"));
//		
		//EXECL本体数据一行所在的行数
		marginRows = Integer.parseInt(rptXmlRd.getexcelPropData("margin"));
		//初始化本体样式
		if (bodyCellStyleList.isEmpty()) {
			//获取模板的样式
			for (int row = initRow; row < initRow + bodyRowCount; row++) {
				for (int col = 0; col < bodyColCount; col++) {
					bodyCellStyleList.add(excelSTLWr.getLocalCell(sheet, col, row));
				}
			}
			bodyRangs = sheet.getMergedCells();
		}
	}
	/**
	 * 初始化EXCEL模板的本体组的样式 主要是小计 中计 大计 分组合计等处理
	 * @param _sheetIdx
	 */
	private void initExcelBodyGroupStyle(int _sheetIdx, String iden) {
		WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
		String grows = null;
		String gcols = null;
		if (iden.equals("head")) {
			grows = rptXmlRd.getexcelPropData("bodyGHRows");
			gcols = rptXmlRd.getexcelPropData("bodyGHCols");
		}
		if (iden.equals("foot")) {
			grows = rptXmlRd.getexcelPropData("bodyGFRows");
			gcols = rptXmlRd.getexcelPropData("bodyGFCols");
		}
		String[] rows = grows.split(",");
		String[] cols = gcols.split(",");
		
//		int row01 = Integer.parseInt(rowArr[0])+ 1 + 0
//						- Integer.parseInt(rowArr[0]) - 1 
//						- Integer.parseInt(rptXmlRd.getexcelPropData("margin"));
//		
//		int row02 = Integer.parseInt(rowArr[1])+ 1 + 0
//						-Integer.parseInt(rowArr[1])- 1 
//						-Integer.parseInt(rptXmlRd.getexcelPropData("margin"));
//		int col01 = Integer.parseInt(colArr[0]);
//		int col02 = Integer.parseInt(colArr[1]);
		
		int row01 = Integer.parseInt(rows[0]);
		int row02 = Integer.parseInt(rows[1]);
		int col01 = Integer.parseInt(cols[0]);
		int col02 = Integer.parseInt(cols[1]);
		
		//获取模板的样式
		for (int row = row01; row <= row02; row++) {
			for (int col = col01; col <= col02; col++) {
				if (iden.equals("head")) {
					bodyGroupHeadCellStyleList.add(excelSTLWr.getLocalCell(sheet, col, row));
					continue;
				}
				if (iden.equals("foot")) {
					bodyGroupFootCellStyleList.add(excelSTLWr.getLocalCell(sheet, col, row));
					continue;
				}
			}
		}
		bodyRangs = sheet.getMergedCells();
	}
	/**
	 * 初始化EXCEL模板的底部的样式
	 * @param _sheetIdx
	 */
	private void intExcelFootStyle(int _sheetIdx) {
		WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
		String footRows = rptXmlRd.getexcelPropData("footRows");
		String footCols = rptXmlRd.getexcelPropData("footCols");
		//初始化底部的样式集合
		if (footCellStyleList.isEmpty()) {
			if (footRows == null || footCols == null ){
				return;
			}
			if (footRows.trim().length() == 0) {
				return;
			}
			if (footCols.trim().length() == 0) {
				return;
			}
			//获取模板底部的起始和终止行号
			String[] rows = footRows.split(",");
			//获取模板底部的起始列和终止列号
			String[] cols = footCols.split(",");
			int initRow=Integer.parseInt(rptXmlRd.getexcelPropData("sourceRow"));
			int row01 = Integer.parseInt(rows[0])+ 1 + 0
								- Integer.parseInt(rows[0]) - 1 
								- Integer.parseInt(rptXmlRd.getexcelPropData("margin"));
			
			int row02 = Integer.parseInt(rows[1])+ 1 + 0
								-Integer.parseInt(rows[1])- 1 
								-Integer.parseInt(rptXmlRd.getexcelPropData("margin"));
			
			int col01 = Integer.parseInt(cols[0]);
			int col02 = Integer.parseInt(cols[1]);
			//获取模板的样式
			for (int row = row01; row <= row02; row++) {
				for (int col = col01; col <= col02; col++) {
					footCellStyleList.add(excelSTLWr.getLocalCell(sheet, col, row));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param _xmlPath
	 */
	public void setExcelTempRptXml(String _xmlPath) {
		createReportXMLReader(_xmlPath);
	}
	
	/**
	 * 写入EXCEL模板的头部文件数据
	 * @param _dataMap 头部数据集合映射 KEY对应EXCEL文件中的模板属性
	 * @param _sheetIdx 操作的EXCEL的Sheet位置
	 */
	public void pageHeadFetchData(Map _dataMap, int _sheetIdx){

		
		WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
		//头部所占用的起始行和结束行号
		String headRows = rptXmlRd.getexcelPropData("headRows");
		//头部所占用的起始列和结束列号
		String headCols = rptXmlRd.getexcelPropData("headCols");
		//初始化EXCEL的头部样式结合
		if (headCellStyleList.isEmpty()) {
			if (headRows == null || headCols == null ){
				return;
			}
			if (headRows.trim().length() == 0) {
				return;
			}
			if (headCols.trim().length() == 0) {
				return;
			}
			String[] rows = headRows.split(",");
			String[] cols = headCols.split(",");
			int row01 = Integer.parseInt(rows[0]);
			int row02 = Integer.parseInt(rows[1]);
			int col01 = Integer.parseInt(cols[0]);
			int col02 = Integer.parseInt(cols[1]);
			//获取模板的样式
			for (int row = row01; row <= row02; row++) {
				for (int col = col01; col <= col02; col++) {
					headCellStyleList.add(excelSTLWr.getLocalCell(sheet, col, row));
				}
			}
		}
		
		//填写EXCEL模板头部的数据集合
		for (int i = 0; i < headCellStyleList.size(); i++) {
			ExcelCell tmpCell = (ExcelCell) headCellStyleList.get(i);
			ExcelCell copyCell = tmpCell.copyCell();
			String content = copyCell.getLocStr();
			if(content.matches(".*#.+#.*")){
				String key = content.substring(content.indexOf("#")+1,content.lastIndexOf("#"));
				if (_dataMap.get(key) != null) {
					try {
						String value = (String)_dataMap.get(key);
						copyCell.setLocStr(content.replaceAll("#"+key+"#", value));
						excelSTLWr.setCell(sheet, copyCell, ICellType.STRING);
					} catch (Exception e) {
						throw new ExcelReportException("pageHeadFetchData error", e);
					}
				}
			} 
				
		}	
	}
	public void pageHeadGroupFetch(){
		
	}
	/**
	 * 
	 * @param _dataList
	 * @param _sheetIdx
	 */
	public void pageBodyGroupHeadFetchData(List _dataList, int _sheetIdx){
		WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
		//起始行位置
		int initRow = 0;
		//本体的总行数
		int bodyRowCount = 0;
		//本体的总列数
		int bodyColCount= 0;
		//各本体之间的间隔行数
		int marginRows = 0; 
		//EXCEL本体的起始位置
		String bgHRows = rptXmlRd.getexcelPropData("bodyGHRows");
		//头部所占用的起始列和结束列号
		String bgHCols = rptXmlRd.getexcelPropData("bodyGHCols");
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
		marginRows = Integer.parseInt(rptXmlRd.getexcelPropData("margin"));
		//初始化本体样式
		if (bodyGroupHeadCellStyleList.isEmpty()) {
			//获取模板的样式
			for (int row = initRow; row < initRow + bodyRowCount; row++) {
				for (int col = 0; col < bodyColCount; col++) {
					bodyGroupHeadCellStyleList.add(excelSTLWr.getLocalCell(sheet, col, row));
				}
			}
			bodyRangs = sheet.getMergedCells();
		}
		int writeLocRow = initRow;
		bodyStaLocRow = initRow;
		//本体数据多次数据填写处理 起始行和起始列
		if (bodyEndLocRow != -1) {
			writeLocRow = bodyEndLocRow;
			bodyStaLocRow = bodyEndLocRow;
			//插入间隔空白行
//			for (int i = 0; i < bodyRowCount + marginRows; i++) {
//				excelSTLWr.insertRow(sheet, writeLocRow+i-marginRows);
//			}
			for (int i = 0; i < bodyGroupHeadCellStyleList.size(); i++) {
				ExcelCell tmpCell = (ExcelCell) bodyGroupHeadCellStyleList.get(i);
				tmpCell.setLocRow(bodyStaLocRow + tmpCell.getInitRow()-row01);
			}	
		}
		
		//填写EXCEL本体数据
		for (int i = 1; i <= _dataList.size(); i++) {
			Map map = (Map)_dataList.get(i-1);
			ExpExcelOperator.writeData(excelSTLWr,  bodyGroupHeadCellStyleList , map, _sheetIdx, 
					writeLocRow, bodyRowCount, bodyColCount, bodyRowCount * (i-1),  initRow,marginRows*(i-1), null);
			writeLocRow = writeLocRow + bodyRowCount + marginRows;
		}
		pageBodyGroupMerges(sheet, bodyGroupHeadCellStyleList, "bodyGHMerge");
		bodyEndLocRow = writeLocRow;
	}
	/**
	 * 写入EXCEL模板的本体文件数据
	 * @param _dataList EXCEL本体数据集合列表，每行列表对应EXCEL模板本体的一行数据映射集合 KEY对应EXCEL文件中的模板属性
	 * @param _sheetIdx 操作的EXCEL的Sheet位置
	 */
	public void pageBodyFetchData(List _dataList, int _sheetIdx){
		WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
		//起始行位置
		int initRow = 0;
		//本体的总行数
		int bodyRowCount = 0;
		//本体的总列数
		int bodyColCount= 0;
		//各本体之间的间隔行数
		int marginRows = 0; 
		String bodyRows = rptXmlRd.getexcelPropData("bodyRows");
		//头部所占用的起始列和结束列号
		String bodyCols = rptXmlRd.getexcelPropData("bodyCols");
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
		marginRows = Integer.parseInt(rptXmlRd.getexcelPropData("margin"));
		
		
		//初始化本体样式
		if (bodyCellStyleList.isEmpty()) {
			//获取模板的样式
			for (int row = initRow; row < initRow + bodyRowCount; row++) {
				for (int col = 0; col < bodyColCount; col++) {
					bodyCellStyleList.add(excelSTLWr.getLocalCell(sheet, col, row));
				}
			}
			bodyRangs = sheet.getMergedCells();
		}
		int writeLocRow = initRow;
		bodyStaLocRow = initRow;
		//本体数据多次数据填写处理 起始行和起始列
		if (bodyEndLocRow != -1) {
			writeLocRow = bodyEndLocRow;
			bodyStaLocRow = bodyEndLocRow;
			//插入间隔空白行
//			for (int i = 0; i < bodyRowCount + marginRows; i++) {
//				excelSTLWr.insertRow(sheet, writeLocRow+i-marginRows);
//			}
			for (int i = 0; i < bodyCellStyleList.size(); i++) {
				ExcelCell tmpCell = (ExcelCell) bodyCellStyleList.get(i);
				tmpCell.setLocRow(bodyStaLocRow);
			}	
		}
		
		//填写EXCEL本体数据
		for (int i = 1; i <= _dataList.size(); i++) {
			Map map = (Map)_dataList.get(i-1);
			ExpExcelOperator.writeData(excelSTLWr,  bodyCellStyleList , map, _sheetIdx, 
					writeLocRow, bodyRowCount, bodyColCount, bodyRowCount * (i-1),  initRow,marginRows*(i-1), null);
			writeLocRow = writeLocRow + bodyRowCount + marginRows;
			if (i+1 > _dataList.size()) {
				break;
			} else {
				ExpExcelOperator.paintCellStyle(excelSTLWr, bodyCellStyleList, bodyRangs, _sheetIdx, 
						writeLocRow, bodyRowCount, i, initRow,marginRows);
			}
		}
		bodyEndLocRow = writeLocRow;
	}
	
	/**
	 * 针对本体填入的数据合并
	 * @param _sheetIdx
	 */
	public void PageBodyColsMerges(int _sheetIdx, String  colMerges) {
		WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
		int col1=0;
		int row1=this.bodyStaLocRow;
		int col2=0;
		int row2=this.bodyEndLocRow-1;
		if (colMerges == null) {
			return;
		}
		if (colMerges.trim().length() ==0) {
			return;
		}
		String[] mergeCols = colMerges.split(",");
		for(int i=0;i<mergeCols.length; i++) {
			col1 = Integer.parseInt(mergeCols[i]);
			col2 = Integer.parseInt(mergeCols[i]);
			try {
				sheet.mergeCells(col1, row1, col2, row2);
			} catch (Exception e) {
				throw new ExcelReportException("bodyColsMerges error", e);
			}
		}
		
	}
	
	public void pageBodyGroupFootFetchData(List _dataList, int _sheetIdx){
		WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
		//起始行位置
		int initRow = 0;
		//本体的总行数
		int bodyRowCount = 0;
		//本体的总列数
		int bodyColCount= 0;
		//各本体之间的间隔行数
		int marginRows = 0; 
		
		
		String bgFRows = rptXmlRd.getexcelPropData("bodyGFRows");
		//头部所占用的起始列和结束列号
		String bgFCols = rptXmlRd.getexcelPropData("bodyGFCols");
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
		marginRows = Integer.parseInt(rptXmlRd.getexcelPropData("margin"));
		//初始化本体样式
		if (bodyGroupFootCellStyleList.isEmpty()) {
			//获取模板的样式
			for (int row = row01; row < row02; row++) {
				for (int col = col01; col < col02; col++) {
					bodyGroupFootCellStyleList.add(excelSTLWr.getLocalCell(sheet, col, row));
				}
			}
			bodyRangs = sheet.getMergedCells();
		}
		int writeLocRow = initRow;
		bodyStaLocRow = initRow;
		//本体数据多次数据填写处理 起始行和起始列
		if (bodyEndLocRow != -1) {
			writeLocRow = bodyEndLocRow;
			bodyStaLocRow = bodyEndLocRow;
			//插入间隔空白行
//			for (int i = 0; i < bodyRowCount + marginRows; i++) {
//				excelSTLWr.insertRow(sheet, writeLocRow+i-marginRows);
//			}
			for (int i = 0; i < bodyGroupFootCellStyleList.size(); i++) {
				ExcelCell tmpCell = (ExcelCell) bodyGroupFootCellStyleList.get(i);
				tmpCell.setLocRow(bodyStaLocRow + tmpCell.getInitRow()-row01);
				
			}	
		}
		
		
		//填写EXCEL本体数据
		for (int i = 1; i <= _dataList.size(); i++) {
			Map map = (Map)_dataList.get(i-1);
			ExpExcelOperator.writeData(excelSTLWr,  bodyGroupFootCellStyleList , map, _sheetIdx, 
					writeLocRow, bodyRowCount, bodyColCount, bodyRowCount * (i-1),  initRow,marginRows*(i-1), null);
			writeLocRow = writeLocRow + bodyRowCount + marginRows;
		}
		pageBodyGroupMerges(sheet, bodyGroupFootCellStyleList, "bodyGFMerge");
		bodyEndLocRow = writeLocRow;
	}	
	/**
	 * 针对本体填入的数据合并
	 * @param _sheetIdx
	 */
	private void pageBodyGroupMerges(WritableSheet sheet, List cellStyleList, String mergeIden) {
		String bodyGRange = rptXmlRd.getexcelPropData(mergeIden);
		if (bodyGRange == null) {
			return;
		}
		String rangs[] = bodyGRange.split(";");
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
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	/**
	 * 写入EXCEL模板的底部文件数据
	 * @param _dataMap 底部数据集合映射 KEY对应EXCEL文件中的模板属性
	 * @param _sheetIdx 操作的EXCEL的Sheet位置
	 */
	public void pageFootFetchData(Map _dataMap, int _sheetIdx){
		WritableSheet sheet = excelSTLWr.getWorkSheet(_sheetIdx);
		String footRows = rptXmlRd.getexcelPropData("footRows");
		String footCols = rptXmlRd.getexcelPropData("footCols");
		//初始化底部的样式集合
		if (footCellStyleList.isEmpty()) {
			if (footRows == null || footCols == null ){
				return;
			}
			if (footRows.trim().length() == 0) {
				return;
			}
			if (footCols.trim().length() == 0) {
				return;
			}
			//获取模板底部的起始和终止行号
			String[] rows = footRows.split(",");
			//获取模板底部的起始列和终止列号
			String[] cols = footCols.split(",");
			int initRow=Integer.parseInt(rptXmlRd.getexcelPropData("sourceRow"));
			int row01 = Integer.parseInt(rows[0])+ 1 + this.bodyEndLocRow 
								- Integer.parseInt(rows[0]) - 1 
								- Integer.parseInt(rptXmlRd.getexcelPropData("margin"));
			
			int row02 = Integer.parseInt(rows[1])+ 1 + this.bodyEndLocRow
								-Integer.parseInt(rows[1])- 1 
								-Integer.parseInt(rptXmlRd.getexcelPropData("margin"));
			
			int col01 = Integer.parseInt(cols[0]);
			int col02 = Integer.parseInt(cols[1]);
			//获取模板的样式
			for (int row = row01; row <= row02; row++) {
				for (int col = col01; col <= col02; col++) {
					footCellStyleList.add(excelSTLWr.getLocalCell(sheet, col, row));
				}
			}
		}
		//填写数据
		for (int i = 0; i < footCellStyleList.size(); i++) {

			ExcelCell tmpCell = (ExcelCell) footCellStyleList.get(i);
			ExcelCell copyCell = tmpCell.copyCell();
			String content = copyCell.getLocStr();
			if(content.matches(".*#.+#.*")){
				String key = content.substring(content.indexOf("#")+1,content.lastIndexOf("#"));
				if (_dataMap.get(key) != null) {
					try {
						String value = (String)_dataMap.get(key);
						copyCell.setLocStr(content.replaceAll("#"+key+"#", value));
						excelSTLWr.setCell(sheet, copyCell, ICellType.STRING);
					} catch (Exception e) {
						throw new ExcelReportException("pageFootFecthData error", e);
					}
				}
			}
		}	
		
	}
	public void pageFootGroupFetchData(){
		
	}
	public void pageTotalFetchData(){
		
	}
	
	public void dispose(){
		this.excelSTLWr.dispose();
	}
}
