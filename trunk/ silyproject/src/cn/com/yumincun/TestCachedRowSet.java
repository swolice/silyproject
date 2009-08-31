package cn.com.yumincun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.RowSet;
import com.sun.rowset.CachedRowSetImpl;

public class TestCachedRowSet {

	public static void testCachedRowSet() {

	}

	public static com.sun.rowset.CachedRowSetImpl Query_all()
			throws SQLException {

		Connection conn = null;

		String url = "jdbc:microsoft:sqlserver://127.0.0.1:1433;DatabaseName=test";

		String user = "sa";

		String password = "jishijun";

		// 获得数据库连接

		conn = DriverManager.getConnection(url, user, password);

		java.sql.Statement stmt = conn.createStatement();

		// 查询数据库，获得表数据

		ResultSet rs = stmt.executeQuery("select * from test"); // 根据ResultSet对象生成CachedRowSet类型的对象

		CachedRowSetImpl crs = new CachedRowSetImpl();

		crs.populate(rs);

		// 关闭ResultSet

		rs.close();

		// 关闭数据库的连接

		conn.close();

		return crs;

	}

	public static void main(String[] args) throws SQLException {

		String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";

		Connection conn = null;

		String url = "jdbc:microsoft:sqlserver://127.0.0.1:1433;DatabaseName=test;SelectMethod=Cursor";// 最后注意事项的部分

		String user = "sa";

		String password = "jishijun";

		try {

			Class.forName(driver).newInstance();

			CachedRowSetImpl crs = new CachedRowSetImpl();

			crs = Query_all();

			crs.setTableName("test");

			crs.next();

			// 改操作

			crs.updateString(1, "434");

			// 查操作
			//while(crs.next()){
				
				String a = crs.getString("aaaa");
				String b = crs.getString("bbbb");
				String c = crs.getString("cccc");

				System.out.println(a+"\t"+b+"\t"+c);

			//}
				
			
			// 删操作

//			crs.deleteRow();
//
			crs.updateRow();
//
//			// 重新获取与数据库的连接
//
			conn = DriverManager.getConnection(url, user, password);
//
//			// 将CachedRowSet的内容更新到数据库
//
			crs.acceptChanges(conn);
//
//			// 关闭CachedRowSet
//
			crs.close();
//
//			// 关闭数据库连接
			conn.close();

		} catch (InstantiationException e) {

			e.printStackTrace();

		} catch (IllegalAccessException e) {

			e.printStackTrace();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		}

	}

}
