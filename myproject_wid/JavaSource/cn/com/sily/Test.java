/*
 * Created on 2009-8-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.com.sily;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author a
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Test {

	public static void main(String[] args) {
//		try {
////			Reader read = new FileReader("e:/link.txt");
////			BufferedReader br = new BufferedReader(read);
////			String s[] = new String[400];
////			String temp = "";
////			int i = 0;
////			while ((temp = br.readLine()) != null) {
//////				if (temp.indexOf("href=") != -1) {
//////					System.out.println(temp.replaceAll("\t", ""));
//////				}
////				System.out.println(temp.replaceAll("\">第[0-9]{3}集",""));
////				s[i++] = temp;
////			}
//			//System.out.println(s.length);
////			for (int j = s.length-1; j > 0; j--) {
////				if (s[j] != null) {
////					//System.out.println(">第"+j+"集");
////					System.out.println(s[j].replaceAll("href=\"",""));
////				}
////			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		for (int i = 1; i <= 100; i++) {
//			if(i<10){
//				System.out.println("siinfo00" + i + " varchar2(200),");
//			}else if(i<99){
//				System.out.println("siinfo0" + i + " varchar2(200),");
//			}else if(i==100){
//				System.out.println("siinfo" + i + " varchar2(1000)");
//			}
//			
//		}
		
		
//		Pattern pattern = Pattern.compile("[1|2|3|4|5|6]");
//		String ss = "6";
//		boolean b = pattern.matcher(ss).matches();
//		if(b){
//			System.out.println("true");
//		}else{
//			System.out.println("false");
//		}
		
//		for (int i = 1; i <= 100; i++) {
//			String sql = "insert into interface_base_info(row_id,baseinfo001,baseinfo003,baseinfo006,inserttime,updatetime) values(f_get_globalid,'SI000"+i+"','浙江鸿程"+i+"','571',sysdate,sysdate);";
//			System.out.println(sql);
//		}
		
//		try {
//			FileOutputStream file = new FileOutputStream("e:/qc.txt");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		Pattern pattern = Pattern.compile("1|2|3");
//		Matcher matcher = pattern.matcher("1123");
//		boolean b= matcher.matches();
//		//当条件满足时，将返回true，否则返回false
//		System.out.println(20/7);
		
		
//		String str = "测试java程序的截取字符问题";
//		int count = str.length()%2 == 0 ? str.length()/2 :str.length()/2+1; 
//		for (int i = 0; i < count; i++) {
//			if(str.length() < 2*(i+1)){
//				System.out.println(str.substring(i*2,str.length()));
//			}else{
//				System.out.println(str.substring(i*2,2*(i+1)));
//			}
//			
//		}
		
		String[] ss = "1231,,,,12321".split(",");
		
		System.out.println(ss.length);
	}
}
