package comm.page;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.Map;
import java.util.Iterator;

public class PageTag extends TagSupport {
    private String name;
    private String type="1"; //type="1" ǰ̨չʾ��type="2"��̨չʾ,type="3"js�ͷ�ҳ�ֿ���type="4"�����õ�

    private String formName="pageForm"; //������
    private String action=""; //�ύaction
    public PageTag() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public int doStartTag() throws JspTagException {
        try {
            Page page = (Page) pageContext.getRequest().getAttribute(name);
            if (page == null) {
                pageContext.getOut().write("page����Ϊ�գ�");
                return 1;
            }

            StringBuffer buf = new StringBuffer();
            buf.append("<form id=\"" + formName + "\" name=\"" + formName +
                       "\" method=\"post\" action=\"" + action + "\">\n");
            Map map = pageContext.getRequest().getParameterMap();
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();

                if (!key.equals("pageNo") && !key.equals("pageSize")) {
                    Object value = map.get(key);
                    if (value instanceof java.lang.
                        String[]) {
                        String[] ss = (String[]) value;
                        for (String s : ss) {
                            buf.append(
                                    "<input type=\"hidden\" name=\"" +
                                    key +
                                    "\" value=\"" + s +
                                    "\" />\n");
                        }
                    }

                    else {

                        buf.append(
                                "<input type=\"hidden\" name=\"" +
                                key +
                                "\" value=\"" + value +
                                "\" />\n");

                    }

                }
            }
            buf.append("<input type=\"hidden\" name=\"pageNo\" value='" +
                       page.cur + "' />\n");
            buf.append("<input type=\"hidden\" name=\"pageSize\" value='" +
                       page.rows + "' />\n");
            buf.append("<script>viewPage(" + page.cur + "," + page.sumPages +
                       "," + page.sumCounts + "," + type + ",document."+formName+");</script>\n");
            buf.append("</form>\n");

            pageContext.getOut().write(buf.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                pageContext.getOut().write("��ҳ��ǩ����!" + ex.toString());
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
        }
        return 1;
    }

    public String getFormName() {
        return formName;
    }

    public String getAction() {
        return action;
    }


}
