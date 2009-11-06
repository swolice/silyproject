package com.crm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Created on 2009-9-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author sily
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CRM_DB {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager
					.getConnection(
							"jdbc:oracle:thin:@172.16.1.252:1521:CRMTEST",
							"eim", "123");
			for (int i = 1; i <= 100; i++) {
				stmt = conn.createStatement();
				String sql = "update interface_base_info set baseinfo014 = 'E0001' where baseinfo099 = '重单'";
				stmt.executeUpdate(sql);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@61.129.250.74:1521:jtcrm", "jtcrm_app",
					"jtcrm123");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void executeSQL(String sql) {
		Connection conn = this.getConnection();
		Statement stmt = null;
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != stmt) {
				try {
					stmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public ResultSet selectSQL(String sql){
		Connection conn = this.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != stmt) {
				try {
					stmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return rs;
	}
}