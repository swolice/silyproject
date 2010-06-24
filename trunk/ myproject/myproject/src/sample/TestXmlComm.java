/*
 * 创建日期： 2010-2-25
 *
 */
package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cthq.crm.webservice.common.xml.imp.XMLBaseReader;
import com.cthq.crm.webservice.common.xml.imp.XMLCommonParse;
import com.cthq.crm.webservice.common.xml.imp.XMLCommonReader;
import com.cthq.crm.webservice.common.xml.imp.XMLElement;
import com.cthq.crm.webservice.common.xml.imp.XMLNode;
import com.cthq.crm.webservice.common.xml.imp.XMLNodeCollection;
import com.cthq.crm.webservice.common.xml.imp.XMLNodeCollection.Entry;
import com.cthq.crm.webservice.util.ReadFile;
import com.cthq.crm.webservice.xml.template.engine.XmlTemplateContainer;

/**
 * @author 吉仕军
 * 
 */
public class TestXmlComm {

	public static void main(String[] args) {
//		commonReader();
		// commonParse();
		testchkXmlFrame();
	}

	public static void commonReader() {
		XMLCommonReader xcr = new XMLCommonReader();
		// 设置层次结构
		List xmlLevelList = new ArrayList(3);

		xmlLevelList.add(0, "root/BaseInfo");
		xmlLevelList.add(1, "root/ProductInfo/BussinessInfo");
		xmlLevelList.add(2, "root/ProductInfo/BussinessInfo/BusiList");

		xcr.setXMLLevelList(xmlLevelList);

		ReadFile rf = new ReadFile();
		String xmlstr = rf.getFileContent(
				"E:/myeclipseworkspace/Account/JavaSource/test/xmltest11.xml",
				"UTF-8");
		try {
			xcr.analysisXML(xmlstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String bizid = xcr.getXmlDomElementLeafNodeValue((String) xmlLevelList
				.get(0), "BizID", 0);
		System.out.println(bizid);
//		List rootList = getXmlLevelItemMap(xcr, (String) xmlLevelList.get(0));
//		List baseList = getXmlLevelItemMap(xcr, (String) xmlLevelList.get(1));
		List list = getXmlLevelItemList(xcr, (String) xmlLevelList.get(2));
	}

	public static void commonParse() {

		XMLCommonParse xcp = new XMLCommonParse();
		// 模板文件路径
		String bentXmlTemp = "E:/myeclipseworkspace/Account/JavaSource/test/testemp.xml";

		XmlTemplateContainer xmlTempContainer = new XmlTemplateContainer();

		List xmlLevelList = new ArrayList(3);
		xmlLevelList.add(0, "root/BaseInfo");
		xmlLevelList.add(1, "root/ProductInfo/BussinessInfo");
		xmlLevelList.add(2, "root/ProductInfo/BussinessInfo/BusiList");
		// 设置生成器的xml层次结构
		xcp.setXMLLevelList(xmlLevelList);

		// 设置容器的层次结构
		xmlTempContainer.setXMLLevelList(xmlLevelList);

		try {
			// 设置XML模板文件解析器
			xmlTempContainer.setXMLReader(bentXmlTemp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Map rootMap = new HashMap();
		rootMap.put("BizID", "BizID");
		rootMap.put("ProductID", "ProductID");
		rootMap.put("BizCustAccount", "BizCustAccount");

		xmlTempContainer.setXMLLevelData(rootMap, 0, 0);

		Map busiMap = new HashMap();
		busiMap.put("PhoneNum", "111111111111");
		xmlTempContainer.setXMLLevelData(busiMap, 2, 0);
		busiMap = new HashMap();
		busiMap.put("PhoneNum", "22222222222");
		xmlTempContainer.setXMLLevelData(busiMap, 2, 0);

		xcp.createXMLLevelData(xmlTempContainer);
		// XML字符串
		String returnXML = xcp.getXMLContent();

		System.out.println(returnXML);
	}

	/**
	 * 获取非重复的层次路径下叶子节点
	 * 
	 * @param xmlBaseReader
	 *            xml读取类
	 * @param xmlLevelItemXPath
	 *            xml层次路径名称
	 * @return
	 */
//	private static List<Map<String, String>> getXmlLevelItemMap(
//			XMLBaseReader xmlBaseReader, String xmlLevelItemXPath) {
//
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//
//		Iterator<XMLElement> iter = xmlBaseReader.xmlDomElementIterator(xmlLevelItemXPath);
//		if (null == iter) {
//			// 错误处理
//			// return
//		} else {
//			// 遍历叶子节点
//			while (iter.hasNext()) {
//				Map<String, String> leafNodeData = new HashMap<String, String>();
//				XMLElement xmlElement = iter.next();
//				XMLNodeCollection nodeCollection = xmlElement
//						.getElementLeafNodeColl();
//				// 获取Root路径下的叶子节点
//				for (int i = 0; i < nodeCollection.getNodesSize(); i++) {
//					XMLNode node = nodeCollection.getNodesAttributeMap(i);
//					leafNodeData.put(node.getName(), node.getValue().trim());
//				}
//				list.add(leafNodeData);
//			}
//		}
//		return list;
//	}

	/**
	 * 获取重复数据
	 * 
	 * @param xmlBaseReader
	 *            读写xml类
	 * @param xpath
	 *            层次结构路径名称
	 * @return
	 */
	private static List getXmlLevelItemList(XMLBaseReader xmlBaseReader,
			String xmlLevelItemXPath) {
		List onlyattrlist = new ArrayList();
		int count = xmlBaseReader.xmlDomElementCount(xmlLevelItemXPath);
		for (int i = 0; i < count; i++) {
			Iterator it = xmlBaseReader.xmlDomElementLeafIterator(
					xmlLevelItemXPath, i);
			List elementList = new ArrayList();
			if (null != it) {
				while (it.hasNext()) {
					Entry entry = (Entry) it.next();
					XMLNode xn = entry.xmlNode;
					Map map = new HashMap();
					map.put(xn.getName(), xn.getValue());
					System.out.println(xn.getName() + " : "
							+ xn.getValue().trim());
					elementList.add(map);
				}
			}
			onlyattrlist.add(elementList);
		}
		return onlyattrlist;
	}
	
	public static void testchkXmlFrame(){
		XMLCommonReader xcr = new XMLCommonReader();
		XMLCommonReader xcr_source = new XMLCommonReader();
		// 设置层次结构
//		List<String> xmlLevelList = new ArrayList<String>(2);
//
//		xmlLevelList.add(0, "ContractRoot/TcpCont");
//		xmlLevelList.add(1, "ContractRoot/SvcCont/BusEvent/CustomerInfo");
//
//		xcr.setXMLLevelList(xmlLevelList);
//		xcr_source.setXMLLevelList(xmlLevelList);

		ReadFile rf = new ReadFile();
		String xmlstr = rf.getFileContent("d:/custinfo_chk_receive.xml","UTF-8");
		String xmlstr_source = rf.getFileContent("d:/aaaaa.xml","UTF-8");
		try {
			xcr.analysisXML(xmlstr);
			xcr_source.analysisXML(xmlstr_source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		chkXmlFrame(xmlLevelList,xcr,xcr_source);
	}
	
//	@SuppressWarnings("unchecked")
//	public static void chkXmlFrame(List<String> xmlLevelList,
//			XMLCommonReader chkReader, XMLCommonReader resReader) {
//		for (int i = 0; i < xmlLevelList.size(); i++) {
//			Iterator<Entry> it = chkReader.xmlDomElementLeafIterator(
//					xmlLevelList.get(i), 0);
//			if (null != it) {
//				while (it.hasNext()) {
//					Entry entry = it.next();
//					XMLNode xn = entry.xmlNode;
//					String elementName = xn.getName();
//					String elementValue = resReader
//							.getXmlDomElementLeafNodeValue(xmlLevelList.get(i),
//									elementName, 0);
//					if (null == elementValue) {
//						System.out.println("【" + elementName + "】节点不存在");
//					}
//				}
//			}
//		}
//	}
}
