package dms.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import dms.bean.Analyzer;

public class CategoryTag extends TagSupport {
	private Integer catId;

	public int doStartTag() throws JspTagException {
		if (catId == null)
			return 1;

		try {
			StringBuffer buf = new StringBuffer();

		    if (catId<Analyzer.types.length){
		    	buf.append(Analyzer.types[catId]);
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

	/**
	 * @return the catId
	 */
	public Integer getCatId() {
		return catId;
	}

	/**
	 * @param catId the catId to set
	 */
	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	
	
	

}