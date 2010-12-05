package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

public class JsoupDao {
	
	private static Logger logger = Logger.getLogger("log");
	
	private static byte[] bytes = new byte[0];
	/**
	 * 
	 * @param urlhref
	 * @return true:记录存在 false：记录不存在
	 */
	public static boolean hasUrlHref(String urlhref,String urltype) {
		String sql = "SELECT row_id FROM url_info where url_href=? and url_type=?";
		Connection connection = DatabaseTool.getInstance().getConnection();
		PreparedStatement pStatement = null;
		ResultSet rSet = null;
		try {
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, urlhref);
			pStatement.setString(2, urltype);
			rSet = pStatement.executeQuery();
			if(rSet.next())
				return true;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}finally{
			try {
				rSet.close();
				pStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param urlhref
	 * @return 
	 */
	public static void insertUrlHref(String urlhref,String urltype,String title,String uphref) {
		String sql = "insert into url_info(url_href,url_type,html_title,up_url_href,row_id,create_time) " +
				"values(?,?,?,?,?,?)";
		
		Connection connection = DatabaseTool.getInstance().getConnection();
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, urlhref);
			pStatement.setString(2, urltype);
			pStatement.setString(3, title);
			pStatement.setString(4, uphref);
			pStatement.setString(5, UUID.randomUUID().toString().replaceAll("-", ""));
			pStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
			synchronized (bytes) {
				pStatement.execute();
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}finally{
			try {
				pStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	public static  List<String> getFileList() {
		String sql = "SELECT distinct url_href FROM url_info where url_type=2 and vflag=0 order by create_time desc limit 0,10 ";
		Connection connection = DatabaseTool.getInstance().getConnection();
		PreparedStatement pStatement = null;
		ResultSet rSet = null;
		List<String> list = new ArrayList<String>();
		try {
			pStatement = connection.prepareStatement(sql);
			synchronized (bytes) {
				rSet = pStatement.executeQuery();
			}
			while(rSet.next()){
				list.add(rSet.getString("url_href"));
			}
			
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}finally{
			try {
				rSet.close();
				pStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		try {
			String inwhString = "";
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					if(i == (list.size()-1)){
						inwhString = "'" + list.get(i) + "'";
					}else{
						inwhString = "'" + list.get(i) + "',";
					}
				}
				String updatesql = "update url_info set vflag=1 where url_href in(" + inwhString + ")";
				
				DatabaseTool.getInstance().executeSQL(updatesql);
			}
		} catch (SQLException e1) {
			logger.error(e1.getMessage(),e1);
		}
		
		try {
			if(list.size() == 0){
				Thread.currentThread().sleep(120000);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}
	
	public static String getDownUrlHref(){
		String sqlString = "SELECT distinct url_href FROM url_info where vflag=0 and url_type=1 limit 1";
		try {
			List<List<String>> list = DatabaseTool.getInstance().executeQuery(sqlString);
			if(list.size()>0){
				return list.get(0).get(0);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static  void  updateVflag(String href){
		String sqlString  = "update url_info set vflag=1 where url_type=1 and vflag=0 and url_href=?";
		Connection connection = DatabaseTool.getInstance().getConnection();
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(sqlString);
			pStatement.setString(1, href);
			synchronized (bytes) {
				pStatement.execute();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}finally{
			try {
				pStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
