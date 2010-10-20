package dms.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import util.DbUtil;

public class DomainPicTag extends TagSupport {
	private Long domainId;

	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	public int doStartTag() throws JspTagException {
		if (domainId == null)
			return 1;

		try {
			StringBuffer buf = new StringBuffer();

			dms.data.Sites sites = (dms.data.Sites) DbUtil.getDao().get(
					dms.data.Sites.class, domainId);

			if (sites!=null){
				buf.append(sites.getPicUrl());
			}else{
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
