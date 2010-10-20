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
<link rel="stylesheet" type="text/css" href="css/front_new.css" />
<script type="text/javascript" src="js/jquery-1.4.2g.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script src="js/formtools.js"></script>
<script src="js/c.js"></script>
<script>
	jQuery(function(){
  $form(<%=utils.FormUtil.json(request)%>,document.searchForm);

	jQuery("#msgCount").load("home.do?method=msgCount");  

 });
  function check(){
   var reg = /^[a-zA-Z_][a-zA-Z_0-9]{5,11}$/;
   var reg1 = /^[a-zA-Z_0-9]{6,12}$/;

	 if(!reg1.exec(document.regForm.password.value)){
	 	alert("密码格式不正确");
	 	document.regForm.password.focus();
	  return false;
	 }
	 if(document.regForm.password.value!=document.regForm.password1.value){
	 	alert("两次输入的密码不一致！");
	 	document.regForm.password1.focus();
	 	return false;
	 }
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
	alert('${errMsg}');
	</script>
</c:if>

</head>
<body>

<%@include file="header.jsp"%>

<div id="main">


  <table cellSpacing=0 cellPadding=2 width="600" border=0 align="center">
  <tr>
  <td>我的团结客</td> <td><a href="home.do?method=comment&type=0">我的团购评论</a></td> <td><a href="home.do?method=comment&type=1">我的团网评论</a></td><td><a href="home.do?method=msg">我的短信息(<span id="msgCount">0</span>)</a></td>
  </tr>
  </table>
  
  <div class="front_form">
      <form action="home.do?method=send"  method="post">
        <div>
          <label>发给：</label>
          <input id="recv_user" type="text" name="target" class="required" value="${target.username}"/>
        </div>
        <div>
  <label>标题：</label>
  <input style="width: 400px;" id="message_title" type="text" name="title" class="required" value="" />
</div>
<div>
  <label>内容：</label>
  <textarea style="width: 600px;" name="content" rows="15"></textarea>
</div>

<div>
  <label>&nbsp;</label>
  <input name="commit" type="submit" value="发送" />
</div>

      </form>
    </div>
  
  
  
  
</div>


<div class="clean"></div>
<%@include file="bottom.jsp"%>

</body>
</html>