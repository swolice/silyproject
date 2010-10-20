<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link href="/style/style.css" rel="stylesheet" type="text/css" />
</head>
<%
dms.data.Userinfo user=(dms.data.Userinfo)session.getAttribute("SESSION_USER");
if (user==null||!"sharp".equals(user.getUsername())){
	response.sendRedirect("/login.jsp");
}
%>

<frameset rows="120,31,*" cols="*" frameborder="no" border="0" framespacing="0" id="topmain">  
<frame src="top.html" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />  
    <frame src="siderbar-h.html" name="siderbar-h" scrolling="No" noresize="noresize" id="siderbar-h" title="siderbar-h" />  
  <frameset rows="*" cols="210,6,*" framespacing="0" frameborder="no" border="0" id="leftmain">  
    <frame src="left.jsp" name="leftFrame" scrolling="auto" noresize="noresize" id="leftFrame" title="leftFrame" />  
    <frame src="siderbar-v.html" name="siderbar-v" scrolling="No" noresize="noresize" id="siderbar-v" title="siderbar-v" />  
    <frame src="ok.html" name="mainFrame" scrolling="auto" noresize="noresize" id="mainFrame" title="mainFrame" />  
  </frameset>  
</frameset>  
<noframes><body>noframe error!   
</body></noframes>
</html>