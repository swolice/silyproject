<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@page import="dms.data.Info"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jstl/functions" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="description" content=" ">
<meta name="keywords" content=" ">
<title></title>
<script src="../js/My97DatePicker46/WdatePicker.js" defer="defer"></script>
</head>
<body>
<br>
<form name="infoForm" method="post" action="info.do?method=edit" id="infoForm"> 
<input type="hidden" name="id" id="id" value="${obj.id}"/>
<table width="90%" border="1" align="center" cellpadding="0"
	cellspacing="0" bordercolorlight=#AAAAAA bordercolordark=white>
	<tr>
		<td width="80">
			团购类型
		</td>
		<td>
			&nbsp;<mytld:categorySelectTag name="category" catId="${obj.category}"></mytld:categorySelectTag>
		</td>
	</tr>
	<tr>
		<td width="80">
			城市
		</td>
		<td>
			&nbsp;<mytld:citySelectTag name="city" cityId="${obj.city}"></mytld:citySelectTag>
		</td>
	</tr>
	<tr>
		<td width="80">
			站点
		</td>
		<td>
			&nbsp;<mytld:domainSelectTag name="siteId" domainId="${obj.siteId}"></mytld:domainSelectTag>
		</td>
	</tr>	
	<tr>
		<td width="80">
			服务
		</td>
		<td>
			&nbsp;<mytld:serviceSelectTag name="service" service="${obj.service}"></mytld:serviceSelectTag>
		</td>
	</tr>	
		<tr>
		<td width="80">
			商圈
		</td>
		<td>
			&nbsp;<mytld:groupSelectTag name="group" group="${obj.group}"></mytld:groupSelectTag>
		</td>
	</tr>		
	<tr>
		<td width="80">
			往期地址
		</td>
		<td>
			<input type="text" name="url1" value="${obj.url1}"/>
		</td>
	</tr>	
	<tr>
		<td width="80">
			开始时间
		</td>
		<td>
			<input type="text" name="stime" value="<fmt:formatDate value="${obj.startTime}" pattern="yy-MM-dd HH:mm:ss"/>" size="16" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			结束时间
		</td>
		<td>
			<input type="text" name="stime" value="<fmt:formatDate value="${obj.endTime}" pattern="yy-MM-dd HH:mm:ss"/>" size="16" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'stime\')}'})"/>
		</td>
	</tr>	
		<tr>
		<td width="80">
			原价
		</td>
		<td>
			<input type="text" name="orgPrice" value="${obj.orgPrice}"/>
		</td>
	</tr>
		<tr>
		<td width="80">
			售价
		</td>
		<td>
			<input type="text" name="sellPrice" value="${obj.sellPrice}"/>
		</td>
	</tr>	
	<tr>
		<td width="80">
			折扣
		</td>
		<td>
			<input type="text" name="discount" value="${obj.discount}"/>
		</td>
	</tr>		
	<tr>
		<td width="80">
			标题
		</td>
		<td>
			<textarea name="title" rows="5" cols="100">${obj.title}</textarea>
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
			用户数
		</td>
		<td>
			<input type="text" name="userCount" value="${obj.userCount}"/>
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
	<tr>
		<td width="80">
			电话
		</td>
		<td>
			<input type="text" name="phone" value="${obj.phone}"/>
		</td>
	</tr>		
	<tr>
		<td width="80">
			网站
		</td>
		<td>
			<input type="text" name="webSite" value="${obj.webSite}"/>
		</td>
	</tr>		
	<tr>
		<td width="80">
			交通信息
		</td>
		<td>
			<input type="text" name="trafficInfo" value="${obj.trafficInfo}"/>
		</td>
	</tr>		
	<tr>
		<td width="80">
			简介
		</td>
		<td>
			<input type="text" name="biefe" value="${obj.biefe}"/>
		</td>
	</tr>		
	<tr>
		<td width="80">
			试团
		</td>
		<td>
			<c:choose>
			 <c:when test="${obj.ifTest==0}">
			  <input type="radio" name="ifTest" value="0" checked="checked"/>是<input type="radio" name="ifTest" value="1" />否
			 </c:when>
		   <c:otherwise>
		   	<input type="radio" name="ifTest" value="0" />是<input type="radio" name="ifTest" value="1" checked="checked"/>否
		   </c:otherwise>
		 </c:choose>
		</td>
	</tr>		

</table>
<p style="text-align:center"><input type="submit" name="submit1" value="提交">&nbsp;&nbsp;<input type="reset" name="reset1" value="取消">&nbsp;&nbsp;<input type="button" name="back" value="返回" onclick="history.go(-1);"></p>
</form>
</body>
</html>