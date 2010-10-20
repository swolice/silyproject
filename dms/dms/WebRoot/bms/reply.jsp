<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@page import="dms.data.City"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
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
<form name="complainForm" method="post" action="complain.do?method=reply" id="complainForm">
<input type="hidden" name="id" id="id" value="${obj.id}"/>
<table width="90%" border="1" align="center" cellpadding="0"
	cellspacing="0" bordercolorlight=#AAAAAA bordercolordark=white>
	<tr>
		<td width="80">
			投诉对象
		</td>
		<td>
			<a href="/index.do?method=detail&id=${obj.infoId}" target="_blank"><mytld:infoTag infoId="${obj.infoId}"></mytld:infoTag></a>
		</td>
	</tr>
<tr>
	<td width="80">
			投诉人
		</td>
		<td>
			${obj.username}
		</td>
	</tr>		
	<tr>
		<td width="80">
			邮箱
		</td>
		<td>
			${obj.email}
		</td>
	</tr>	
	<tr>
		<td width="80">
			电话
		</td>
		<td>
			${obj.phone}
		</td>
	</tr>	
	<tr>
		<td width="80">
			回复
		</td>
		<td>
			<textarea name="content">${obj.reply}</textarea>
		</td>
	</tr>
</table>
<p style="text-align:center"><input type="submit" name="submit1" value="提交">&nbsp;&nbsp;<input type="reset" name="reset1" value="取消">&nbsp;&nbsp;<input type="button" name="back" value="返回" onclick="history.go(-1);"></p>
</form>
</body>
</html>