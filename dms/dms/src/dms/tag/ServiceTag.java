package dms.tag;


import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import dms.data.Info;
import dms.data.Service;

import util.DbUtil;

public class ServiceTag extends TagSupport {
	private Long service;

	private int type=0;//0pic 1name


	/**
	 * @return the service
	 */
	public Long getService() {
		return service;
	}



	/**
	 * @param service the service to set
	 */
	public void setService(Long service) {
		this.service = service;
	}



	public int doStartTag() throws JspTagException {
		if (service == null)
			return 1;

		try {
			StringBuffer buf = new StringBuffer();

			Service sites = (Service) DbUtil.getDao().get(Service.class, service);

			if (sites != null) {
				if (type==0){
					buf.append(sites.getPic());
				}else{
					buf.append(sites.getName());
				}
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
