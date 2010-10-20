package logic;

import java.util.Date;
import java.util.List;

import dms.data.Comment;
import dms.data.Message;
import dms.data.Userinfo;
import util.DbUtil;

public class HomeLogic {

	public int msgCount(Long userId) {
		String sql = "select id from message where receiver=? and read_flag!=0 ";
		return DbUtil.getDao().count(sql, new Object[] { userId.intValue() });
	}

	public Userinfo getUserinfo(Long id) {
		return (Userinfo) DbUtil.getDao().load(Userinfo.class, id);
	}

	public Message send(Long id, String target, String title, String content)
			throws Exception {
		String sql = "select * from userinfo t where username=? ";
		List<Userinfo> list = DbUtil.getDao().list(sql,
				new Object[] { target }, 0, 1,new Userinfo());

		if (list == null || list.size() < 1)
			throw new Exception("发送的用户不存在！");
		Message m = new Message();
		m.setSender(id.intValue());
		m.setReceiver(list.get(0).getId().intValue());
		m.setReadFlag(1);
		m.setCreateTime(new Date());
		m.setTitle(title);
		m.setContent(content);
		m.setEditTime(new Date());
		m.setMsgType(0);

		DbUtil.getDao().save(m);
		return m;
	}

	public Userinfo modify(Long id, String password, String password1,
			String email) throws Exception {
		Userinfo info = (Userinfo) DbUtil.getDao().load(Userinfo.class, id);
		if (info == null)
			throw new Exception("请重新登录!");
		if (password != null && password.length() > 0
				&& !password.equals(password1)) {
			throw new Exception("两次输入密码不一致");
		}
		if (password != null && password.length() > 0) {
			info.setPassword(password);
		}
		if (email != null && email.length() > 0) {
			info.setEmail(email);
		}
		DbUtil.getDao().update(info);
		return info;
	}

	public List<Comment> listComment(Long userId, int commentType, int offset,
			int size) {
		String sql = "select * from comment t where user_id=? and comment_type=? order by id desc";

		return DbUtil.getDao().list(sql, new Object[] { userId, commentType },
				offset, size,new Comment());
	}

	public int countComment(Long userId, int commentType) {
		String sql = "select * from comment t where user_id=? and comment_type=? order by id desc";

		return DbUtil.getDao().count(sql, new Object[] { userId, commentType });
	}

	public List<Comment> newComment(int commentType, int offset, int size) {
		String sql = "select * from comment t where comment_type=? order by id desc";
		return DbUtil.getDao().list(sql, new Object[] { commentType }, offset,
				size,new Comment());
	}

	public List<Message> listMsg(Long id, int offset, int size) {
		String sql = "select * from message where receiver=? order by read_flag desc,id desc";
		List list = DbUtil.getDao().list(sql, new Object[] { id.intValue() },
				offset, size, new Message());
		
		String sql1="update message set read_flag=0 where receiver=? ";
		DbUtil.getDao().update(sql1,new Object[]{id.intValue()});
		
		return list;

	}

	
	public int countMessage(Long id) {
		String sql = "select * from message t where receiver=? ";

		return DbUtil.getDao().count(sql, new Object[] { id.intValue() });
	}
	
	
	public static void main(String[] args) {
		Long id=2l;
		
		System.out.println(new HomeLogic().countMessage(id));
	}
}
