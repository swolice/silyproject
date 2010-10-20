package dms.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import dms.data.Userinfo;

import util.DbUtil;

public class UserTag extends TagSupport {
	private Long userId;

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int doStartTag() throws JspTagException {
		if (userId == null)
			return 1;

		try {
			StringBuffer buf = new StringBuffer();

			Userinfo sites = (Userinfo) DbUtil.getDao().get(Userinfo.class,
					userId);

			if (sites != null) {
				buf.append(sites.getUsername());
			} else {
			}

			pageContext.getOut().write(buf.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				pageContext.getOut().write("" + ex.toString());
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}
		return 1;
	}

}
