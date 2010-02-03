package cn.com.pbx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;

public class TimeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn1 = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/pbx", "root", "jishijun");
			Statement stmt = conn1.createStatement();
			String sql = "select userid+10000 as userid, username,userpassword,useremail,usersex,joindate from dv_user";
			ResultSet rs = stmt.executeQuery(sql);
			insertDate(rs);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		
//		Date today;
//		try {
//			today = sdf.parse("2009-09-13 00:00:00");
//			System.out.println(today.getTime());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		//sdf.format(today);
	
		

	}
	
	public static void insertDate(ResultSet rs){
		Connection conn = null;
		
		String sql = "insert into sq_user (name,nick,pwd,rawPwd,timeZone,Gender,RegDate,check_status,RealPic,birthday,experience,gold) values (?,?,?,?,?,?,?,?,'face.gif',?,?,?)";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/pbx_com_cn", "root", "jishijun");
			
			conn.setAutoCommit(false);
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					ps.setString(1, rs.getString("userid"));
		            ps.setString(2, rs.getString("username"));
		            String pwd = rs.getString("userpassword");
		            ps.setString(3, pwd);
		            ps.setString(4, pwd);
		            ps.setString(5, "GMT+08:00");
		            String gender = rs.getString("usersex").equals("1")?"M":"F";
		            ps.setString(6, gender);
		            String timestr = "";
		            try {
						timestr = ""+(sdf.parse(rs.getString("joindate"))).getTime();
					} catch (ParseException e) {
						e.printStackTrace();
					}  
					ps.setString(7, timestr);
		            ps.setInt(8, 1);
		            ps.setString(9, timestr);
		            ps.setInt(10, 66);
		            ps.setInt(11, 108);
				}
				ps.executeUpdate();
				conn.commit();
			}
			ps.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.rollback();
				conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	
	}

}
