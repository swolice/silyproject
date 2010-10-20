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
 
</script>
</head>
<body>

<%@include file="header.jsp"%>


<div id="main">
<br>
  <table cellSpacing=4 cellPadding=5 width="600" border=0 align="center">
  <tr>
  <td class="cur_city" colspan="2">用户注册</td>
  </tr>
  <tr>
  <td colspan="2">
  恭喜您注册成为<a href="index.do?method=list" target="_blank">团结客</a>用户！
  </td>
  </tr>
  <tr>
  <td width="300">
  最新团购信息：
  </td>
   <td align="right" width="300" >
  <a href="index.do?method=list">更多团购信息</a>
  </td>
  </tr>
  
<c:if test="${list!=null&&!empty list}">
<c:forEach var="obj" items="${list}" varStatus="index">  
	<c:if test="${index.index%2==0}"> 
	 <tr>
	</c:if>
  <td width="300">
   [ <a href="index.do?method=site&id=${obj.siteId}" target="_blank"><mytld:domainTag domainId="${obj.siteId}"></mytld:domainTag></a> ]&nbsp;&nbsp;<span class="discount">仅售${obj.sellPrice/100}元&nbsp;&nbsp;${obj.discount/100}折 </span><br> <span class="intro">${obj.title}</span>
</td>

<c:if test="${index.index%2==1}"> 
</tr>
</c:if>
<c:set var="_num" value="${index.index}"/>
   </c:forEach>
</c:if>
    
<c:choose>
<c:when test="${_num%2==0}">
<td></td></tr>
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>   

  </table>
</div>

<%@include file="bottom.jsp"%>

</body>
</html>