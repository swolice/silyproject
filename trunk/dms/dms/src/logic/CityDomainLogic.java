package logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import util.DbUtil;

import dms.data.SiteCity;
import form.CityDomainForm;

public class CityDomainLogic {
	/**
	 * 新增修改
	 * 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public SiteCity edit(CityDomainForm form) throws Exception {
		SiteCity sc;

		if (form.getId() == null || form.getId().intValue() < 1) {
			sc = new SiteCity();
			String sql = "select * from site_city where city=? and site_id=? ";
			List list = DbUtil.getDao().list(sql,
					new Object[] { form.getCity(), form.getSiteId() }, 0, 1,
					new SiteCity());
			if (list != null && list.size() > 0) {
				throw new Exception("已经存在相同站点城市！");
			}
		} else {
			sc = get(form.getId());
			if (sc == null) {
				throw new Exception("没有找到该信息！");
			}

			String sql = "select * from site_city where city=? and site_id=? and id!=?";
			List list = DbUtil.getDao().list(
					sql,
					new Object[] { form.getCity(), form.getSiteId(),
							form.getId() }, 0, 1, new SiteCity());
			if (list != null && list.size() > 0) {
				throw new Exception("已经存在相同站点城市！");
			}
		}

		// 复制属性
		try {
			BeanUtils.copyProperties(sc, form);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("网站创建过程中发生错误！");
		}

		Date now = new Date();
		if (form.getId() == null || form.getId().intValue() < 1) {
			sc.setCreateTime(now);
		}
		sc.setEditTime(now);

		if (form.getId() == null || form.getId().intValue() < 1) {
			DbUtil.getDao().save(sc);
		} else {
			DbUtil.getDao().update(sc);
		}
		return sc;
	}

	/**
	 * 通过ID获取
	 * 
	 * @param id
	 * @return
	 */
	public SiteCity get(Long id) {
		try {
			SiteCity city = (SiteCity) DbUtil.getDao().load(SiteCity.class, id);
			return city;
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
	public List list(int city, int siteId, int offset, int size) {
		String sql = "select * from site_city where 1=1 ";//

		List params = new ArrayList();
		if (city > 0) {
			sql += " and city=? ";
			params.add(city);
		}

		if (siteId > 0) {
			sql += " and site_id=? ";
			params.add(siteId);
		}

		sql += " order by id desc";

		List<SiteCity> list = DbUtil.getDao()
				.list(sql, params.toArray() , offset, size,
						new SiteCity());

		return list;
	}

	/**
	 * 总数
	 * 
	 * @return
	 */
	public Integer count(int city, int siteId) {
		String sql = "select id from site_city where 1=1 ";
		List params = new ArrayList();
		if (city > 0) {
			sql += " and city=? ";
			params.add(city);
		}

		if (siteId > 0) {
			sql += " and site_id=? ";
			params.add(siteId);
		}
		return DbUtil.getDao().count(sql, params.toArray());
	}

	public SiteCity delete(Long id) throws Exception {
		SiteCity rules = get(id);
		if (rules == null) {
			throw new Exception("没有找到城市");
		}
		DbUtil.getDao().delete(rules);
		return rules;
	}
	
	public static void main(String[] args) {
		
		CityDomainLogic logic=new CityDomainLogic();
		int c=logic.count(0,0);
		System.out.println(c);
		
	}

}
