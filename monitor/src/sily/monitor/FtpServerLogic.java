/*
 * BnetFtpServerReceiveLogic.java
 * 创建日期： 2009-9-15
 */
package sily.monitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import sily.util.MonitorResourceBundle;
import sily.util.PropertyWrapper;

/**
 * 处理ftp接收文件
 * @author 吉仕军
 */
public class FtpServerLogic{
	Logger syslogger = Logger.getLogger(FtpServerLogic.class.getName());
	
	public static void main(String[] args) {
		try {
			new FtpServerLogic().uploadMonitorImg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Description: 向FTP服务器上传文件
	 * @Version1.0 Jul 27, 2008 4:31:09 PM by 崔红保（cuihongbao@d-heaven.com）创建
	 * @param url FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param path FTP服务器保存目录
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input 输入流
	 */
	public void uploadMonitorImg(){
		FTPClient ftp = new FTPClient();
		try {
			
			PropertyWrapper   propWrapper = new PropertyWrapper();
			
			try{
				propWrapper.loadProperty(MonitorResourceBundle.getResourseFilePath("monitor.properties"));
			} catch(Exception ex) {
				syslogger.error(ex.getMessage(), ex);
			}
			//获取FTP服务器的IP地址
    		String ftpIp = propWrapper.getProperty("WEB_FTP_SERVER_IP");
    		if (isNullOrEmpty(ftpIp)) {
    			syslogger.error("FTP 服务器地址没有设置");
    		}
    		//获取FTP服务器用户
    		String user =  propWrapper.getProperty("WEB_FTP_SERVER_USER");
    		if (isNullOrEmpty(ftpIp)) {
    			syslogger.error("FTP 服务器用户没有设置");
    		}
    		//获取FTP服务器密码
    		String pass =  propWrapper.getProperty("WEB_FTP_SERVER_PASS");
    		if (isNullOrEmpty(ftpIp)) {
    			syslogger.error("FTP 服务器密码没有设置");
    		}
    		//获取FTP服务器端口
    		String port =  propWrapper.getProperty("WEB_FTP_SERVER_PORT");
    		if (isNullOrEmpty(ftpIp)) {
    			syslogger.error("FTP 服务器端口没有设置");
    		}
    		int intPort = Integer.parseInt(port);
			
			int reply;
			ftp.connect(ftpIp, intPort);//连接FTP服务器
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(user, pass);//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
			}
			//获取FTP上传的目录
    		String ftpServerUploadPath =  propWrapper.getProperty("FTP_UPLOAD_FILE_PATH");
    		ftp.changeWorkingDirectory(ftpServerUploadPath);
    		
    		DateFormat df = new SimpleDateFormat("yyyyMMdd");
    		String date = df.format(new Date());
    		
			boolean bb = ftp.changeWorkingDirectory(date);
			
			if (!bb) {// 如果不能进入dir下，说明此目录不存在！  
				
				boolean bb1 = ftp.makeDirectory(date);   

				if(!bb1){
					syslogger.error("创建文件目录【"+date+"】 失败！");
					ftp.changeWorkingDirectory(ftpServerUploadPath);
				}else{
					ftp.changeWorkingDirectory(date);
				}
            }   

			String localPath = propWrapper.getProperty("ftpSendFileDir");
       		
    		File fileUploadLocalPath = new File(localPath);
    		if (!fileUploadLocalPath.isDirectory()) {
    			return;
    		}
    		
    		File localFiles[] = fileUploadLocalPath.listFiles();
    		for( int i=0; i< localFiles.length; i++) {
    			syslogger.info("上传文件名： 【" + localFiles[i].getName() + "】开始");
    			InputStream input = new FileInputStream(localFiles[i]);
    			ftp.storeFile(localFiles[i].getName(), input);
    			input.close();
    			syslogger.info("上传文件名： 【" + localFiles[i].getName() + "】结束");
    			localFiles[i].delete();
    			
    			syslogger.info("删除已上传的： 【" + localFiles[i].getName() + "】结束");
    		}
			ftp.logout();
		} catch (IOException e) {
			syslogger.error("上传文件失败",e);
			try {
				new SendEmail().sendMail("上传文件失败");
			} catch (Exception e1) {
				syslogger.error("上传文件失败",e);
			}
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
	}
	
	
	/**
	 * Description: 从FTP服务器下载文件
	 * @Version1.0 Jul 27, 2008 5:32:36 PM by 崔红保（cuihongbao@d-heaven.com）创建
	 * @param url FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 * @return
	 * @throws Exception 
	 */
	public boolean downFile(String url, int port,String username, String password, String remotePath,String fileName,String localPath) throws Exception {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for(FTPFile ff:fs){
				if(ff.getName().equals(fileName)){
					File localFile = new File(localPath+"/"+ff.getName());
					
					OutputStream is = new FileOutputStream(localFile); 
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}
			
			ftp.logout();
			success = true;
		} catch (IOException e) {
			syslogger.error("下载文件失败",e);
			new SendEmail().sendMail("下载文件失败");
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	
	
	private boolean isNullOrEmpty(String param) {
		if (null == param) {
			return true;
		}
		if (param.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
}
