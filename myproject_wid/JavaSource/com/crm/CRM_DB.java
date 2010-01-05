package com.crm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	//SqlQuery sq = new SqlQuery();
		CRM_DB crm_db = new CRM_DB();
		String sql = "select * from ti_soap_msg_history";
		try {
			List list = crm_db.query(sql,0,10);
			System.out.println(list.get(0));
			System.out.println(list.get(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List query(final String sql,  final int  intStart, final int intMaxCount) throws Exception {
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
	private  void updateBaseInfo(){
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
