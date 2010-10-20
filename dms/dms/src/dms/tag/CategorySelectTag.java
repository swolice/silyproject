package dms.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import dms.bean.Analyzer;

public class CategorySelectTag extends TagSupport {
	private Long catId;
	private String name;
	private int opt = 0;

	/**
	 * @return the opt
	 */
	public int getOpt() {
		return opt;
	}

	/**
	 * @param opt the opt to set
	 */
	public void setOpt(int opt) {
		this.opt = opt;
	}

	/**
	 * @return the catId
	 */
	public Long getCatId() {
		return catId;
	}

	/**
	 * @param catId the catId to set
	 */
	public void setCatId(Long catId) {
		this.catId = catId;
	}

	public int doStartTag() throws JspTagException {
		try {
			StringBuffer buf = new StringBuffer();
			buf.append("<select name='").append(name).append("' ");
			buf.append(">");

			if (opt == 1) {
				buf.append("<option value='0'>请选择</option> ");
			}

			for (int i = 0; i < Analyzer.types.length; i++) {
				buf.append("<option value='").append(i).append("' ");

				if (catId != null && catId.longValue() == i) {
					buf.append(" selected='true' ");
				}

				buf.append(">").append(Analyzer.types[i]).append("</option>");
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
