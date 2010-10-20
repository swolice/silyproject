/*
 * 创建日期： 2010-2-24
 *
 */
package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cthq.crm.project.common.util.ReadFile;
import com.cthq.crm.project.common.xml.imp.XMLBaseReader;
import com.cthq.crm.project.common.xml.imp.XMLElement;
import com.cthq.crm.project.common.xml.imp.XMLNode;
import com.cthq.crm.project.common.xml.imp.XMLNodeCollection;
import com.cthq.crm.project.common.xml.imp.XMLNodeCollection.Entry;

/**
 * @author 吉仕军
 *	
 */
public class TestXmlReader extends XMLBaseReader {

	private static final List xmlLevelList = new ArrayList(2);
	
	static{
		xmlLevelList.add(0, "root/excelprop");
		xmlLevelList.add(1, "root/body");
	}
	
	public List getXMLLevelList() {
		
		return xmlLevelList;
	}

	public String getXMLLevelXPath(int loc) {
		if (loc >= xmlLevelList.size()) {
			return "";
		}
		return (String) xmlLevelList.get(loc);
	}
	
	/**
	 * 指定项目元素的定位数据loc 获取该层次结构不重复的数据
	 * 
	 * @return 指定项目下叶子节点元素集合 如:<CustOrder><EventType>3 </EventType>
	 *         </CustOrder> xmlNode.nodeName 元素名称(EventType);xmlNode.nodeValue
	 *         值(3);
	 */
	private Map getXmlLevelItemMap(String xmlLevelItemXPath) {
		Map leafNodeData = new HashMap();
		Iterator iter = super.xmlDomElementIterator(xmlLevelItemXPath);
		if (null == iter) {
			//错误处理
			//return
		} else {
			//遍历叶子节点
			
			while(iter.hasNext()) {
				XMLElement xmlElement = (XMLElement) iter.next();
				XMLNodeCollection nodeCollection = xmlElement
						.getElementLeafNodeColl();
				//获取Root路径下的叶子节点
				for (int i = 0; i < nodeCollection.getNodesSize(); i++) {
					XMLNode node = nodeCollection.getNodesAttributeMap(i);
					System.out.println(node.getName() +" : "+node.getValue().trim());
					leafNodeData.put(node.getName(), node.getValue().trim());
				}
			
			}
		}
		return leafNodeData;
	}
	
	
	/**
	 * 获取同一个层次结构下的数据 如：root/ProductInfo/BussinessInfo/BusiList
	 * @param xmlCommon
	 * @param xpath
	 * @return
	 */
	private List getXmlLevelItemList(String xpath){
		List onlyattrlist = new ArrayList();
		int count = this.xmlDomElementCount(xpath);
		for (int i = 0; i < count; i++) {
			Iterator it = this.xmlDomElementLeafIterator(xpath,i);
			List elementList = new ArrayList();
			if(null != it){
				while(it.hasNext()){
					Entry entry = (Entry) it.next();
					XMLNode xn = entry.xmlNode;
					Map map = new HashMap();
					map.put(xn.getName(),xn.getValue());
					System.out.println(xn.getName() +" : "+xn.getValue().trim());
					elementList.add(map);
				}
			}
			onlyattrlist.add(elementList);
		}
		return onlyattrlist;
	}
	
	
	public static void main(String[] args) {
		TestXmlReader txr = new TestXmlReader();
		ReadFile rf = new ReadFile();
		String xmlstr = rf.getFileContent("g:/resultXml2010-07-06 15_22_30.xml");
		try {
			txr.analysisXML(xmlstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map map = txr.getXmlLevelItemMap((String)xmlLevelList.get(0));
		Map map2 = txr.getXmlLevelItemMap((String)xmlLevelList.get(1));
		List list = txr.getXmlLevelItemList((String)xmlLevelList.get(2));
		
	}

}
