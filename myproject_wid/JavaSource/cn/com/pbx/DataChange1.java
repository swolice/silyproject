package cn.com.pbx;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class DataChange1 {

	private static String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)}; DBQ=e:/asdfhyprqlk1hl312.mdb";

	private static String tableName = "Dv_UserGroups";

	public static void main(String[] args) {
		
		PrintStream pw;
		try {
			pw = new PrintStream(new FileOutputStream("e:/pbxerror.log"));
			System.setOut(pw);
			pw.flush();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			conn = DriverManager.getConnection(url, "", "");
			DatabaseMetaData db = conn.getMetaData();
			String[] types = { "TABLE" };
			ResultSet rset = db.getTables(null, null, null, types);
			 String sql = "select * from " + tableName + " order by 1 ";
			 stmt = conn.createStatement();
			 rs = stmt.executeQuery(sql);
			 
			// // 建表sql
			// ResultSetMetaData rsmd = rs.getMetaData();
			// createTable(rsmd);
//			while (rset.next()) {
//				tableName = rset.getString("TABLE_NAME");
//				
//
//				String sql = "select * from " + tableName + " order by 1 ";
//				if (!"Dv_notdownload".equals(tableName)) {
//					stmt = conn.createStatement();
//					rs = stmt.executeQuery(sql);
//					//System.out.println("drop table " + tableName + ";");
//					// 建表sql
//					// ResultSetMetaData rsmd = rs.getMetaData();
//					// createTable(rsmd);
//					
//					insertData(rs);
//				}
//			}
			insertData(rs);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���excel�е�������mysql�еĽ������ 建表
	 * 
	 * @param rsmd
	 */
	public static void createTable(ResultSetMetaData rsmd) {
		StringBuffer sb = new StringBuffer();
		System.out.println("-- ---start-------" + tableName
				+ "-------start-------");
		sb.append("create table " + tableName + " (\n");
		try {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String cName = rsmd.getColumnName(i);
				if ("length".equals(cName.toLowerCase())) {
					cName = tableName + "_length";
				}
//				if ("length".equals(cName.toLowerCase())) {
//					cName = tableName + "_length";
//				}
				String cType = rsmd.getColumnTypeName(i);
				// System.out.println(cType);
				sb.append("\t");
				int cLength = rsmd.getColumnDisplaySize(i);
				if ("COUNTER".equals(cType.trim())) {
					sb.append(cName + " int auto_increment primary key");
				} else if ("VARCHAR".equals(cType.trim())) {
					sb.append(cName + " varchar(" + cLength + ")");
				} else if ("INTEGER".equals(cType.trim())
						|| "SMALLINT".equals(cType.trim())) {
					sb.append(cName + " int");
				} else if ("BYTE".equals(cType.trim())) {
					sb.append(cName + " char(1)");
				} else if ("DOUBLE".equals(cType.trim())) {
					sb.append(cName + " double");
				} else if ("LONGCHAR".equals(cType.trim())) {
					if (cLength > 21845) {
						sb.append(cName + " text");
					} else {
						sb.append(cName + " varchar(" + cLength + ")");
					}
				} else if ("DATETIME".equals(cType.trim())) {
					sb.append(cName + " datetime");
				}
				if (rsmd.getColumnCount() == i) {
					sb.append("\n");
				} else {
					sb.append(", \n");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sb.append(");");
		System.out.println(sb.toString());
		System.out.println();
	}

	public static void insertData(ResultSet rs) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/pbx", "root", "root");
			conn.setAutoCommit(false);

			StringBuffer sql_sb = new StringBuffer();
			StringBuffer sql_sb_value = new StringBuffer();
			sql_sb.append("insert into " + tableName + "(");
			sql_sb_value.append(" values(");
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String cName = rsmd.getColumnName(i);
				// String cType = rsmd.getColumnTypeName(i);
				if ("length".equals(cName.toLowerCase())) {
					cName = tableName + "_length";
				}
				if (i == rsmd.getColumnCount()) {
					sql_sb.append(cName + ")");
					sql_sb_value.append("?)");
				} else {
					sql_sb.append(cName + ",");
					sql_sb_value.append("?,");
				}
			}
			String sql = sql_sb.append(sql_sb_value).toString();
			PreparedStatement ps = (PreparedStatement) conn
					.prepareStatement(sql);
			int count = 0;
			while (rs.next()) {
				System.out.println(count++);
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String cType = rsmd.getColumnTypeName(i);
					if ("COUNTER".equals(cType.trim())) {
						ps.setInt(i, rs.getInt(i));
					} else if ("VARCHAR".equals(cType.trim())) {
						String tempStr = rs.getString(i);
						if (null == tempStr || "".equals(tempStr.trim())) {
							tempStr = "";
						}
						ps.setString(i, tempStr);
					} else if ("INTEGER".equals(cType.trim())) {
						ps.setString(i, rs.getString(i));
					} else if ("SMALLINT".equals(cType.trim())) {
						ps.setString(i, rs.getString(i));
					} else if ("BYTE".equals(cType.trim())) {
						ps.setString(i, String.valueOf(rs.getByte(i)));
					} else if ("LONGCHAR".equals(cType.trim())) {
						ps.setString(i, rs.getString(i));
					} else if ("DATETIME".equals(cType.trim())) {
						ps.setDate(i, rs.getDate(i));
					} else if ("DOUBLE".equals(cType.trim())) {
						ps.setDouble(i, rs.getDouble(i));
					}
				}
				ps.executeUpdate();
				conn.commit();
			}
			ps.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("---------error--------"+tableName+"-----------error----------");
		} finally {
			try {
				conn.rollback();
				conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	}
}
