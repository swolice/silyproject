package test;

import util.DbUtil;
import dms.data.Info;

public class TestDao {
	
	public static void main(String args[]){
		Info t=new Info();
//		t.setCity("北京");
		t.setAddress("大声说收拾收拾说");
		DbUtil.getDao().save(t);
		
		System.out.println(t.getId());
		
//		String s="22a";
//		System.out.println("!"+s.substring(s.length())+"!");
		
		
//		String s="  asdfds  s2  s   ";
//		System.out.println(s.replaceAll("^\\W*|\\W*$","")+"!");
	}

}
