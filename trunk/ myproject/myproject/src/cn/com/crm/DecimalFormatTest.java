package cn.com.crm;

import java.math.BigDecimal;
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
//		DecimalFormat bf = new DecimalFormat("$##,###,###,###.######");
//		Object d = 111111111111111111.22111;
//		System.out.println(bf.format(d));
//		
//		BigDecimal big = new BigDecimal("111111111111111111.0");
//		
//		double a = 100.00;
//		
//		System.out.println(big);
		
//		replaceAllTest();
		
		
		double i=2, j=2.1, k=2.5, m=2.9; 
		   System.out.println("舍掉小数取整:Math.floor(2)=" + (int)Math.floor(i)); 
		   System.out.println("舍掉小数取整:Math.floor(2.1)=" + (int)Math.floor(j)); 
		   System.out.println("舍掉小数取整:Math.floor(2.5)=" + (int)Math.floor(k)); 
		   System.out.println("舍掉小数取整:Math.floor(2.9)=" + (int)Math.floor(m)); 
		   
		   System.out.println("四舍五入取整:(2)=" + new BigDecimal("2").setScale(0, BigDecimal.ROUND_HALF_UP)); 
		   System.out.println("四舍五入取整:(2.1)=" + new BigDecimal("2.1").setScale(0, BigDecimal.ROUND_HALF_UP)); 
		   System.out.println("四舍五入取整:(2.5)=" + new BigDecimal("2.5").setScale(0, BigDecimal.ROUND_HALF_UP)); 
		   System.out.println("四舍五入取整:(2.9)=" + new BigDecimal("2.9").setScale(0, BigDecimal.ROUND_HALF_UP)); 
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
