<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content=" ">
<meta name="keywords" content=" ">
<title> </title>
<link rel="stylesheet" type="text/css" href="css/tuan.css" />
<script type="text/javascript" src="js/jquery-1.4.2g.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script src="js/formtools.js"></script>
<script src="js/c.js"></script>
<script>
	jQuery(function(){
  $form(<%=utils.FormUtil.json(request)%>,document.searchForm);
 });
 
 
 function check(){
   var reg = /^[a-zA-Z_][a-zA-Z_0-9]{4,11}$/;
   var reg1 = /^[a-zA-Z_0-9]{6,12}$/;
	 if(!reg.exec(document.regForm.username.value)){
	 	alert("用户名格式不正确");
	 	document.regForm.username.focus();
	  return false;
	 }
	 if(!reg1.exec(document.regForm.password.value)){
	 	alert("密码格式不正确");
	 	document.regForm.password.focus();
	  return false;
	 }
 return true;
 }
 
 
 
</script>
<c:if test="${errMsg!=null}">
	<script>
		alert("${errMsg}");
	</script>
</c:if>

</head>
<body>

<%@include file="header.jsp"%>

<div id="main">
<br>
  <table cellSpacing=0 cellPadding=2 width="600" border=0 align="center">
  <form name="regForm" method="post" id="regForm" action="passport.do?method=login" onsubmit="return check();">
  <tr>
  <td class="cur_city" colspan=2>用户登录</td>
  </tr>
  <tr>
  <td>
  用户名：</td><td><input type="text" id="txtUser" name="username"  size="10" /> 
  </td>
  </tr>
  <tr>
  <td>
  密码：</td><td><input type="password" id="txtUser1" name="password" size="10" /> &nbsp;&nbsp;<a href="password_reset.jsp">忘记密码？</a>
  </td>
  </tr>
  <tr>
  <td align="center" height="50" valign="bottom" colspan="2">
  <input type="submit" value="登录" >
  </td>
  </tr>
  </table>
</div>


<%@include file="bottom.jsp"%>

</body>
</html>