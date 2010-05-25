package cn.com.crm;

import java.text.DecimalFormat;

public class DecimalFormatTest {
 
	public static void main(String[] args) {
		DecimalFormat bf = new DecimalFormat("###,###.######");
		Object d = 111111111111.22111;
		System.out.println(bf.format(d));
	}
	
	public String format(String style,Object obj){
		DecimalFormat bf = new DecimalFormat(style);
		return bf.format(obj);
	}

}
