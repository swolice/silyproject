package cn.com.crm;

import java.text.DecimalFormat;
import java.util.regex.Matcher;

public class DecimalFormatTest {
 
	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DecimalFormat bf = new DecimalFormat("$##,###,###,###.######");
		Object d = 111111111111111111.22111;
		System.out.println(bf.format(d));
		
		
//		replaceAllTest();
	}
	
	/**
	 * @param style
	 * @param obj
	 * @return
	 */
	public String format(String style,Object obj){
		DecimalFormat bf = new DecimalFormat(style);
		return bf.format(obj);
	}
	
	public static void replaceAllTest(){
		String aa = "aaaa$aa";

		String bb = Matcher.quoteReplacement(aa);
		
		String cc = "aaaaaa".replaceAll("aaaaaa", bb);
		
		System.out.println(cc);
	}

}
