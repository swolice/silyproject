package org.json;

import org.apache.log4j.Logger;

import com.cthq.crm.webservice.util.ReadFile;

public class Xml2Json {

	public static void main(String[] args) {
		ReadFile rf = new ReadFile();
		
		String aa = rf.getFileContent("d:/我的桌面/aaa1.xml","GBK");
		
		
		try {
			JSONObject j = XML.toJSONObject(aa);
			String jsonStr = j.toString();
			
//			System.out.println( jsonStr);
			
			Logger.getLogger("LogFile").info(jsonStr);
			
			
			JSONObject jStr = new JSONObject(jsonStr);
			String xmlString = XML.toString(jStr);
			
			Logger.getLogger("LogFile").info(xmlString);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
