package logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import util.DbUtil;

import dms.data.Group;
import form.GroupForm;

public class GroupLogic {
	/**
	 * 新增修改
	 * 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Group edit(GroupForm form) throws Exception {
		Group group;

		if (form.getId() == null || form.getId().intValue() < 1) {
			group = new Group();
			String sql = "select * from groups where name=? and city=?";
			List list = DbUtil.getDao().list(sql,
					new Object[] { form.getName(),form.getCity() }, 0, 1, new Group());
			if (list != null && list.size() > 0) {
				throw new Exception("已经存在相同名称！");
			}
		} else {
			group = get(form.getId());
			if (group == null) {
				throw new Exception("没有找到该信息！");
			}

			String sql = "select * from groups where name=? and city=? and id!=?";
			List list = DbUtil.getDao().list(sql,
					new Object[] { form.getName(),form.getCity(), form.getId() }, 0, 1,
					new Group());
			if (list != null && list.size() > 0) {
				throw new Exception("已经存在相同名称！");
			}
		}

		// 复制属性
		try {
			BeanUtils.copyProperties(group, form);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("商圈创建过程中发生错误！");
		}


		if (form.getId() == null || form.getId().intValue() < 1) {
			DbUtil.getDao().save(group);
		} else {
			DbUtil.getDao().update(group);
		}
		return group;
	}

	/**
	 * 通过ID获取
	 * 
	 * @param id
	 * @return
	 */
	public Group get(Long id) {
		try {
			Group group = (Group) DbUtil.getDao().load(Group.class, id);
			return group;
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
	public List list(int city,int offset, int size) {
		String sql = "select * from groups where 1=1 " ;
		List params=new ArrayList();
		
		if (city>0){
			sql+=" and city=? ";
			params.add(city);
		}
		
		
		sql+=" order by sort_order";

		List<Group> list = DbUtil.getDao().list(sql, params.toArray(), offset, size,
				new Group());

		return list;
	}

	/**
	 * 总数
	 * 
	 * @return
	 */
	public Integer count(int city) {
		String sql = "select id from groups where 1=1 ";
		List params=new ArrayList();
		
		if (city>0){
			sql+=" and city=? ";
			params.add(city);
		} 
		return DbUtil.getDao().count(sql, params.toArray());
	}

	public Group delete(Long id) throws Exception {
		Group group = get(id);
		if (group == null) {
			throw new Exception("没有找到商圈");
		}
		DbUtil.getDao().delete(group);
		return group;
	}

}
