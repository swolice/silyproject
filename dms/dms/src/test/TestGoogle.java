package test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TestGoogle {
	
	public static void main(String[] args) {
		try {
			System.out.println(URLEncoder.encode("北京 海淀区志新东路14-1号","utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
