package dms.tag;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import dms.data.Rules;

public class XmlTag extends TagSupport {
	private Rules rule;

	/**
	 * @return the rule
	 */
	public Rules getRule() {
		return rule;
	}

	/**
	 * @param rule
	 *            the rule to set
	 */
	public void setRule(Rules rule) {
		this.rule = rule;
	}

	public int doStartTag() throws JspTagException {
		if (rule == null || rule.getRuleXml() == null
				|| rule.getRuleXml().length() < 1)
			return 1;

		try {

			InputStream in = new ByteArrayInputStream(rule.getRuleXml()
					.getBytes("UTF-8"));
			Document document = new SAXReader().read(in);

			Element root = document.getRootElement();
			
			viewElement(root);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 1;
	}
	
	
	public void viewElement(Element e){
		;
		String tagName=e.getName();
		if (tagName==null) return;
		
		try{
		if (tagName.equalsIgnoreCase("url")){
			pageContext.getOut().write("µØÖ·url:<input type='text' name='url' id='url' value='"+e.getText()+"'/><br/>");
		}else if (tagName.equalsIgnoreCase("city")){
			pageContext.getOut().write("³ÇÊÐ:<input type='text' name='city' id='city' value='"+e.getText()+"'/><br/>");
		}else if (tagName.equalsIgnoreCase("charset")){
			pageContext.getOut().write("±àÂë:<input type='text' name='charset' id='charset' value='"+e.getText()+"'/><br/>");
		}else if (tagName.equalsIgnoreCase("seg")){
			
			
		}else if (tagName.equalsIgnoreCase("value")){
			
		}else if (tagName.equalsIgnoreCase("spider")){
			
		}
		}catch(Exception e1){
			e1.printStackTrace();
		}
	}
}
