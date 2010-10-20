package logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.DbUtil;
import dms.data.Comment;
import dms.data.Info;
import dms.data.Sites;

public class CommentLogic {

	public Comment get(Long id) {
		try {
			Comment city = (Comment) DbUtil.getDao().load(Comment.class, id);
			return city;
		} catch (Exception e) {
			return null;
		}
	}

	public String commentTarget(Comment c) {
		if (c == null)
			return "";
		if (c.getCommentType() != null && c.getCommentType().intValue() == 0) {
			Info info = (Info) DbUtil.getDao().load(Info.class,
					new Long(c.getInfoId().longValue()));
			if (info != null)
				return info.getTitle();
			return "";
		} else {
			Sites site = (Sites) DbUtil.getDao().load(Sites.class,
					new Long(c.getInfoId().longValue()));
			if (site != null)
				return site.getSiteName();
			return "";
		}

	}

	/**
	 * 列表
	 * 
	 * @param offset
	 * @param size
	 * @return
	 */
	public List list(int auditFlag, String content, int commentType,
			String username, Date startTime, Date endTime, int offset, int size) {
		String sql = "select * from comment t where 1=1 ";//

		List params = new ArrayList();
		if (auditFlag > -1) {
			sql += " and audit_flag=? ";
			params.add(auditFlag);
		}

		if (content != null && content.length() > 0) {
			sql += " and content like ? ";
			params.add("%" + content + "%");
		}

		if (commentType > -1) {
			sql += " and comment_type=? ";
			params.add(commentType);
		}

		if (username != null && username.length() > 0) {
			sql += " and exists (select id from userinfo t1 where t1.username like ? and t1.id=t.user_id ) ";
			params.add("%" + username + "%");
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

		List<Comment> list = DbUtil.getDao().list(sql, params.toArray(),
				offset, size, new Comment());

		return list;
	}

	/**
	 * 总数
	 * 
	 * @return
	 */
	public Integer count(int auditFlag, String content, int commentType,
			String username, Date startTime, Date endTime) {
		String sql = "select * from comment t where 1=1 ";//

		List params = new ArrayList();
		if (auditFlag > -1) {
			sql += " and audit_flag=? ";
			params.add(auditFlag);
		}

		if (content != null && content.length() > 0) {
			sql += " and content like ? ";
			params.add("%" + content + "%");
		}

		if (commentType > -1) {
			sql += " and comment_type=? ";
			params.add(commentType);
		}

		if (username != null && username.length() > 0) {
			sql += " and exists (select id from userinfo t1 where t1.username like ? and t1.id=t.user_id ) ";
			params.add("%" + username + "%");
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

	public Comment delete(Long id) throws Exception {
		Comment rules = get(id);
		if (rules == null) {
			throw new Exception("没有找到评论");
		}
		DbUtil.getDao().delete(rules);
		return rules;
	}

	public Comment audit(Long id) throws Exception {
		Comment rules = get(id);
		if (rules == null) {
			throw new Exception("没有找到评论");
		}
		rules.setAuditFlag(0);
		DbUtil.getDao().update(rules);
		return rules;
	}

	public Comment audit1(Long id) throws Exception {
		Comment rules = get(id);
		if (rules == null) {
			throw new Exception("没有找到评论");
		}
		rules.setAuditFlag(1);
		DbUtil.getDao().update(rules);
		return rules;
	}

}
