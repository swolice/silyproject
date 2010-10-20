<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@page import="dms.data.Sites"%>
<%@page import="dms.data.Rules"%>
<%@page import="dms.data.City"%>
<%@page import="java.util.List"%>
<%@page import="util.DbUtil"%>
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
<form name="ruleForm" method="post" action="rule.do?method=edit" id="ruleForm"> 
<input type="hidden" name="id" id="id" value="${obj.id}"/>
<table width="90%" border="1" align="center" cellpadding="0"
	cellspacing="0" bordercolorlight=#AAAAAA bordercolordark=white>
	<tr>
		<td width="80">
			站点
		</td>
		<td>
			<select name="domainId" id="domainId">
				<%
    Rules rule=(Rules)request.getAttribute("obj");    				
		String sql="select * from sites order by id";
		List<Sites> list=DbUtil.getDao().list(sql,null,-1,-1,new Sites());
		if (list!=null&&list.size()>0){
			for (Sites site:list){
			out.print("<option value='"+site.getId()+"' "+((rule!=null&&(site.getId().longValue()==rule.getDomainId().longValue()))?" selected ":" ")+" >"+site.getSiteName()+"</option>");
			}
		}
				%>
			</select>
		</td>
	</tr>
	<tr>
		<td width="80">
			城市
		</td>
		<td>
		  <select name="city" id="city">
				<%
	  sql="select * from city order by sort_order";
		List<City> list1=DbUtil.getDao().list(sql,null,-1,-1,new City());
		if (list1!=null&&list1.size()>0){
			for (City city:list1){
			out.print("<option value='"+city.getId()+"' "+((rule!=null&&(city.getId().longValue()==rule.getCity().longValue()))?" selected ":" ")+" >"+city.getCityName()+"</option>");
			}
		}
				%>		  	
			</select>
		</td>
	</tr>
		<tr>
		<td width="80">
			 是否生效
		</td>
		<td>
			<c:choose>
			 <c:when test="${obj.activeFlag==1}">
			  <input type="radio" name="activeFlag" value="0"/>是<input type="radio" name="activeFlag" value="1" checked="checked"/>否
			 </c:when>
		   <c:otherwise>
		   	<input type="radio" name="activeFlag" value="0" checked="checked"/>是<input type="radio" name="activeFlag" value="1" />否
		   </c:otherwise>
		 </c:choose>
		</td>
	</tr>
</table>
<p style="text-align:center"><input type="submit" name="submit1" value="提交">&nbsp;&nbsp;<input type="reset" name="reset1" value="取消">&nbsp;&nbsp;<input type="button" name="back" value="返回" onclick="history.go(-1);"></p>
</form>
</body>
</html>