package cn.com.sily;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import sample.DatabaseTool;

public class TestPreStatment {

	public static void main(String[] args) {
		DatabaseTool dTool = DatabaseTool.getInstance();
		String sqlString = "insert into  tt_i_rim_si(row_id,row_num,bill,create_date) values(?,?,?,sysdate)";
		try {
			PreparedStatement preparedStatement = dTool.getConnection().prepareStatement(sqlString);
			preparedStatement.setString(1, UUID.randomUUID().toString().replaceAll("-", ""));
			preparedStatement.setString(2, "11");
			preparedStatement.setString(3, "22.22");
			preparedStatement.addBatch();
			preparedStatement.executeBatch();
			preparedStatement.clearBatch();
			preparedStatement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		sqlString = "select * from tt_i_rim_si";
//		try {
//			List list = dTool.executeQuery(sqlString);
//			for (int i = 0; i < list.size(); i++) {
//				Map map = (Map)list.get(i);
//				System.out.println(map.get("ROW_NUM"));
//			}
//			System.out.println();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
}
