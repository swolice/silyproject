<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jstl/functions" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<%=request.getContextPath()%>/style/bms.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/style/style.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/js/jquery-1.4.2g.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/li.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/bmsPage.js" type="text/javascript"></script>
<script>
	function deleteRule(id){
	 if (confirm("确实删除吗？操作不可恢复！")){
		location.href="city.do?method=delete&id="+id;
	 }
	 return false;
	}
	
</script>
</head>

<body>
<div class="pagecoantiner">
<div class="bmstop">
  <div class="bmstitle">城市管理 </div>
  <div class="bmsadd"><a href="city.jsp"><img src="<%=request.getContextPath()%>/images/gwbms_03.gif" width="16" height="16" border="0" align="absmiddle" />添加城市</a></div>
  <div class="clear"></div>
</div>
<div class="gwbmscon">

  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="bmstab" id="list-table">
    <tr>
      <th>名称</th>
      <th>排序</th>
      <th>分组</th>
      <th align="center">操作</th>
      </tr>
    <c:if test="${list!=null&&!empty list}">
      <c:forEach var="obj" items="${list}" varStatus="index">      
    <tr>
      <td class="first-cell" >${obj.cityName}</td>
      <td>${obj.sortOrder}</td>
      <td>${obj.cityGroup}</td>
      <td align="center">
      	<a href="city.do?method=view&id=${obj.id}">编辑</a> | <a href="#" onclick="deleteRule(${obj.id});return false;">删除</a> </td>
      </tr>
	      </c:forEach>
    </c:if>
  </table>
  <div align="right"><mytld:turnPage name="page" action="city.do?method=list" formName="cityListForm"></mytld:turnPage></div>
  </div>
</div>
</body>
</html>
