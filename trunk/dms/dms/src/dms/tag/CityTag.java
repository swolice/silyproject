package dms.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import util.DbUtil;

public class CityTag extends TagSupport {
	private Long cityId;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public int doStartTag() throws JspTagException {
		if (cityId == null)
			return 1;

		try {
			StringBuffer buf = new StringBuffer();

			dms.data.City city = (dms.data.City) DbUtil.getDao().get(
					dms.data.City.class, cityId);

			if (city!=null){
				buf.append(city.getCityName());
//				System.out.println(cat.getCatName());
			}else{
				System.out.println("北京");
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
