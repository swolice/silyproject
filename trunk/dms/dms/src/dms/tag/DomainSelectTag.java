package dms.tag;

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import dms.data.Sites;

import util.DbUtil;

public class DomainSelectTag extends TagSupport {
	private Long domainId;
	private String name;

	private int opt = 0;

	/**
	 * @return the opt
	 */
	public int getOpt() {
		return opt;
	}

	/**
	 * @param opt
	 *            the opt to set
	 */
	public void setOpt(int opt) {
		this.opt = opt;
	}

	/**
	 * @return the domainId
	 */
	public Long getDomainId() {
		return domainId;
	}

	/**
	 * @param domainId
	 *            the domainId to set
	 */
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	public int doStartTag() throws JspTagException {
		try {
			StringBuffer buf = new StringBuffer();
			buf.append("<select name='").append(name).append("' ");
			buf.append(">");

			String sql = "select * from sites order by id";
			List<Sites> list = DbUtil.getDao().list(sql, null, -1, -1,
					new Sites());

			if (opt == 1) {
				buf.append("<option value='0'>请选择</option> ");
			}

			for (Sites s : list) {
				buf.append("<option value='").append(s.getId()).append("' ");

				if (domainId != null
						&& domainId.longValue() == s.getId().longValue()) {
					buf.append(" selected='true' ");
				}

				buf.append(">").append(s.getSiteName()).append("</option>");
			}

			buf.append("</select>");

			pageContext.getOut().write(buf.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				pageContext.getOut().write("��վѡ���ǩ���!" + ex.toString());
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}
		return 1;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
