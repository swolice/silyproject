package logic;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import util.DbUtil;
import util.Tools;
import dms.data.Sites;
import form.SiteForm;

public class SiteLogic {
	/**
	 * 新增修改
	 * 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Sites edit(SiteForm form,String path) throws Exception {
		Sites sites;

		if (form.getId() == null || form.getId().intValue() < 1) {
			sites = new Sites();
			String sql = "select * from sites where site_name=?";
			List list = DbUtil.getDao().list(sql,
					new Object[] { form.getSiteName() }, 0, 1, new Sites());
			if (list != null && list.size() > 0) {
				throw new Exception("已经存在相同名称！");
			}
		} else {
			sites = get(form.getId());
			if (sites == null) {
				throw new Exception("没有找到该网站信息！");
			}

			String sql = "select * from sites where site_name=? and id!=?";
			List list = DbUtil.getDao().list(sql,
					new Object[] { form.getSiteName(), form.getId() }, 0, 1,
					new Sites());
			if (list != null && list.size() > 0) {
				throw new Exception("已经存在相同名称！");
			}
		}

		// 复制属性
		try {
			BeanUtils.copyProperties(sites, form);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("网站创建过程中发生错误！");
		}

		//上传站点图片
		if (form.getPic()!=null&&form.getPic().getFileName()!=null&&form.getPic().getFileSize()>0){
			try{
			String name=""+new Date().getTime()+(int)(Math.random()*1000);
			String orgname=form.getPic().getFileName();
			if (orgname.indexOf(".")>0){
				name+=orgname.substring(orgname.lastIndexOf("."));
			}
			InputStream in=form.getPic().getInputStream();
			Tools.saveFile(in, path, name);
			in.close();
			
			sites.setPicUrl("/sites/"+name);
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		
		Date now = new Date();
		if (form.getId() == null || form.getId().intValue() < 1) {
			sites.setCreateTime(now);
		}
		sites.setEditTime(now);

		if (form.getId() == null || form.getId().intValue() < 1) {
			DbUtil.getDao().save(sites);
		} else {
			DbUtil.getDao().update(sites);
		}
		return sites;
	}

	/**
	 * 通过ID获取
	 * 
	 * @param id
	 * @return
	 */
	public Sites get(Long id) {
		try {
			Sites sites = (Sites) DbUtil.getDao().load(Sites.class, id);
			return sites;
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
		String sql = "select * from sites order by id desc";

		List<Sites> list = DbUtil.getDao().list(sql, null, offset, size,
				new Sites());

		return list;
	}

	/**
	 * 总数
	 * 
	 * @return
	 */
	public Integer count() {
		String sql = "select id from sites ";

		return DbUtil.getDao().count(sql, null);
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
	public Sites delete(Long id) throws Exception {
		Sites site = get(id);
		if (site == null) {
			throw new Exception("没有找到网站");
		}
		DbUtil.getDao().delete(site);
		return site;
	}

}
