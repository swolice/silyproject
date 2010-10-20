package dms.tag;

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import util.DbUtil;

import dms.bean.Analyzer;
import dms.data.Group;
import dms.data.Service;

public class GroupSelectTag extends TagSupport {
	private Long group;
	private String name;
	private int opt = 1;

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
	 * @return the group
	 */
	public Long getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Long group) {
		this.group = group;
	}

	public int doStartTag() throws JspTagException {
		try {
			StringBuffer buf = new StringBuffer();
			buf.append("<select name='").append(name).append("' ");
			buf.append(">");

			if (opt == 1) {
				buf.append("<option value='0'>请选择</option> ");
			}

			String sql = "select * from groups order by sort_order,id";
			List<Group> list = DbUtil.getDao().list(sql, null, -1, -1,
					new Group());

			for (int i = 0; i < list.size(); i++) {
				Group g = list.get(i);

				buf.append("<option value='").append(g.getId()).append("' ");

				if (g != null && group != null
						&& g.getId().longValue() == group.longValue()) {
					buf.append(" selected='true' ");
				}

				buf.append(">").append(g.getName()).append("</option>");
			}

			buf.append("</select>");

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
