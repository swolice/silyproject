package com.sily.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sily.publish.PublishResourceBundle;

public class ImportDBByFile {

	private static Logger logger = Logger.getLogger("publish");
	
	public void process() {
		
		String config = PublishResourceBundle.getResourcesAbsolutePath("file.properties");
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(config));
		} catch (FileNotFoundException e) {
			Logger.getLogger("publish").error(e.getMessage(),e);
		} catch (IOException e) {
			Logger.getLogger("publish").error(e.getMessage(),e);
		}
		try {
			String directory = prop.getProperty("tianya_d");
			File d = new File(directory);
			File files[] = d.listFiles();
			for (int i = 0; i < files.length; i++) {
				FileInputStream fis = new FileInputStream(files[i]);
				InputStreamReader isr = new InputStreamReader(fis, "GBK");
				
				BufferedReader br = new BufferedReader(isr);
				
				DatabaseTool dt = DatabaseTool.getInstance();
				Connection conn = dt.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement ps = conn.prepareStatement("insert into account(user_name,password,email) values(?,?,?)");
				int len = 0;
				String oneline = "";
				while((oneline = br.readLine()) != null){
					String ols[] = oneline.split("\\s+");
					if(ols.length > 2){
						len++;
						ps.setString(1, ols[0]);
						ps.setString(2, ols[1]);
						ps.setString(3, ols[2]);
						ps.addBatch();
					}
					if(len == 1000){
						logger.info("执行导入数据："  + files[i] + System.currentTimeMillis());
						ps.executeBatch();
						ps.clearBatch();
						len = 0;
					}
				}
				if(len>0){
					logger.info("执行导入数据：" + files[i] + System.currentTimeMillis());
					ps.executeBatch();
					ps.clearBatch();
					ps.close();
				}
				conn.commit();
				dt.freeConnection(conn);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
