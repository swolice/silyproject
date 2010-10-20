package logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.DbUtil;

import dms.data.City;
import dms.data.Comment;
import dms.data.Group;
import dms.data.Info;
import dms.data.IpAction;
import dms.data.Service;
import dms.data.SiteCity;
import dms.data.Sites;
import dms.data.Userinfo;

public class IndexLogic {
	/**
	 * 列表
	 * 
	 * @param offset
	 * @param size
	 * @return
	 */
	public List<Info> list(int city, String key, int category, int priceDegree,
			int discountDegree, String sort, int sortType, int offset, int size,String[] groups) {
		String sql = "select * from info t where t.end_time>now() ";

		if (priceDegree > 0) {
			switch (priceDegree) {
			case 1:
				sql += " and sell_price<1000 ";
				break;
			case 2:
				sql += " and sell_price<10000 and sell_price>=1000 ";
				break;
			case 3:
				sql += " and sell_price<20000  and sell_price>=10000 ";
				break;
			case 4:
				sql += " and sell_price<50000  and sell_price>=20000 ";
				break;
			case 5:
				sql += " and sell_price>=50000 ";
				break;
			default:
				break;
			}
		}

		if (discountDegree > 0) {
			switch (discountDegree) {
			case 1:
				sql += " and discount<100 ";
				break;
			case 2:
				sql += " and discount<300 and discount>=100 ";
				break;
			case 3:
				sql += " and discount<400  and discount>=300 ";
				break;
			case 4:
				sql += " and discount<500  and discount>=400 ";
				break;
			case 5:
				sql += " and discount>=500 ";
				break;
			default:
				break;
			}
		}

		List params = new ArrayList();
		if (key != null && key.trim().length() > 0) {
			sql += " and title like ? ";
			params.add("%" + key.trim() + "%");
		}

		if (category > -1) {
			sql += " and category=? ";
			params.add(category);
		}

		if (city > 0) {
			sql += " and city = ? ";
			params.add(city);
		}

		if (groups!=null&&groups.length>0){
			sql+=" and t.group1 in "+getArrayString(groups)+ " ";
		}
		
		if (sort != null && sort.length() > 0) {
			sql += " order by " + sort;
			if (sortType == 1) {
				sql += " desc ";
			}
		} else {
			sql += " order by id desc ";
		}

		List<Info> list = DbUtil.getDao().list(sql, params.toArray(), offset,
				size, new Info());

		return list;
	}

	public List<Info> list(int city, String key, int category, int priceDegree,
			int discountDegree, String sort, int sortType, int offset,
			int size, int mlat, int mlon, int nlat, int nlon,String[] groups) {
		String sql = "select * from info t where t.end_time>now() ";

		if (priceDegree > 0) {
			switch (priceDegree) {
			case 1:
				sql += " and sell_price<1000 ";
				break;
			case 2:
				sql += " and sell_price<10000 and sell_price>=1000 ";
				break;
			case 3:
				sql += " and sell_price<20000  and sell_price>=10000 ";
				break;
			case 4:
				sql += " and sell_price<50000  and sell_price>=20000 ";
				break;
			case 5:
				sql += " and sell_price>=50000 ";
				break;
			default:
				break;
			}
		}

		if (discountDegree > 0) {
			switch (discountDegree) {
			case 1:
				sql += " and discount<100 ";
				break;
			case 2:
				sql += " and discount<300 and discount>=100 ";
				break;
			case 3:
				sql += " and discount<400  and discount>=300 ";
				break;
			case 4:
				sql += " and discount<500  and discount>=400 ";
				break;
			case 5:
				sql += " and discount>=500 ";
				break;
			default:
				break;
			}
		}

		List params = new ArrayList();
		if (key != null && key.trim().length() > 0) {
			sql += " and title like ? ";
			params.add("%" + key.trim() + "%");
		}

		if (category > -1) {
			sql += " and category=? ";
			params.add(category);
		}

		if (city > 0) {
			sql += " and city = ? ";
			params.add(city);
		}

		if (mlat > 0 && mlon > 0 && nlat > 0 && nlon > 0) {
			sql += " and exists (select id from info_address t1 where t1.info_id=t.id and t1.lat>=? and t1.lon>=? and t1.lat<=? and t1.lon<=? ) ";
			params.add(nlat);
			params.add(nlon);
			params.add(mlat);
			params.add(mlon);
		}
		
		if (groups!=null&&groups.length>0){
			sql+=" and t.group1 in "+getArrayString(groups)+ " ";
		}

		if (sort != null && sort.length() > 0) {
			sql += " order by " + sort;
			if (sortType == 1) {
				sql += " desc ";
			}
		} else {
			sql += " order by id desc ";
		}

		List<Info> list = DbUtil.getDao().list(sql, params.toArray(), offset,
				size, new Info());

		return list;
	}

