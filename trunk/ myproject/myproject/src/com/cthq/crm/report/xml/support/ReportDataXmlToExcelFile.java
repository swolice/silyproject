/*
 * Created on 2009-11-13
 *
 */
package com.cthq.crm.report.xml.support;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Range;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cthq.crm.common.database.ICommonBean;
import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.report.excel.support.ExpExcelOperator;
import com.cthq.crm.report.util.ReportProcNumberNoGet;
import com.cthq.crm.report.util.ResultSetOperator;
import com.cthq.crm.webservice.util.ReadFile;
import com.cthq.crm.webservice.util.WriteFile;
import com.cthq.crm.webservice.xml.template.engine.XmlTemplateContainer;

/**
 * @author sily
 *  
 */
public class ReportDataXmlToExcelFile {
	
	private Log log = LogFactory.getLog(this.getClass());
	/**
	 * 返回按xml模板生成的头数据串
	 * @param xmlTemp 模板的实际路径
	 * @param infoMap 头部数据键值对
	 * @return 按xml模板生成的数据串
	 * @throws Exception
	 */
	private String getHeadDataXmlString(String xmlTemp, Map infoMap)
			throws Exception {
		
		//解读XML模板文件与数据库数据转换存储的模板容器
		XmlTemplateContainer xmlTempContainer = new XmlTemplateContainer();
		ReportXMLParse xmlParser = new ReportXMLParse();
		List xmlLevelList = xmlParser.getXMLLevelList();
		xmlTempContainer.setXMLLevelList(xmlLevelList);
		//设置XML模板文件解析器
		xmlTempContainer.setXMLReader(xmlTemp);
		xmlTempContainer.setXMLLevelData(infoMap, 1, 0);
		//创建XML层次数据
		xmlParser.createXMLLevelData(xmlTempContainer);
		//XML字符串
		xmlParser.getXMLContent();
		String headXML = xmlParser.getXMLContent((String) xmlLevelList.get(1),
				0);
		
		
		return "<root><flag>1</flag>"+headXML.replaceAll("\n","").replaceAll("\r","")+"</root>";
	}

	/**
	 * 返回按xml模板生成的中部数据串
	 * @param xmlTemp 模板的实际路径
	 * @param infoMap 中部数据键值对
	 * @return 按xml模板生成的数据串
	 * @throws Exception
	 */
	private String getBodyDataXmlString(String xmlTemp, Map infoMap)
			throws Exception {
		//解读XML模板文件与数据库数据转换存储的模板容器
		XmlTemplateContainer xmlTempContainer = new XmlTemplateContainer();
		//设置XML模板文件的层次数据
		ReportXMLParse xmlParser = new ReportXMLParse();
		List xmlLevelList = xmlParser.getXMLLevelList();
		xmlTempContainer.setXMLLevelList(xmlLevelList);
		//设置XML模板文件解析器
		xmlTempContainer.setXMLReader(xmlTemp);
		xmlTempContainer.setXMLLevelData(infoMap, 2, 0);
		infoMap.clear();
		//创建XML层次数据
		xmlParser.createXMLLevelData(xmlTempContainer);
		//XML字符串
		xmlParser.getXMLContent();
		String bodyXML = xmlParser.getXMLContent((String) xmlLevelList.get(2),
				0);
		
		return "<root><flag>2</flag>"+bodyXML.replaceAll("\n","").replaceAll("\r","")+"</root>";
	}

	/**
	 * 返回按xml模板生成的尾部数据串
	 * @param xmlTemp 模板的实际路径
	 * @param infoMap 尾部数据键值对
	 * @return 按xml模板生成的数据串
	 * @throws Exception
	 */
	private String getFootDataXmlString(String xmlTemp, Map infoMap)
			throws Exception {
		
		
		//解读XML模板文件与数据库数据转换存储的模板容器
		XmlTemplateContainer xmlTempContainer = new XmlTemplateContainer();
		ReportXMLParse xmlParser = new ReportXMLParse();
		List xmlLevelList = xmlParser.getXMLLevelList();
		xmlTempContainer.setXMLLevelList(xmlLevelList);
		//设置XML模板文件解析器
		xmlTempContainer.setXMLReader(xmlTemp);
		xmlTempContainer.setXMLLevelData(infoMap, 3, 0);
		//创建XML层次数据

		xmlParser.createXMLLevelData(xmlTempContainer);
		//XML字符串
		xmlParser.getXMLContent();
		String footXML = xmlParser.getXMLContent((String) xmlLevelList.get(3),
				0);
		return "<root><flag>3</flag>"+footXML.replaceAll("\n","").replaceAll("\r","")+"</root>";
	}

	private String createTempDir(){
	    String tmpdir = "/";
	    try {
	        tmpdir = Thread.currentThread().getContextClassLoader().getResource("log4j.properties").getPath(); 
			tmpdir = tmpdir.replaceAll("%20"," ").split("WEB-INF")[0] + "temp/";
			File file = new File(tmpdir);
			if(!file.exists()){
				file.mkdirs();
			}
        } catch (Exception e) {
            tmpdir = System.getProperty("java.io.tmpdir");
        }
		
		return tmpdir;
	}

	/**
	 *	通过临时文件中取到的数据，写入excel中 其中body为多行循环体
	 * @param xmlfile 存放xml格式的excel数据临时文件
	 * @param excelTempFile excel模板文件
	 * @param outExcelFile excel输出文件
	 * @param rows xmlfile文件的行数 每行显示一个body循环体
	 * @param EXCEL_SHEET_ROW_SIZE sheet每页显示的记录条数
	 */
	
