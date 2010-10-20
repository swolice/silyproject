package logic;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import util.DbUtil;

import dms.data.Complain;
import form.ComplainForm;

public class ComplainLogic {
	
	public Complain create(ComplainForm form,Long uid)throws Exception {
		Complain complain;

		complain=new Complain();

		// 复制属性
		try {
			BeanUtils.copyProperties(complain, form);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("投诉过程中发生错误！");
		}

		Date now = new Date();
		complain.setCreateTime(now);
		complain.setEditTime(now);
		
		if (uid!=null&&uid.intValue()>0){
			complain.setUserId(uid.intValue());
		}

		DbUtil.getDao().save(complain);
		
		return complain;
	}
	
	
	public Complain get(Long id) {
		try {
			Complain complain = (Complain) DbUtil.getDao().load(Complain.class, id);
			return complain;
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
		String sql = "select * from complain order by id desc";

		List<Complain> list = DbUtil.getDao().list(sql, null, offset, size,
				new Complain());

		return list;
	}
	
	
	/**
	 * 总数
	 * 
	 * @return
	 */
	public Integer count() {
		String sql = "select id from complain ";

		return DbUtil.getDao().count(sql, null);
	}
	
	
	public Complain reply(Long id,String content){
		Complain c=get(id);
		c.setReply(content);
		DbUtil.getDao().update(c);
		return c;
	}
	
}
