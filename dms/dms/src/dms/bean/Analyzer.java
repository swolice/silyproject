package dms.bean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import dms.data.Group;
import dms.data.Info;
import dms.data.InfoAddress;

//import util.DbUtil;
import util.DbUtil;
import util.HttpUtil;
import util.google.GeoCoder;

public class Analyzer {
	private int error = -99999999;

	private String rule;
	private Long domain;
	private Long city;
	private String charset;
	private String url1;
	private Long ruleId;

	private List<Info> infoList = new ArrayList<Info>();

	public Analyzer(String rule, Long domain, Long city, Long ruleId) {
		this.rule = rule;
		this.domain = domain;
		this.city = city;

		this.ruleId = ruleId;

	}

	public void setCommonInfo(Info info) {
		info.setCreateTime(new Date());
		info.setEditTime(new Date());
		info.setDeleteFlag(1);
	}

	public void analyze() {
		if (rule == null)
			return;
		try {
			InputStream in = new ByteArrayInputStream(rule.getBytes("UTF-8"));
			Document document = new SAXReader().read(in);

			Element root = document.getRootElement();

			String url = root.elementText("url");
			charset = root.elementText("charset");
			String city = root.elementText("city");

			if (getUrl1()!=null&&getUrl1().length()>0){
				url=getUrl1();
			}
			
			if (url == null || url.length() < 1)
				return;
			if (charset == null)
				charset = "utf-8";

			Info info = new Info();
			info.setUrl(url);
			url1 = url;
			try {
				info.setCity(Integer.parseInt(city));
			} catch (Exception e) {
				e.printStackTrace();
			}

			String html = HttpUtil.get(url, null, charset);

			if (html == null || html.length() < 1) {
				return;
			}

			process(root, html, info);

			save(info);

			print(info);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean save(Info info) {
		if (info.getTitle() != null && info.getEndTime() != null) {
			setCommonInfo(info);

			// DbUtil.getDao().save(info);
			infoList.add(info);
			return true;
		}
		infoList.add(info);
		return false;
	}

	public void saveDB() {
		for (Info info : infoList) {

			boolean exists = false;
			Info info1 = null;

			String sql = "select * from info t where t.url1=? ";
			List<Info> list1 = DbUtil.getDao().list(sql,
					new Object[] { info.getUrl1() }, 0, 1 ,new Info());
			if (list1 != null && list1.size() > 0) {
				exists = true;
				info1 = list1.get(0);
			} else {
				String sql2 = "select * from info t where t.title=? and t.url=? ";
				List<Info> list2 = DbUtil.getDao().list(sql2,
						new Object[] { info.getTitle(), info.getUrl() }, 0, 1,
						new Info());

				if (list2 != null && list2.size() > 0) {
					exists = true;
					info1 = list2.get(0);
				}
			}

			if (!exists) {// 数据库中不存在
				if (info.getTitle()==null||info.getTitle().length()<10){//团购标题为空，不保存
					return;
				}
				
				if (getDomain() != null) {
					info.setSiteId(getDomain().intValue());
				}
				info.setViewFlag(0);
				info.setIfTest(1);
				info.setClickCount(0);
				info.setPromoteCount(0);
				info.setCommentCount(0);
				if (info.getStartTime() == null) {
					info.setStartTime(new Date());
				}
				if (getRuleId() != null) {
					info.setRuleId(getRuleId().intValue());
				}
				info.setEndFlag(1);

				DbUtil.getDao().save(info);

				String xy = info.getLatlon();
				if (xy != null) {
					xy = xy.replaceAll("\\|", seperator);
					xy = xy.replaceAll("%7C", seperator);
					xy = xy.replaceAll("%7c", seperator);
					String[] xys = xy.split(seperator);
					String[] as = new String[xys.length];
					String[] ns = new String[xys.length];
					if (info.getAddress() != null) {
						as = info.getAddress().split(seperator);
					}
					if (info.getViewName() != null) {
						ns = info.getViewName().split(seperator);
					}

					for (int i = 0; i < xys.length; i++) {
						try {
							String[] lls = xys[i].split(",");
							String slon = lls[1];
							String slat = lls[0];
							InfoAddress ifAdd = new InfoAddress();
							ifAdd.setAddress(getString(as[i < as.length ? i
									: as.length - 1]));
							ifAdd.setViewName(getString(ns[i < ns.length ? i
									: ns.length - 1]));
							ifAdd.setCreateTime(new Date());
							ifAdd.setEditTime(new Date());
							ifAdd.setInfoId(info.getId().intValue());
							ifAdd
									.setLat((int) (Double.parseDouble(slat) * 100000));
							ifAdd
									.setLon((int) (Double.parseDouble(slon) * 100000));
							DbUtil.getDao().save(ifAdd);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else if (info.getAddress() != null) {
					String[] as = info.getAddress().split(seperator);

					for (String ad : as) {
						try {

							InfoAddress ifAdd = new InfoAddress();
							ifAdd.setAddress(getString(ad));
							ifAdd.setCreateTime(new Date());
							ifAdd.setEditTime(new Date());
							ifAdd.setInfoId(info.getId().intValue());
							try {
								double[] ll = GeoCoder.decode(info.getCity()
										+ " " + ad);
								if (ll != null && ll.length == 2) {
									ifAdd.setLat((int) (ll[1] * 100000));
									ifAdd.setLon((int) (ll[0] * 100000));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							DbUtil.getDao().save(ifAdd);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			} else {//数据库存在
				// if (list1 != null && list1.size() > 0) {
				// Info info1 = list1.get(0);
				
				if (info.getUserCount()!=null&&info.getUserCount()>0){
				  info1.setUserCount(info.getUserCount());
				}
				
				if (info.getTitle()!=null){
					info1.setTitle(info.getTitle());
				}
				if (info.getPicUrl()!=null){
					info1.setPicUrl(info.getPicUrl());
				}
				if (info.getViewName()!=null){
					info1.setViewName(info.getViewName());
				}
				
				if (info.getAddress()!=null){
					info1.setAddress(info.getAddress());
					info1.setGroup(getGroup(info1.getAddress(),info1.getCity()));
				}
				
				
				if (info1.getUrl1()==null||info1.getUrl1().length()<1){
					info1.setUrl1(info.getUrl1());
				}
				
				if (info1.getEndTime()==null||info1.getEndTime().getTime()<new Date().getTime()){
					info1.setEndFlag(0);
				}
				
				DbUtil.getDao().update(info1);
				// }
			}
		}
	}

	public void print(Info info) {
		System.out.println(info.getId());
		System.out.println(info.getTitle());
		System.out.println(info.getEndTime());
		System.out.println(info.getDiscount());
		System.out.println(info.getSellPrice());
		System.out.println(info.getOrgPrice());
		System.out.println(info.getUserCount());

	}

	public String process(Element e, String htmlString, Info info) {
		if (e.getName().equals("value")) {
			setValue(e, htmlString, info);
			return htmlString;
		} else if (e.getName().equals("seg")) {
			htmlString = seg(e, htmlString, info);
			return htmlString;
		} else if (e.getName().equals("spider")) {
			spider(e, htmlString, info);
			return htmlString;
		}

		Iterator it = e.elementIterator();
		String htmlString1 = htmlString;
		while (it.hasNext()) {
			Element e1 = (Element) it.next();
			htmlString1 = process(e1, htmlString1, info);
		}

		return htmlString1;
	}

	String urlSeperator = "#####";
	String keySeperator = "_____";

	public String spider(Element e, String htmlString, Info info) {
		String reg = e.elementText("reg");

		String ms = "";
		if (reg.indexOf(urlSeperator) > 0) {
			ms = reg.substring(reg.indexOf(urlSeperator)
					+ urlSeperator.length());
			reg = reg.substring(0, reg.indexOf(urlSeperator));
		}

		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(htmlString);

		if (m.find()) {
			String url = m.group(1);

			int k = 1;
			if (ms.length() > 0) {
				try {
					for (int j = 1;; j++) {
						if (ms.indexOf(keySeperator + j) > -1) {
							ms = ms
									.replaceFirst(keySeperator + j, m
											.group(k++));
						} else {
							break;
						}
					}
				} catch (Exception e1) {
				}
				url = ms;
			}

			if (e.attributeValue("new") != null
					&& e.attributeValue("new").equals("0")) {
				info = new Info();
				info.setUrl(url);
				String htmlString1 = HttpUtil.get(url, null, charset);

				Iterator it = e.elementIterator();
				String htmlString2 = htmlString1;
				while (it.hasNext()) {
					Element e1 = (Element) it.next();
					htmlString1 = process(e1, htmlString1, info);
				}

				save(info);
			} else {

				String htmlString1 = HttpUtil.get(url, null, charset);

				Iterator it = e.elementIterator();
				String htmlString2 = htmlString1;
				while (it.hasNext()) {
					Element e1 = (Element) it.next();
					htmlString1 = process(e1, htmlString1, info);
				}

			}

		}

		return htmlString;
	}

	public String seg(Element e, String htmlString, Info info) {
		if (e.attributeValue("loop") != null
				&& e.attributeValue("loop").equals("0")) {

			String htmlReturn = htmlString;
			// String htmlString3=htmlString;
			while (true) {

				String start = e.elementText("start");
				String end = e.elementText("end");

				int s = 0;
				if (start != null)
					s = htmlReturn.indexOf(start);
				if (s < 0)
					break;

				int e1 = htmlReturn.length();
				if (end != null)
					e1 = htmlReturn.indexOf(end, s);

				String htmlString1 = "";
				if (e1 < s)
					break;
				else {
					htmlString1 = htmlReturn.substring(s, e1);
					htmlReturn = htmlReturn.substring(e1);
				}

				Iterator it = e.elementIterator();

				if (e.attributeValue("new") != null
						&& e.attributeValue("new").equals("0")) {
					info = new Info();
					info.setUrl(url1);
				}

				String htmlString2 = htmlString1;

				while (it.hasNext()) {
					Element e2 = (Element) it.next();
					htmlString2 = process(e2, htmlString2, info);
				}

				if (e.attributeValue("new") != null
						&& e.attributeValue("new").equals("0")) {
					save(info);
				}

			}
			return htmlReturn;
		} else {
			String start = e.elementText("start");
			String end = e.elementText("end");

			int s = 0;
			if (start != null)
				s = htmlString.indexOf(start);
			if (s < 0)
				s = 0;

			int e1 = htmlString.length();
			if (end != null)
				e1 = htmlString.indexOf(end, s);
			if (e1 < s)
				e1 = htmlString.length();

			String htmlString1 = htmlString.substring(s, e1);
			Iterator it = e.elementIterator();

			if (e.attributeValue("new") != null
					&& e.attributeValue("new").equals("0")) {
				info = new Info();
			}

			String htmlString2 = htmlString1;
			while (it.hasNext()) {
				Element e2 = (Element) it.next();
				htmlString2 = process(e2, htmlString2, info);
			}

			if (e.attributeValue("new") != null
					&& e.attributeValue("new").equals("0")) {
				save(info);
			}
			return htmlString.substring(e1);
		}
	}

	// public String seg(Element e, String htmlString) {
	// String start = e.elementText("start");
	// String end = e.elementText("end");
	//
	// int s = 0;
	// if (start != null)
	// s = htmlString.indexOf(start);
	// if (s < 0)
	// s = 0;
	//
	// int e1 = htmlString.length();
	// if (end != null)
	// e1 = htmlString.indexOf(end, s);
	// if (e1 < s)
	// e1 = htmlString.length();
	//
	// String seg = htmlString.substring(s, e1);
	// return seg;
	// }

	public static String seperator = "###";
	private static String[] keywords1 = { "演出", "真人", "CS", "电影", "门票", "漂流",
			"兑换券", "瑜伽", "温泉", "漂流", "游乐园", "摄影", "桌游", "游泳", "击剑", "射箭", "溜冰",
			"培训", "海岸", "冲浪", "次卡", "年卡", "月卡" };
	private static String[] keywords2 = { "美容", "美体", "SPA", "按摩", "体检" };
	private static String[] keywords3 = { "蛋糕", "甜品", "麻辣", "自助餐", "餐厅", "火锅",
			"烤鱼", "套餐", "料理" };
	private static String[] keywords4 = { "包快递", "全国包邮" };
	public static String[] types = { "休闲娱乐", "美容健康", "餐饮美食", "精品团购" };

	public int getType(String title) {
		int pos1 = 99999, pos2 = 99999, pos3 = 99999, pos4 = 9999;
		for (String k : keywords1) {
			if (title.indexOf(k) > -1) {
				if (title.indexOf(k) < pos1)
					pos1 = title.indexOf(k);
			}
		}
		for (String k : keywords2) {
			if (title.indexOf(k) > -1) {
				if (title.indexOf(k) < pos2)
					pos2 = title.indexOf(k);
			}
		}
		for (String k : keywords3) {
			if (title.indexOf(k) > -1) {
				if (title.indexOf(k) < pos3)
					pos3 = title.indexOf(k);
			}
		}
		for (String k : keywords4) {
			if (title.indexOf(k) > -1) {
				if (title.indexOf(k) < pos4)
					pos4 = title.indexOf(k);
			}
		}

		return min(pos1, pos2, pos3, pos4);
	}

	public int min(int... p) {
		if (p[0] < p[1] && p[0] < p[2] && p[0] < p[3])
			return 0;
		if (p[1] < p[0] && p[1] < p[2] && p[1] < p[3])
			return 1;
		if (p[2] < p[0] && p[2] < p[1] && p[2] < p[3])
			return 2;
		return 3;
	}

	public void setValue(Info info, String name, String value) {

		if (name == null || name.length() < 1 || value == null)
			return;
		if (name.equalsIgnoreCase("start_Time")) {
			info.setStartTime(getDate(value));
		} else if (name.equalsIgnoreCase("end_Time")) {
			info.setEndTime(getDate(value));
		} else if (name.equalsIgnoreCase("org_Price")) {
			info.setOrgPrice(getDouble(value));
		} else if (name.equalsIgnoreCase("sell_Price")) {
			info.setSellPrice(getDouble(value));
		} else if (name.equalsIgnoreCase("discount")) {
			info.setDiscount(getDouble(value));
		} else if (name.equalsIgnoreCase("title")) {
			info.setTitle(getString(value));
			info.setCategory(getType(info.getTitle()));
		} else if (name.equalsIgnoreCase("detail")) {
			info.setDetail(getString(value));
		} else if (name.equalsIgnoreCase("address")) {
			if (info.getAddress() == null
					|| info.getAddress().trim().length() == 0) {
				info.setAddress(getString(value));
				info.setGroup(getGroup(info.getAddress(),info.getCity()));
			} else {
				info.setAddress(info.getAddress() + seperator
						+ getString(value));
				info.setGroup(getGroup(info.getAddress(),info.getCity()));
			}
		} else if (name.equalsIgnoreCase("user_Count")) {
			info.setUserCount(getInt(value));
		} else if (name.equalsIgnoreCase("phone")) {
			info.setPhone(getString(value));
		} else if (name.equalsIgnoreCase("pic_url")) {
			info.setPicUrl(getString(value));
		} else if (name.equalsIgnoreCase("web_Site")) {
			info.setWebSite(getString(value));
		} else if (name.equalsIgnoreCase("traffic_Info")) {
			info.setTrafficInfo(getString(value));
		} else if (name.equalsIgnoreCase("biefe")) {
			info.setBiefe(getString(value));
		} else if (name.equalsIgnoreCase("latlon")) {
			if (info.getLatlon() == null
					|| info.getLatlon().trim().length() < 1) {
				info.setLatlon(getString(value));
			} else {
				info.setLatlon(info.getLatlon() + seperator + getString(value));
			}
		} else if (name.equalsIgnoreCase("view_name")) {
			if (info.getViewName() == null
					|| info.getViewName().trim().length() < 1) {
				info.setViewName(getString(value));
			} else {
				info.setViewName(info.getViewName() + seperator
						+ getString(value));
			}
		} else if (name.equalsIgnoreCase("URL1")) {
			String u1=getString(value);
			info.setUrl1(getUrl(u1));
		}

	}
	
	public int getGroup(String address,int city){//根据城市和地址获得商圈
		try{
			String sql="select * from group t where city=? order by sort_order";
			List<Group> list=DbUtil.getDao().list(sql,new Object[]{city},-1,-1,new Group());
			
			if (list!=null)
			for (Group g:list){
				if (g.getKeyword()!=null&&g.getKeyword().length()>0){
					String[] ks=g.getKeyword().split(",");
					
					for (String s:ks){
						if (address.indexOf(s)>-1){
							return g.getId().intValue();
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public String getUrl(String s){
		if (s==null) return null;
		if (s.startsWith("http")) return s;
		if (s.startsWith("/")){
			String u=getUrl1();
			if (u.indexOf("/",8)>8){
			  u=u.substring(0,u.indexOf("/",8));
			}
			return u+s;
		}
		String u=getUrl1();
		if (u.endsWith("/")) return u+s;
		if (u.indexOf("/",8)<8) return u+"/"+s;
		u=u.substring(0,u.lastIndexOf("/"));
		return u+"/"+s;
	}

	public String getString(String s) {
		try {
			return s.replaceAll("[\n\f\r]", "");
			// return s.replaceAll("^\\W*|\\W*$", "");
		} catch (Exception e) {
			return s;
		}
	}

	public Date getDate(String s) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(s);

			if (Math.abs(d.getTime() - new Date().getTime()) / 1000 / 3600 / 24 < 100) {
				return d;
			}

		} catch (Exception e) {
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:mm:ss");
			Date d = sdf.parse(s);

			if (Math.abs(d.getTime() - new Date().getTime()) / 1000 / 3600 / 24 < 100) {
				return d;
			}

		} catch (Exception e) {

		}

		try {
			if (Long.parseLong(s) < new Date().getTime() - 1000 * 3600 * 24
					* 1000) {// 时间差 美团

				if (Long.parseLong(s) < 100) {// 爱帮团 剩余时间
					Calendar c = Calendar.getInstance();
					c.set(Calendar.HOUR_OF_DAY, 0);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);
					c.set(Calendar.MILLISECOND, 0);
					c.add(Calendar.DATE, Integer.parseInt(s) + 1);
					return c.getTime();
				}

				Calendar c = Calendar.getInstance();
				c.add(Calendar.SECOND, Integer.parseInt(s));
				return c.getTime();
			}

			Date d = new Date(Long.parseLong(s));
			return d;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return null;
	}

	public int getInt(String s) {
		try {
			return Integer.parseInt(getString(s));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return error;
	}

	public int getDouble(String s) {
		try {
			return (int) (Double.parseDouble(getString(s)) * 100);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return error;
	}

	public int getLatlon(String s) {
		try {
			return (int) (Double.parseDouble(getString(s)) * 100000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return error;
	}

	public void setValue(Element e, String htmlString, Info info) {
		// String seg = seg(e, htmlString);

		String names = e.elementText("name");
		if (names == null || names.length() < 1)
			return;

		String[] ns = names.split(",");

		String reg = e.elementText("reg");
		if (reg == null || reg.length() < 1) {
			setValue(info, ns[0], htmlString);
			return;
		}

		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(htmlString);

		if (m.find()) {
			for (int i = 1; i <= m.groupCount(); i++) {
				if (i - 1 < ns.length) {
					setValue(info, ns[i - 1], m.group(i));
				} else {
					break;
				}
			}
		}
	}

	/**
	 * @return the rule
	 */
	public String getRule() {
		return rule;
	}

	/**
	 * @param rule
	 *            the rule to set
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}

	/**
	 * @return the domain
	 */
	public Long getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(Long domain) {
		this.domain = domain;
	}

	/**
	 * @return the city
	 */
	public Long getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(Long city) {
		this.city = city;
	}

	/**
	 * @return the infoList
	 */
	public List<Info> getInfoList() {
		return infoList;
	}

	/**
	 * @param infoList
	 *            the infoList to set
	 */
	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}

	/**
	 * @return the url1
	 */
	public String getUrl1() {
		return url1;
	}

	/**
	 * @param url1
	 *            the url1 to set
	 */
	public void setUrl1(String url1) {
		this.url1 = url1;
	}

	/**
	 * @return the ruleId
	 */
	public Long getRuleId() {
		return ruleId;
	}

	/**
	 * @param ruleId
	 *            the ruleId to set
	 */
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

}
