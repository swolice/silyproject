/*
 * Created on 2010-1-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cthq.crm.project.common.database.access;

import java.sql.CallableStatement;
import java.sql.SQLException;

import com.cthq.crm.project.common.database.exception.DBRunException;

/**
 * @author db2admin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DbPrepareCall  implements IDbAccess{
	private CallableStatement callStmt = null;
	
	public void setCallableStatement(CallableStatement _callStmt) {
		callStmt = _callStmt;
	}
	public void execute() {
		try{
			callStmt.execute();
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	public void setInt(int _paramsIndx, int _val) {
		try{
			this.callStmt.setInt(_paramsIndx, _val);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	public void setInt(String parameterName, int _value) {
		try{
			this.callStmt.setInt(parameterName, _value);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	public void setObject(int _paramsIndx, Object _val) {
		try{
			this.callStmt.setObject(_paramsIndx, _val);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	public void setObject(String parameterName, Object _value) {
		try{
			this.callStmt.setObject(parameterName, _value);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
		
	}
	
	public Object getObject(int _paramsIndx) {
		try{
			return this.callStmt.getObject(_paramsIndx);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	public Object getObject(String parameterName) {
		try{
			return this.callStmt.getObject(parameterName);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
		
	}
	/**
	 * 释放连接数据库的资源
	 *
	 */
	public void dispose() {
		try {
			if(callStmt != null){
				callStmt.close();
			}
		} catch (SQLException ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
}
