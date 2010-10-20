package logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.DbUtil;
import dms.data.Userinfo;

public class UserLogic {

	public Userinfo get(Long id) {
		try {
			Userinfo city = (Userinfo) DbUtil.getDao().load(Userinfo.class, id);
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
	public List list(String username, String email, Date startTime,
			Date endTime, int offset, int size) {
		String sql = "select * from userinfo where 1=1 ";//

		List params = new ArrayList();
		if (username != null && username.length() > 0) {
			sql += " and username like ? ";
			params.add("%"+username+"%");
		}

		if (email != null && email.length() > 0) {
			sql += " and email like ? ";
			params.add("%"+email+"%");
		}
		if (startTime != null) {
			sql += " and create_time>=? ";
			params.add(startTime);
		}

		if (endTime != null) {
			sql += " and create_time<=? ";
			params.add(endTime);
		}

		sql += " order by id desc";

		List<Userinfo> list = DbUtil.getDao().list(sql, params.toArray(),
				offset, size, new Userinfo());

		return list;
	}

	/**
	 * 总数
	 * 
	 * @return
	 */
	public Integer count(String username, String email, Date startTime,
			Date endTime) {
		String sql = "select * from userinfo where 1=1 ";//

		List params = new ArrayList();
		if (username != null && username.length() > 0) {
			sql += " and username like ? ";
			params.add("%"+username+"%");
		}

		if (email != null && email.length() > 0) {
			sql += " and email like ? ";
			params.add("%"+email+"%");
		}

		if (startTime != null) {
			sql += " and create_time>=? ";
			params.add(startTime);
		}

		if (endTime != null) {
			sql += " and create_time<=? ";
			params.add(endTime);
		}
		return DbUtil.getDao().count(sql, params.toArray());
	}

	public Userinfo delete(Long id) throws Exception {
		Userinfo rules = get(id);
		if (rules == null) {
			throw new Exception("没有找到用户");
		}
		DbUtil.getDao().delete(rules);
		return rules;
	}

	public Userinfo suspend(Long id) throws Exception {
		Userinfo rules = get(id);
		if (rules == null) {
			throw new Exception("没有找到用户");
		}
		rules.setActiveFlag(1);
		DbUtil.getDao().update(rules);
		return rules;
	}

	public Userinfo active(Long id) throws Exception {
		Userinfo rules = get(id);
		if (rules == null) {
			throw new Exception("没有找到用户");
		}
		rules.setActiveFlag(0);
		DbUtil.getDao().update(rules);
		return rules;
	}

}
