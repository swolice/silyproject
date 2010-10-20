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
</script>
</head>

<body>
<div class="pagecoantiner">
<div class="bmstop">
  <div class="bmstitle">网站管理 </div>
  <div class="bmsadd"><a href="site.jsp"><img src="<%=request.getContextPath()%>/images/gwbms_03.gif" width="16" height="16" border="0" align="absmiddle" />添加网站</a></div>
  <div class="clear"></div>
</div>
<div class="gwbmscon">

  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="bmstab" id="list-table">
    <tr>
      <th>站点名称</th>
      <th>首页</th>
      <th>域名</th>
      <th>上线时间</th>
      <th>购买类型</th>
      <th>网站类型</th>
      <th>组织频率</th>
      <th>购买人数</th>
      <th>电子邮件</th>
      <th>QQ</th>
      <th>MSN</th>
      <th>电话</th>
      <th>简要描述</th>
      <th>是否显示</th>
      <th align="center">操作</th>
      </tr>
    <c:if test="${list!=null&&!empty list}">
      <c:forEach var="obj" items="${list}" varStatus="index">      
    <tr>
      <td class="first-cell" >${obj.siteName}</td>
      <td><a href="#" onMouseover="ddrivetip('${obj.webPage}', 250)"; onMouseout="hideddrivetip()"><c:out value="${fn:substring(obj.webPage,0,25)}"/></a></td>
      <td>${obj.domain}</td>
      <td>${obj.onlineTime}</td>
      <td>${obj.purchaseType}</td>
      <td>${obj.siteType}</td>
      <td>${obj.frequency}</td>
      <td>${obj.averagePurchaser}</td>
      <td>${obj.email}</td>
      <td>${obj.qq}</td>
      <td>${obj.msn}</td>
      <td>${obj.phone}</td>
      <td><a href="#" onMouseover="ddrivetip('${obj.brief}', 250)"; onMouseout="hideddrivetip()"><c:out value="${fn:substring(obj.brief,0,25)}"/></a></td>
      <td>${obj.viewFlag==0?'是':'否'}</td>
      <td align="center">
      	<a href="<%=request.getContextPath()%>/bms/site.do?method=view&id=${obj.id}">编辑</a> | <a href="<%=request.getContextPath()%>/bms/site.do?method=delete&id=${obj.id}">删除</a> </td>
      </tr>
	      </c:forEach>
    </c:if>
  </table>
  <div align="right"><mytld:turnPage name="page" action="site.do?method=list" formName="siteListForm"></mytld:turnPage></div>
  </div>
</div>
</body>
</html>
