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
<script language="javascript" type="text/javascript" src="/js/My97DatePicker46/WdatePicker.js"></script>
<script>
	function deleteRule(id){
	 if (confirm("确实删除吗？操作不可恢复！")){
		location.href="user.do?method=delete&id="+id;
	 }
	 return false;
	}
	
</script>
</head>

<body>
<div class="pagecoantiner">
<div class="bmstop">
  <div class="bmstitle">用户管理 </div>
  <!--div class="bmsadd"><a href="rule.jsp"><img src="<%=request.getContextPath()%>/images/gwbms_03.gif" width="16" height="16" border="0" align="absmiddle" />添加规则</a></div-->
  <div class="clear"></div>
</div>
<div class="gwbmscon">
	
	<form name="userForm" id="userForm" method="post" action="user.do?method=list">
    <div class="kuang_search">
      <div class="kuang_search01">
        <table width="100%" class="table_sear">
          <tr>
            <th><span style="color:#ff0000">*</span>用户名：</th>
            <td><input type=text name="username" value="${username}"/>
            	</td>
            <th>邮箱：</th>
            <td><input type=text name="email" value="${email}"/></td>
          </tr>
          <tr>
            <th>创建时间</th>
            <td>
            <input id="startTime" class="Wdate" type="text" value="${startTime}"
										onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\');}'})" size="22" name="startTime" />
									-
            <input id="endTime" class="Wdate" type="text" value="${endTime}"
										onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\');}'})" size="22" name="endTime" />
            	</td>
            <th></th>
            <td><input name="button" type="submit" class="button_sear" id="button" value="搜索" /></td>
          </tr>
        </table>
      </div>
    </div>
  </form>
	

  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="bmstab" id="list-table">
    <tr>
      <th>ID</th>
      <th>用户名</th>
      <th>密码</th>
      <th>邮箱</th>
      <th>注册日期</th>    
      <th align="center">操作</th>
      </tr>
    <c:if test="${list!=null&&!empty list}">
      <c:forEach var="obj" items="${list}" varStatus="index">      
    <tr>
      <td class="first-cell" >${obj.id}</td>
      <td>${obj.username}</td>
      <td>${obj.password}</td>
      <td>${obj.email}</td>
      <td><fmt:formatDate value="${obj.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>      
      <td align="center">
        <a href="#" onclick="deleteRule(${obj.id});return false;">删除</a> |
        <c:choose>
        	<c:when test="${obj.activeFlag==1}">
        		<a href="user.do?method=active&id=${obj.id}">激活</a>
        	</c:when>
        	<c:otherwise>
        		<a href="user.do?method=suspend&id=${obj.id}">挂起</a>
        	</c:otherwise>
        </c:choose>
         </td>
      </tr>
	      </c:forEach>
    </c:if>
  </table>
  <div align="right"><mytld:turnPage name="page" action="rule.do?method=list" formName="userListForm"></mytld:turnPage></div>
  </div>
</div>
</body>
</html>
