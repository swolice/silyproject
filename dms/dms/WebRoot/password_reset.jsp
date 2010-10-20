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
	 var ereg=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	 if(!ereg.exec(document.regForm.email.value)){
	 	alert("邮箱格式不正确！");
	 	document.regForm.email.focus();
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
  <form name="regForm" method="post" action="passport.do?method=reset" id="regForm" onsubmit="return check();">
  <tr>
  <td class="cur_city">找回密码</td>
  </tr>
  <tr>
  <td>
  请输入您注册的电子邮箱，然后点击确定重新设置密码。
为了确保您能及时地收到<a href="index.do?method=list" target="_blank">团结客</a>的信件，您最好将 service@tuanjieke.com 加入您的邮件列表
  </td>
  </tr>
  <tr>
  <td>
  电子邮箱：<input type="text" id="txtUser" name="email" size="40" />
  </td>
  </tr>
  <tr>
  <td align="center" height="50" valign="bottom">
  <input type="submit" value="找回密码" >
  </td>
  </tr>
  </form>
  </table>
</div>


<%@include file="bottom.jsp"%>

</body>
</html>