	public List<Info> listMap(int city, String key, int category, int priceDegree,
			int discountDegree, String sort, int sortType, int offset,
			int size, int mlat, int mlon, int nlat, int nlon,String[] groups) {
		String sql = "select * from info t where t.end_time>now() ";

		if (priceDegree > 0) {
			switch (priceDegree) {
			case 1:
				sql += " and sell_price<1000 ";
				break;
			case 2:
				sql += " and sell_price<10000 and sell_price>=1000 ";
				break;
			case 3:
				sql += " and sell_price<20000  and sell_price>=10000 ";
				break;
			case 4:
				sql += " and sell_price<50000  and sell_price>=20000 ";
				break;
			case 5:
				sql += " and sell_price>=50000 ";
				break;
			default:
				break;
			}
		}

		if (discountDegree > 0) {
			switch (discountDegree) {
			case 1:
				sql += " and discount<100 ";
				break;
			case 2:
				sql += " and discount<300 and discount>=100 ";
				break;
			case 3:
				sql += " and discount<400  and discount>=300 ";
				break;
			case 4:
				sql += " and discount<500  and discount>=400 ";
				break;
			case 5:
				sql += " and discount>=500 ";
				break;
			default:
				break;
			}
		}

		List params = new ArrayList();
		if (key != null && key.trim().length() > 0) {
			sql += " and title like ? ";
			params.add("%" + key.trim() + "%");
		}

		if (category > -1) {
			sql += " and category=? ";
			params.add(category);
		}

		if (city > 0) {
			sql += " and city = ? ";
			params.add(city);
		}

		if (mlat > 0 && mlon > 0 && nlat > 0 && nlon > 0) {
			sql += " and exists (select id from info_address t1 where t1.info_id=t.id and t1.lat>=? and t1.lon>=? and t1.lat<=? and t1.lon<=? ) ";
			params.add(nlat);
			params.add(nlon);
			params.add(mlat);
			params.add(mlon);
		}else{
			sql += " and exists (select t1.id from info_address t1 where t1.info_id=t.id ) ";
		}

		if (groups!=null&&groups.length>0){
			sql+=" and t.group1 in "+getArrayString(groups)+ " ";
		}
		
		if (sort != null && sort.length() > 0) {
			sql += " order by " + sort;
			if (sortType == 1) {
				sql += " desc ";
			}
		} else {
			sql += " order by id desc ";
		}

		List<Info> list = DbUtil.getDao().list(sql, params.toArray(), offset,
				size, new Info());

		return list;
	}
	
