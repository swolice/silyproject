/*
 * JSP generated by Resin-3.1.8 (built Mon, 17 Nov 2008 12:15:21 PST)
 */

package _jsp._bms;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import dms.data.City;

public class _city__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    javax.servlet.ServletContext application = _jsp_application;
    com.caucho.jsp.PageContextImpl pageContext = _jsp_application.getJspApplicationContext().allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);
    javax.servlet.jsp.PageContext _jsp_parentContext = pageContext;
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    response.setContentType("text/html; charset=utf-8");
    request.setCharacterEncoding("UTF-8");
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      _caucho_expr_0.print(out, _jsp_env, false);
      out.write(_jsp_string1, 0, _jsp_string1.length);
      _caucho_expr_1.print(out, _jsp_env, false);
      out.write(_jsp_string2, 0, _jsp_string2.length);
      _caucho_expr_2.print(out, _jsp_env, false);
      out.write(_jsp_string3, 0, _jsp_string3.length);
      _caucho_expr_3.print(out, _jsp_env, false);
      out.write(_jsp_string4, 0, _jsp_string4.length);
      if (_caucho_expr_4.evalBoolean(_jsp_env)) {
        out.write(_jsp_string5, 0, _jsp_string5.length);
      }
      else {
        out.write(_jsp_string6, 0, _jsp_string6.length);
      }
      out.write(_jsp_string7, 0, _jsp_string7.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_application.getJspApplicationContext().freePageContext(pageContext);
    }
  }

  private java.util.ArrayList _caucho_depends = new java.util.ArrayList();

  public java.util.ArrayList _caucho_getDependList()
  {
    return _caucho_depends;
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;
    if (com.caucho.server.util.CauchoSystem.getVersionId() != 1886798272571451039L)
      return true;
    for (int i = _caucho_depends.size() - 1; i >= 0; i--) {
      com.caucho.vfs.Dependency depend;
      depend = (com.caucho.vfs.Dependency) _caucho_depends.get(i);
      if (depend.isModified())
        return true;
    }
    return false;
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void init(ServletConfig config)
    throws ServletException
  {
    com.caucho.server.webapp.WebApp webApp
      = (com.caucho.server.webapp.WebApp) config.getServletContext();
    super.init(config);
    com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
    manager.addTaglibFunctions(_jsp_functionMap, "c", "http://java.sun.com/jstl/core");
    com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.PageContextImpl(webApp, this);
    _caucho_expr_0 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj.id}");
    _caucho_expr_1 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj.cityName}");
    _caucho_expr_2 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj.sortOrder}");
    _caucho_expr_3 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj.cityGroup}");
    _caucho_expr_4 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj.hot==1}");
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("bms/city.jsp"), -4426301693594905560L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }
  private static com.caucho.el.Expr _caucho_expr_0;
  private static com.caucho.el.Expr _caucho_expr_1;
  private static com.caucho.el.Expr _caucho_expr_2;
  private static com.caucho.el.Expr _caucho_expr_3;
  private static com.caucho.el.Expr _caucho_expr_4;

  private final static char []_jsp_string6;
  private final static char []_jsp_string0;
  private final static char []_jsp_string3;
  private final static char []_jsp_string1;
  private final static char []_jsp_string7;
  private final static char []_jsp_string4;
  private final static char []_jsp_string2;
  private final static char []_jsp_string5;
  static {
    _jsp_string6 = "\r\n		   	<input type=\"radio\" name=\"hot\" value=\"0\" checked=\"checked\"/>\u662f<input type=\"radio\" name=\"hot\" value=\"1\" />\u5426\r\n		   ".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />\r\n<meta name=\"description\" content=\" \">\r\n<meta name=\"keywords\" content=\" \">\r\n<title></title>\r\n</head>\r\n<body>\r\n<br>\r\n<form name=\"cityForm\" method=\"post\" action=\"city.do?method=edit\" id=\"cityForm\"> \r\n<input type=\"hidden\" name=\"id\" id=\"id\" value=\"".toCharArray();
    _jsp_string3 = "\"/>\r\n		</td>\r\n	</tr>\r\n		<tr>\r\n		<td width=\"80\">\r\n			\u5206\u7ec4\r\n		</td>\r\n		<td>\r\n			<input type=\"text\" name=\"cityGroup\" value=\"".toCharArray();
    _jsp_string1 = "\"/>\r\n<table width=\"90%\" border=\"1\" align=\"center\" cellpadding=\"0\"\r\n	cellspacing=\"0\" bordercolorlight=#AAAAAA bordercolordark=white>\r\n	<tr>\r\n		<td width=\"80\">\r\n			\u57ce\u5e02\u540d\u79f0\r\n		</td>\r\n		<td>\r\n			<input type=\"text\" name=\"cityName\" value=\"".toCharArray();
    _jsp_string7 = "\r\n		</td>\r\n	</tr>	\r\n</table>\r\n<p style=\"text-align:center\"><input type=\"submit\" name=\"submit1\" value=\"\u63d0\u4ea4\">&nbsp;&nbsp;<input type=\"reset\" name=\"reset1\" value=\"\u53d6\u6d88\">&nbsp;&nbsp;<input type=\"button\" name=\"back\" value=\"\u8fd4\u56de\" onclick=\"history.go(-1);\"></p>\r\n</form>\r\n</body>\r\n</html>".toCharArray();
    _jsp_string4 = "\"/>\r\n		</td>\r\n	</tr>\r\n	<tr>\r\n		<td width=\"80\">\r\n			 \u662f\u5426\u70ed\u70b9\r\n		</td>\r\n		<td>\r\n			".toCharArray();
    _jsp_string2 = "\"/>\r\n		</td>\r\n	</tr>\r\n	<tr>\r\n		<td width=\"80\">\r\n			\u6392\u5e8f\r\n		</td>\r\n		<td>\r\n			<input type=\"text\" name=\"sortOrder\" value=\"".toCharArray();
    _jsp_string5 = "\r\n			  <input type=\"radio\" name=\"hot\" value=\"0\"/>\u662f<input type=\"radio\" name=\"hot\" value=\"1\" checked=\"checked\"/>\u5426\r\n			 ".toCharArray();
  }
}
