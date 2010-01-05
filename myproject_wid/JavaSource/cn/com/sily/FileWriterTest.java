/*
 * Created on 2009-9-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.com.sily;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author a
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileWriterTest {

	public static void main(String args[]){
		String sendSoapXML = "1231231321";
		FileWriter fw;
		try {
			fw = new FileWriter("d:/custAccount.xml");
			fw.write(sendSoapXML);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} 
	
}
