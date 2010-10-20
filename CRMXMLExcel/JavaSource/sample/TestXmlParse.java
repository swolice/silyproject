/*
 * 创建日期： 2010-2-24
 *
 */
package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cthq.crm.project.common.xml.imp.XMLBaseParse;
import com.cthq.crm.project.common.xml.template.engine.XmlTemplateContainer;

/**
 * @author 吉仕军
 *	
 */
public class TestXmlParse extends XMLBaseParse{

	//XML层次结构列表
	private static final List xmlLevelList = new ArrayList(2);
	static {
		xmlLevelList.add(0, "root/test1");
		xmlLevelList.add(1, "root/test2");
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
		
		
		XmlTemplateContainer xmlTempContainer = new XmlTemplateContainer();
		
		xmlTempContainer.setXMLLevelList(xmlLevelList);
		Map rootMap = new HashMap();
		rootMap.put("BizID","{BizID}");
        rootMap.put("ProductID","{ProductID}");
        rootMap.put("BizCustAccount","BizCustAccount");
  
        xmlTempContainer.setXMLNodeData(rootMap, 0, 0);
      
        HashMap  busiMap = new HashMap();
        busiMap.put("PhoneNum","{开始测试}");
        xmlTempContainer.setXMLNodeData(busiMap, 1, 0);
        
        txp.createXMLLevelData(xmlTempContainer);
		//XML字符串
		String returnXML = txp.getXMLContent();
		
		System.out.println(returnXML);
	}
}
