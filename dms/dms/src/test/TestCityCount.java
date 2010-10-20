package test;

public class TestCityCount {

	public static void main(String[] args) {
		String sql = "select c1.id,c1.city_name,t1.num from city c1,"
				+ "(select c.id,count(t.id) as num from city c,info t WHERE c.id=t.city and t.end_time>? group by c.id) t1 "
				+ "where c1.id=t1.id order by c1.sort_order ";
		java.util.Date now=new java.util.Date ();
		java.util.List list=util.DbUtil.getDao().list(sql, new Object[]{now},-1,-1);
		
		System.out.println(list.size());
//		Number
			
	}

}
