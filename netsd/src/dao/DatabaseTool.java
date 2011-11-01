package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.JsoupLogic;

import org.apache.log4j.Logger;

import utils.MyResourceBundle;

public class DatabaseTool {

	private static Logger logger = Logger.getLogger("log");
	
	private DatabaseTool(){}   
	
	private static DatabaseTool instance = new DatabaseTool();
	
	public static DatabaseTool getInstance() { 
        return instance; 
    } 

	public Connection getConnection() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String connName = "jdbc:mysql://172.16.1.3:3306/netsd";
			Connection conn = DriverManager.getConnection(connName, "sily","jishijun");

//			Class.forName("org.sqlite.JDBC");    
//			String conn_str = "jdbc:sqlite:" + JsoupLogic.getCommonProp("db_path");;
//			Connection conn = DriverManager.getConnection(conn_str);   
//			
            return conn;        
        }catch(Exception ex){        
        	logger.error(ex.getMessage(),ex);   
        }        
        return null;      
	}
	
	 public void freeConnection(Connection conn){        
        if(conn!=null){        
            try {        
                conn.close();        
            } catch (SQLException e) {                      
            	logger.error(e.getMessage(),e);    
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
	/**
	 * 
	 * @param sql
	 * @return false:存在记录 
	 * @throws SQLException
	 */
	
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
			logger.error(e.getMessage(),e);    
		} finally {
			if(rs != null)rs.close();
			if (stat != null)
				stat.close();
			this.freeConnection(conn);
		}
		return true;
	}

	public List<List<String>> executeQuery(String sql) throws SQLException {
		Connection conn = this.getConnection();
		Statement stat = null;
		ResultSet rs = null;
		
		List<List<String>> dataList = new ArrayList<List<String>>();
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				List<String> rowList = new ArrayList<String>();
				ResultSetMetaData meta = rs.getMetaData();
				int colCount = meta.getColumnCount();
				for (int i = 0; i < colCount; i++) {
					String data = rs.getString(i + 1);
					data = data == null ? "" : data;
					rowList.add(data);
				}
				dataList.add(rowList);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
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
