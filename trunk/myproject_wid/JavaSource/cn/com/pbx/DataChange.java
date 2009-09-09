package cn.com.pbx;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class DataChange {

	private static String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)}; DBQ=e:/asdfhyprqlk1hl312.mdb";

	public static void main(String[] args) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection conn = DriverManager.getConnection(url, "", "");
			String sql = "select * from Dv_User order by UserID";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
//			while(rs.next()){
//				System.out.println(rs.getInt(1));
//			}
			
			// 建表sql
//			ResultSetMetaData rsmd = rs.getMetaData();
//			createTable(rsmd);
			insertData(rs);
			rs.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * ���excel�е��ֶΣ�����mysql�еĽ������
	 * 
	 * @param rsmd
	 */
	public static void createTable(ResultSetMetaData rsmd) {
		StringBuffer sb = new StringBuffer();
		sb.append("create table Dv_User (\n");
		try {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String cName = rsmd.getColumnName(i);
				String cType = rsmd.getColumnTypeName(i);
				int cLength = rsmd.getColumnDisplaySize(i);
				if ("COUNTER".equals(cType.trim())) {
					sb.append(cName + " int auto_increment primary key");
				} else if ("VARCHAR".equals(cType.trim())) {
					sb.append(cName + " varchar(" + cLength + ")");
				} else if ("INTEGER".equals(cType.trim())) {
					sb.append(cName + " int");
				} else if ("BYTE".equals(cType.trim())) {
					sb.append(cName + " char(1)");
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
		sb.append(")");
		System.out.println(sb.toString());
	}

	public static void insertData(ResultSet rs) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/pbx", "root", "root");
			conn.setAutoCommit(false);

			StringBuffer sql_sb = new StringBuffer();
			StringBuffer sql_sb_value = new StringBuffer();
			sql_sb.append("insert into Dv_User(");
			sql_sb_value.append(" values(");
			ResultSetMetaData rsmd = rs.getMetaData();
			//System.out.println(rsmd.getColumnCount());
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String cName = rsmd.getColumnName(i);
				// String cType = rsmd.getColumnTypeName(i);

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
			
			while (rs.next()) {
				System.out.println(rs.getRow());
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String cType = rsmd.getColumnTypeName(i);
					if ("COUNTER".equals(cType.trim())||"INTEGER".equals(cType.trim())) {
						ps.setInt(i, rs.getInt(i));
					} else if ("VARCHAR".equals(cType.trim())||"LONGCHAR".equals(cType.trim())) {
						ps.setString(i, rs.getString(i));
					} else if ("INTEGER".equals(cType.trim())) {
						ps.setInt(i, rs.getInt(i));
					} else if ("BYTE".equals(cType.trim())) {
						ps.setString(i, String.valueOf(rs.getByte(i)));
					} else if ("DATETIME".equals(cType.trim())) {
						java.sql.Date date = rs.getDate(i);
						if(date == null){
							ps.setDate(i,new Date(20090807));
						}else{
							ps.setDate(i,date);
						}
						
					}
					ps.addBatch();
				}
				
				ps.executeUpdate();
			}
			conn.commit();
			ps.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