	/**
	 * 总数
	 * 
	 * @return
	 */
	public Integer count(int city, String key, int category, int priceDegree,
			int discountDegree, int mlat, int mlon, int nlat, int nlon,String[] groups) {
		String sql = "select * from info t where t.end_time>now() ";

		if (priceDegree > 0) {
			switch (priceDegree) {
			case 1:
				sql += " and sell_price<1000 ";
				break;
			case 2:
				sql += " and sell_price<10000 and sell_price>=1000 ";
				break;
			case 3:
				sql += " and sell_price<20000  and sell_price>=10000 ";
				break;
			case 4:
				sql += " and sell_price<50000  and sell_price>=20000 ";
				break;
			case 5:
				sql += " and sell_price>=50000 ";
				break;
			default:
				break;
			}
		}

		if (discountDegree > 0) {
			switch (discountDegree) {
			case 1:
				sql += " and discount<100 ";
				break;
			case 2:
				sql += " and discount<300 and discount>=100 ";
				break;
			case 3:
				sql += " and discount<400  and discount>=300 ";
				break;
			case 4:
				sql += " and discount<500  and discount>=400 ";
				break;
			case 5:
				sql += " and discount>=500 ";
				break;
			default:
				break;
			}
		}

		List params = new ArrayList();
		if (key != null && key.trim().length() > 0) {
			sql += " and title like ? ";
			params.add("%" + key.trim() + "%");
		}

		if (category > -1) {
			sql += " and category=? ";
			params.add(category);
		}

		if (city > 0) {
			sql += " and city = ? ";
			params.add(city);
		}

		if (mlat > 0 && mlon > 0 && nlat > 0 && nlon > 0) {
			sql += " and exists (select id from info_address t1 where t1.info_id=t.id and t1.lat>=? and t1.lon>=? and t1.lat<=? and t1.lon<=? ) ";
			params.add(nlat);
			params.add(nlon);
			params.add(mlat);
			params.add(mlon);
		}
		
		if (groups!=null&&groups.length>0){
			sql+=" and t.group1 in "+getArrayString(groups)+ " ";
		}

		return DbUtil.getDao().count(sql, params.toArray());
	}

	public List listGroup(Long city){
		String sql="select * from groups where city=?";
		
		List<Group> list=DbUtil.getDao().list(sql,new Object[]{city},-1,-1,new Group());
		return list;
	}
	
	
	public Integer countMap(int city, String key, int category, int priceDegree,
			int discountDegree,String[] groups) {
		String sql = "select * from info t where t.end_time>now() ";

		if (priceDegree > 0) {
			switch (priceDegree) {
			case 1:
				sql += " and sell_price<1000 ";
				break;
			case 2:
				sql += " and sell_price<10000 and sell_price>=1000 ";
				break;
			case 3:
				sql += " and sell_price<20000  and sell_price>=10000 ";
				break;
			case 4:
				sql += " and sell_price<50000  and sell_price>=20000 ";
				break;
			case 5:
				sql += " and sell_price>=50000 ";
				break;
			default:
				break;
			}
		}

		if (discountDegree > 0) {
			switch (discountDegree) {
			case 1:
				sql += " and discount<100 ";
				break;
			case 2:
				sql += " and discount<300 and discount>=100 ";
				break;
			case 3:
				sql += " and discount<400  and discount>=300 ";
				break;
			case 4:
				sql += " and discount<500  and discount>=400 ";
				break;
			case 5:
				sql += " and discount>=500 ";
				break;
			default:
				break;
			}
		}

		List params = new ArrayList();
		if (key != null && key.trim().length() > 0) {
			sql += " and title like ? ";
			params.add("%" + key.trim() + "%");
		}

		if (category > -1) {
			sql += " and category=? ";
			params.add(category);
		}

		if (city > 0) {
			sql += " and city = ? ";
			params.add(city);
		}

		
		sql += " and exists (select t1.id from info_address t1 where t1.info_id=t.id ) ";
		
		if (groups!=null&&groups.length>0){
			sql+=" and t.group1 in "+getArrayString(groups)+ " ";
		}
		// if (mlat>0&&mlon>0&&nlat>0&&nlon>0){
		// sql+=" and exists (select id from info_address t1 where
		// t1.info_id=t.id and t1.lat>=? and t1.lon>=? and t1.lat<=? and
		// t1.lon<=? ) ";
		// params.add(nlat);
		// params.add(nlon);
		// params.add(mlat);
		// params.add(mlon);
		// }

		return DbUtil.getDao().count(sql, params.toArray());
	}
	
	
	public Integer count(int city, String key, int category, int priceDegree,
			int discountDegree,String[] groups) {
		String sql = "select * from info t where t.end_time>now() ";

		if (priceDegree > 0) {
			switch (priceDegree) {
			case 1:
				sql += " and sell_price<1000 ";
				break;
			case 2:
				sql += " and sell_price<10000 and sell_price>=1000 ";
				break;
			case 3:
				sql += " and sell_price<20000  and sell_price>=10000 ";
				break;
			case 4:
				sql += " and sell_price<50000  and sell_price>=20000 ";
				break;
			case 5:
				sql += " and sell_price>=50000 ";
				break;
			default:
				break;
			}
		}

		if (discountDegree > 0) {
			switch (discountDegree) {
			case 1:
				sql += " and discount<100 ";
				break;
			case 2:
				sql += " and discount<300 and discount>=100 ";
				break;
			case 3:
				sql += " and discount<400  and discount>=300 ";
				break;
			case 4:
				sql += " and discount<500  and discount>=400 ";
				break;
			case 5:
				sql += " and discount>=500 ";
				break;
			default:
				break;
			}
		}

		List params = new ArrayList();
		if (key != null && key.trim().length() > 0) {
			sql += " and title like ? ";
			params.add("%" + key.trim() + "%");
		}

		if (category > -1) {
			sql += " and category=? ";
			params.add(category);
		}

		if (city > 0) {
			sql += " and city = ? ";
			params.add(city);
		}
		
		if (groups!=null&&groups.length>0){
			sql+=" and t.group1 in "+getArrayString(groups)+ " ";
		}
		

		// if (mlat>0&&mlon>0&&nlat>0&&nlon>0){
		// sql+=" and exists (select id from info_address t1 where
		// t1.info_id=t.id and t1.lat>=? and t1.lon>=? and t1.lat<=? and
		// t1.lon<=? ) ";
		// params.add(nlat);
		// params.add(nlon);
		// params.add(mlat);
		// params.add(mlon);
		// }

		return DbUtil.getDao().count(sql, params.toArray());
	}
	
