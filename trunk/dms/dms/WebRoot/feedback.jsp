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
	<%
	String infoId=request.getParameter("id");
	if (infoId==null) infoId="";
		String infoType=request.getParameter("type");
	if (infoType==null) infoType="";
	try{
	dms.data.Info info=(dms.data.Info)util.DbUtil.getDao().load(dms.data.Info.class,new Long(infoId));
	if (info!=null) request.setAttribute("info",info);
	}catch(Exception e){}
	%>
<br>
  <table cellSpacing=0 cellPadding=2 width="600" border=0 align="center">
  	<form name="complainForm" id="complainForm" method=post action="/complain.do?method=create"/>
  		<input type="hidden" name="commentType" value="<%=infoType%>"/>
  		<input type="hidden" name="infoId" value="<%=infoId%>"/>
  <tr>
  <td class="cur_city">团购投诉</td>
  </tr>
  <tr>
  <td><a href="/index.do?method=detail&id=${info.id}" target="_blank"><span class="intro">${info.title}</span></a></td>
  </tr>
  <tr>
  <td>
  请在此处输入您的投诉：（限 1000 个字符）<br>
<textarea name="content" id="content" rows="10" cols="20" style="width:500px;"></textarea> *
  </td>
  </tr>
  <tr>
  <td>
  类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：<input type="radio" name="viewType" value="0" checked="checked"/>投诉<input type="radio" name="viewType" value="1" />建议
  </td>
  </tr>
  <tr>  
  <tr>
  <td>
  <!--input type="checkbox" name="checkbox" value="checkbox">非会员<br-->
  用&nbsp;&nbsp;户&nbsp;&nbsp;名：<input type="text" id="username"  size="10" name="username" /> *
  </td>
  </tr>
  <tr>
  <td>
  邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：<input type="text" id="email" name="email" size="40" /> *
  </td>
  </tr>
  <tr>
  <tr>
  <td>
  联系电话：<input type="text" id="phone" name="phone" size="15" /> *  
  </td>
  </tr>
   <tr>
  <td align="center" height="50" valign="bottom">
  <input type="submit" value="提交" >
  </td>
  </tr>
</form>
  </table>
</div>

<%@include file="bottom.jsp"%>

</body>
</html>