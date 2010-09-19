package cn.com.crm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestTimer {

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		
		System.out.println("20100820".compareTo("20100821"));
		
		c.setTime(getDateFromString("20100820"));
		c1.setTime(getDateFromString("20100820"));
		
		while(c.before(c1)){
			System.out.println(getTimeFormate(c.getTime()));
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		System.out.println(getTimeFormate(c1.getTime()));
	}
	
	public static String getTimeFormate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		return sdf.format(date);
	}
	
	
	
	
	public static Date getDateFromString(String datestr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		try {
			return sdf.parse(datestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
