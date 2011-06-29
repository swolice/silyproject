package com.sily.publish;

/**
 * 
 * Company: 广州正道科技有限公司 
 * Title: 账务系统 
 * 类描述: 获取资源文件路径
 * @version 1.0
 * @since Mar 5, 2010 2:59:22 PM
 * @author 吉仕军
 */
public class PublishResourceBundle{

	/**
	 * 获取class路径下的文件绝对路径
	 * @param relationPath
	 * @return
	 */
	public static String getResourcesAbsolutePath(String relationPath) {
		//获取文件的路径
		String path = Thread.currentThread().getContextClassLoader().getResource(relationPath).getPath();
		//把路径中%20的空格替换成 " " 加上class路径下的文件路径
		String aba_path = path.replaceAll("%20"," ");
		
		return aba_path;
	}
	
	/**
	 * 获取应用的绝对路径 
	 * @param relationPath
	 * @return
	 */
	public static String getAppAbstractPath() {
		String tmpdir = Thread.currentThread().getContextClassLoader().getResource("common.properties").getPath(); 
		tmpdir = tmpdir.replaceAll("%20"," ").split("WEB-INF")[0];
		
		return tmpdir;
	}
	
	
	public String createTemp(){
		return Thread.currentThread().getContextClassLoader().getResource("/").getPath();
	}
}

