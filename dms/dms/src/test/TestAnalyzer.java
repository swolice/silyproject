package test;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dms.bean.Analyzer;

public class TestAnalyzer {

	public static void main(String args[]) {
// String fileName = "conf/nuomi.xml";
// Analyzer a = new Analyzer(fileName);
//
// a.analyze();
//
// HashMap map = a.getMap();
//
// System.out.println(map.entrySet().toString().replaceAll(",", "\n"));

//		String xml="<xml><seg new=\"1\" loop=\"1\" ID=\"i100901182704\"><spider ID=\"i100901182946\" new=\"1\"><value ID=\"i100901183019\"><name><![CDATA[USER_COUNT]]></name><reg><![CDATA[([\\d\\D]+)]]></reg></value><reg><![CDATA[get\\(\"(.*)\",\\{id:(\\d+)\\}#####http://tuan.fantong.com$$$$$1?id=$$$$$2]]></reg></spider><start><![CDATA[get(\"/team/buy_num\"]]></start><end><![CDATA[function(data){]]></end></seg><seg new=\"1\" loop=\"1\" ID=\"i100901163731\"><value ID=\"i100901163803\"><name><![CDATA[TITLE]]></name><reg><![CDATA[<h1>(.*)</h1>]]></reg></value><value ID=\"i100901163927\"><name><![CDATA[SELL_PRICE]]></name><reg><![CDATA[<p class=\"deal-price\"><strong>(.*)</strong>]]></reg></value><seg new=\"1\" loop=\"1\" ID=\"i100901163830\"><value ID=\"i100901163857\"><name><![CDATA[ORG_PRICE]]></name><reg><![CDATA[<td>(.*)</td>]]></reg></value><value ID=\"i100901163957\"><name><![CDATA[DISCOUNT]]></name><reg><![CDATA[<td>(.*)折</td>]]></reg></value><start><![CDATA[<th>节省</th>]]></start><end><![CDATA[</table>]]></end></seg><value ID=\"i100901164311\"><name><![CDATA[END_TIME]]></name><reg><![CDATA[diff=\"(\\d+)000\">]]></reg></value><start><![CDATA[<div id=\"content\">]]></start><end><![CDATA[<div id=\"deal-stuff\" class=\"cf\">]]></end></seg><domain ID=\"domain\">11</domain><city ID=\"city\">2</city><url ID=\"url\">http://tuan.fantong.com/beijing</url><charset ID=\"charset\">utf-8</charset></xml>";
//		
//		Analyzer analyzer=new Analyzer(xml,2l,1l);
//		analyzer.analyze();
		
		String xml=	"<xml><seg new=\"1\" loop=\"1\" ID=\"i100901210107\"><value ID=\"i100901210131\"><name><![CDATA[TITLE]]></name><reg><![CDATA[今日团购：(.*)</h1>]]></reg></value><value ID=\"i100901210203\"><name><![CDATA[SELL_PRICE]]></name><reg><![CDATA[<span id=\"price\">￥(.*)</span>]]></reg></value><seg new=\"1\" loop=\"1\" ID=\"i100901210234\"><value ID=\"i100901210249\"><name><![CDATA[ORG_PRICE]]></name><reg><![CDATA[<td>￥(.*)</td>]]></reg></value><value ID=\"i100901210547\"><name><![CDATA[DISCOUNT]]></name><reg><![CDATA[<td>(.*)折</td>]]></reg></value><start><![CDATA[<tr class=\"zhe\">]]></start><end><![CDATA[</tr>]]></end></seg><seg new=\"1\" loop=\"1\" ID=\"i100901212203\"><value ID=\"i100901212230\"><name><![CDATA[END_TIME]]></name><reg><![CDATA[parse\\('(.*)']]></reg></value><start><![CDATA[Date.parse(]]></start><end><![CDATA[.replace]]></end></seg><start><![CDATA[今日团购：]]></start><end><![CDATA[<div class=\"dealdetail\">]]></end></seg><domain ID=\"domain\">15</domain><city ID=\"city\">2</city><url ID=\"url\">http://t.58.com/bj</url><charset ID=\"charset\">utf-8</charset></xml>";
		
		Analyzer analyzer=new Analyzer(xml,2l,1l,1l);
		analyzer.analyze();
		
		
//String reg = "get\\(\"(.*)\",\\{id:(\\d+)\\}#####http://tuan.fantong.com_____1?id=_____2";
//		
//
//reg="<xml><seg new=\"1\" loop=\"1\" ID=\"i100901210107\"><value ID=\"i100901210131\"><name><![CDATA[TITLE]]></name><reg><![CDATA[今日团购：(.*)</h1>]]></reg></value><value ID=\"i100901210203\"><name><![CDATA[SELL_PRICE]]></name><reg><![CDATA[<span id=\"price\">￥(.*)</span>]]></reg></value><seg new=\"1\" loop=\"1\" ID=\"i100901210234\"><value ID=\"i100901210249\"><name><![CDATA[ORG_PRICE]]></name><reg><![CDATA[<td>￥(.*)</td>]]></reg></value><value ID=\"i100901210547\"><name><![CDATA[DISCOUNT]]></name><reg><![CDATA[<td>(.*)折</td>]]></reg></value><start><![CDATA[<tr class=\"zhe\">]]></start><end><![CDATA[</tr>]]></end></seg><seg new=\"1\" loop=\"1\" ID=\"i100901212203\"><value ID=\"i100901212230\"><name><![CDATA[END_TIME]]></name><reg><![CDATA[([0-9\\-: ]+)]]></reg></value><start><![CDATA[Date.parse(]]></start><end><![CDATA[.replace]]></end></seg><start><![CDATA[今日团购：]]></start><end><![CDATA[<div class=\"dealdetail\">]]></end></seg><domain ID=\"domain\">15</domain><city ID=\"city\">2</city><url ID=\"url\">http://t.58.com/bj</url><charset ID=\"charset\">utf-8</charset></xml>";
//		String ms="";
//		if (reg.indexOf("#####")>0){
//			ms=reg.substring(reg.indexOf("#####")+"#####".length());
//			reg=reg.substring(0,reg.indexOf("#####"));
//		}
//		
//		Pattern p = Pattern.compile(reg);
//		Matcher m = p.matcher("get(\"/team/buy_num\",{id:204}");
//
//		if (m.find()) {
//			String url = m.group(1);
//			
//			System.out.println("url : "+url);
//			System.out.println("ms : "+ms);
//			
//			int k = 1;
//			if (ms.length() > 0) {
//				try {
//					for (int j = 1;; j++) {
//						System.out.println("-------- : "+ms.indexOf("_____" + j));
//						if (ms.indexOf("_____" + j) > -1) {
//							ms = ms.replaceFirst("_____" + j, m.group(k++));
//							System.out.println("j "+j+ " : "+ms);
//						} else {
//							break;
//						}
//					}
//				} catch (Exception e1) {
//				}
//				url = ms;
//			}
//			
//			System.out.println("url : "+url);
//		
//		}
		
		
	}
}
