package util;

import javax.servlet.http.HttpServletRequest;
import comm.page.Page;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLDecoder;

import org.json.JSONObject;
import java.util.Collection;
import org.json.JSONArray;

public class BeanUtil {
	public BeanUtil() {
	}

	private static int pageSize = 10;

	public static Long getLong(HttpServletRequest req, String name,
			Long defaultValue) {
		if (req == null || name == null)
			return defaultValue;
		try {
			return Long.parseLong(req.getParameter(name));
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	public static Integer getInt(HttpServletRequest req, String name,
			Integer defaultValue) {
		if (req == null || name == null)
			return defaultValue;
		try {
			return Integer.parseInt(req.getParameter(name));
		} catch (Exception ex) {
			return defaultValue;
		}
	}
	
	public static Double getDouble(HttpServletRequest req, String name,
			Double defaultValue) {
		if (req == null || name == null)
			return defaultValue;
		try {
			return Double.parseDouble(req.getParameter(name));
		} catch (Exception ex) {
			return defaultValue;
		}
	}
	
	public static String getString(HttpServletRequest req, String name) {
		if (req == null || name == null)
			return null;
		try {
			String s=req.getParameter(name);
			System.out.println("s :"+s);
			if (s==null) return null;
			s=s.trim();
			if (s.length()==0) return s;
//			s=URLDecoder.decode(s,"UTF-8");
			int i=0;
			for (;i<s.length();i++){
				if (s.charAt(i)==' '){
					continue;
				}else{
					break;
				}
			}
			
			System.out.println("s1 : "+s.substring(i).trim());
			return s.substring(i).trim();
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static String getUtfString(HttpServletRequest req, String name) {
		if (req == null || name == null)
			return null;
		try {
			String s=req.getParameter(name);
			if (s==null) return null;
			s=s.trim();
			if (s.length()==0) return s;
			s=URLDecoder.decode(s,"UTF-8");
			int i=0;
			for (;i<s.length();i++){
				if (s.charAt(i)==' '){
					continue;
				}else{
					break;
				}
			}
			
			System.out.println("s1 : "+s.substring(i).trim());
			return s.substring(i).trim();
		} catch (Exception ex) {
			return null;
		}
	}
	

	public static Page getPage(HttpServletRequest request, int count) {
		int curPage = BeanUtil.getInt(request, "pageNo", 1);
		return new Page(curPage, pageSize, count);
	}

	public static boolean writeJson(HttpServletResponse response, Object obj) {
		try {
			response.setContentType("application/x-javascript; charset=GBK");
			PrintWriter writer = response.getWriter();
			if (obj instanceof Collection) {
				writer.write(new JSONArray((Collection) obj).toString());
			} else {
				writer.write(new JSONObject(obj).toString());
			}
			writer.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean writeString(HttpServletResponse response, String s) {
		try {
			response.setContentType("application/x-javascript; charset=GBK");
			PrintWriter writer = response.getWriter();
			writer.write(s);
			writer.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean writeError(HttpServletResponse response, String s) {
		try {
			response.setContentType("text/html; charset=GBK");
			response.setStatus(500);
			PrintWriter writer = response.getWriter();
			writer.write(s);
			writer.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
