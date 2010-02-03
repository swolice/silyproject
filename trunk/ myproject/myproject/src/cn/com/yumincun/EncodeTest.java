package cn.com.yumincun;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class EncodeTest {

	public static void main(String[] args) {
		String str = "%E9%AA%8C%E8%AF%81%E7%A0%81%E5%87%BA%E9%94%99%EF%BC%8C%E8%AF%B7%E6%A3%80%E6%9F%A5%EF%BC%81%3CBR%3E%E6%82%A8%E5%B7%B2%E5%9C%A83%E7%A7%92%E4%B8%AD%E5%A4%B1%E8%B4%A5%E4%BA%862%E6%AC%A1%EF%BC%81%E8%AF%B7%E6%B3%A8%E6%84%8F%EF%BC%9A%E5%A6%82%E6%9E%9C30%E7%A7%92%E5%86%85%E5%A4%A7%E4%BA%8E3%E6%AC%A1%E6%82%A8%E5%B0%86%E8%A2%AB%E5%BB%B6%E6%97%B660%E7%A7%92%E7%99%BB%E5%BD%95%EF%BC%81";
		String decode = "";
		try {
			decode = URLDecoder.decode(str,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(decode);
	}
}
