package logic;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import comm.ThreadFactory;

import util.Base64;
import util.DbUtil;
import util.MailSender;

import dms.data.Userinfo;
import form.RegForm;

public class PassportLogic {

	public Userinfo reg(RegForm form) throws Exception {
		String sql = "select * from userinfo t where t.email=? ";
		List list = DbUtil.getDao().list(sql, new Object[] { form.getEmail() },
				0, 1);

		if (list != null && list.size() > 0) {
			throw new Exception("邮箱已注册！");
		}

		sql = "select * from userinfo t where t.username=? ";
		list = DbUtil.getDao().list(sql, new Object[] { form.getUsername() },
				0, 1);

		if (list != null && list.size() > 0) {
			throw new Exception("用户名已注册！");
		}

		Userinfo info = new Userinfo();
		// 复制属性
		try {
			BeanUtils.copyProperties(info, form);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("注册失败！");
		}

		info.setCreateTime(new Date());
		info.setEditTime(new Date());
		info.setActiveFlag(0);
		DbUtil.getDao().save(info);

		return info;
	}

	public Userinfo login(RegForm form) throws Exception {
		String sql = "select * from userinfo t where t.username=? and t.password=? ";
		List list = DbUtil.getDao().list(sql,
				new Object[] { form.getUsername(), form.getPassword() }, 0, 1,
				new Userinfo());

		if (list != null && list.size() > 0) {
			Userinfo user=(Userinfo) list.get(0);
			if (user.getActiveFlag()==null||user.getActiveFlag()!=0){
				throw new Exception("用户被挂起！");
			}
			
			return user;
		}

		throw new Exception("用户名或密码错误！");
	}

	public void reset(String email) throws Exception{
		String sql="select * from userinfo t where t.email=? ";
		List<Userinfo> list=DbUtil.getDao().list(sql,new Object[]{email},0, 1,new Userinfo());
		if (list==null||list.size()<1) throw new Exception("没有找到用户！");
		
		Userinfo info=list.get(0);
		info.setPassword(new String(Base64.encode((""+(int)(Math.random()*1000000)).getBytes())));
		DbUtil.getDao().update(info);
		
		MailSender sender=new MailSender(email,"团结客-重置密码","您的用户名是"+info.getUsername()+",您的密码现在已经给您重置为:"+info.getPassword());
		
		ThreadFactory.getThreadPool().execute(sender);
	}
}
