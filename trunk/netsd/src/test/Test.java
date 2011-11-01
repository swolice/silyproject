package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class Test {

	public static void main(String[] args) throws Exception {
		// System.setProperty("java.library.path", "C:/TDDOWNLOAD/SQL");

//		Class.forName("org.sqlite.JDBC");
//		Connection conn = DriverManager
//				.getConnection("jdbc:sqlite:z:/url_info.db");
		// 建立事务机制,禁止自动提交，设置回滚点
//		conn.setAutoCommit(false);

//		Statement stat = conn.createStatement();
//		stat.executeUpdate("create  table url_info(row_id primary key,url_href ,up_url_href,url_type char(1),vflag char(1),create_time )");
		// stat.executeUpdate("insert into people1 values ('Gandhi',
		// 'politics');");
		// stat.executeUpdate("insert into people1 values ('Turing',
		// 'computers');");
		// stat.executeUpdate("insert into people1 values ('Wittgenstein',
		// 'smartypants');");
//		conn.commit();

		// ResultSet rs = stat.executeQuery("select * from people1;");
		// while (rs.next()) {
		// System.out.println("name = " + rs.getString("name"));
		// System.out.println("occupation = " + rs.getString("occupation"));
		// }

		// rs.close();
//		conn.close();
		
		
//		System.out.println(UUID.randomUUID());
		
		String aaString = "01正常/09关闭/02冻结";
		System.out.println(aaString.split("/")[0].replaceAll("\\d", ""));
		
	}
}
