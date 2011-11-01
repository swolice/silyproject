package dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import utils.StringUtils;

public class DatabaseTool {

	private static Logger logger = Logger.getLogger("log");
	
	private DatabaseTool(){}   
	
	private static DatabaseTool instance = new DatabaseTool();
	
	public static DatabaseTool getInstance() { 
        return instance; 
    } 

	public Connection getConnection() {
		try{
			Class<?> driver = Class.forName("com.mysql.jdbc.Driver");
			
			InputStream is = driver.getClassLoader().getResourceAsStream("conf/common.properties");
			Properties prop = new Properties();
			prop.load(is);
			String connName = prop.getProperty("url");
			String username = prop.getProperty("username");
			String passwd = prop.getProperty("passwd");
			Connection conn = DriverManager.getConnection(connName, username,passwd);
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
	
	public static void main(String[] args) {
		DatabaseTool dt = DatabaseTool.getInstance();
		try {
			List<List<String>> list = dt.executeQuery("select guid from wp_posts where `post_mime_type` = 'image/jpeg'");
			for (int i = 0; i < list.size(); i++) {
				List<String> tmpList = list.get(i);
				for (int j = 0; j < tmpList.size(); j++) {
					String url  = tmpList.get(j);
					String[] fields = url.split("/");
					System.out.println(url);
					String path = "h:" + File.separator + fields[4] +  File.separator +  fields[5];
					String filename =  path + File.separator + fields[6];
					
					downloadFile(url,path,filename);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void downloadFile(String url,String path,String filename) throws ConnectException {
		if (StringUtils.isEmpty(url))
			return;
		try {
			URL u = new URL(url);
			URLConnection c = u.openConnection();
			c.connect();
			InputStream is = c.getInputStream();
			
			File file = new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
			
			OutputStream os = new FileOutputStream(filename);
			int tmp = 0;
			while ((tmp = is.read()) != -1) {
				os.write(tmp);
			}
			os.close();
			is.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
