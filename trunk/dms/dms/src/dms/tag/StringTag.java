package dms.tag;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class StringTag extends TagSupport {
    private String value = null;
    private int length = 0;
    public StringTag() {
    }

    public int doStartTag() throws JspTagException {
        try {
            if (value == null || length < 1) {
                pageContext.getOut().write(value);
            } else {
                if (value.length() > length) {
                    pageContext.getOut().write(value.substring(0, length - 1) +
                                               "..");
                } else {
                    pageContext.getOut().write(value);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                pageContext.getOut().write("标签出错!" + ex.toString());
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
        }
        return 1;

    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

}
