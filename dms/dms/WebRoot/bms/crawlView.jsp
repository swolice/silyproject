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
</head>

<body>


  <table width="200%" border="0" align="center" cellpadding="0" cellspacing="0" class="bmstab" id="list-table">
    <tr>
<!--th>创建时间</th>
<th>编辑时间</th-->
<th>团购类型</th>
<th>图片</th>
<th>开始时间</th>
<th>结束时间</th>
<th>原价，分</th>
<th>售价，分</th>
<th>折扣,折扣*100</th>
<th>标题</th>
<th>站点ID</th>
<th>详细信息</th>
<th>商家名称</th>
<th>商家地址</th>
<th>购买人数</th>
<!--th>是否显示</th>
<th>删除标识</th-->
<th>商家电话</th>
<th>商家网址</th>
<th>公交信息</th>
<th>简介</th>
<th>经纬度</th>
<th>实际地址</th>
<th>地区</th>
      </tr>
    <c:if test="${list!=null&&!empty list}">
      <c:forEach var="obj" items="${list}" varStatus="index">      
    <tr valign="top">
      <!--td class="first-cell" >${obj.createTime}</td>
<td>${obj.editTime}</td-->
<td>${obj.category}</td>
<td><img src="${obj.picUrl}" width="50" height="40"/></td>
<td><fmt:formatDate value="${obj.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
<td><fmt:formatDate value="${obj.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
<td>${obj.orgPrice}</td>
<td>${obj.sellPrice}</td>
<td>${obj.discount}</td>
<td>${obj.title}</td>
<td>${obj.siteId}</td>
<td><c:out value="${obj.detail}" escapeXml="false"/></td>
<td>${obj.viewName}</td>
<td>${obj.address}</td>
<td>${obj.userCount}</td>
<!--td>${obj.viewFlag}</td>
<td>${obj.deleteFlag}</td-->
<td>${obj.phone}</td>
<td>${obj.webSite}</td>
<td>${obj.trafficInfo}</td>
<td>${obj.biefe}</td>
<td>${obj.latlon}</td>
<td>${obj.url1}</td>
<td>${obj.area}</td>
      </tr>
	      </c:forEach>
    </c:if>
  </table>
</body>
</html>