	private void writeXmlBodyToExcel(String xmlfile, String excelTempFile,String outExcelFile,int rows,Map pramMap) {
		
		File file = new File(xmlfile);
		Workbook rwb = null;
		FileInputStream fis = null;
		WritableWorkbook wwb = null;
		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		ReportXMLReader rxr = null;
		ExcelSTLWrite excelSTLWr = new ExcelSTLWrite();
		try {
			excelSTLWr.init(excelTempFile, outExcelFile);
			wwb = excelSTLWr.getGWriteBook();
			reader = new InputStreamReader(
					new FileInputStream(file),"UTF-8");
			bufferedReader = new BufferedReader(reader);
			//创建xml解析器
			rxr = new ReportXMLReader();
			WritableSheet sheet = excelSTLWr.getWorkSheet(0);
			
			int EXCEL_SHEET_ROW_SIZE = Integer.parseInt((String)pramMap.get("count"));			
			//判断工作表的个数
			int sheets = (rows % EXCEL_SHEET_ROW_SIZE == 0) ? (rows / EXCEL_SHEET_ROW_SIZE)
					: (rows / EXCEL_SHEET_ROW_SIZE + 1);
			//删除除模板外其他的sheet
			while(wwb.getNumberOfSheets()>1){
				wwb.removeSheet(1);
			}
			//复制工作表
			for(int i=1;i<sheets;i++){
				wwb.copySheet(0,sheet.getName() + i,i);
			}
			String line = null;
			int count = 0;
			//获取插入数据的起始行
			
			int sourceRow = 0;
			int bodyRows = 0;
			int bodyCols= 0;
			int margin = 0; 
			if(pramMap.size()>1){
				 sourceRow=Integer.parseInt((String)pramMap.get("sourceRow"));
				 bodyRows=Integer.parseInt((String)pramMap.get("bodyRows"));
				 bodyCols=Integer.parseInt((String)pramMap.get("bodyCols"));
				 margin=Integer.parseInt((String)pramMap.get("margin"));
			}
			int tempRow = sourceRow;
			int sheetLoc = 0;
			while ((line = bufferedReader.readLine()) != null) {
				//如果行为空，就不解析
				if("".equals(line.trim())){
					continue;
				}
				
				try {
					rxr.analysisXML(line);
					//如果flag值为1：表示头部2：表示中部 3：表示尾部
					if("1".equals(rxr.getXmlDomElementLeafNodeValue("root","flag",0))){
						Map map = rxr.getXmlLevelItemAttrMap("root/head");
						//头部都一样
						for(int i=0;i<wwb.getNumberOfSheets();i++){
							ExpExcelOperator.insertHeaderData(wwb.getSheet(i), map);
						}
					}else if("3".equals(rxr.getXmlDomElementLeafNodeValue("root","flag",0))){
						Map map = rxr.getXmlLevelItemAttrMap("root/foot");
						//尾部数据一样
						for(int i=0;i<wwb.getNumberOfSheets();i++){
							ExpExcelOperator.insertFootData(wwb.getSheet(i), map);
						}
					}else if("2".equals(rxr.getXmlDomElementLeafNodeValue("root","flag",0))){
						Map map = rxr.getXmlLevelItemAttrMap("root/body");
						ExpExcelOperator.copyRows(excelSTLWr,sheetLoc, tempRow, bodyRows, bodyCols, margin, map, null);
						tempRow = tempRow + bodyRows + margin;
						count++;
					}else{
						continue;
					}
					//sheet满足一定的行数，换sheet写入
					if(count != 0 && (count % EXCEL_SHEET_ROW_SIZE == 0)){
						excelSTLWr.removeRangeRows(sheet, tempRow-margin, tempRow + bodyRows);
						sheetLoc = count/EXCEL_SHEET_ROW_SIZE;
						sheet = excelSTLWr.getWorkSheet(sheetLoc);
						tempRow = sourceRow;
					}
				} catch (Exception e1) {
					log.error(e1.getMessage(),e1);
				}
			}
			//删除最后复制的模板行
			if(count>0){
				excelSTLWr.removeRangeRows(sheet, tempRow-margin, tempRow + bodyRows);
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (BiffException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			excelSTLWr.dispose();
			rxr.dispose();
			try {
				if (null != rwb)
					rwb.close();
				if (null != fis)
					fis.close();
				if(null != bufferedReader)
					bufferedReader.close();
				if(null != reader)
					reader.close();
			} catch (IOException e1) {
				log.error(e1.getMessage(), e1);
			}
		}
	}
	/**
	 *	通过临时文件中取到的数据，写入excel中 其中body为多行循环体
	 * @param xmlfile 存放xml格式的excel数据临时文件
	 * @param excelTempFile excel模板文件
	 * @param outExcelFile excel输出文件
	 * @param rows xmlfile文件的行数 每行显示一个body循环体
	 * @param pramMap sheet相关参数
	 */
	
	private void writeXmlBodyToExcelByIndex(String xmlfile, String excelTempFile,String outExcelFile,int rows,Map pramMap) {
		
		File file = new File(xmlfile);
		Workbook rwb = null;
		FileInputStream fis = null;
		WritableWorkbook wwb = null;
		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		ReportXMLReader rxr = null;
		ExcelSTLWrite excelSTLWr = new ExcelSTLWrite();
		try {
			excelSTLWr.init(excelTempFile, outExcelFile);
			wwb = excelSTLWr.getGWriteBook();
			reader = new InputStreamReader(
					new FileInputStream(file),"UTF-8");
			bufferedReader = new BufferedReader(reader);
			//创建xml解析器
			rxr = new ReportXMLReader();
			int index = Integer.parseInt((String)pramMap.get("index"));
			WritableSheet sheet = excelSTLWr.getWorkSheet(index);
			String line = null;
			//获取插入数据的起始行
			
			int sourceRow = 0;
			int bodyRows = 0;
			int bodyCols= 0;
			int margin = 0; 
			if(pramMap.size()>1){
				sourceRow=Integer.parseInt((String)pramMap.get("sourceRow"));
				bodyRows=Integer.parseInt((String)pramMap.get("bodyRows"));
				bodyCols=Integer.parseInt((String)pramMap.get("bodyCols"));
				margin=Integer.parseInt((String)pramMap.get("margin"));
			}
			int tempRow = sourceRow;
			while ((line = bufferedReader.readLine()) != null) {
				//如果行为空，就不解析
				if("".equals(line.trim())){
					continue;
				}
				try {
					rxr.analysisXML(line);
					//如果flag值为1：表示头部2：表示中部 3：表示尾部
					if("1".equals(rxr.getXmlDomElementLeafNodeValue("root","flag",0))){
						Map map = rxr.getXmlLevelItemAttrMap("root/head");
						ExpExcelOperator.insertHeaderData(sheet, map);
					}else if("3".equals(rxr.getXmlDomElementLeafNodeValue("root","flag",0))){
						Map map = rxr.getXmlLevelItemAttrMap("root/foot");
						ExpExcelOperator.insertFootData(sheet, map);
					}else if("2".equals(rxr.getXmlDomElementLeafNodeValue("root","flag",0))){
						Map map = rxr.getXmlLevelItemAttrMap("root/body");
						ExpExcelOperator.copyRows(excelSTLWr,index, tempRow, bodyRows, bodyCols, margin, map, null);
						tempRow = tempRow + bodyRows + margin;
					}else{
						continue;
					}
				} catch (Exception e1) {
					log.error(e1.getMessage(),e1);
				}
			}
			if(rows>0){
				excelSTLWr.removeRangeRows(sheet, tempRow-margin, tempRow + bodyRows);
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (BiffException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			excelSTLWr.dispose();
			rxr.dispose();
			try {
				if (null != rwb)
					rwb.close();
				if (null != fis)
					fis.close();
				if(null != bufferedReader)
					bufferedReader.close();
				if(null != reader)
					reader.close();
			} catch (IOException e1) {
				log.error(e1.getMessage(), e1);
			}
		}
	}
	
	/**
	 * 可以输出body为多行的循环体
	 * @param tempExcel excel模板文件路径
	 * @param tempxml 对应xml文件的模板路径
	 * @param list 存放数据
	 * @param count 每个sheet显示记录数
	 * @return
	 */
	public String getOutExcelWithMultiLine(String tempExcel,String tempxml, List list,Map headMap,Map bootMap,int count){
		
		if((null == tempExcel) || (null == list)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		//获取临时文件目录
		String tmpdir = createTempDir();
		String tempName = tempExcel.substring(tempExcel.lastIndexOf("/") + 1,tempExcel.lastIndexOf("."));
		if(tempName.length()>20){
			tempName = tempName.substring(0,20);
		}
		//临时文本文件名
		String tempfilename = tempName
		+ ReportProcNumberNoGet.getCurrentTime();
		//临时的excel文件
		String outExcel = tmpdir + tempName
		+ ReportProcNumberNoGet.getCurrentTime() + ".xls";
		try {
			int rows = this.writeListToXml(tmpdir, tempfilename, tempxml, list, headMap, bootMap);
			//当记录数为空的时候，创建一个空文件
			Map paraMap = new HashMap();
			if(rows == 0){
				File file = new File(tmpdir + tempfilename);
				if (!file.exists()) {
					file.createNewFile();
				}
			}else{
				paraMap = getParaMap(tempxml);
			}
			paraMap.put("count", ""+count);
			
			this.writeXmlBodyToExcel(tmpdir + tempfilename, tempExcel,
					outExcel,rows,paraMap);
			
			return outExcel;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file = new File(tmpdir + tempfilename);
			if (null != file) {
				file.delete();
			}
		}
		return null;
	}
	/**
	 * 通过一定格式xml文件向excel模板中插入数据
	 * @param xmlfile
	 * @param tempExcel
	 * @param tempxml
	 * @param index
	 * @return
	 */
	public String getExcelByDataXml(String xmlfile, String tempExcel,String tempxml,int index){
		File file = new File(xmlfile);
		Workbook rwb = null;
		FileInputStream fis = null;
		WritableWorkbook wwb = null;
		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		ReportXMLReader rxr = null;
		ExcelSTLWrite excelSTLWr = new ExcelSTLWrite();
		//获取临时文件目录
		String tmpdir = createTempDir();
		String tempName = tempExcel.substring(tempExcel.lastIndexOf("/") + 1,tempExcel.lastIndexOf("."));
		if(tempName.length()>20){
			tempName = tempName.substring(0,20);
		}
		//临时的excel文件
		String outExcel = tmpdir + tempName
		+ ReportProcNumberNoGet.getCurrentTime() + ".xls";
		try {
			excelSTLWr.init(tempExcel, outExcel);
			wwb = excelSTLWr.getGWriteBook();
			reader = new InputStreamReader(
					new FileInputStream(file),"UTF-8");
			bufferedReader = new BufferedReader(reader);
			//创建xml解析器
			rxr = new ReportXMLReader();
			WritableSheet sheet = excelSTLWr.getWorkSheet(index);
			Map pramMap = getParaMap(tempxml);
			
			int count = 0;
			//获取插入数据的起始行
			int sourceRow = 0;
			int bodyRows = 0;
			int bodyCols= 0;
			int margin = 0; 
			if(null != pramMap){
				 sourceRow=Integer.parseInt((String)pramMap.get("sourceRow"));
				 bodyRows=Integer.parseInt((String)pramMap.get("bodyRows"));
				 bodyCols=Integer.parseInt((String)pramMap.get("bodyCols"));
				 margin=Integer.parseInt((String)pramMap.get("margin"));
			}
			int tempRow = sourceRow;
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				//如果行为空，就不解析
				if("".equals(line.trim())){
					continue;
				}
				try {
					rxr.analysisXML(line);
					//如果flag值为1：表示头部2：表示中部 3：表示尾部
					if("1".equals(rxr.getXmlDomElementLeafNodeValue("root","flag",0))){
						Map map = rxr.getXmlLevelItemAttrMap("root/head");
						//头部都一样
						for(int i=0;i<wwb.getNumberOfSheets();i++){
							ExpExcelOperator.insertHeaderData(wwb.getSheet(i), map);
						}
					}else if("3".equals(rxr.getXmlDomElementLeafNodeValue("root","flag",0))){
						Map map = rxr.getXmlLevelItemAttrMap("root/foot");
						//尾部数据一样
						for(int i=0;i<wwb.getNumberOfSheets();i++){
							ExpExcelOperator.insertFootData(wwb.getSheet(i), map);
						}
					}else if("2".equals(rxr.getXmlDomElementLeafNodeValue("root","flag",0))){
						Map map = rxr.getXmlLevelItemAttrMap("root/body");
						ExpExcelOperator.copyRows(excelSTLWr,index, tempRow, bodyRows, bodyCols, margin, map, null);
						tempRow = tempRow + bodyRows + margin;
						count++;
					}else{
						continue;
					}
				} catch (Exception e1) {
					log.error(e1.getMessage(),e1);
				}
			}
			//删除最后复制的模板行
			if(count>0){
				excelSTLWr.removeRangeRows(sheet, tempRow-margin, tempRow + bodyRows);
			}
			return outExcel;
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (BiffException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			excelSTLWr.dispose();
			rxr.dispose();
			try {
				if (null != rwb)
					rwb.close();
				if (null != fis)
					fis.close();
				if(null != bufferedReader)
					bufferedReader.close();
				if(null != reader)
					reader.close();
			} catch (IOException e1) {
				log.error(e1.getMessage(), e1);
			}
		}
		return null;
	}
	
	/**
	 * 可以输出body为多行的循环体
	 * @param tempExcel excel模板文件路径
	 * @param tempxml 对应xml文件的模板路径
	 * @param list 存放数据
	 * @param index 需要插入数据的sheet的下标
	 * @return
	 */
	public String getOutExcelBySheetIndex(String tempExcel,String tempxml, List list,Map headMap,Map bootMap,int index){
		
		if((null == tempExcel) || (null == list)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		//获取临时文件目录
		String tmpdir = createTempDir();
		String tempName = tempExcel.substring(tempExcel.lastIndexOf("/") + 1,tempExcel.lastIndexOf("."));
		if(tempName.length()>20){
			tempName = tempName.substring(0,20);
		}
		//临时文本文件名
		String tempfilename = tempName
		+ ReportProcNumberNoGet.getCurrentTime();
		//临时的excel文件
		String outExcel = tmpdir + tempName
		+ ReportProcNumberNoGet.getCurrentTime() + ".xls";
		try {
			int rows = this.writeListToXml(tmpdir, tempfilename, tempxml, list, headMap, bootMap);
			//当记录数为空的时候，创建一个空文件
			Map paraMap = new HashMap();
			if(rows == 0){
				File file = new File(tmpdir + tempfilename);
				if (!file.exists()) {
					file.createNewFile();
				}
			}else{
				paraMap = getParaMap(tempxml);
			}
			paraMap.put("index", ""+index);
			
			this.writeXmlBodyToExcelByIndex(tmpdir + tempfilename, tempExcel,
					outExcel,rows,paraMap);
			
			return outExcel;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file = new File(tmpdir + tempfilename);
			if (null != file) {
//				file.delete();
			}
		}
		return null;
	}
	/**
	 * 传入list时不需要写临时文本文件
	 * @param tempExcel excel模板文件路径
	 * @param tempxml 对应xml文件的模板路径
	 * @param list 存放数据
	 * @param index 需要插入数据的sheet的下标
	 * @return
	 */
	public String getOutExcelByWithOutXml(String tempExcel,String tempxml, List list,Map headMap,Map footMap,int intSheetIdx){
		
		if((null == tempExcel) || (null == list)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		//获取临时文件目录
		String tmpdir = createTempDir();
		String tempName = tempExcel.substring(tempExcel.lastIndexOf("/") + 1,tempExcel.lastIndexOf("."));
		if(tempName.length()>20){
			tempName = tempName.substring(0,20);
		}
		//临时的excel文件
		String outExcel = tmpdir + tempName
		+ ReportProcNumberNoGet.getCurrentTime() + ".xls";
		Workbook rwb = null;
		FileInputStream fis = null;
		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		ExcelSTLWrite excelSTLWr = new ExcelSTLWrite();
		try {
			excelSTLWr.init(tempExcel, outExcel);
			WritableSheet sheet = excelSTLWr.getWorkSheet(intSheetIdx);
			//处理EXCEL头部的信息
			if(null != headMap){
				ExpExcelOperator.insertHeaderData(sheet, headMap);
			}
			//处理EXCEL模板底部信息
			if(null != footMap){
				ExpExcelOperator.insertFootData(sheet, footMap);
			}
			//处理EXCEL模板的本体信息BODY
			Map pramMap = new HashMap();	
			int rows = list.size();
			if(rows == 0){
			}else{
				pramMap = getParaMap(tempxml);
				//起始行位置
				int initRow = 0;
				//本体的总行数
				int bodyRowCount = 0;
				//本体的总列数
				int bodyColCount= 0;
				//各本体之间的间隔行数
				int marginRows = 0; 
				
				int pageSize = 5000;
				if(pramMap.size()>1){
					initRow=Integer.parseInt((String)pramMap.get("sourceRow"));
					bodyRowCount = Integer.parseInt((String)pramMap.get("bodyRows"));
					bodyColCount = Integer.parseInt((String)pramMap.get("bodyCols"));
					marginRows = Integer.parseInt((String)pramMap.get("margin"));
					//每页显示的记录数
					if(null != pramMap.get("pageSize")){
						pageSize = Integer.parseInt((String)pramMap.get("pageSize"));
					}
				}
				//计算需要些多少个sheet
				int sheets = (rows%pageSize==0)?(rows/pageSize):(rows/pageSize+1);
				int indexs[] = new int[sheets];
				indexs[0] = intSheetIdx;
				for (int i = 1; i < sheets; i++) {
					indexs[i] = excelSTLWr.getNumberOfSheets();
					ExpExcelOperator.copySheet(excelSTLWr,intSheetIdx,excelSTLWr.getNumberOfSheets(),i);						
				}
				//获取插入数据的起始行
				int writeLocRow = initRow;
				List cellStyleList = new ArrayList();
				//获取模板的样式
				for (int row = initRow; row < initRow + bodyRowCount; row++) {
					for (int col = 0; col < bodyColCount; col++) {
						cellStyleList.add(excelSTLWr.getLocalCell(sheet, col, row));
					}
				}
				Range rangs[] = sheet.getMergedCells();
				//开始写数据
				
				for (int j = 0; j < sheets; j++) {
					List tempList = null;
					if(list.size()<=pageSize*(j+1)){
						tempList = list.subList(pageSize*j, list.size());
					}else{
						tempList = list.subList(pageSize*j, pageSize*(j+1));
					}
					
					intSheetIdx = indexs[j];
					writeLocRow = initRow;
					
					for (int i = 1; i <= tempList.size(); i++) {
						Map map = (Map)tempList.get(i-1);
						map.put("STRNUM", ""+i);
						ExpExcelOperator.writeData(excelSTLWr,  cellStyleList , map, intSheetIdx, 
								writeLocRow, bodyRowCount, bodyColCount, bodyRowCount * (i-1),  initRow,marginRows*(i-1));
						
						writeLocRow = writeLocRow + bodyRowCount + marginRows;
						
						if (i+1 > tempList.size()) {
							break;
						} else {
							ExpExcelOperator.paintCellStyle(excelSTLWr, cellStyleList,rangs, intSheetIdx, 
									writeLocRow, bodyRowCount, i, initRow,marginRows);
						}
					}
				}
			}
			return outExcel;
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (BiffException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			excelSTLWr.dispose();
			try {
				if (null != rwb)
					rwb.close();
				if (null != fis)
					fis.close();
				if(null != bufferedReader)
					bufferedReader.close();
				if(null != reader)
					reader.close();
			} catch (IOException e1) {
				log.error(e1.getMessage(), e1);
			}
		}
		return null;
	}
	/**
	 * 可以输出body为多行的循环体
	 * @param tempExcel excel模板文件路径
	 * @param tempxml 对应xml文件的模板路径
	 * @param list 存放数据
	 * @param index 需要插入数据的sheet的下标
	 * @return
	 */
	public String getOutExcelBySheetIndex(String tempExcel,String tempxml, ResultSetOperator rso,Map headMap,Map bootMap,int index){
		
		if(null == tempExcel){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String tmpdir = createTempDir();
		
		String tempName = tempExcel.substring(tempExcel.lastIndexOf("/") + 1,tempExcel.lastIndexOf("."));
		if(tempName.length()>20){
			tempName = tempName.substring(0,20);
		}
		//临时文本文件名
		String tempfilename = tempName + ReportProcNumberNoGet.getCurrentTime();
		//临时的excel文件
		String outExcel = tmpdir + tempName
				+ ReportProcNumberNoGet.getCurrentTime() + ".xls";
		try {
			int i = this.writeRSOToXml(tmpdir, tempfilename, tempxml, rso, headMap, bootMap);
			//清空数据
			//当记录数为空的时候，创建一个空文件
			Map paraMap = new HashMap();
			if(i == 0){
				File file = new File(tmpdir + tempfilename);
				if (!file.exists()) {
					file.createNewFile();
				}
			}else{
				paraMap = getParaMap(tempxml);
			}
			paraMap.put("index", ""+index);
			
			this.writeXmlBodyToExcelByIndex(tmpdir + tempfilename, tempExcel,
					outExcel,i,paraMap);
			return outExcel;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file = new File(tmpdir + tempfilename);
			if (null != file) {
				file.delete();
			}
			rso.dispose();
		}
		
		return null;
	}
	/**
	 * 可以输出body为多行的循环体
	 * @param tempExcel excel模板文件路径
	 * @param tempxml 对应xml文件的模板路径
	 * @param list 存放数据
	 * @param index 需要插入数据的sheet的下标
	 * @return
	 */
	public String getOutExcelBySheetIndex(String tempExcel,String tempxml, ICommonBean cb,Map headMap,Map bootMap,int index){
		
		if(null == tempExcel){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String tmpdir = createTempDir();
		
		String tempName = tempExcel.substring(tempExcel.lastIndexOf("/") + 1,tempExcel.lastIndexOf("."));
		if(tempName.length()>20){
			tempName = tempName.substring(0,20);
		}
		//临时文本文件名
		String tempfilename = tempName + ReportProcNumberNoGet.getCurrentTime();
		//临时的excel文件
		String outExcel = tmpdir + tempName
				+ ReportProcNumberNoGet.getCurrentTime() + ".xls";
		try {
			int i = this.writeCBToXml(tmpdir, tempfilename, tempxml, cb, headMap, bootMap);
			//清空数据
			//当记录数为空的时候，创建一个空文件
			Map paraMap = new HashMap();
			if(i == 0){
				File file = new File(tmpdir + tempfilename);
				if (!file.exists()) {
					file.createNewFile();
				}
			}else{
				paraMap = getParaMap(tempxml);
			}
			paraMap.put("index", ""+index);
			
			this.writeXmlBodyToExcelByIndex(tmpdir + tempfilename, tempExcel,
					outExcel,i,paraMap);
			return outExcel;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file = new File(tmpdir + tempfilename);
			if (null != file) {
				file.delete();
			}
//			if(null != cb){
//			    cb.clear();
//			}
		}
		
		return null;
	}
	/**
	 * 将list中的数据按照模板文件写入临时文件中
	 * @param tmpdir
	 * @param tempfilename
	 * @param tempxml
	 * @param list
	 * @param headMap
	 * @param bootMap
	 * @return
	 * @throws Exception
	 */
	private int writeListToXml(String tmpdir, String tempfilename,
			String tempxml, List list, Map headMap, Map bootMap)
			throws Exception {
		WriteFile wf = new WriteFile();
		if (null != headMap) {
			String head = this.getHeadDataXmlString(tempxml, headMap) + "\n";
			wf.write(tmpdir, tempfilename, head, true, "UTF-8");
		}
		StringBuffer sb = new StringBuffer();
		int rows = list.size();
		for (int i = 0; i < rows; i++) {
			Map bodyMap = new HashMap();
			// 文件开头序号，用来判断插入数据的起始行
			bodyMap.put("STRNUM", "" + (i + 1));
			// 获取当前记录的放入map中
			Map dataMap = (Map) list.get(i);
			bodyMap.putAll(dataMap);
			try {
				// 获取xml格式数据字符串
				String body = this.getBodyDataXmlString(tempxml, bodyMap)
						+ "\n";
				sb.append(body);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			// 将字符串存放入sb中，当记录数为100的整数倍的时候，写入文件，重新创建一个StringBuffer对象
			if ((i % 100 == 0) && (i != 0)) {
				wf.write(tmpdir, tempfilename, sb.toString(), true, "UTF-8");
				sb = new StringBuffer();
			}
		}
		// 如果sb中有数据，则写入临时文件中
		if (sb.length() != 0) {
			wf.write(tmpdir, tempfilename, sb.toString(), true, "UTF-8");
		}
		if (null != bootMap) {
			String boot = this.getFootDataXmlString(tempxml, bootMap) + "\n";
			wf.write(tmpdir, tempfilename, boot, true, "UTF-8");
		}
		return rows;
	}
	
	private int writeRSOToXml(String tmpdir, String tempfilename,
			String tempxml, ResultSetOperator rso, Map headMap, Map bootMap)
			throws Exception {
		WriteFile wf = new WriteFile();
		
		if(null != headMap){
			String head = this.getHeadDataXmlString(tempxml, headMap) + "\n";
			wf.write(tmpdir, tempfilename, head, true, "UTF-8");
		}
		int i = 0;
		if(null != rso){
			List list = rso.getColumnNames();
			StringBuffer sb = new StringBuffer();
			i = 1;
			while(rso.hasNext()){
				Map bodyMap = new HashMap();
				//文件开头序号，用来判断插入数据的起始行
				bodyMap.put("STRNUM", "" + i);
				//获取当前记录的放入map中
				for (int j = 0; j < list.size(); j++) {
					String colName = (String) list.get(j);
					bodyMap.put(colName, rso.getObject(colName));
				}

				try {
					//获取xml格式数据字符串
					String body = this.getBodyDataXmlString(tempxml, bodyMap) + "\n";
					sb.append(body);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				//将字符串存放入sb中，当记录数为100的整数倍的时候，写入文件，重新创建一个StringBuffer对象
				if((i%100 == 0) && (i!= 0)){
					wf.write(tmpdir, tempfilename, sb.toString(), true, "UTF-8");
					sb = new StringBuffer();
				} 
				i++;
			}
			//如果sb中有数据，则写入临时文件中
			if(sb.length()!=0){
				wf.write(tmpdir, tempfilename, sb.toString(), true, "UTF-8");
			}
		}
		if(null != bootMap){
			String foot = this.getFootDataXmlString(tempxml, bootMap) + "\n";
			wf.write(tmpdir, tempfilename, foot, true, "UTF-8");
		}
		
		return i;
	}
	private int writeCBToXml(String tmpdir, String tempfilename,
			String tempxml, ICommonBean cb, Map headMap, Map bootMap)
			throws Exception {
		WriteFile wf = new WriteFile();
		
		if(null != headMap){
			String head = this.getHeadDataXmlString(tempxml, headMap) + "\n";
			wf.write(tmpdir, tempfilename, head, true, "UTF-8");
		}
		int i = 0;
		if(null != cb){
			 
		    Iterator it = cb.getColumnNames();
		    List  list = new ArrayList();
		    while(it.hasNext()){
			    String colName = (String) it.next();
			    list.add(colName);
			}
			StringBuffer sb = new StringBuffer();
			for(i = 0;i<cb.getRows();i++){
				Map bodyMap = new HashMap();
				//文件开头序号，用来判断插入数据的起始行
				bodyMap.put("STRNUM", "" + (i + 1));
				//获取当前记录的放入map中
				for(int j=0;j<list.size();j++){
					String colName = (String) list.get(j);
				    bodyMap.put(colName, cb.getString(i,colName));
				}
				try {
					//获取xml格式数据字符串
					String body = this.getBodyDataXmlString(tempxml, bodyMap) + "\n";
					sb.append(body);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				//将字符串存放入sb中，当记录数为100的整数倍的时候，写入文件，重新创建一个StringBuffer对象
				if((i%100 == 0) && (i!= 0)){
					wf.write(tmpdir, tempfilename, sb.toString(), true, "UTF-8");
					sb = new StringBuffer();
				} 
			}
			//如果sb中有数据，则写入临时文件中
			if(sb.length()!=0){
				wf.write(tmpdir, tempfilename, sb.toString(), true, "UTF-8");
			}
		}
		if(null != bootMap){
			String foot = this.getFootDataXmlString(tempxml, bootMap) + "\n";
			wf.write(tmpdir, tempfilename, foot, true, "UTF-8");
		}
		
		return i;
	}
	
	/**
	 * 获取excel模板参数map
	 * @param tempxml
	 * @return
	 */
	private Map getParaMap(String tempxml){
		ReportXMLReader xmlcr = new ReportXMLReader();
		ReadFile rf = new ReadFile();
		String xmlstr = rf.getFileContent(tempxml, "UTF-8");
		try {
			xmlcr.analysisXML(xmlstr);
		} catch (Exception e) {
		}
		Map map = xmlcr.getXmlLevelItemMap((String)xmlcr.getXMLLevelXPath(4),0);
		return map;
		
	}
	
	/**
	 * 返回生成的excel文件路径 keyi
	  * @param tempExcel excel模板文件路径
	 * @param tempxml 对应xml文件的模板路径
	 * @param rso ResultSetOperator对象封装rs
	 * @param headMap  没有头部的数据，使用 null
	 * @param bootMap 没有尾部的数据，使用 null
	 * @param count 每个sheet显示的记录条数
	 * @return
	 */
	public String getOutExcelWithMultiLine(String tempExcel,String tempxml,ResultSetOperator rso,Map headMap,Map bootMap,int count){
		if((null == tempExcel) || (null == rso)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String tmpdir = createTempDir();
		
		String tempName = tempExcel.substring(tempExcel.lastIndexOf("/") + 1,tempExcel.lastIndexOf("."));
		if(tempName.length()>20){
			tempName = tempName.substring(0,20);
		}
		//临时文本文件名
		String tempfilename = tempName + ReportProcNumberNoGet.getCurrentTime();
		//临时的excel文件
		String outExcel = tmpdir + tempName
				+ ReportProcNumberNoGet.getCurrentTime() + ".xls";
		try {
			int i = this.writeRSOToXml(tmpdir, tempfilename, tempxml, rso, headMap, bootMap);
			//清空数据
			//当记录数为空的时候，创建一个空文件
			Map paraMap = new HashMap();
			if(i == 0){
				File file = new File(tmpdir + tempfilename);
				if (!file.exists()) {
					file.createNewFile();
				}
			}else{
				paraMap = getParaMap(tempxml);
			}
			paraMap.put("count", ""+count);
			
			this.writeXmlBodyToExcel(tmpdir + tempfilename, tempExcel,
					outExcel,i,paraMap);
			return outExcel;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file = new File(tmpdir + tempfilename);
			if (null != file) {
				file.delete();
			}
			rso.dispose();
		}
		
		return null;
	}
	/**
	 * 返回生成的excel文件路径 keyi
	  * @param tempExcel excel模板文件路径
	 * @param tempxml 对应xml文件的模板路径
	 * @param rso ResultSetOperator对象封装rs
	 * @param headMap  没有头部的数据，使用 null
	 * @param bootMap 没有尾部的数据，使用 null
	 * @param count 每个sheet显示的记录条数
	 * @return
	 */
	public String getOutExcelWithMultiLine(String tempExcel,String tempxml,ICommonBean cb,Map headMap,Map bootMap,int count){
		if((null == tempExcel) || (null == cb)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String tmpdir = createTempDir();
		
		String tempName = tempExcel.substring(tempExcel.lastIndexOf("/") + 1,tempExcel.lastIndexOf("."));
		if(tempName.length()>20){
			tempName = tempName.substring(0,20);
		}
		//临时文本文件名
		String tempfilename = tempName + ReportProcNumberNoGet.getCurrentTime();
		//临时的excel文件
		String outExcel = tmpdir + tempName
				+ ReportProcNumberNoGet.getCurrentTime() + ".xls";
		try {
			int i = this.writeCBToXml(tmpdir, tempfilename, tempxml, cb, headMap, bootMap);
			//清空数据
			//当记录数为空的时候，创建一个空文件
			Map paraMap = new HashMap();
			if(i == 0){
				File file = new File(tmpdir + tempfilename);
				if (!file.exists()) {
					file.createNewFile();
				}
			}else{
				paraMap = getParaMap(tempxml);
			}
			paraMap.put("count", ""+count);
			
			this.writeXmlBodyToExcel(tmpdir + tempfilename, tempExcel,
					outExcel,i,paraMap);
			return outExcel;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file = new File(tmpdir + tempfilename);
			if (null != file) {
				file.delete();
			}
			if(cb != null){
			    cb.clear();
			}
		}
		return null;
	}
	
	/**
	 *	通过临时文件中取到的数据，写入excel中
	 * @param xmlfile 存放xml格式的excel数据临时文件
	 * @param excelTempFile excel模板文件
	 * @param outExcelFile excel输出文件
	 * @param rows xmlfile文件的行数
	 * @param EXCEL_SHEET_ROW_SIZE sheet每页显示的记录条数
	 */
	
	public void writeFileBodyToExcel(String xmlfile, String excelTempFile,String outExcelFile,int rows,int EXCEL_SHEET_ROW_SIZE) {
		
		File file = new File(xmlfile);
		Workbook rwb = null;
		FileInputStream fis = null;
		WritableWorkbook wwb = null;
		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		ReportXMLReader rxr = null;
		try {
			reader = new InputStreamReader(
					new FileInputStream(file),"UTF-8");
			bufferedReader = new BufferedReader(reader);
			//创建xml解析器
			rxr = new ReportXMLReader();
			WritableSheet sheet = null;
			//获取模板文件输入流
			fis = new FileInputStream(new File(excelTempFile));
			//通过输入流获取工作簿
			rwb = Workbook.getWorkbook(fis);
			//通过工作薄复制一个可写入的工作薄
			wwb = Workbook.createWorkbook(new File(outExcelFile), rwb);
			sheet = wwb.getSheet(0);
			//判断工作表的个数
			int sheets = (rows % EXCEL_SHEET_ROW_SIZE == 0) ? (rows / EXCEL_SHEET_ROW_SIZE)
					: (rows / EXCEL_SHEET_ROW_SIZE + 1);
			//删除除模板外其他的sheet
			while(wwb.getNumberOfSheets()>1){
				wwb.removeSheet(1);
			}
			//复制工作表
			for(int i=1;i<sheets;i++){
				wwb.copySheet(0,sheet.getName() + i,i);
			}
			String line = null;
			int count = 0;
			//获取插入数据的起始行
			int temp = getRowNBR(sheet);
			while ((line = bufferedReader.readLine()) != null) {
				//如果行为空，就不解析
				if("".equals(line.trim()))
					continue;
				
				try {
					rxr.analysisXML(line);
					//如果flag值为1：表示头部2：表示中部 3：表示尾部
					if("1".equals(rxr.getXmlDomElementLeafNodeValue("root","flag",0))){
						Map map = rxr.getXmlLevelItemAttrMap("root/head");
						//头部都一样
						for(int i=0;i<wwb.getNumberOfSheets();i++){
							ExpExcelOperator.insertHeaderData(wwb.getSheet(i), map);
						}
					}else if("3".equals(rxr.getXmlDomElementLeafNodeValue("root","flag",0))){
						Map map = rxr.getXmlLevelItemAttrMap("root/foot");
						//尾部数据一样
						for(int i=0;i<wwb.getNumberOfSheets();i++){
							ExpExcelOperator.insertFootData(wwb.getSheet(i), map);
						}
					}else{
						Map map = rxr.getXmlLevelItemAttrMap("root/body");
						ExpExcelOperator.copyRow(sheet, temp, temp + 1);
						ExpExcelOperator.insertRowData(sheet, map, temp);
						temp++;
						count++;
					}
					//sheet满足一定的行数，换sheet写入
					if(count != 0 && (count % EXCEL_SHEET_ROW_SIZE == 0)){
						sheet.removeRow(temp);
						sheet = wwb.getSheet(count/EXCEL_SHEET_ROW_SIZE);
						temp = getRowNBR(sheet);
					}
				} catch (Exception e1) {
					log.error(e1.getMessage(), e1);
				}
			}
			//删除最后复制的模板行
			sheet.removeRow(temp);
			wwb.write();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (BiffException e) {
		} finally {
			rxr.dispose();
			try {
				if (null != wwb)
					wwb.close();
				if (null != rwb)
					rwb.close();
				if (null != fis)
					fis.close();
				if(null != bufferedReader)
					bufferedReader.close();
				if(null != reader)
					reader.close();
			} catch (WriteException e1) {
			} catch (IOException e1) {
			}
		}
	}
	
	
	/**
	 * 返回需要插入中间数据的行号 
	 * @param st 需要查找的sheet
	 * @return
	 */
	private int getRowNBR(WritableSheet wtsh) {
		int num = -1;
		for (int i = 0; i < wtsh.getRows(); i++) {
			Cell[] cell = wtsh.getRow(i);
			for (int j = 0; j < cell.length; j++) {
				String cont = cell[j].getContents().toString();
				cont = cont.replaceAll(" ", "");
				if (cont.equals("%STRNUM%")) {
					num = i;
					return num;
				}
			}
		}
		return num;
	}
	
	/**
	 * 返回生成的excel文件路径
	  * @param tempExcel excel模板文件路径
	 * @param tempxml 对应xml文件的模板路径
	 * @param rso ResultSetOperator对象封装rs
	 * @param headMap  没有头部的数据，使用 null
	 * @param bootMap 没有尾部的数据，使用 null
	 * @param count 每个sheet显示的记录条数
	 * @return
	 */
	public String getOutExcelFile(String tempExcel,String tempxml,ResultSetOperator rso,Map headMap,Map bootMap,int count){
	
		
		if((null == tempExcel) || (null == rso)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		//获取临时文件目录
		String tmpdir = createTempDir();
		
		String tempName = tempExcel.substring(tempExcel.lastIndexOf("/") + 1,tempExcel.lastIndexOf("."));
		if(tempName.length()>20){
			tempName = tempName.substring(0,20);
		}
		//临时文本文件名
		String tempfilename = tempName + ReportProcNumberNoGet.getCurrentTime();
		//临时的excel文件
		String outExcel = tmpdir + tempName
				+ ReportProcNumberNoGet.getCurrentTime() + ".xls";
		try {
			int i = this.writeRSOToXml(tmpdir, tempfilename, tempxml, rso, headMap, bootMap);
			//清空数据
			//当记录数为空的时候，创建一个空文件
			if(i == 0){
				File file = new File(tmpdir + tempfilename);
				if (!file.exists()) {
					file.createNewFile();
				}
			}
			this.writeFileBodyToExcel(tmpdir + tempfilename, tempExcel,
					outExcel,i,count);
			return outExcel;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file = new File(tmpdir + tempfilename);
			if (null != file) {
				file.delete();
			}
			rso.dispose();
		}
		return null;
	}
	/**
	 * 
	 * @param tempExcel excel模板文件路径
	 * @param tempxml 对应xml文件的模板路径
	 * @param list 存放数据
	 * @param count 每个sheet显示记录条数
	 * @return
	 */
	public String getOutExcelFile(String tempExcel,String tempxml, List list,Map headMap,Map bootMap,int count){
		
		if((null == tempExcel) || (null == list)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		//获取临时文件目录
		String tmpdir = createTempDir();
		String tempName = tempExcel.substring(tempExcel.lastIndexOf("/") + 1,tempExcel.lastIndexOf("."));
		if(tempName.length()>20){
			tempName = tempName.substring(0,20);
		}
		//临时文本文件名
		String tempfilename = tempName
				+ ReportProcNumberNoGet.getCurrentTime();
		//临时的excel文件
		String outExcel = tmpdir + tempName
				+ ReportProcNumberNoGet.getCurrentTime() + ".xls";
		try {
			int rows = this.writeListToXml(tmpdir, tempfilename, tempxml, list, headMap, bootMap);
			//当记录数为空的时候，创建一个空文件
			if(rows == 0){
				File file = new File(tmpdir + tempfilename);
				if (!file.exists()) {
					file.createNewFile();
				}
			}
			
			this.writeFileBodyToExcel(tmpdir + tempfilename, tempExcel,
					outExcel,rows,count);
			
			return outExcel;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file = new File(tmpdir + tempfilename);
			if (null != file) {
				file.delete();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param tempExcel excel模板文件路径
	 * @param tempXml xml模板文件路径
	 * @param list 符合条件的数据集合
	 * @param count 每个sheet显示记录条数
	 * @return
	 */
	public byte[] getOutExcelBytes(String tempExcel,String tempXml,List list,int count) {
		
		if((null == tempExcel) || (null == list)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String outExcel = this.getOutExcelFile(tempExcel,tempXml,list,null,null,count);
		try{
			if(null != outExcel){
				byte[] bytes = getOutExcelByteCon(outExcel);
				
				return bytes;
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file1 = new File(outExcel);
			if (null != file1) {
				file1.delete();
			}
		}
		return null;
	}
	
	/**
	 * 获取excel文件字节流
	 * @param tempExcel excel模板文件路径
	 * @param tempXml xml模板文件路径
	 * @param rs 符合条件的数据集合
	 * @param count 每个sheet显示记录条数
	 * @return
	 */
	public byte[] getOutExcelBytes(String tempExcel,String tempXml,ResultSetOperator rso,int count) {
		if((null == tempExcel) || (null == rso)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String outExcel = this.getOutExcelFile(tempExcel,tempXml,rso,null,null,count);
		try{
			if(null != outExcel){
				byte[] bytes = getOutExcelByteCon(outExcel);
				return bytes;
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file1 = new File(outExcel);
			if (null != file1) {
				file1.delete();
			}
		}
		return null;
	}
	/**
	 * 
	 * @param tempExcel excel模板文件路径
	 * @param tempXml xml模板文件路径
	 * @param rs 符合条件的数据集合
	 * @param count 每个sheet显示记录条数
	 * @return
	 */
	public byte[] getOutExcelBytes(String tempExcel,String tempXml,ResultSetOperator rso,Map headMap,Map bootMap,int count) {
		
		if((null == tempExcel) || (null == rso)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String outExcel = this.getOutExcelFile(tempExcel,tempXml,rso,headMap,bootMap,count);
		try{
			if(null != outExcel){
				byte[] bytes = getOutExcelByteCon(outExcel);
				
				return bytes;
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file1 = new File(outExcel);
			if (null != file1) {
				file1.delete();
			}
		}
		return null;
	}
	/**
	 * 
	 * @param tempExcel excel模板文件路径
	 * @param tempXml xml模板文件路径
	 * @param rs 符合条件的数据集合
	 * @param count 每个sheet显示记录条数
	 * @return
	 */
	public byte[] getOutExcelBytesWithMultiLine(String tempExcel,String tempXml,ResultSetOperator rso,Map headMap,Map bootMap,int count) {
		
		if((null == tempExcel) || (null == rso)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String outExcel = this.getOutExcelWithMultiLine(tempExcel,tempXml,rso,headMap,bootMap,count);
		try{
			if(null != outExcel){
				byte[] bytes = getOutExcelByteCon(outExcel);
				
				return bytes;
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file1 = new File(outExcel);
			if (null != file1) {
				file1.delete();
			}
		}
		return null;
	}
	/**
	 * 
	 * @param tempExcel excel模板文件路径
	 * @param tempXml xml模板文件路径
	 * @param cb CommonBean对象
	 * @param count 每个sheet显示记录条数
	 * @return
	 */
	public byte[] getOutExcelBytesWithMultiLine(String tempExcel,String tempXml,ICommonBean cb,Map headMap,Map bootMap,int count) {
		
		if((null == tempExcel) || (null == cb)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String outExcel = this.getOutExcelWithMultiLine(tempExcel,tempXml,cb,headMap,bootMap,count);
		try{
			if(null != outExcel){
				byte[] bytes = getOutExcelByteCon(outExcel);
				
				return bytes;
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file1 = new File(outExcel);
			if (null != file1) {
				file1.delete();
			}
		}
		return null;
	}
	/**
	 * 向一个excel中的多个sheet输入数据
	 * @param tempExcel excel模板文件路径
	 * @param list 每个sheet显示记录条数List{Map key：[head,body,boot,tempxml,index] value[Map,object,Map,String,int]}
	 * 				body如果为commonbean的时候，使用完了，需要调用clear方法
	 * @return excel文件字节流
	 */
	public byte[] getOutExcelBytesWithSheets(String tempExcel,List list) {
		
		if((null == tempExcel) || (null == list)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String outExcel = getOutExcelWithSheets(tempExcel,list);
		try{
			if(null != outExcel){
				byte[] bytes = getOutExcelByteCon(outExcel);
				
				return bytes;
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if(null != outExcel){
				File file1 = new File(outExcel);
				if (null != file1) {
					file1.delete();
				}
			}
		}
		return null;
	}
	
	/**
	 * 向一个excel中的多个sheet输入数据
	 * @param tempExcel excel模板文件路径
	 * @param list 每个sheet显示记录条数List{Map key：[head,body,boot,tempxml,index] value[Map,object,Map,String,int]}
	 * 				body如果为commonbean的时候，使用完了，需要调用clear方法
	 * @return excel绝对路径
	 */
	public String getOutExcelWithSheets(String tempExcel,List list) {
	    if((null == tempExcel) || (null == list)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		int size = list.size();
		String[] tmpExcel = new String[size];
		String outExcel = tempExcel;
		for (int i = 0; i < list.size(); i++) {
			if (null != list.get(i)) {
				if (list.get(i) instanceof Map) {
					Map map = (Map) list.get(i);
					
					if (null == map.get("tempxml") || isNull(map)) {
						return null;
					} else {
						String tempXml = (String)map.get("tempxml");
						String index = (String)map.get("index");
						int shindex = Integer.parseInt(index);
						Object obj = map.get("body");
						if (null == obj) {
							outExcel = this.getOutExcelByWithOutXml(outExcel,
									tempXml, new ArrayList(), getMapValue(map,
											"head"), getMapValue(map, "foot"),
											shindex);
							tmpExcel[i] = outExcel;
							
						}else{
							if(obj instanceof List){
								outExcel = this.getOutExcelByWithOutXml(outExcel,
										tempXml, (List)obj, getMapValue(map,
												"head"), getMapValue(map, "foot"),
												shindex);
								tmpExcel[i] = outExcel;
							}
							if(obj instanceof ResultSetOperator){
								outExcel = this.getOutExcelBySheetIndex(outExcel,
										tempXml, (ResultSetOperator)obj, getMapValue(map,
										"head"), getMapValue(map, "foot"),
										shindex);
								tmpExcel[i] = outExcel;
							}
							if(obj instanceof ICommonBean){
								outExcel = this.getOutExcelBySheetIndex(outExcel,
										tempXml, (ICommonBean)obj, getMapValue(map,
										"head"), getMapValue(map, "foot"),
										shindex);
								tmpExcel[i] = outExcel;
							}
							
						}
					}

				} else {
					return null;
				}

			}
		}
		for (int j = 0; j < tmpExcel.length-1; j++) {
			if(null != tmpExcel[j]){
				File file1 = new File(tmpExcel[j]);
				if (null != file1) {
					file1.delete();
				}
			}
		}
	    return outExcel;
	}
	
	private Map getMapValue(Map map,String key){
		if(null == map.get(key)){
			return null;
		}else{
			if(map.get(key) instanceof Map){
				return (Map)map.get(key);
			}else{
				return null;
			}
		}
	}
	
	private boolean isNull(Map map) {
		if (null == map.get("head") && null == map.get("body")
				&& null == map.get("foot")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 用来添加多个图片 png类型
	 * @param tempExcel
	 * @param tempXml
	 * @param rso
	 * @param list 放入Map 用来添加多个图片
	 * 	Map中key：COLUMN：图片在excel中左上角所在列下标
	 *	Map中key	：ROW：图片在excel中左上角所在行下标
	 * 	Map中key	：IMAGE：图片的路径
	 * @return
	 */
	
	public byte[] getOutExcelWithImgBytes(String tempExcel,String tempXml,ResultSetOperator rso,Map headMap,Map footMap,List list) {
		
		if((null == tempExcel) || (null == rso)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String outExcel = this.getOutExcelFile(tempExcel,tempXml,rso,headMap,footMap,60000);
		if(null != list && list.size() > 0){
			expImageToExcel(list,outExcel);
		}
		try{
			if(null != outExcel){
				byte[] bytes = getOutExcelByteCon(outExcel);
				
				return bytes;
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file1 = new File(outExcel);
			if (null != file1) {
				file1.delete();
			}
		}
		return null;
	}

	/**
	 * 用来添加单个图片 png类型
	 * @param tempExcel
	 * @param tempXml
	 * @param rso
	 * @param map
	 *  map中key：COLUMN：图片在excel中左上角所在列下标 类型 String eg： map.put("COLUMN","5");
	 *	map中key	：ROW：图片在excel中左上角所在行下标 类型 String eg：map.put("ROW","5");
	 * 	map中key	：IMAGE：图片的路径 eg:map.put("IMAGE","e:/aaa.png");
	 * @return
	 */
	public byte[] getOutExcelWithImgBytes(String tempExcel,String tempXml,ResultSetOperator rso,Map headMap,Map footMap,Map map) {
		List list = new ArrayList();
		if(null != map && null != map.get("IMAGE")){
			list.add(map);
			return getOutExcelWithImgBytes(tempExcel,tempXml,rso,headMap,footMap,list);
		}
		return null;
	}
	
	public byte[] getOutExcelBytes(String tempExcel,String tempXml,List list,Map headMap,Map footMap,int count) {
		
		if((null == tempExcel) || (null == list)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String outExcel = this.getOutExcelFile(tempExcel,tempXml,list,headMap,footMap,count);
		try{
			if(null != outExcel){
				byte[] bytes = getOutExcelByteCon(outExcel);
				return bytes;
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file1 = new File(outExcel);
			if (null != file1) {
				file1.delete();
			}
		}
		return null;
	}
	/**
	 * 输出循环体中有多行的excel 字节数组
	 * @param tempExcel
	 * @param tempXml
	 * @param list
	 * @param headMap
	 * @param footMap
	 * @param count 每个sheet循环的记录
	 * @return
	 */
	public byte[] getOutExcelBytesWithMultiLine(String tempExcel,String tempXml,List list,Map headMap,Map footMap,int count) {
		
		if((null == tempExcel) || (null == list)){
			return null;
		}
		if("".equals(tempExcel)){
			return null;
		}
		String outExcel = this.getOutExcelWithMultiLine(tempExcel,tempXml,list,headMap,footMap,count);
		try{
			if(null != outExcel){
				byte[] bytes = getOutExcelByteCon(outExcel);
				return bytes;
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file1 = new File(outExcel);
			if (null != file1) {
				file1.delete();
			}
		}
		return null;
	}
	
	
	public byte[] getExcelBytesByDataXml(String xmlfile, String tempExcel,String tempxml,int index){
		if(null == xmlfile || null==tempExcel||null == tempxml){
			return null ;
		}		
		String outExcel = this.getExcelByDataXml(xmlfile, tempExcel, tempxml, index);
		try{
			if(null != outExcel){
				byte[] bytes = getOutExcelByteCon(outExcel);
				return bytes;
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			File file1 = new File(outExcel);
			if (null != file1) {
				file1.delete();
			}
		}
		return null;
	}
	
	/**
	 * 获取excel数据的二进制数组
	 * 
	 * @return
	 */
	private byte[] getOutExcelByteCon(String excelFile) {
		byte[] attachBytes = null;
		File file = new File(excelFile);
		try {
			FileInputStream inputStream = new FileInputStream(file);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			int cb = 0;
			while ((cb = inputStream.read()) != -1) {
				outStream.write(cb);
			}
			inputStream.close();
			attachBytes = outStream.toByteArray();
			outStream.close();
			return attachBytes;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 
	 * @param @param list 放入Map 用来添加多个图片
	 * 	Map中key：COLUMN：图片在excel中左上角所在列下标
	 *	Map中key	：ROW：图片在excel中左上角所在行下标
	 * 	Map中key	：IMAGE：图片的路径
	 * @param outExcel 输出excel的路径
	 */
	private void expImageToExcel(List list,String outExcel){
		
		if(null != list && list.size()>0){
			File excelfile = new File(outExcel);
			WritableWorkbook wwb = null;
			Workbook wb = null;
			try {
				wb = Workbook.getWorkbook(excelfile);
	            //打开一个文件的副本，并且指定数据写回到原文件
				wwb = Workbook.createWorkbook(excelfile, wb);
				WritableSheet ws = wwb.getSheet(0);
				for (int i = 0; i < list.size(); i++) {
					Map map = (Map)list.get(i);
					if(null == list.get(i)||null == map.get("IMAGE")){
						return;
					}
					int x = 5;
					int y = 5;
					if(null == map.get("COLUMN")){
					}else{
						x = Integer.parseInt((String)map.get("COLUMN"));
					}
					if(null == map.get("ROW")){
					}else{
						y = Integer.parseInt((String)map.get("ROW"));
					}
					String img_path = (String)map.get("IMAGE");
					File _file = new File(img_path); //读入文件 
					if(!_file.exists()){
						return;
					}
					BufferedImage src = javax.imageio.ImageIO.read(_file); //构造Image对象 
					int width = src.getWidth(null); //得到源图宽 
					int height = src.getHeight(null); //得到源图长 
					int xx = x;
					int yy = y;
					int w = 0;
					int h = 0;
					double ww = 0.0;
					double hh = 0;
					while(w < width){
						w += ws.getColumnView(xx).getSize()/256*8.5;
						xx++;
					}
					if(w>width){
						ww = (xx - x) - (double)(Math.round((w-width)/(ws.getColumnView(xx-1).getSize())*100)/100.0);
					}else{
						ww = xx - x;
					}
					while(h < height){
						h += ws.getRowView(yy).getSize()/20*1.4;
						yy++;
					}
					if(h > height){
						hh = (yy - y) - (double)Math.round(((h-height)/(ws.getRowView(yy-1).getSize())*100/100.0));
					}else{
						hh = yy - y;
					}
//					WritableImage wi = new WritableImage(x,y,Math.round(width/72)*100/100.0,Math.round(height/19)*100/100.0,_file);
					WritableImage wi = new WritableImage(x,y,ww,hh,_file);
					ws.addImage(wi);
				}
				wwb.write();
			} catch (IOException e) {
			} catch (BiffException e) {
			}finally {
				try {
					if(null != wb)
						wb.close();
					if (null != wwb)
						wwb.close();
				} catch (WriteException e1) {
				} catch (IOException e1) {
				}
				
				
			}
		}
	}

}

