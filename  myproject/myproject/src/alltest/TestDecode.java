package alltest;

import java.io.FileReader;


import com.cthq.crm.webservice.encode.Base64;
import com.cthq.crm.webservice.util.ReadFile;

public class TestDecode {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Base64 base64 = new Base64();
		String str = "PCFET0NUWVBFIEhUTUwgUFVCTElDICItLy9XM0MvL0RURCBIVE1MIDQuMCBUcmFuc2l0aW9uYWwv";
//		FileReader fr = new FileReader(str);
		byte valByte[] = base64.decodeFromCharToByte(str.toCharArray());
		String result = new String(valByte);
		System.out.println(result);
	}
	
	public String name() {
		return "";	 
	}
}

