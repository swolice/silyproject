package logic;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import util.DbUtil;

import dms.data.InfoAddress;
import form.AddressForm;

public class PoiLogic {
	/**
	 * 新增修改
	 * 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public InfoAddress edit(AddressForm form) throws Exception {
		InfoAddress addr;

		if (form.getId() == null || form.getId().intValue() < 1) {
			addr = new InfoAddress();
		} else {
			addr = get(form.getId());
			if (addr == null) {
				throw new Exception("没有找到该地址信息！");
			}
		}

		// 复制属性
		try {
			BeanUtils.copyProperties(addr, form);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("网站创建过程中发生错误！");
		}

		Date now = new Date();
		if (form.getId() == null || form.getId().intValue() < 1) {
			addr.setCreateTime(now);
		}
		addr.setEditTime(now);


		if (form.getId() == null || form.getId().intValue() < 1) {
			DbUtil.getDao().save(addr);
		} else {
			DbUtil.getDao().update(addr);
		}
		return addr;
	}

	/**
	 * 通过ID获取
	 * 
	 * @param id
	 * @return
	 */
	public InfoAddress get(Long id) {
		try {
			InfoAddress info = (InfoAddress) DbUtil.getDao().load(InfoAddress.class, id);
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
	public List list(Long infoId, int offset, int size) {

		String sql = " select * from info_address where info_id=? ";

		List<InfoAddress> list = DbUtil.getDao().list(sql,
				new Object[] { infoId }, offset, size, new InfoAddress());

		return list;
	}

	/**
	 * 总数
	 * 
	 * @return
	 */
	public Integer count(Long infoId) {
		String sql = " select * from info_address where info_id=?  ";

		return DbUtil.getDao().count(sql, new Object[] { infoId });
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
	public InfoAddress delete(Long id) throws Exception {
		InfoAddress addr = get(id);
		if (addr == null) {
			throw new Exception("没有找到地址信息");
		}
		DbUtil.getDao().delete(addr);
		return addr;
	}
}