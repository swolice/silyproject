<%@ page contentType="text/html; charset=utf-8" language="java" %>
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

<input type="hidden" name="id" id="id" value="${obj.id}"/>
<table width="90%" border="1" align="center" cellpadding="0"
	cellspacing="0" bordercolorlight=#AAAAAA bordercolordark=white>
	<tr>
		<td width="80">
			用户名
		</td>
		<td>
			<mytld:userTag userId="${obj.userId}"></mytld:userTag>
		</td>
	</tr>
	<tr>
		<td width="80">
			被评内容
		</td>
		<td>
			${target}
		</td>
	</tr>
		<tr>
		<td width="80">
			内容
		</td>
		<td>
			${obj.content}
		</td>
	</tr>
		<tr>
		<td width="80">
			发布时间
		</td>
		<td>
			<fmt:formatDate value="${obj.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>	
</table>
<p style="text-align:center">
	<c:choose>
	<c:when test="${obj.auditFlag==1}">
	<input type="button" name="submit1" value="审核通过" onclick="location.href='comment.do?method=audit&type=0&id=${obj.id}'">
</c:when>
<c:otherwise>
	<input type="button" name="submit1" value="审核不通过" onclick="location.href='comment.do?method=audit&type=1&id=${obj.id}'">
</c:otherwise>
</c:choose>
	&nbsp;&nbsp;<input type="button" name="reset1" onclick="if confirm('确实删除？'){location.href='comment.do?method=delete&id=${obj.id}'}" value="删除">&nbsp;&nbsp;<input type="button" name="back" value="返回" onclick="history.go(-1);"></p>
</body>
</html>