	public String getArrayString(String[] ss){
		if (ss==null||ss.length<1){
			return "(-999)";
		}
		
		StringBuffer buf=new StringBuffer();
		buf.append("(-999,");
		
		for (String s:ss){
			if (s!=null&&s.length()>0){
				buf.append(s);
				buf.append(',');
			}
		}
		buf.deleteCharAt(buf.length()-1);
		buf.append(')');
		return buf.toString();
		
	}
	
	public Service getCityService(Long city,Long site){
		String sql="select * from site_city where city=? and site_id=? ";
		List<SiteCity> list=DbUtil.getDao().list(sql,new Object[]{city,site},0,1,new SiteCity());
		if (list!=null&&list.size()>0){
			SiteCity sc=list.get(0);
			
			if (sc.getService()!=null&&sc.getService()>0){
				return (Service)DbUtil.getDao().load(Service.class, sc.getService().longValue());
				
			}
			
		}
		
		
		return null;
		
	}
	
	
	public List listService(){
		String sql="select * from service order by id desc";
		List<Service> list=DbUtil.getDao().list(sql, null,-1,-1,new Service());
		return list;
	}

	public Info get(Long id) {
		try {
			Info info = (Info) DbUtil.getDao().load(Info.class, id);
			return info;
		} catch (Exception e) {
			return null;
		}
	}

	public Sites getSite(Long id) {
		try {
			Sites site = (Sites) DbUtil.getDao().load(Sites.class, id);
			return site;
		} catch (Exception e) {
			return null;
		}
	}
	
	public double getDouble(String sql,Object[] ps){
		List list=DbUtil.getDao().list(sql,ps,0,1);
		if (list!=null&&list.size()>0){
			Number n=(Number)list.get(0);
			return n.doubleValue();
		}
		return 0;
	}
	
	public double[] getStar(Long city,Long siteId){
		double cityStar=0,cityView=0,star=0,view=0;
		String sql1="select AVG((user_count-10*negative_count-moderate_count)*10/user_count) from info where city=? and site_id=?";
		cityStar=getDouble(sql1, new Object[]{city,siteId});
		String sql2="select AVG((user_count-10*negative_count)*10/user_count) from info where city=? and site_id=?";
		cityView=getDouble(sql2,new Object[]{city,siteId});
		
		String sql3="select AVG((user_count-10*negative_count-moderate_count)*10/user_count) from info where site_id=?";
		star=getDouble(sql3, new Object[]{siteId});
		String sql4="select AVG((user_count-10*negative_count)*10/user_count) from info where site_id=?";
		view=getDouble(sql4,new Object[]{siteId});
		
		return new double[]{cityStar,cityView,star,view};
	}

