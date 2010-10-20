/*
 * 创建日期： 2009-12-7
 *
 */
package com.cthq.crm.project.common.database.access;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cthq.crm.project.common.database.exception.DBRunException;

/**
 * @author 吉仕军
 *  封装了结果集ResultSet
 *
 */
public class DbResultSet implements IDbAccess{
	//查询结果
	private Statement stmt = null;
	//查询结果集合
	private ResultSet rs = null;
	/**
	 * @param rs
	 *            The rs to set.
	 */
	public final void setResult(ResultSet rs) {
		this.rs = rs;
	}

	/**
	 * @param stmt The stmt to set.
	 */
	public final void setStatement(Statement stmt) {
		this.stmt = stmt;
	}
	/**
	 * 移动结果集合
	 * @return
	 * @throws SQLException
	 */
	public boolean hasNext() {
		try {
			if (rs == null) {
				return false;
			}
			if (stmt == null) {
				return false;
			}
			if (rs.next()) {
				return true;
			} else {
				dispose();
				return false;
			}
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}

	}
	/**
	 * 获取结果对象
	 * @param colname  检索结果语句中对应的列名
	 * @return
	 * @throws SQLException
	 */
	public Object getObject(String colname){
		try {
			return rs.getObject(colname);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	/**
	 * 获取结果对象
	 * @param _colunIndex 检索结果语句中对应的列所在的位置
	 * @return
	 * @throws SQLException
	 */
	public Object getObject(int _colunIndex)  {
		try {
			return rs.getObject(_colunIndex);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
		
	}
	/**
	 * 
	 * @param colname
	 * @return
	 * @throws SQLException
	 */
	public String getString(String colname)  {
		try {
			return rs.getString(colname);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
		
	}
	/**
	 * 
	 * @param _colunIndex
	 * @return
	 * @throws SQLException
	 */
	public String getString(int _colunIndex)  {
		try {
			return rs.getString(_colunIndex);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	/**
	 * 
	 * @param colname
	 * @return
	 * @throws SQLException
	 */
	public int getInt(String colname)  {
		try {
			return rs.getInt(colname);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	/**
	 * 
	 * @param _colunIndex
	 * @return
	 * @throws SQLException
	 */
	public int getInt(int _colunIndex) {
		try {
			return rs.getInt(_colunIndex);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	/**
	 * 
	 * @param colname
	 * @return
	 * @throws SQLException
	 */
	public long getLong(String colname) {
		
		try {
			return rs.getLong(colname);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	/**
	 * 
	 * @param _colunIndex
	 * @return
	 * @throws SQLException
	 */
	public long getLong(int _colunIndex)  {
		
		try {
			return rs.getLong(_colunIndex);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	
	/**
	 * 
	 * @param colname
	 * @return
	 * @throws SQLException
	 */
	public Blob getBlob(String colname)  {
		try {
			return rs.getBlob(colname);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	
	}
	/**
	 * 
	 * @param colname
	 * @return
	 * @throws SQLException
	 */
	public Blob getBlob(int _colunIndex){
		try {
			return rs.getBlob(_colunIndex);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
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
	/**
	 * 释放连接数据库的资源
	 *
	 */
	public void dispose() {
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
		
		try {
			if(stmt != null){
				stmt.close();
			}
		} catch (SQLException ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
	}
	

}
