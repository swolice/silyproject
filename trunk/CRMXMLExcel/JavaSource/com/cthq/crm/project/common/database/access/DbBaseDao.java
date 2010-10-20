/*
 * 创建日期：2009/12/02
 * CommonDao.java
 */
package com.cthq.crm.project.common.database.access;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.cthq.crm.project.common.database.exception.DBRunException;


/**
 * 数据库访问基础类
 * 
 * @author 蒋峰
 */
public final class DbBaseDao extends SqlQuery {
	/**
	 * 执行数据库的查询操作处理
	 * @param sql
	 * @return
	 */
	public final DbResultSet getDbResultSet(String sql) {
		DbResultSet dbRset = new DbResultSet();
		Statement stmt = null;
		ResultSet rest = null;
		int intSearchCount = -1;
		try {
			//创建查询上下文
			stmt = this.getConnection().createStatement();
			rest = stmt.executeQuery(sql);
			if (rest == null) {
				return null;
			}
			dbRset.setResult(rest);
			dbRset.setStatement(stmt);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		} finally {
		}
		return dbRset;
	}
	/**
	 * 执行数据库的存储过程操作
	 * @param sql
	 * @return
	 */
	public final DbPrepareCall getDbPrepareCall(String sql) {
		DbPrepareCall dbCall = new DbPrepareCall();
		CallableStatement callStmt = null;
		try {
			callStmt = this.getConnection().prepareCall(sql);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		} finally {
		}
		dbCall.setCallableStatement(callStmt);
		return dbCall;
	}
	/**
	 * 暂时
	 * @param sql
	 */
	public final void getDbPrepareStatement(String sql) {
//		try {
//			callStmt = this.getConnection().prepareStatement(sql)
//		} catch(Exception ex) {
//			throw new CommonRunException(ex.getMessage(), ex, "DB-001");
//		} finally {
//		}
	}
}
