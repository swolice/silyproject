package util.google;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import util.google.GeocoderBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//import com.vincent.googlegeocoder.bean.GeocoderBean; 

public class GeoCoder {

	/**
	 * 未出现错误，已对地址成功地进行了解析，并返回其地址解析
	 */
	public static int G_GEO_SUCCESS = 200;
	/**
	 * 无法成功解析行车路线请求。例如，如果此请求包含的路标数大于允许的最大最大数，则该请求可能已被拒绝。
	 */
	public static int G_GEO_BAD_REQUEST = 400;
	/**
	 * 无法成功处理地址解析或行车路线请求，但是确切的失败原因未知。
	 */
	public static int G_GEO_SERVER_ERROR = 500;
	/**
	 * HTTP q 参数缺失或没有值。对于地址解析请求，这意味着将空地址指定为输入。对于行车路线请求，这意味着在输入中未指定查询。
	 */
	public static int G_GEO_MISSING_QUERY = 601;
	/**
	 * 找不到指定地址的对应地理位置。这可能是地址比较新，或地址不正确。
	 */
	public static int G_GEO_UNKNOWN_ADDRESS = 602;
	/**
	 * 由于合法性或合同原因，无法返回指定地址的地址解析或指定行车路线查询的路线。
	 */
	public static int G_GEO_UNAVAILABLE_ADDRESS = 603;
	/**
	 * Gdirections 对象无法计算查询中提到的两点之间的行车路线。这通常是因为两点之间无可用路线，或我们没有该地区的路线数据。
	 */
	public static int G_GEO_UNKNOWN_DIRECTIONS = 604;
	/**
	 * 指定的密钥无效或与指定的域不匹配。
	 */
	public static int G_GEO_BAD_KEY = 610;
	/**
	 * 指定的密钥超出了 24
	 * 小时的请求限制或在过短的一段时间内提交了过多的请求。如果您要同时或循环发送多个请求，请在代码中使用计时器或暂停以确保不会过快地发送请求。
	 */
	public static int G_GEO_TOO_MANY_QUERIES = 620;

	public GeoCoder(String country, String province, String road)
			throws IOException {
		this(country + province + road);
	}

	public GeoCoder(String address) throws IOException {
		this.address = address;
		this.mGeocoderBean = fromJson(getJson());
	}

	/**
	 * 获取状态码
	 * 
	 * @return
	 */
	public int getCode() {
		return mGeocoderBean.getStatus().getCode();
	}

	/**
	 * 返回你输入搜索的地址
	 * 
	 * @return
	 */
	public String getSearchAddr() {
		return mGeocoderBean.getName();
	}

	/**
	 * 返回获取到的地址数
	 * 
	 * @return
	 */
	public int getCount() {
		return mGeocoderBean.getPlacemark().size();
	}

	/**
	 * 返回查询得到的第一条数据的经纬度，结果是一个double数组， [0]=longitude [1]=latitude [2]=altitude
	 * 如果没有数据，则返回null
	 * 
	 * @return
	 */
	public double[] getFirstLatLng() {
		if (mGeocoderBean.getPlacemark().size() > 0) {
			return mGeocoderBean.getPlacemark().get(0).getPoint()
					.getCoordinates();
		} else {
			return null;
		}
	}

	private GeocoderBean fromJson(Reader reader) {
		Gson gson = new Gson();
		Type t = new TypeToken<GeocoderBean>() {
		}.getType();
		GeocoderBean mGeocoderBean = gson.fromJson(reader, t);
		return mGeocoderBean;
	}

	private Reader getJson() throws IOException {
		String addr = URLEncoder.encode(address, "utf-8");
		URL url = new URL("http://maps.google.com/maps/geo?q=" + addr
				+ "&oe=utf8&output=json&key=abcdefg");
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream();
		InputStreamReader reader = new InputStreamReader(is);
		return reader;
	}

	private String address;
	private GeocoderBean mGeocoderBean;
	
	public static double[] decode(String address){
		try {
			GeoCoder mGeocoder = new GeoCoder(address);
			double[] d=mGeocoder.mGeocoderBean.getPlacemark().get(0).getPoint().getCoordinates();
			return d;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			GeoCoder mGeocoder = new GeoCoder("北京市海淀区知春路甲48号");
			System.out.println(mGeocoder.getCount());
			double[] d=mGeocoder.mGeocoderBean.getPlacemark().get(0).getPoint().getCoordinates();
			System.out.println(d[0]);
			System.out.println(d[1]);
						
			// System.out.println(mGeocoder.mGeocoderBean.getStatus().getCount());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
