/*
 * JSP generated by Resin-3.1.8 (built Mon, 17 Nov 2008 12:15:21 PST)
 */

package _jsp;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

public class _site__jsp extends com.caucho.jsp.JavaPage
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
    dms.tag.CityTag _jsp_CityTag_0 = null;
    dms.tag.ServiceTag _jsp_ServiceTag_1 = null;
    com.caucho.jsp.IteratorLoopSupportTag _jsp_loop_0 = null;
    dms.tag.DomainTag _jsp_DomainTag_2 = null;
    org.apache.taglibs.standard.tag.rt.fmt.FormatDateTag _jsp_FormatDateTag_3 = null;
    comm.page.PageTag _jsp_PageTag_4 = null;
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      out.print((request.getContextPath()));
      out.write(_jsp_string1, 0, _jsp_string1.length);
      _caucho_expr_0.print(out, _jsp_env, false);
      out.write(_jsp_string2, 0, _jsp_string2.length);
      out.print((utils.FormUtil.json(request)));
      out.write(_jsp_string3, 0, _jsp_string3.length);
      _caucho_expr_0.print(out, _jsp_env, false);
      out.write(_jsp_string4, 0, _jsp_string4.length);
      
   if (request.getParameter("sCity")!=null){
   try{
		Long sc=Long.parseLong(request.getParameter("city"));   
		session.setAttribute("SESSION_CITY",sc);
   }catch(Exception e){}
   }
   
   if (session.getAttribute("SESSION_CITY")!=null){
   	
   }else{
   		session.setAttribute("SESSION_CITY",2l);	
   }
   
   request.setAttribute("SESSION_CITY",session.getAttribute("SESSION_CITY"));
   
      out.write(_jsp_string5, 0, _jsp_string5.length);
      if (_jsp_CityTag_0 == null) {
        _jsp_CityTag_0 = new dms.tag.CityTag();
        _jsp_CityTag_0.setPageContext(pageContext);
        _jsp_CityTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
      }

      _jsp_CityTag_0.setCityId(new java.lang.Long(_caucho_expr_1.evalLong(_jsp_env)));
      _jsp_CityTag_0.doStartTag();
      out.write(_jsp_string6, 0, _jsp_string6.length);
      
		String sql = "select c1.id,c1.city_name,t1.num from city c1,"
				+ "(select c.id,count(t.id) as num from city c,info t WHERE c.id=t.city and t.end_time>now() group by c.id) t1 "
				+ "where c1.id=t1.id order by c1.sort_order ";
		java.util.Date now=new java.util.Date ();
		java.util.List list=util.DbUtil.getDao().list(sql, null,-1,-1);

			int city=2;

			try{
				city=Integer.parseInt(request.getParameter("city"));
			}catch(Exception e){
				try{
					city=Integer.parseInt((String)request.getSession().getAttribute("SESSION_CITY"));
				}catch(Exception e1){
					city=2;
				}
			}

		
		if (list!=null)
		for (int i=0;i<list.size();i++){
		Object o=list.get(i);
		Object[] objs=(Object[])o;
		Number id=(Number)objs[0];
		String name=(String)objs[1];
		Number num=(Number)objs[2];
		if (city==id.intValue()){
		
      out.write(_jsp_string7, 0, _jsp_string7.length);
      out.print((name));
      out.write(_jsp_string8, 0, _jsp_string8.length);
      out.print((num));
      out.write(_jsp_string9, 0, _jsp_string9.length);
      
	  }else{
	  
      out.write(_jsp_string10, 0, _jsp_string10.length);
      out.print((id));
      out.write(_jsp_string11, 0, _jsp_string11.length);
      out.print((name));
      out.write(_jsp_string12, 0, _jsp_string12.length);
      out.print((num));
      out.write(_jsp_string13, 0, _jsp_string13.length);
      
		}
		if (i%5==4) out.println("<br/>");		
    }
      out.write(_jsp_string14, 0, _jsp_string14.length);
      
	if (session.getAttribute("SESSION_USER")==null){
	
      out.write(_jsp_string15, 0, _jsp_string15.length);
      }else{
      out.write(_jsp_string16, 0, _jsp_string16.length);
      }
      out.write(_jsp_string17, 0, _jsp_string17.length);
      
	if (session.getAttribute("SESSION_USER")!=null){
	
      out.write(_jsp_string18, 0, _jsp_string18.length);
      }
      out.write(_jsp_string19, 0, _jsp_string19.length);
      _caucho_expr_1.print(out, _jsp_env, false);
      out.write(_jsp_string20, 0, _jsp_string20.length);
      out.print(("mapView".equals(request.getParameter("method"))?"mapView":"list"));
      out.write(_jsp_string21, 0, _jsp_string21.length);
      _caucho_expr_2.print(out, _jsp_env, false);
      out.write(_jsp_string22, 0, _jsp_string22.length);
      _caucho_expr_3.print(out, _jsp_env, false);
      out.write(_jsp_string23, 0, _jsp_string23.length);
      _caucho_expr_4.print(out, _jsp_env, false);
      out.write(_jsp_string24, 0, _jsp_string24.length);
      _caucho_expr_2.print(out, _jsp_env, false);
      out.write(_jsp_string25, 0, _jsp_string25.length);
      _caucho_expr_2.print(out, _jsp_env, false);
      out.write(_jsp_string26, 0, _jsp_string26.length);
      if (_caucho_expr_5.evalBoolean(_jsp_env)) {
        out.write(_jsp_string27, 0, _jsp_string27.length);
        if (_jsp_ServiceTag_1 == null) {
          _jsp_ServiceTag_1 = new dms.tag.ServiceTag();
          _jsp_ServiceTag_1.setPageContext(pageContext);
          _jsp_ServiceTag_1.setParent((javax.servlet.jsp.tagext.Tag) null);
        }

        _jsp_ServiceTag_1.setService(new java.lang.Long(_caucho_expr_6.evalLong(_jsp_env)));
        _jsp_ServiceTag_1.doStartTag();
        out.write(_jsp_string28, 0, _jsp_string28.length);
      }
      out.write(_jsp_string29, 0, _jsp_string29.length);
      _caucho_expr_7.print(out, _jsp_env, false);
      out.write(_jsp_string30, 0, _jsp_string30.length);
      _caucho_expr_8.print(out, _jsp_env, false);
      out.write(_jsp_string31, 0, _jsp_string31.length);
      _caucho_expr_9.print(out, _jsp_env, false);
      out.write(_jsp_string32, 0, _jsp_string32.length);
      _caucho_expr_10.print(out, _jsp_env, false);
      out.write(_jsp_string33, 0, _jsp_string33.length);
      _caucho_expr_11.print(out, _jsp_env, false);
      out.write(_jsp_string34, 0, _jsp_string34.length);
      _caucho_expr_12.print(out, _jsp_env, false);
      out.write(_jsp_string35, 0, _jsp_string35.length);
      if (_caucho_expr_13.evalBoolean(_jsp_env)) {
        out.write(_jsp_string36, 0, _jsp_string36.length);
        if (_jsp_loop_0 == null)
          _jsp_loop_0 = new com.caucho.jsp.IteratorLoopSupportTag();
        _jsp_loop_0.setParent((javax.servlet.jsp.tagext.Tag) null);
        java.lang.Object _jsp_items_9 = _caucho_expr_14.evalObject(_jsp_env);
        java.util.Iterator _jsp_iter_9 = com.caucho.jstl.rt.CoreForEachTag.getIterator(_jsp_items_9);
        _jsp_loop_0.init(0, Integer.MAX_VALUE, 1);
        Object _jsp_status_9 = pageContext.putAttribute("index", _jsp_loop_0);
        Object _jsp_oldVar_9 = pageContext.getAttribute("obj1");
        while (_jsp_iter_9.hasNext()) {
          Object _jsp_i_9 = _jsp_iter_9.next();
          pageContext.setAttribute("obj1", _jsp_i_9);
          _jsp_loop_0.setCurrent(_jsp_i_9, _jsp_iter_9.hasNext());
          out.write(_jsp_string37, 0, _jsp_string37.length);
          if (_caucho_expr_15.evalBoolean(_jsp_env)) {
            out.write(_jsp_string38, 0, _jsp_string38.length);
            if (_caucho_expr_16.evalBoolean(_jsp_env)) {
              out.write(_jsp_string39, 0, _jsp_string39.length);
              _caucho_expr_17.print(out, _jsp_env, false);
              out.write(_jsp_string40, 0, _jsp_string40.length);
              _caucho_expr_18.print(out, _jsp_env, false);
              out.write(_jsp_string41, 0, _jsp_string41.length);
              _caucho_expr_19.print(out, _jsp_env, false);
              out.write(_jsp_string42, 0, _jsp_string42.length);
            }
            else {
              out.write(_jsp_string43, 0, _jsp_string43.length);
              _caucho_expr_19.print(out, _jsp_env, false);
              out.write(_jsp_string44, 0, _jsp_string44.length);
            }
            out.write(_jsp_string45, 0, _jsp_string45.length);
          }
          else {
            out.write(_jsp_string46, 0, _jsp_string46.length);
            _caucho_expr_20.print(out, _jsp_env, false);
            out.write(_jsp_string40, 0, _jsp_string40.length);
            _caucho_expr_18.print(out, _jsp_env, false);
            out.write(_jsp_string41, 0, _jsp_string41.length);
            _caucho_expr_19.print(out, _jsp_env, false);
            out.write(_jsp_string47, 0, _jsp_string47.length);
          }
          out.write(_jsp_string48, 0, _jsp_string48.length);
          if (_jsp_DomainTag_2 == null) {
            _jsp_DomainTag_2 = new dms.tag.DomainTag();
            _jsp_DomainTag_2.setPageContext(pageContext);
            _jsp_DomainTag_2.setParent((javax.servlet.jsp.tagext.Tag) _jsp_loop_0);
          }

          _jsp_DomainTag_2.setDomainId(new java.lang.Long(_caucho_expr_21.evalLong(_jsp_env)));
          _jsp_DomainTag_2.doStartTag();
          out.write(_jsp_string49, 0, _jsp_string49.length);
          if (_caucho_expr_15.evalBoolean(_jsp_env)) {
            out.write(_jsp_string50, 0, _jsp_string50.length);
            if (_jsp_FormatDateTag_3 == null) {
              _jsp_FormatDateTag_3 = new org.apache.taglibs.standard.tag.rt.fmt.FormatDateTag();
              _jsp_FormatDateTag_3.setPageContext(pageContext);
              _jsp_FormatDateTag_3.setParent((javax.servlet.jsp.tagext.Tag) _jsp_loop_0);
              _jsp_FormatDateTag_3.setPattern("yyyy\u5e74M\u6708d\u65e5");
            }

            _jsp_FormatDateTag_3.setValue((java.util.Date) _caucho_expr_22.evalObject(_jsp_env));
            _jsp_FormatDateTag_3.doEndTag();
            out.write(_jsp_string51, 0, _jsp_string51.length);
            _caucho_expr_18.print(out, _jsp_env, false);
            out.write(_jsp_string52, 0, _jsp_string52.length);
          }
          else {
            out.write(_jsp_string53, 0, _jsp_string53.length);
            _caucho_expr_18.print(out, _jsp_env, false);
            out.write(_jsp_string54, 0, _jsp_string54.length);
            _caucho_expr_23.print(out, _jsp_env, false);
            out.write(_jsp_string55, 0, _jsp_string55.length);
            _caucho_expr_18.print(out, _jsp_env, false);
            out.write(_jsp_string56, 0, _jsp_string56.length);
            _caucho_expr_18.print(out, _jsp_env, false);
            out.write(_jsp_string57, 0, _jsp_string57.length);
          }
          out.write(_jsp_string58, 0, _jsp_string58.length);
          _caucho_expr_24.print(out, _jsp_env, false);
          out.write(_jsp_string59, 0, _jsp_string59.length);
        }
        pageContext.pageSetOrRemove("obj1", _jsp_oldVar_9);
        pageContext.removeAttribute("index");
        out.write(_jsp_string60, 0, _jsp_string60.length);
      }
      out.write(_jsp_string61, 0, _jsp_string61.length);
      if (_jsp_PageTag_4 == null) {
        _jsp_PageTag_4 = new comm.page.PageTag();
        _jsp_PageTag_4.setPageContext(pageContext);
        _jsp_PageTag_4.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_PageTag_4.setName("page");
        _jsp_PageTag_4.setFormName("info1Form");
      }

      _jsp_PageTag_4.setAction(_caucho_expr_25.evalString(_jsp_env));
      _jsp_PageTag_4.doStartTag();
      out.write(_jsp_string62, 0, _jsp_string62.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      if (_jsp_CityTag_0 != null)
        _jsp_CityTag_0.release();
      if (_jsp_ServiceTag_1 != null)
        _jsp_ServiceTag_1.release();
      if (_jsp_DomainTag_2 != null)
        _jsp_DomainTag_2.release();
      if (_jsp_FormatDateTag_3 != null)
        _jsp_FormatDateTag_3.release();
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
    manager.addTaglibFunctions(_jsp_functionMap, "fmt", "http://java.sun.com/jstl/fmt");
    manager.addTaglibFunctions(_jsp_functionMap, "mytld", "/tld/MyTld");
    com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.PageContextImpl(webApp, this);
    _caucho_expr_0 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj.id}");
    _caucho_expr_1 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${SESSION_CITY}");
    _caucho_expr_2 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj.webPage}");
    _caucho_expr_3 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${list!=null&&!empty list?list[0].picUrl:obj.picUrl}");
    _caucho_expr_4 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj.siteName}");
    _caucho_expr_5 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${service!=null}");
    _caucho_expr_6 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${service.id}");
    _caucho_expr_7 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj.address}");
    _caucho_expr_8 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj.phone}");
    _caucho_expr_9 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${cityStar}");
    _caucho_expr_10 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${cityView}");
    _caucho_expr_11 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${star}");
    _caucho_expr_12 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${view}");
    _caucho_expr_13 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${list!=null&&!empty list}");
    _caucho_expr_14 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${list}");
    _caucho_expr_15 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj1.endTime<now}");
    _caucho_expr_16 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj1.url1!=null}");
    _caucho_expr_17 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj1.url1}");
    _caucho_expr_18 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj1.id}");
    _caucho_expr_19 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj1.picUrl}");
    _caucho_expr_20 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj1.url}");
    _caucho_expr_21 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj1.siteId}");
    _caucho_expr_22 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj1.startTime}");
    _caucho_expr_23 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj1.endTime1}");
    _caucho_expr_24 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "${obj1.title}");
    _caucho_expr_25 = com.caucho.jsp.JspUtil.createExpr(pageContext.getELContext(), "index.do?method=site&id=${obj.id}");
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("site.jsp"), -3644726363643931651L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("header.jsp"), 5870888889786724232L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("city.jsp"), 5280727461662574296L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("ad.html"), 6168093199597145537L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("bottom.jsp"), 4734239649724377095L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/tld/MyTld.tld"), -2192669327613808265L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(dms.tag.CityTag.class, -5625564777567069442L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(dms.tag.ServiceTag.class, -5145540148349242840L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(dms.tag.DomainTag.class, -4892645898963761506L));
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/tld/fmt.tld"), 308338869353100094L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(org.apache.taglibs.standard.tag.rt.fmt.FormatDateTag.class, 7844905413159918656L));
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, new com.caucho.make.ClassDependency(comm.page.PageTag.class, 4257593798314990154L));
  }

  static {
    try {
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  private static com.caucho.el.Expr _caucho_expr_0;
  private static com.caucho.el.Expr _caucho_expr_1;
  private static com.caucho.el.Expr _caucho_expr_2;
  private static com.caucho.el.Expr _caucho_expr_3;
  private static com.caucho.el.Expr _caucho_expr_4;
  private static com.caucho.el.Expr _caucho_expr_5;
  private static com.caucho.el.Expr _caucho_expr_6;
  private static com.caucho.el.Expr _caucho_expr_7;
  private static com.caucho.el.Expr _caucho_expr_8;
  private static com.caucho.el.Expr _caucho_expr_9;
  private static com.caucho.el.Expr _caucho_expr_10;
  private static com.caucho.el.Expr _caucho_expr_11;
  private static com.caucho.el.Expr _caucho_expr_12;
  private static com.caucho.el.Expr _caucho_expr_13;
  private static com.caucho.el.Expr _caucho_expr_14;
  private static com.caucho.el.Expr _caucho_expr_15;
  private static com.caucho.el.Expr _caucho_expr_16;
  private static com.caucho.el.Expr _caucho_expr_17;
  private static com.caucho.el.Expr _caucho_expr_18;
  private static com.caucho.el.Expr _caucho_expr_19;
  private static com.caucho.el.Expr _caucho_expr_20;
  private static com.caucho.el.Expr _caucho_expr_21;
  private static com.caucho.el.Expr _caucho_expr_22;
  private static com.caucho.el.Expr _caucho_expr_23;
  private static com.caucho.el.Expr _caucho_expr_24;
  private static com.caucho.el.Expr _caucho_expr_25;

  private final static char []_jsp_string45;
  private final static char []_jsp_string1;
  private final static char []_jsp_string27;
  private final static char []_jsp_string21;
  private final static char []_jsp_string44;
  private final static char []_jsp_string55;
  private final static char []_jsp_string3;
  private final static char []_jsp_string11;
  private final static char []_jsp_string16;
  private final static char []_jsp_string41;
  private final static char []_jsp_string26;
  private final static char []_jsp_string40;
  private final static char []_jsp_string58;
  private final static char []_jsp_string19;
  private final static char []_jsp_string54;
  private final static char []_jsp_string25;
  private final static char []_jsp_string56;
  private final static char []_jsp_string50;
  private final static char []_jsp_string9;
  private final static char []_jsp_string5;
  private final static char []_jsp_string30;
  private final static char []_jsp_string6;
  private final static char []_jsp_string17;
  private final static char []_jsp_string48;
  private final static char []_jsp_string34;
  private final static char []_jsp_string62;
  private final static char []_jsp_string2;
  private final static char []_jsp_string32;
  private final static char []_jsp_string49;
  private final static char []_jsp_string33;
  private final static char []_jsp_string8;
  private final static char []_jsp_string59;
  private final static char []_jsp_string24;
  private final static char []_jsp_string51;
  private final static char []_jsp_string12;
  private final static char []_jsp_string18;
  private final static char []_jsp_string60;
  private final static char []_jsp_string42;
  private final static char []_jsp_string29;
  private final static char []_jsp_string28;
  private final static char []_jsp_string15;
  private final static char []_jsp_string53;
  private final static char []_jsp_string52;
  private final static char []_jsp_string23;
  private final static char []_jsp_string43;
  private final static char []_jsp_string22;
  private final static char []_jsp_string47;
  private final static char []_jsp_string13;
  private final static char []_jsp_string61;
  private final static char []_jsp_string7;
  private final static char []_jsp_string14;
  private final static char []_jsp_string57;
  private final static char []_jsp_string39;
  private final static char []_jsp_string35;
  private final static char []_jsp_string31;
  private final static char []_jsp_string36;
  private final static char []_jsp_string38;
  private final static char []_jsp_string37;
  private final static char []_jsp_string10;
  private final static char []_jsp_string46;
  private final static char []_jsp_string0;
  private final static char []_jsp_string4;
  private final static char []_jsp_string20;
  static {
    _jsp_string45 = "\r\n	    ".toCharArray();
    _jsp_string1 = "/js/bmsPage.js\" type=\"text/javascript\"></script>\r\n<script src=\"js/pageEffect.js\"></script>\r\n<script>\r\n	function comment(){\r\n		if (jQuery('#reply_content').val().length<1){\r\n		 alert(\"\u8bf7\u8f93\u5165\u8bc4\u8bba\u5185\u5bb9\uff01\");\r\n		 jQuery('#reply_content').focus();\r\n		 return false;\r\n		}\r\n		if (jQuery('#reply_content').val().length>1000){\r\n		 alert(\"\u8bc4\u8bba\u5185\u5bb9\u5e94\u5c11\u4e8e1000\u5b57\uff01\");\r\n		 jQuery('#reply_content').focus();\r\n		 return false;\r\n		}\r\n		\r\n		jQuery.ajax({\r\n			type:'post',\r\n			url : 'index.do?method=comment',\r\n			data : {'content':jQuery('#reply_content').val(),'id':'".toCharArray();
    _jsp_string27 = "\r\n						<img src='".toCharArray();
    _jsp_string21 = "\" method=\"post\" name=\"searchForm\" id=\"searchForm\">\r\n   	<input type=\"hidden\" name=\"pd\" />\r\n   	<input type=\"hidden\" name=\"dd\" />\r\n   	<input type=\"hidden\" name=\"sort\" />\r\n   	<input type=\"hidden\" name=\"sortType\" />\r\n   	<input type=\"hidden\" name=\"type\"/>\r\n   	<input type=\"hidden\" name=\"group\"/>\r\n            <strong>\u56e2\u8d2d\u6d3b\u52a8</strong> <input type=\"text\" class=\"keyword ac_input\" size=\"60\" value=\"\" name=\"key\" id=\"key\"  style=\"color:#999;\" autocomplete=\"off\">\r\n			<select name=\"category\" id=\"category\"><option value=\"-1\">\u6240\u6709\u56e2\u8d2d</option><option value=\"0\">\u4f11\u95f2\u5a31\u4e50</option><option value=\"1\">\u7f8e\u5bb9\u5065\u5eb7</option><option value=\"2\">\u9910\u996e\u7f8e\u98df</option><option value=\"3\">\u7cbe\u54c1\u56e2\u8d2d</option></select>\r\n           <input type=\"button\" class=\"button\" value=\"\u641c\u7d22\"  name=\"button\" id=\"button\" onclick=\"submitForm();\" />\r\n   	</form>    \r\n  </div>\r\n  <div class=\"search_right\"></div> \r\n<div class=\"clean\"></div>\r\n\r\n<div id=\"main\">\r\n  <div class=\"main_left\">\r\n  <table cellSpacing=1 cellPadding=2 width=\"660\" border=0 align=\"center\">\r\n     <tr>\r\n       <td width=\" \"><a href=\"".toCharArray();
    _jsp_string44 = "\" width=\"110\" height=\"80\">\r\n	           ".toCharArray();
    _jsp_string55 = ";\r\nif (startTime>=endTime){\r\n	jQuery('#countDown".toCharArray();
    _jsp_string3 = ",document.searchForm);\r\n    \r\n    jQuery(\"#cc\").load(\"index.do?method=listComment&type=1&id=".toCharArray();
    _jsp_string11 = "\">".toCharArray();
    _jsp_string16 = "\r\n	<a href=\"/home.jsp\">\u6211\u7684\u56e2\u7ed3\u5ba2</a>\r\n	".toCharArray();
    _jsp_string41 = "\"><img src=\"".toCharArray();
    _jsp_string26 = "</a>\r\n	   <br>\r\n     ".toCharArray();
    _jsp_string40 = "\" target=\"_blank\" id=\"href".toCharArray();
    _jsp_string58 = "\r\n	   	<br> \r\n			".toCharArray();
    _jsp_string19 = "\r\n	</div>\r\n  <div class=\"top_right\"></div> \r\n  </td>\r\n  </tr>\r\n  <tr>\r\n   <td colspan=\"3\" valign=\"bottom\" align=\"center\">\r\n   <table cellSpacing=0 cellPadding=0 width=\"600\" border=0 align=\"center\">\r\n  <tr>\r\n  <td width=\"171\" height=\"29\" class=\"Nav\">\r\n    <a href=\"index.do?method=list\">\u4eca\u65e5\u56e2\u8d2d</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"index.do?method=city&id=".toCharArray();
    _jsp_string54 = "\"></span>\r\n	   	<script type=\"text/javascript\">\r\njQuery(function(){\r\nvar str = '';\r\nvar myDate = new Date();\r\nvar startTime = myDate.getTime(); \r\nvar endTime = ".toCharArray();
    _jsp_string25 = "\" target=\"_blank\">".toCharArray();
    _jsp_string56 = "').html(\"\u5df2\u7ecf\u7ed3\u675f\u3002\");\r\n}else{\r\nnew CD({startTime:startTime, endTime:endTime, callback:function(day, hour, minute, second){\r\nstr = '<span>' + (day != 0 ? day + '\u5929' : '') + formatNum(hour) + '\u65f6' + formatNum(minute) + '\u5206' + (day == 0 ? formatNum(second) + '\u79d2' : '') + '</span>';\r\njQuery('#countDown".toCharArray();
    _jsp_string50 = "\r\n	   		\u3010<font color=\"red\">\u8fc7\u671f</font>\u3011 ".toCharArray();
    _jsp_string9 = ")</span>&nbsp;\r\n		".toCharArray();
    _jsp_string5 = "\r\n   	\r\n  <span class=\"cur_city\">".toCharArray();
    _jsp_string30 = "\r\n       <br>\r\n	   \u7535\u8bdd\uff1a".toCharArray();
    _jsp_string6 = "</span>&nbsp;&nbsp;<span class=\"more_city\"><a href=\"javascript:void(0)\" onmouseover=\"jQuery('#city_list').show();\"  onmouseout=\"jQuery('#city_list').hide();\">\u5207\u6362\u57ce\u5e02</a>\r\n  </span>\r\n<div class=\"city_list\" id=\"city_list\" style=\"display: none;\" onmouseover=\"this.style.display='block';\"  onmouseout=\"this.style.display='none';\">\r\n  	".toCharArray();
    _jsp_string17 = "\r\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"setHomepage();return false;\">\u8bbe\u4e3a\u9996\u9875</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"addCookie();return false;\">\u6536\u85cf\u7ad9\u70b9</a>\r\n		".toCharArray();
    _jsp_string48 = "\r\n       </td>\r\n	   <td valign=\"top\">\u3010".toCharArray();
    _jsp_string34 = " &nbsp;&nbsp;\u603b\u6ee1\u610f\u5ea6\uff1a".toCharArray();
    _jsp_string62 = "</td>\r\n    </tr>\r\n	\r\n </table>\r\n\r\n \r\n <br>\r\n   \r\n   \r\n   	<div class=\"line_tabs\">\r\n    <span class=\"cur_city\">\u7528\u6237\u8bc4\u8bba</span>\r\n	</div>\r\n	<a name=\"commentDiv\"></a>\r\n	<div id=\"cc\">\r\n	</div>\r\n\r\n\r\n<table cellSpacing=2 cellPadding=2 width=\"700\" border=0 align=\"center\">\r\n   <tr><td class=\"cur_city\">\u53d1\u8868\u8bc4\u8bba</td></tr>\r\n	<tr>\r\n     <td>\u4e0d\u8d85\u8fc71000\u4e2a\u6c49\u5b57<br>\r\n<textarea name=\"content\" id=\"reply_content\" rows=\"10\" cols=\"58\"></textarea>\r\n </td>\r\n	 </tr>\r\n	 	<tr>\r\n     <td><input type=\"radio\" name=\"viewType\" value=\"0\" checked=\"checked\">\u63d0\u793a<input type=\"radio\" name=\"viewType\" value=\"1\" >\u5dee\u8bc4<input type=\"radio\" name=\"viewType\" value=\"2\">\u597d\u8bc4</td>\r\n	 </tr>\r\n	<tr>\r\n     <td><input type=\"button\" onclick=\"comment();\" value=\"\u53d1\u8868\u8bc4\u8bba\" class='submit_button'/></td>\r\n	 </tr>\r\n	 </table>	\r\n   \r\n  </div>\r\n <div class=\"main_right\">\r\n   <table cellSpacing=0 cellPadding=0 width=\"236\" border=0 align=\"center\">\r\n    <tr>\r\n     <td><a href=\"\" target=\"_blank\"><img src=\"image/ad.jpg\" width=\"236\" height=\"610\" alt=\"\"></a>\r\n	 </td>\r\n	 </tr>\r\n   </table>\r\n  </div>\r\n</div>\r\n<div class=\"clean\"></div>\r\n<div style=\"magin:auto; width:96%; text-align:center; height:100px; padding-top:40px;\">\r\n<a href=\"\" target=\"_blank\">\u5173\u4e8e\u6211\u4eec</a> | <a href=\"\" target=\"_blank\">\u5e7f\u544a\u5408\u4f5c</a> | <a href=\"\" target=\"_blank\">\u8054\u7cfb\u6211\u4eec</a> | <a href=\"complain.do?method=list\">\u7528\u6237\u6295\u8bc9</a>| <a href=\"index.do?method=service\">\u4fdd\u969c\u670d\u52a1</a><p>\r\nCopyright &copy 2007-2010 Magickylin.com&nbsp;&nbsp;			\r\n			<a href=\"http://www.miibeian.gov.cn/\" target=\"_blank\">\u4eacICP\u8bc1070743</a></p>\r\n</div>\r\n</body>\r\n</html>".toCharArray();
    _jsp_string2 = "','type':1},\r\n			dataType : 'html',\r\n			success : function(data){\r\n				var tr=jQuery(\"<tr></tr>\");\r\n				var td=jQuery(\"<td></td>\");\r\n				td.html(data);\r\n				td.appendTo(tr);\r\n				jQuery(\"#c1\").prepend(tr);\r\n				jQuery('#reply_content').val(\"\");\r\n				//var count=parseInt(jQuery(\"#commentCount\").text());\r\n				//jQuery(\"#commentCount\").text(count+1);				\r\n			},\r\n			error : function(XMLHttpRequest, textStatus,\r\n										errorThrown) {\r\n									try{\r\n									alert(XMLHttpRequest.responseText);\r\n									}catch(e){\r\n									alert(\"\u8bc4\u8bba\u5931\u8d25\uff01\");\r\n								 }\r\n			}\r\n\r\n		});\r\n	}\r\n	\r\n	\r\n  jQuery(function(){\r\n    $form(".toCharArray();
    _jsp_string32 = " &nbsp;&nbsp;\u57ce\u5e02\u6ee1\u610f\u5ea6\uff1a".toCharArray();
    _jsp_string49 = "\u3011 \r\n	   	".toCharArray();
    _jsp_string33 = "<br/>\r\n	   \u603b\u661f\u7ea7\uff1a".toCharArray();
    _jsp_string8 = " <span class='count'>(".toCharArray();
    _jsp_string59 = "\r\n       </td>\r\n	 </tr>\r\n	       ".toCharArray();
    _jsp_string24 = "&nbsp;&nbsp;<a href=\"".toCharArray();
    _jsp_string51 = "\u53d1\u5e03\r\n	   			<script>\r\n	   				jQuery(function(){\r\n	   					jQuery(\"#href".toCharArray();
    _jsp_string12 = "<span class='count'>(".toCharArray();
    _jsp_string18 = "\r\n	|&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"logout.jsp\">\u9000\u51fa</a>\r\n	".toCharArray();
    _jsp_string60 = "\r\n    ".toCharArray();
    _jsp_string42 = "\" width=\"110\" height=\"80\"></a>\r\n	   	       	".toCharArray();
    _jsp_string29 = "\r\n					 <br>\r\n	   \u5730\u5740\uff1a".toCharArray();
    _jsp_string28 = "'/>\r\n					".toCharArray();
    _jsp_string15 = "\r\n	<a href=\"/login.jsp\">\u767b\u5f55</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/regist.jsp\">\u6ce8\u518c</a>\r\n	".toCharArray();
    _jsp_string53 = "\r\n	   	<span id=\"countDown".toCharArray();
    _jsp_string52 = "\").removeAttr(\"href\");\r\n	   				});\r\n	   			</script>\r\n	   	".toCharArray();
    _jsp_string23 = "\" width=\"200\" height=\"56\"></a></td>\r\n	   <td>".toCharArray();
    _jsp_string43 = "\r\n	          	<img src=\"".toCharArray();
    _jsp_string22 = "\" target=\"_blank\"><img src=\"".toCharArray();
    _jsp_string47 = "\" width=\"110\" height=\"80\"></a>\r\n	    ".toCharArray();
    _jsp_string13 = ")</span></a>&nbsp;\r\n		".toCharArray();
    _jsp_string61 = "\r\n     \r\n	  \r\n	  \r\n	  \r\n    <tr>\r\n    	<td colspan=\"2\">".toCharArray();
    _jsp_string7 = "\r\n		".toCharArray();
    _jsp_string14 = "\r\n  </div>\r\n  </td>\r\n  <td width=\"260\"></td>\r\n  <td>\r\n<div class=\"top_left\"></div>\r\n<div class=\"top_middle\">\r\n	".toCharArray();
    _jsp_string57 = "').html(str);\r\nif(day == 0 && hour == 0 && minute == 0 && second == 0){\r\nwindow.location.reload();\r\n}\r\n}});\r\n}\r\nfunction formatNum(n){\r\nreturn n < 10 ? '0' + n : n;\r\n}\r\n})\r\n</script>\r\n	    ".toCharArray();
    _jsp_string39 = "\r\n	   	       	<a href=\"".toCharArray();
    _jsp_string35 = "\r\n       </td>\r\n	 </tr>\r\n   </table>\r\n   <br>\r\n\r\n\r\n   <table cellSpacing=4 cellPadding=4 width=\"660\" border=0 align=\"center\" style=\"line-height:20px;\">\r\n     <tr>\r\n	<td colspan=2 class=\"title\">\u8fd1\u671f\u56e2\u8d2d\u6d3b\u52a8</td>\r\n	</tr>\r\n	<tr>\r\n	<td colspan=2 background=\"image/bg_line.jpg\" height=\"1\">\r\n	</td>\r\n	</tr>\r\n	\r\n	  \r\n	    ".toCharArray();
    _jsp_string31 = "<br/>\r\n	   \u57ce\u5e02\u661f\u7ea7\uff1a".toCharArray();
    _jsp_string36 = "\r\n      ".toCharArray();
    _jsp_string38 = "\r\n	   		     ".toCharArray();
    _jsp_string37 = "  \r\n	 <tr>\r\n       <td width=\" \">\r\n      ".toCharArray();
    _jsp_string10 = "\r\n	  <a href=\"index.do?method=list&city=".toCharArray();
    _jsp_string46 = "\r\n	    	<a href=\"".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n<meta name=\"description\" content=\" \">\r\n<meta name=\"keywords\" content=\" \">\r\n<title> </title>\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"css/tuan.css\" />\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"style/style.css\" />\r\n<script type=\"text/javascript\" src=\"js/jquery-1.4.2g.js\"></script>\r\n<script type=\"text/javascript\" src=\"js/util.js\"></script>\r\n<script src=\"js/formtools.js\"></script>\r\n<script src=\"js/c.js\"></script>\r\n<script src=\"js/li.js\"></script>\r\n<script src=\"".toCharArray();
    _jsp_string4 = "\");\r\n    \r\n });\r\n</script>\r\n</head>\r\n<body>\r\n\r\n<script src=\"/js/h.js\"></script>\r\n<table cellSpacing=0 cellPadding=2 width=\"98%\" border=0 align=\"center\">\r\n  <tr>\r\n  <td width=\"89\" rowspan=\"2\"><img src=\"image/logo.jpg\" width=\"202\" height=\"96\"></td>\r\n   <td width=\"100\">\r\n   ".toCharArray();
    _jsp_string20 = "\">\u56e2\u8d2d\u5927\u5168</a>\r\n  </td>\r\n  <td class=\"Navmap\">\r\n <a href=\"index.do?method=mapView\">\u56e2\u8d2d\u5730\u56fe</a>\r\n   </td>\r\n   <td width=\"200\"></td>\r\n   </tr>\r\n   </table>\r\n  </td>\r\n</tr>\r\n</table>\r\n<div class=\"search_left\"></div>\r\n  <div class=\"search_middle\">\r\n   <form action=\"index.do?method=".toCharArray();
  }
}
