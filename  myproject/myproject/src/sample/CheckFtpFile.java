/**
 * 
 */
package sample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.cthq.crm.webservice.common.xml.imp.XMLBaseReader;
import com.cthq.crm.webservice.common.xml.imp.XMLCommonReader;
import com.cthq.crm.webservice.common.xml.imp.XMLNode;
import com.cthq.crm.webservice.common.xml.imp.XMLNodeCollection.Entry;
import com.cthq.crm.webservice.util.ReadFile;

/**
 * @author sily
 *
 */
public class CheckFtpFile {

	public static void main(String[] args) {
		XMLCommonReader xCommonReader = new XMLCommonReader();
		List<String> list = new ArrayList<String>(1);
		list.add(0,"root");
		xCommonReader.setXMLLevelList(list);
		ReadFile  readFile = new ReadFile();
		String strXml = readFile.getFileContent("H:\\workspace\\myproject\\src\\test\\NewFile.xml");
		try {
			xCommonReader.analysisXML(strXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List returnList = getXmlLevelItemList(xCommonReader,"root");
		System.out.println(returnList);
	}
	
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
					List alist = xn.getAttributes();
					System.out.println(xn.getName() + " : "
							+ alist.toString());
					elementList.add(alist);
				}
			}
			onlyattrlist.add(elementList);
		}
		return onlyattrlist;
	}
}
