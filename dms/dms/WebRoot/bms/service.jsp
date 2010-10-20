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
<form name="serviceForm" method="post" action="service.do?method=edit" id="serviceForm" enctype="multipart/form-data">
<input type="hidden" name="id" id="id" value="${obj.id}"/>
<table width="90%" border="1" align="center" cellpadding="0"
	cellspacing="0" bordercolorlight=#AAAAAA bordercolordark=white>
	<tr>
		<td width="80">
			名称
		</td>
		<td>
			<input type="text" name="name" value="${obj.name}"/>
		</td>
	</tr>
<tr>
	<td width="80">
			图片
		</td>
		<td>
			<input type="file" name="picFile" />
			<c:if test="${obj.pic!=null}">
				<img src="${obj.pic}"/>
			</c:if>
		</td>
	</tr>		
	<tr>
		<td width="80">
			描述
		</td>
		<td>
			<input type="text" name="remark" value="${obj.remark}"/>
		</td>
	</tr>
</table>
<p style="text-align:center"><input type="submit" name="submit1" value="提交">&nbsp;&nbsp;<input type="reset" name="reset1" value="取消">&nbsp;&nbsp;<input type="button" name="back" value="返回" onclick="history.go(-1);"></p>
</form>
</body>
</html>