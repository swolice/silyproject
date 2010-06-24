/*
 * 创建日期： 2009-12-7
 *
 */
package com.cthq.crm.report.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 吉仕军
 *  封装了结果集ResultSet
 *
 */
public class ResultSetOperator {
	
	private Statement stmt;
	
	private ResultSet rs;

	public boolean hasNext() throws SQLException {
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}
	
	public Object getObject(String colname) throws SQLException {
		return rs.getObject(colname);
	}
	
	public List getColumnNames() throws SQLException {
		ResultSetMetaData  rsmd = rs.getMetaData();
		List list = new ArrayList();
		//将所有的字段名都放入list中
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			String colName = rsmd.getColumnName(i);
			list.add(colName);
		}
		return list;
	}
	
	public void dispose() {
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
		}
		try {
			if(stmt != null){
				stmt.close();
			}
		} catch (SQLException e) {
		}
	}
	
	/**
	 * @param rs
	 *            The rs to set.
	 */
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	/**
	 * @param stmt The stmt to set.
	 */
	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
}
