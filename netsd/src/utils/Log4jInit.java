package utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import logic.JsoupLogic;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class Log4jInit extends HttpServlet {

	private static final long serialVersionUID = -3073616375084465227L;

	public void init() {   
    	ServletContext context = getServletConfig().getServletContext();
    	String logpath = JsoupLogic.getCommonProp("log_path");
    	System.setProperty("log_path",logpath);
        String prefix = context.getRealPath("/");
        String file = getInitParameter("log4j-init-conf");
        if(file != null) {
        	PropertyConfigurator.configure(prefix+file);
        }else{
        	LogFactory.getLog(this.getClass()).fatal("Log4jInit初始化失败");
        }
    }

}