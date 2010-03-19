package sily.util;

/**
 * @author yier
 *
 */
public  class MonitorResourceBundle {

	public static String getResourseFilePath(String path){
		String absPath = MonitorResourceBundle.class.getClassLoader().getResource("").getPath();
		absPath = absPath.replaceAll("%20", " ") + path;
		return absPath;
	}
}
