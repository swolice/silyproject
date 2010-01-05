/*
 * Created on 2009-4-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.crm;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author 蒋峰
 *
 */
public class SqlQuery extends  JdbcDaoSupport {
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	protected static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param obj
	 * @return
	 */
	protected static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			if (((String)obj).trim().length() == 0) {
			    return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		if (isNull(obj)) {
			return null;
		}
		if (obj instanceof String) {
			return ((String)obj).trim();
		}
		if (obj instanceof String[]) {
			return ((String[])obj)[0].trim();
		}
		return null; 
	}
	/**
	 * 获取数据库查询记录集合
	 * @param sql 查询SQL文
	 * @return 返回查询结果一览 如果没有获取到数据库记录 返回的结果集合为NULL
	 * @throws Exception
	 */
	public List query(final String sql) throws  Exception {
		Statement stmt = null;
		ResultSet rest = null;
		int intSearchCount = -1;
		try {
			//创建查询上下文
			stmt = this.getConnection().createStatement();
			rest = stmt.executeQuery(sql);
			int count = 0;
			if (rest == null) {
				return null;
			}
			int size = rest.getFetchSize();
			intSearchCount = size;
			return resultSetToList(rest, -1);
		} catch(Exception ex) {
			throw ex;
		} finally {
			if (rest != null) {
				rest.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	/**
	 * 分段获取数据库的查询结果集合
	 * @param sql 查询SQL文
	 * @param intStart 获取记录的检索的数据位置
	 * @param intMaxCount 最大记录数 如果该参数的值设为-1 表示需要将当前的结果全部获取
	 * @return 返回查询结果一览 如果没有获取到数据库记录 返回的结果集合为NULL
	 * @throws Exception
	 */
	public List query(final String sql,  final int  intStart, final int intMaxCount) throws Exception {
		int resultSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;
		int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
		Statement stmt = null;
		ResultSet rest = null;
		List resultList  = new ArrayList(2);
		int intSearchCount = -1;
		try {
			//创建查询上下文
			stmt = this.getConnection().createStatement(resultSetType, resultSetConcurrency);
			rest = stmt.executeQuery(sql);
			int count = 0;
			if (rest == null) {
				return null;
			}     
			if(!rest.last())    {
				return null;
			}
			intSearchCount = rest.getRow();
			int intTmpStart = 0;
			int intRec = 0;
			//查询的记录总数>获取记录数的起始位置
			if (intSearchCount > intStart) {
				//记录数中0或1开始
				if (intStart-1 <= 0) {
					rest.beforeFirst();
				} else {
					rest.absolute(intStart-1);
				}
			} else {
				if (intMaxCount != -1) {
					intTmpStart = intSearchCount - intMaxCount-1;
					if (intTmpStart <= 0) {
						rest.beforeFirst();
					} else {
						rest.absolute(intTmpStart);
					}
				}
			}
			//设置查询结果
			resultList.add(0, resultSetToList(rest, intMaxCount));
			//设置当前查询结果的总记录数
			resultList.add(1, String.valueOf(intSearchCount));
		} catch(Exception ex) {
			throw ex;
		} finally {
			if (rest != null) {
				rest.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return resultList;
	}
	
	public int execute(String sql) throws Exception {
		Statement stmt = null;
		try {
			//创建查询上下文
			stmt = this.getConnection().createStatement();
			return stmt.executeUpdate(sql);
		} catch(Exception ex) {
			throw ex;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	/**
	 * ResultSet结果集rs转换成List集合
	 * 
	 * @param rs
	 *            ResultSet 待转换的ResultSet
	 * @return List
	 * @throws Exception
	 */
	protected List resultSetToList(ResultSet rs, int intMaxCount) throws Exception {
		if (rs == null) {
			return null;
		}
		List ret = new ArrayList();
		int count = 0;
		while (rs.next()) {
			ret.add(resultSetToMap(rs));
			if (intMaxCount != -1) {
				count = count + 1;
				if (count == intMaxCount) {
					break;
				}
			}
		}
		return ret;
	}
	/**
	 * 把java.sql.ResultSet结果集rs转换成Map集合
	 * 
	 * @param rs
	 *            ResultSet 待转换的ResultSet
	 * @return Map
	 * @throws Exception
	 */
	protected Map resultSetToMap(ResultSet rs) throws Exception {
		if (rs == null) {
			return null;
		}
		ResultSetMetaData meta = rs.getMetaData();
		int count = meta.getColumnCount();
		Map map = new HashMap(count);
		for (int i = 1; i <= count; i++) {	
			map.put(meta.getColumnName(i),rs.getObject(i));			
		}		
		return map;
	}	
	
}

