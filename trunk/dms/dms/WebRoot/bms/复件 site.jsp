<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@page import="dms.data.Sites"%>
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
<form name="siteForm" method="post" action="site.do?method=edit" id="siteForm" enctype="multipart/form-data"> 
<input type="hidden" name="id" id="id" value="${obj.id}"/>
<table width="90%" border="1" align="center" cellpadding="0"
	cellspacing="0" bordercolorlight=#AAAAAA bordercolordark=white>
	<tr>
		<td width="80">
			站点名称
		</td>
		<td>
			<input type="text" name="siteName" value="${obj.siteName}"/>
		</td>
	</tr>
  <tr>
		<td width="80">
			站点图片
		</td>
		<td>
			<input type="file" name="pic" />
			<c:if test="${obj.picUrl!=null}">
				<img src="${obj.picUrl}"/>
			</c:if>
		</td>
	</tr>	
	<tr>
		<td width="80">
			首页
		</td>
		<td>
			<input type="text" name="webPage" value="${obj.webPage}"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			地址
		</td>
		<td>
			<input type="text" name="address" value="${obj.address}"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			域名
		</td>
		<td>
			<input type="text" name="domain" value="${obj.domain}"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			上线时间
		</td>
		<td>
			<input type="text" name="onlineTime" value="${obj.onlineTime}"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			购买类型
		</td>
		<td>
			<input type="text" name="purchaseType" value="${obj.purchaseType}"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			网站类型
		</td>
		<td>
			<input type="text" name="siteType" value="${obj.siteType}"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			团购组织频率
		</td>
		<td>
			<input type="text" name="frequency" value="${obj.frequency}"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			团购平均购买人数
		</td>
		<td>
			<input type="text" name="averagePurchaser" value="${obj.averagePurchaser}"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			电子邮件
		</td>
		<td>
			<input type="text" name="email" value="${obj.email}"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			QQ
		</td>
		<td>
			<input type="text" name="qq" value="${obj.qq}"/>
		</td>
	</tr>	
			<tr>
		<td width="80">
			MSN
		</td>
		<td>
			<input type="text" name="msn" value="${obj.msn}"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			电话
		</td>
		<td>
			<input type="text" name="phone" value="${obj.phone}"/>
		</td>
	</tr>			<tr>
		<td width="80">
			简要描述
		</td>
		<td>
			<textarea name="brief" rows="5" cols="100">${obj.brief}</textarea>
		</td>
	</tr>
		<tr>
		<td width="80">
			 是否显示
		</td>
		<td>
			<c:choose>
			 <c:when test="${obj.viewFlag==1}">
			  <input type="radio" name="viewFlag" value="0"/>是<input type="radio" name="viewFlag" value="1" checked="checked"/>否
			 </c:when>
		   <c:otherwise>
		   	<input type="radio" name="viewFlag" value="0" checked="checked"/>是<input type="radio" name="viewFlag" value="1" />否
		   </c:otherwise>
		 </c:choose>
		</td>
	</tr>	
</table>
<p style="text-align:center"><input type="submit" name="submit1" value="提交">&nbsp;&nbsp;<input type="reset" name="reset1" value="取消">&nbsp;&nbsp;<input type="button" name="back" value="返回" onclick="history.go(-1);"></p>
</form>
</body>
</html>