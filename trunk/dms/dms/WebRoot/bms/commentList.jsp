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
		location.href="comment.do?method=delete&id="+id;
	 }
	 return false;
	}
	
</script>
</head>

<body>
<div class="pagecoantiner">
<div class="bmstop">
  <div class="bmstitle">评论管理 </div>
  <!--div class="bmsadd"><a href="rule.jsp"><img src="<%=request.getContextPath()%>/images/gwbms_03.gif" width="16" height="16" border="0" align="absmiddle" />添加规则</a></div-->
  <div class="clear"></div>
</div>
<div class="gwbmscon">
	
	<form name="userForm" id="userForm" method="post" action="comment.do?method=list">
    <div class="kuang_search">
      <div class="kuang_search01">
        <table width="100%" class="table_sear">
          <tr>
            <th><span style="color:#ff0000">*</span>贴子状态：</th>
            <td><select name="auditFlag">
            	<option value="-1">请选择</option>
              <option value="0" ${(auditFlag==null||auditFlag==0)?"selected":""}>已审核</option>
              <option value="1" ${(auditFlag==1)?"selected":""}>待审核</option>
          </select> </td>
            <th>贴子内容：</th>
            <td><input type=text name="content" value="${content}"/></td>
          </tr>
          <tr>
            <th><span style="color:#ff0000">*</span>贴子种类：</th>
            <td><select name="commentType">
            	<option value="-1">请选择</option>
              <option value="0" ${(commentType==null||commentType==0)?"selected":""}>团购评论</option>
              <option value="1" ${(commentType==1)?"selected":""}>网站评论</option>
          </select> </td>
            <th>发贴者用户名：</th>
            <td><input type=text name="username" value="${username}"/></td>
          </tr>          
          <tr>
            <th>创建时间:</th>
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
      <th>内容</th>
      <th>用户名</th>
      <th>帖子类型</th>
      <th>发布时间</th>    
      <th align="center">操作</th>
      </tr>
    <c:if test="${list!=null&&!empty list}">
      <c:forEach var="obj" items="${list}" varStatus="index">      
    <tr>
      <td class="first-cell" >${obj.id}</td>
      <td>${obj.content}</td>
      <td><mytld:userTag userId="${obj.userId}"></mytld:userTag></td>
      <td>${obj.commentType==1?"网站评论":"团购评论"}</td>
      <td><fmt:formatDate value="${obj.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>      
      <td align="center">
      	<a href="comment.do?method=view&id=${obj.id}">审核</a> |
        <a href="#" onclick="deleteRule(${obj.id});return false;">删除</a>
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
