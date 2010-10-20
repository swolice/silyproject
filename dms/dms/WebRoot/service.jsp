<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
<link rel="stylesheet" type="text/css" href="style/pageEffect.css" />
<script type="text/javascript" src="js/jquery-1.4.2g.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/pageEffect.js"></script>
<script src="js/formtools.js"></script>
<script src="js/c.js"></script>
<script>
	jQuery(function(){
 });

 
</script>


</head>
<body>

<%@include file="header.jsp"%>

<div id="main">
<br>
  
  <table cellSpacing=0 cellPadding=2 width="600" border=0 align="center">
	<c:if test="${list!=null&&!empty list}">
  <c:forEach var="obj" items="${list}" varStatus="index">
  <tr> 
  <td width="80"><img src="${obj.pic}"/><td>
  <td>${obj.remark}</td>
  </tr>

  </c:forEach>
  </c:if>
  
  </table>
  <br>
  
</div>


<div class="clean"></div>
<%@include file="bottom.jsp"%>

</body>
</html>