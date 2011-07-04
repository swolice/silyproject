package test;

import java.io.IOException;

import sun.misc.BASE64Decoder;

public class StringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String sssString = "f:/test.xml";
//		System.out.println(sssString.lastIndexOf("."));
//		System.out.println(sssString.substring(sssString.lastIndexOf(".")));
		
		
		String ssString = "vbG98LLp0a8ueGxz";
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] zw = decoder.decodeBuffer(ssString);
			String ssString2 = new String(zw,"GBK");
			System.out.println(ssString2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
