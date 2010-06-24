/*
 * 创建日期： 2010-2-24
 *
 */
package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cthq.crm.webservice.common.xml.imp.XMLBaseParse;
import com.cthq.crm.webservice.util.ReadFile;
import com.cthq.crm.webservice.xml.template.engine.XmlTemplateContainer;

/**
 * @author 吉仕军
 *	
 */
public class TestXmlParse extends XMLBaseParse{

	//XML层次结构列表
	private static final List xmlLevelList = new ArrayList(3);
	static {
		xmlLevelList.add(0, "root/BaseInfo");
		xmlLevelList.add(1, "root/ProductInfo/BussinessInfo");
		xmlLevelList.add(2, "root/ProductInfo/BussinessInfo/BusiList");
	}

	/* (non-Javadoc)
	 * @see com.cthq.crm.webservice.common.xml.interfaces.IXMLParser#getXMLLevelList()
	 */
	public List getXMLLevelList() {
		return xmlLevelList;
	}

	/* (non-Javadoc)
	 * @see com.cthq.crm.webservice.common.xml.interfaces.IXMLParser#getXMLLevelXPath(int)
	 */
	public String getXMLLevelXPath(int loc) {
		if (loc >= xmlLevelList.size()) {
			return "";
		}
		return (String)xmlLevelList.get(loc);
	}

	/* (non-Javadoc)
	 * @see com.cthq.crm.webservice.common.xml.interfaces.IXMLParser#chkXMLValidate()
	 */
	public void chkXMLValidate() {

	}

	/* (non-Javadoc)
	 * @see com.cthq.crm.webservice.common.xml.interfaces.IXMLParser#writeXMLToFile(java.util.Map)
	 */
	public void writeXMLToFile(Map _paramMap) {

	}
	
	
	public static void main(String[] args) {
		TestXmlParse txp = new TestXmlParse();
		
		//模板文件路径
		String bentXmlTemp = "E:/myeclipseworkspace/Account/JavaSource/test/testemp.xml";
		
		XmlTemplateContainer xmlTempContainer = new XmlTemplateContainer();
		
		xmlTempContainer.setXMLLevelList(xmlLevelList);
		try {
			//设置XML模板文件解析器
			xmlTempContainer.setXMLReader(bentXmlTemp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Map rootMap = new HashMap();
		rootMap.put("BizID","BizID");
        rootMap.put("ProductID","ProductID");
        rootMap.put("BizCustAccount","BizCustAccount");
  
        xmlTempContainer.setXMLLevelData(rootMap, 0, 0);
        
        Map busiMap = new HashMap();
        busiMap.put("PhoneNum","111111111111");
        xmlTempContainer.setXMLLevelData(busiMap, 2, 0);
        busiMap = new HashMap();
        busiMap.put("PhoneNum","22222222222");
        xmlTempContainer.setXMLLevelData(busiMap, 2, 0);
        
        txp.createXMLLevelData(xmlTempContainer);
		//XML字符串
		String returnXML = txp.getXMLContent();
		
		System.out.println(returnXML);
	}
}
