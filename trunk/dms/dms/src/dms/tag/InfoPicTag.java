package dms.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import dms.data.Info;

import util.DbUtil;

public class InfoPicTag extends TagSupport {
	private Long infoId;

	/**
	 * @return the infoId
	 */
	public Long getInfoId() {
		return infoId;
	}

	/**
	 * @param infoId the infoId to set
	 */
	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	public int doStartTag() throws JspTagException {
		if (infoId == null)
			return 1;

		try {
			StringBuffer buf = new StringBuffer();

			Info sites = (Info) DbUtil.getDao().get(Info.class, infoId);

			if (sites != null) {
				buf.append(sites.getPicUrl());
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
