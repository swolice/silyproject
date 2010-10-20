package logic;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import util.DbUtil;
import dms.data.City;
import dms.data.Rules;
import dms.data.Sites;
import form.CityForm;
import form.SiteForm;

public class CityLogic {
	/**
	 * 新增修改
	 * 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public City edit(CityForm form) throws Exception {
		City city;

		if (form.getId() == null || form.getId().intValue() < 1) {
			city = new City();
			String sql = "select * from city where city_name=?";
			List list = DbUtil.getDao().list(sql,
					new Object[] { form.getCityName() }, 0, 1, new City());
			if (list != null && list.size() > 0) {
				throw new Exception("已经存在相同名称！");
			}
		} else {
			city = get(form.getId());
			if (city == null) {
				throw new Exception("没有找到该信息！");
			}

			String sql = "select * from city where city_name=? and id!=?";
			List list = DbUtil.getDao().list(sql,
					new Object[] { form.getCityName(), form.getId() }, 0, 1,
					new City());
			if (list != null && list.size() > 0) {
				throw new Exception("已经存在相同名称！");
			}
		}

		// 复制属性
		try {
			BeanUtils.copyProperties(city, form);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("网站创建过程中发生错误！");
		}

		Date now = new Date();
		if (form.getId() == null || form.getId().intValue() < 1) {
			city.setCreateTime(now);
		}
		city.setEditTime(now);

		if (form.getId() == null || form.getId().intValue() < 1) {
			DbUtil.getDao().save(city);
		} else {
			DbUtil.getDao().update(city);
		}
		return city;
	}

	/**
	 * 通过ID获取
	 * 
	 * @param id
	 * @return
	 */
	public City get(Long id) {
		try {
			City city = (City) DbUtil.getDao().load(City.class, id);
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
	public List list(int offset, int size) {
		String sql = "select * from city order by id desc";

		List<City> list = DbUtil.getDao().list(sql, null, offset, size,
				new City());

		return list;
	}

	/**
	 * 总数
	 * 
	 * @return
	 */
	public Integer count() {
		String sql = "select id from city ";

		return DbUtil.getDao().count(sql, null);
	}

	public City delete(Long id) throws Exception {
		City rules = get(id);
		if (rules == null) {
			throw new Exception("没有找到城市");
		}
		DbUtil.getDao().delete(rules);
		return rules;
	}

}
