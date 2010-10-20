package dms.tag;

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import util.DbUtil;

import dms.bean.Analyzer;
import dms.data.Service;

public class ServiceSelectTag extends TagSupport {
	private Long service;
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
	 * @return the service
	 */
	public Long getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(Long service) {
		this.service = service;
	}

	public int doStartTag() throws JspTagException {
		try {
			StringBuffer buf = new StringBuffer();
			buf.append("<select name='").append(name).append("' ");
			buf.append(">");

			if (opt == 1) {
				buf.append("<option value='0'>请选择</option> ");
			}

			String sql = "select * from service order by id";
			List<Service> list = DbUtil.getDao().list(sql, null, -1, -1,
					new Service());

			for (int i = 0; i < list.size(); i++) {
				Service s = list.get(i);

				buf.append("<option value='").append(s.getId()).append("' ");

				if (s != null && service != null
						&& s.getId().longValue() == service.longValue()) {
					buf.append(" selected='true' ");
				}

				buf.append(">").append(s.getName()).append("</option>");
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
