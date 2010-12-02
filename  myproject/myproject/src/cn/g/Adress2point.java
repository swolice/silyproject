/**
 * 
 */
package cn.g;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.MessageFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.media.rtsp.protocol.Request;

/**
 * 
 * 名称： Adress2point 描述： 创建人： 吉仕军 创建时间： Oct 28, 2010 3:54:21 PM 修改人： 修改时间： 修改备注：
 * 
 * @version 1.0
 */
public class Adress2point {
	public static void main(String[] args) {
		try {
			getLatlng("北京市海淀区知春路甲48号");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 利用googlemap api 通过 HTTP 进行地址解析
	 * 
	 * @param address
	 *            地址
	 * @return HTTP状态代码,精确度（请参见精确度常数）,纬度,经度
	 * @throws IOException 
	 */
	private static String getLatlng(String address) throws IOException {
		String addr = URLEncoder.encode(address, "utf-8");
		URL url = new URL("http://maps.google.com/maps/geo?q=" + addr
				+ "&oe=utf-8&output=json&key=abcdefg");
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream();
		InputStreamReader reader = new InputStreamReader(is,"UTF-8");
		BufferedReader br = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		String str = br.readLine();
		while (null != str) {
			System.out.println(str);
			sb.append(str);
			str = br.readLine();
		}
		try {
			JSONObject jObject = new JSONObject(sb.toString());
			JSONArray Placemark = jObject.getJSONArray("Placemark");
			JSONObject jObject1 = Placemark.getJSONObject(0);
			JSONArray coordinates = jObject1.getJSONObject("Point").getJSONArray("coordinates");
			System.out.println(coordinates.get(0));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return "";
		
		
//		String ret = "";
//		if (address != null && !address.equals("")) {
//			try {
//				address = URLEncoder.encode(address, "UTF-8");// 进行这一步是为了避免乱码
//			} catch (UnsupportedEncodingException e1) {
//				e1.printStackTrace();
//				System.out.println("转码失败");
//			}
//			String[] arr = new String[4];
//			arr[0] = address;
//			arr[1] = "xml";
//			arr[2] = "true";
//			arr[3] = "KEY";
//			String url = MessageFormat.format("http://maps.google.com/maps/api/geocode/xml?geo?q={0}&output={1}&sensor={2}",arr);
//			URL urlmy = null;
//			try {
//				urlmy = new URL(url);
//				HttpURLConnection con = (HttpURLConnection) urlmy
//						.openConnection();
//				con.setFollowRedirects(true);
//				con.setInstanceFollowRedirects(false);
//				con.connect();
//				BufferedReader br = new BufferedReader(new InputStreamReader(
//						con.getInputStream(), "UTF-8"));
//				String s = "";
//				StringBuffer sb = new StringBuffer("");
//				while ((s = br.readLine()) != null) {
//					sb.append(s + "\r\n");
//				}
//				ret = "" + sb;
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//				//.error("通过http方式获取地址信息失败", e);
//			} catch (IOException e) {
//				e.printStackTrace();
//				//				logger.error("文件读取失败", e);
//			}
//		}
//		return ret;
	}

}
