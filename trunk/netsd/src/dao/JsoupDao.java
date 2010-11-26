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

public class JsoupDao {
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
			e.printStackTrace();
		}finally{
			try {
				rSet.close();
				pStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
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
			pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				pStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static List<String> getFileList() {
		String sql = "SELECT distinct url_href FROM url_info where url_type=2 and vflag=0 order by create_time desc limit 0,10 ";
		String updatesql = "update url_info set vflag=1 where url_href in("
						+ "select t.url_href from "
						+ "(SELECT distinct url_href FROM url_info where url_type=2 and vflag=0 order by create_time desc limit 0,10) t) ";
		Connection connection = DatabaseTool.getInstance().getConnection();
		PreparedStatement pStatement = null;
		ResultSet rSet = null;
		List<String> list = new ArrayList<String>();
		try {
			pStatement = connection.prepareStatement(sql);
			rSet = pStatement.executeQuery();
			while(rSet.next()){
				list.add(rSet.getString("url_href"));
			}
			DatabaseTool.getInstance().executeSQL(updatesql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rSet.close();
				pStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			if(list.size() == 0){
				Thread.currentThread().sleep(120000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return "";
	}
	
	public static void updateVflag(String href){
		String sqlString  = "update url_info set vflag=1 where url_type=1 and vflag=0 and url_href=?";
		Connection connection = DatabaseTool.getInstance().getConnection();
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(sqlString);
			pStatement.setString(1, href);
			pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				pStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
