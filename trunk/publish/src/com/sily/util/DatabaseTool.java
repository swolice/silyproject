package com.sily.util;

import java.io.InputStream;
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
import java.util.Properties;

public class DatabaseTool {

	private DatabaseTool(){}   
	
	private static DatabaseTool instance = new DatabaseTool();
	
	public static DatabaseTool getInstance() { 
        return instance; 
    } 

	public Connection getConnection() {
		try{
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
			Properties prop = new Properties();
			prop.load(is);
			
			Class.forName(prop.getProperty("driver"));
			String connName = prop.getProperty("url");
			Connection con = DriverManager.getConnection(connName, prop.getProperty("username"),prop.getProperty("password"));

			return con;        
        }catch(Exception ex){        
            ex.printStackTrace();        
        }        
        return null;      
	}
	
	 public void freeConnection(Connection conn){        
        if(conn!=null){        
            try {        
                conn.close();        
            } catch (SQLException e) {                      
                e.printStackTrace();        
            }        
        }        
    }     
	
	public void executeSQL(String sql) throws SQLException {
		Connection conn = this.getConnection();
		Statement stat = null;
		try {
			stat = conn.createStatement();
			stat.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stat != null)
				stat.close();
			this.freeConnection(conn);
		}
	}
	public boolean executeSQL1(String sql) throws SQLException {
		Connection conn = this.getConnection();
		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			if(rs.next()){
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null)rs.close();
			if (stat != null)
				stat.close();
			this.freeConnection(conn);
		}
		return true;
	}

	public List executeQuery(String sql) throws SQLException {
		Connection conn = this.getConnection();
		Statement stat = null;
		ResultSet rs = null;
		List dataList = new ArrayList();
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			ResultSetMetaData meta = rs.getMetaData();
			int colCount = meta.getColumnCount();
			List columnList = new ArrayList();
			for (int i = 1; i < colCount+1; i++) {
				String data = meta.getColumnName(i);
				columnList.add(data);
			}
			while (rs.next()) {
				Map rowMap = new HashMap();
				for (int i = 0; i < columnList.size(); i++) {
					String colName = (String)columnList.get(i);
					String data = rs.getString(colName);
					data = data == null ? "" : data;
					rowMap.put(colName,data);
				}
				dataList.add(rowMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			this.freeConnection(conn);
		}
		return dataList;
	}
	
}
