package logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import util.DbUtil;
import dms.data.Comment;
import dms.data.Info;
import dms.data.Userinfo;
import form.InfoForm;

public class InfoLogic {

	public Date parseDate(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			return sdf.parse(time.replaceAll("\\D", ""));
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	public Date parseDate1(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			return sdf.parse(time.replaceAll("\\D", ""));
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * 新增修改
	 * 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Info edit(InfoForm form) throws Exception {
		Info info;

		if (form.getId() == null || form.getId().intValue() < 1) {
			info = new Info();
			info.setClickCount(0);
			info.setPromoteCount(0);
			info.setCommentCount(0);
		} else {
			info = get(form.getId());
			if (info == null) {
				throw new Exception("没有找到该团购信息！");
			}
		}

		// 复制属性
		try {
			BeanUtils.copyProperties(info, form);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("网站创建过程中发生错误！");
		}

		Date now = new Date();
		if (form.getId() == null || form.getId().intValue() < 1) {
			info.setCreateTime(now);
		}
		info.setEditTime(now);

		Date sd = parseDate(form.getStime());
		if (sd != null)
			info.setStartTime(sd);
		Date ed = parseDate(form.getEtime());
		if (ed != null)
			info.setEndTime(ed);

		if (form.getId() == null || form.getId().intValue() < 1) {
			DbUtil.getDao().save(info);
		} else {
			DbUtil.getDao().update(info);
		}
		return info;
	}

	/**
	 * 通过ID获取
	 * 
	 * @param id
	 * @return
	 */
	public Info get(Long id) {
		try {
			Info info = (Info) DbUtil.getDao().load(Info.class, id);
			return info;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 列表
	 * 
	 * @param offset
	 * @param size
	 * @return
	 */
	public List list(String stime, String etime, int city, int domain,
			int offset, int size) {
		List params = new ArrayList();

		String sql = " select * from info where 1=1 ";

		Date sd = parseDate1(stime);
		if (sd != null) {
			sql += " and start_time>=? ";
			params.add(sd);
		}

		Date ed = parseDate1(etime);
		if (ed != null) {
			sql += " and end_time<= ? ";
			params.add(ed);
		}

		if (city > 0) {
			sql += " and city=? ";
			params.add(city);
		}
		if (domain > 0) {
			sql += " and site_id=? ";
			params.add(domain);
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
	public Integer count(String stime, String etime, int city, int domain) {
		List params = new ArrayList();

		String sql = " select * from info where 1=1 ";

		Date sd = parseDate1(stime);
		if (sd != null) {
			sql += " and start_time>=? ";
			params.add(sd);
		}

		Date ed = parseDate1(etime);
		if (ed != null) {
			sql += " and end_time<= ? ";
			params.add(ed);
		}

		if (city > 0) {
			sql += " and city=? ";
			params.add(city);
		}
		if (domain > 0) {
			sql += " and site_id=? ";
			params.add(domain);
		}
		return DbUtil.getDao().count(sql, params.toArray());
	}

	// /**
	// * 修改单个属性
	// *
	// * @param id
	// * @param value
	// * @return
	// * @throws Exception
	// */
	// public String edit(String sid, String value) throws Exception {
	// String[] ss = sid.split("_");
	// if (ss.length != 2)
	// throw new Exception("参数不够！");
	// String type = ss[0];
	// Long id = Long.parseLong(ss[1]);
	//
	// EmsGoodsType cat = get(id);
	//
	// if (type.equals("typeName")) {
	// if (value.length()<1){
	// throw new Exception("商品类型名称不能为空！");
	// }
	//			
	// String sql="select id from ems_goods_type t where t.type_name=? and
	// t.id!=?";
	// List list=DbUtil.getDao().list(sql, new Object[]{value,id},0, 1);
	// if (list!=null&&list.size()>0){
	// throw new Exception("已经存在相同的商品类型名称！");
	// }
	//			
	// cat.setTypeName(value);
	// value = "" + cat.getTypeName();
	// } else if (type.equals("attrGroup")) {
	// cat.setAttrGroup(value.replaceAll("\n", ","));
	// value = "" + cat.getAttrGroup();
	// }
	//
	// DbUtil.getDao().update(cat);
	// return value;
	// }
	//
	public Info delete(Long id) throws Exception {
		Info site = get(id);
		if (site == null) {
			throw new Exception("没有找到团购信息");
		}
		DbUtil.getDao().delete(site);
		return site;
	}



}
