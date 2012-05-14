package com.swjsj.silysae;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sina.sae.fetchurl.SaeFetchurl;
import com.sina.sae.mail.SaeMail;

public class XmlReaderByJsoup {

	public static void main(String[] args) {
		// File file = new File("D:\\我的桌面\\tq.xml");
		// FileUtils fu = new FileUtils();
		// String xml = null;
		// try {
		// xml = fu.readFileToString(file,"GBK");
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// String tq = getWeather(xml);
		// System.out.println(tq);
		//

		try {
			String cs = java.net.URLEncoder.encode("长沙", "gb2312");
//
			System.out.println(cs);
			
			sendMail("醴陵");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sendMail(String cityname) {
		String tq = getTQ(cityname, 0);
		String tq1 = getTQ(cityname, 1);
		String tq2 = getTQ(cityname, 2);
		String tianqi = "天气：" + tq;
		tianqi += tq1;
		tianqi += tq2;

		String subject = tianqi;
		byte[] bs = tianqi.getBytes();
//		byte[] newbs = new byte[255];
//		if(bs.length > 256){
////			for (int i = 0; i < 255; i++) {
//				System.arraycopy(bs, 0, newbs, 0, 255);
////				newbs[i] = bs[i];
////			}
//			try {
//				subject = new String(newbs,"gb2312");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
			
		if(bs.length > 256){
			subject = tianqi.substring(0,50);
		}
		SaeMail mail = new SaeMail();
		mail.setSmtpHost("smtp.163.com");
		// 快速发送邮件
//		mail.quickSend("jtcrm_email@163.com", new String[] { "16009413@qq.com" }, tianqi,
//				tianqi, "jtcrm_email@163.com", "jtcrm123");
		mail.clean();// 重用此对象 再次发送
		mail.setFrom("jtcrm_email@163.com");
		mail.setSmtpUsername("jtcrm_email@163.com");
		mail.setSmtpPassword("jtcrm123");
		mail.setCc(new String[] { "13401075137@139.com" });// 抄送地址
		mail.setTo(new String[] { "16009413@qq.com" });// 接收地址
		mail.setSubject(tq);
		mail.setContentType("HTML");// 邮件内容形式可选HTML/TEXT
		mail.setContent(tianqi);
//		mail.setAttach(new String[] { "test.txt" });// 设置附件
//		mail.setCallbackUrl("callbackurl");// 设置发送失败后回调的url
		boolean isOk = mail.send();// 发送邮件
		// 失败输出错误码和错误信息
		if (!isOk) {
			System.out.println(mail.getErrno());
			System.out.println(mail.getErrmsg());
		}
	}

	private static String getTQ(String cityname, int day) {
		try {
			cityname = java.net.URLEncoder.encode(cityname, "gb2312");
			String url = "http://php.weather.sina.com.cn/xml.php?city="
					+ cityname + "&password=DJOYnieT8234jlsK&day=" + day;
			SaeFetchurl fetchUrl = new SaeFetchurl();
			String content = fetchUrl.fetch(url);
			content = new String(content.getBytes("ISO-8859-1"), "UTF-8");
			return XmlReaderByJsoup.getWeather(content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String threeDayWeather(String cityname) {
		String tq = getTQ(cityname, 0);
		String tq1 = getTQ(cityname, 1);
		String tq2 = getTQ(cityname, 2);
		String tianqi = "<br><br><br><br><br><br><div  align=\"left\"><li>今天天气："
				+ tq + "</li>";
		tianqi += "<li>明天天气：" + tq1 + "</li>";
		tianqi += "<li>后天天气：" + tq2 + "</li></div>";
		return tianqi;
	}

	public static String getWeather(String xml) {
		StringBuffer sb = new StringBuffer();
		Document doc;
		try {
			doc = Jsoup.parse(xml);
			Elements es = doc.getElementsByTag("Weather");
			Iterator<Element> it = es.iterator();
			while (it.hasNext()) {
				Element e = it.next();
				Elements ces = e.children();
				if (null != ces) {
					String status1 = ces.select("status1").text();
					String direction1 = ces.select("direction1").text();
					String power1 = ces.select("power1").text();
					String temperature1 = ces.select("temperature1").text();
					sb.append("白天：").append(status1).append(" 风向：")
							.append(direction1).append(power1).append("级 气温：")
							.append(temperature1).append("℃");
					String status2 = ces.select("status2").text();
					String direction2 = ces.select("direction2").text();
					String power2 = ces.select("power2").text();
					String temperature2 = ces.select("temperature2").text();
					sb.append("夜间：").append(status2).append(" 风向：")
							.append(direction2).append(power2).append("级 气温：")
							.append(temperature2).append("℃");
				}
			}
		} catch (Exception e) {
			Logger.getLogger(XmlReaderByJsoup.class).error(e.getMessage(), e);
		}
		return sb.toString();
	}
}