	public List listBySite(Long siteId, int offset, int size) {
		String sql = "select * from info t where site_id=? order by id desc ";
		List list = DbUtil.getDao().list(sql, new Object[] { siteId }, offset,
				size, new Info());

		return list;
	}

	public int countBySite(Long siteId) {
		String sql = "select * from info t where site_id=? ";
		return DbUtil.getDao().count(sql, new Object[] { siteId });
	}

	public Info promote(Long id, String ip) throws Exception {
		Info info = get(id);
		if (info == null) {
			throw new Exception("没有找到团购信息");
		}

		String sql = "select * from ip_action t where ip=? and action_type=1 and target_id=? ";
		List list = DbUtil.getDao().list(sql, new Object[] { ip, id }, 0, 1);

		if (list != null && list.size() > 0) {
			throw new Exception("对不起，您已经投票！");
		}

		if (info.getPromoteCount() != null) {
			info.setPromoteCount(info.getPromoteCount() + 1);
		} else {
			info.setPromoteCount(1);
		}

		DbUtil.getDao().update(info);

		IpAction ipa = new IpAction();
		ipa.setCreateTime(new Date());
		ipa.setActionType(1);
		ipa.setTargetId(id.intValue());
		ipa.setIp(ip);

		DbUtil.getDao().save(ipa);
		return info;
	}

	public Info click(Long id) throws Exception {
		Info info = get(id);
		if (info == null) {
			throw new Exception("没有找到团购信息");
		}
		if (info.getClickCount() != null) {
			info.setClickCount(info.getClickCount() + 1);
		} else {
			info.setClickCount(1);
		}

		DbUtil.getDao().update(info);
		return info;
	}

	public Info comment(Long id) throws Exception {
		Info info = get(id);
		if (info == null) {
			throw new Exception("没有找到团购信息");
		}
		if (info.getCommentCount() != null) {
			info.setCommentCount(info.getCommentCount() + 1);
		} else {
			info.setCommentCount(1);
		}

		DbUtil.getDao().update(info);
		return info;
	}

	public Comment comment(Userinfo user, String content, int type,int viewType, int id)
			throws Exception {
		if (user == null) {
			throw new Exception("请先登录后再评论！");
		}

		Comment c = new Comment();
		c.setCommentType(type);
		c.setContent(content);
		c.setCreateTime(new Date());
		c.setUserId(user.getId().intValue());
		c.setInfoId(id);
		c.setEditTime(new Date());
		c.setViewType(viewType);
		DbUtil.getDao().save(c);
		
		
		if (type==0){//团购评论
			Info info=(Info)DbUtil.getDao().load(Info.class,new Long( id));
			if (info!=null){
				info.setCommentCount(info.getCommentCount()+1);
				
				switch(viewType){
				case 0:info.setModerateCount(info.getModerateCount()+1);break;
				case 1:info.setNegativeCount(info.getNegativeCount()+1);break;
				case 2:info.setPositiveCount(info.getPositiveCount()+1);break;
				}
				
				
				DbUtil.getDao().update(info);
			}
			
		}else if (type==1){//网站评论
			Sites site=(Sites)DbUtil.getDao().load(Sites.class, new Long( id));
			if (site!=null){
				site.setCommentCount(site.getCommentCount()+1);
				switch(viewType){
				case 0:site.setModerateCount(site.getModerateCount()+1);break;
				case 1:site.setNegativeCount(site.getNegativeCount()+1);break;
				case 2:site.setPositiveCount(site.getPositiveCount()+1);break;
				}
				DbUtil.getDao().update(site);
			}
		}
		
		
		return c;
	}

	public String getUserName(Long id) {
		Userinfo info = (Userinfo) DbUtil.getDao().load(Userinfo.class, id);
		if (info != null)
			return info.getUsername();
		return "";
	}

	public List<Comment> listComment(int type, int id, int offset, int size) {
		String sql = "select * from comment t where t.info_id=? and t.comment_type=? order by create_time desc ";

		return DbUtil.getDao().list(sql, new Object[] { id, type }, offset,
				size, new Comment());
	}
	
