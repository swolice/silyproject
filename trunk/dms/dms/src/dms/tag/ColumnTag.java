package dms.tag;

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import dms.data.City;

import util.DbUtil;

public class ColumnTag extends TagSupport {
	private String name;

	public int doStartTag() throws JspTagException {
		try {
			StringBuffer buf = new StringBuffer();
			buf.append("<select name='").append(name).append("' ");
			buf.append(">");

			String sql = "select column_name, column_comment from information_schema.columns where table_schema ='tuan'  and table_name = 'info'";
			List list = DbUtil.getDao().list(sql, null, -1, -1);

			for (Object o : list) {
				Object[] os = (Object[]) o;
				buf.append("<option value='").append(os[0]).append("' ");

				if (name != null && name.equalsIgnoreCase(os[0].toString())) {
					buf.append(" selected='true' ");
				}

				buf.append(">").append(os[1]).append("</option>");
			}

			buf.append("</select>");

			pageContext.getOut().write(buf.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				pageContext.getOut().write("Ñ¡Ôñ±êÇ©³ö´í!" + ex.toString());
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
