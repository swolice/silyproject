package logic;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import util.DbUtil;
import util.Tools;
import dms.data.Service;
import form.ServiceForm;

public class ServiceLogic {
	/**
	 * 新增修改
	 * 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Service edit(ServiceForm form,String path) throws Exception {
		Service service;

		if (form.getId() == null || form.getId().intValue() < 1) {
			service = new Service();
			String sql = "select * from service where name=? ";
			List list = DbUtil.getDao().list(sql,
					new Object[] { form.getName() }, 0, 1, new Service());
			if (list != null && list.size() > 0) {
				throw new Exception("已经存在相同名称！");
			}
		} else {
			service = get(form.getId());
			if (service == null) {
				throw new Exception("没有找到该信息！");
			}

			String sql = "select * from service where name=? and id!=?";
			List list = DbUtil.getDao().list(sql,
					new Object[] { form.getName(),form.getId() }, 0, 1,
					new Service());
			if (list != null && list.size() > 0) {
				throw new Exception("已经存在相同名称！");
			}
		}

		// 复制属性
		try {
			BeanUtils.copyProperties(service, form);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("商圈创建过程中发生错误！");
		}

		
		//上传图片
		if (form.getPicFile()!=null&&form.getPicFile().getFileName()!=null&&form.getPicFile().getFileSize()>0){
			try{
			String name=""+new Date().getTime()+(int)(Math.random()*1000);
			String orgname=form.getPicFile().getFileName();
			if (orgname.indexOf(".")>0){
				name+=orgname.substring(orgname.lastIndexOf("."));
			}
			InputStream in=form.getPicFile().getInputStream();
			Tools.saveFile(in, path, name);
			in.close();
			
			service.setPic("/service/"+name);
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		

		if (form.getId() == null || form.getId().intValue() < 1) {
			DbUtil.getDao().save(service);
		} else {
			DbUtil.getDao().update(service);
		}
		return service;
	}

	/**
	 * 通过ID获取
	 * 
	 * @param id
	 * @return
	 */
	public Service get(Long id) {
		try {
			Service service = (Service) DbUtil.getDao().load(Service.class, id);
			return service;
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
		String sql = "select * from service where 1=1 " ;
//		List params=new ArrayList();
//		
//		if (city>0){
//			sql+=" and city=? ";
//			params.add(city);
//		}
		
		
//		sql+=" order by sort_order";

		List<Service> list = DbUtil.getDao().list(sql, null, offset, size,
				new Service());

		return list;
	}

	/**
	 * 总数
	 * 
	 * @return
	 */
	public Integer count() {
		String sql = "select id from city where 1=1 ";
//		List params=new ArrayList();
//		
//		if (city>0){
//			sql+=" and city=? ";
//			params.add(city);
//		} 
		return DbUtil.getDao().count(sql, null);
	}

	public Service delete(Long id) throws Exception {
		Service service = get(id);
		if (service == null) {
			throw new Exception("没有找到服务");
		}
		DbUtil.getDao().delete(service);
		return service;
	}

}