	public Integer countComment(int type,int id){
		String sql = "select * from comment t where t.info_id=? and t.comment_type=? order by create_time desc ";
		return DbUtil.getDao().count(sql, new Object[] { id, type });
	}

	public List<City> listCity() {
		String sql = "select * from city order by sort_order ";
		List<City> list = DbUtil.getDao().list(sql, null, -1, -1, new City());
		return list;
	}

	public List<SiteCity> listSiteCity(int city) {
		String sql = "select * from site_city where city=? order by sort_order ";
		List<SiteCity> list = DbUtil.getDao().list(sql, new Object[] { city },
				-1, -1, new SiteCity());
		return list;
	}

	public List<Comment> listCommentByUser(int type, int id, int offset,
			int size) {
		String sql = "select * from comment t where t.user_id=? and t.comment_type=? order by create_time desc ";

		return DbUtil.getDao().list(sql, new Object[] { id, type }, offset,
				size, new Comment());
	}

	public List<Comment> newComment(int type, int offset, int size) {
		String sql = "select * from comment t where t.comment_type=? order by create_time desc ";

		return DbUtil.getDao().list(sql, new Object[] { type }, offset, size,
				new Comment());
	}

	public List<Info> topSale(int offset, int size) {
		String sql = "select * from info t where t.end_time>now() order by t.user_count desc";
		return DbUtil.getDao().list(sql, null, offset, size, new Info());
	}

	public List<Info> topClick(int offset, int size) {
		String sql = "select * from info t where t.end_time>now() order by t.click_count desc";
		return DbUtil.getDao().list(sql, null, offset, size, new Info());
	}

	public List newComment1(int type, int offset, int size) {
		if (type == 0) {// 团购评论
			String sql = "select {Info.*},{Comment.*} from info {Info} left join comment {Comment} on {Info}.id={Comment}.info_id and {Comment}.comment_type=? order by {Comment}.create_time desc ";

			List entryList = new ArrayList();
			entryList.add(new Object[] { "Info", Info.class });
			entryList.add(new Object[] { "Comment", Comment.class });

			List list = DbUtil.getDao().list1(sql, new Object[] { type },
					offset, size, entryList);
			return list;

		} else {// 网站评论
			String sql = "select {Sites.*},{Comment.*} from sites {Sites} left join comment {Comment} on {Sites}.id={Comment}.info_id and {Comment}.comment_type=? order by {Comment}.create_time desc ";

			List entryList = new ArrayList();
			entryList.add(new Object[] { "Sites", Sites.class });
			entryList.add(new Object[] { "Comment", Comment.class });

			List list = DbUtil.getDao().list1(sql, new Object[] { type },
					offset, size, entryList);
			return list;
		}
	}

	public List<Info> listTest(int offset, int size) {
		String sql = "select * from info t where t.if_test=0 and t.end_time>now() ";

		return DbUtil.getDao().list(sql, null, offset, size);
	}

	public List<Comment> listTestInfo(int offset, int size) {
		String sql = "select * from comment t where t.comment_type=0 and EXISTS (select id from info t1 where t1.id=t.info_id and t1.if_test=0) order by t.id desc";
		return DbUtil.getDao().list(sql, null, offset, size);
	}

	public List<Comment> listTestSites(int offset, int size) {
		String sql = "select * from comment t where t.comment_type=1 and EXISTS (select id from sites t1 where t1.id=t.info_id and exists (select id from info t2 where t2.site_id=t1.id and t2.if_test=0 )) order by t.id desc";
		return DbUtil.getDao().list(sql, null, offset, size);
	}

	public static void main(String[] args) {
		List list = new IndexLogic().newComment1(1, 0, 5);

		for (Object obj : list) {
			Object[] objs = (Object[]) obj;
			Sites info = (Sites) objs[0];
			Comment c = (Comment) objs[1];

			System.out.println(info.getSiteName());
			System.out.println(c.getContent());
			System.out.println("__________________");

		}

		// System.out.println(new IndexLogic().listComment(0, 15, 0,
		// 20).size());
	}

}
