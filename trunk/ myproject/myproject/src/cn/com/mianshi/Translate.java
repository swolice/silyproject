package cn.com.mianshi;

public class Translate {

	public static String translate(String str){
		String tempstr = ""; 
		try {
			tempstr = new String(str.getBytes("gbk"),"iso-8859-1");
			tempstr = tempstr.trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempstr;
	}
	
	
	public static void main(String[] args) {
		System.out.println(translate("中文的字符，难道是iso8859-1的元婴"));
	}
}
