<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@page import="dms.data.City"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="description" content=" ">
<meta name="keywords" content=" ">
<title></title>
</head>
<body>
<br>
<form name="cityForm" method="post" action="city.do?method=edit" id="cityForm"> 
<input type="hidden" name="id" id="id" value="${obj.id}"/>
<table width="90%" border="1" align="center" cellpadding="0"
	cellspacing="0" bordercolorlight=#AAAAAA bordercolordark=white>
	<tr>
		<td width="80">
			城市名称
		</td>
		<td>
			<input type="text" name="cityName" value="${obj.cityName}"/>
		</td>
	</tr>
	<tr>
		<td width="80">
			排序
		</td>
		<td>
			<input type="text" name="sortOrder" value="${obj.sortOrder}"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			分组
		</td>
		<td>
			<input type="text" name="cityGroup" value="${obj.cityGroup}"/>
		</td>
	</tr>
	<tr>
		<td width="80">
			 是否热点
		</td>
		<td>
			<c:choose>
			 <c:when test="${obj.hot==1}">
			  <input type="radio" name="hot" value="0"/>是<input type="radio" name="hot" value="1" checked="checked"/>否
			 </c:when>
		   <c:otherwise>
		   	<input type="radio" name="hot" value="0" checked="checked"/>是<input type="radio" name="hot" value="1" />否
		   </c:otherwise>
		 </c:choose>
		</td>
	</tr>	
</table>
<p style="text-align:center"><input type="submit" name="submit1" value="提交">&nbsp;&nbsp;<input type="reset" name="reset1" value="取消">&nbsp;&nbsp;<input type="button" name="back" value="返回" onclick="history.go(-1);"></p>
</form>
</body>
</html>