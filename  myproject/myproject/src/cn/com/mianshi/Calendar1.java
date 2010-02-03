package cn.com.mianshi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Calendar1 {

	public static void main(String[] args) {
		Date date = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			date = sf.parse("2009年7月1日");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		//ca.add(Calendar.MONTH, -1);
		//String dateStr = sf.format(ca.getTime());
		
		String ss = getWeek(date);
		System.out.println(ss);
	}
	public static String getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
        }
        return "";
    }

	
}
