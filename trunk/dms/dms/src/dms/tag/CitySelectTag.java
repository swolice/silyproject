package dms.tag;

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import dms.data.City;

import util.DbUtil;

public class CitySelectTag extends TagSupport {
	private Long cityId;
	private String name;
	private int opt=0;
	
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

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public int doStartTag() throws JspTagException {
		try {
			StringBuffer buf = new StringBuffer();
			buf.append("<select name='").append(name).append("' ");
			buf.append(">");

			String sql="select * from city order by sort_order,id";
			List<City> list=DbUtil.getDao().list(sql,null,-1,-1,new City());
			
			if (opt==1){
				buf.append("<option value='0'>请选择</option> ");
			}
			
			for (City c:list){
				buf.append("<option value='").append(c.getId()).append("' ");
				
				if (cityId != null
						&& cityId.longValue() == c.getId().longValue()) {
					buf.append(" selected='true' ");
				}
				
				buf.append(">").append(c.getCityName()).append("</option>");
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
