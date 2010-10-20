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
		location.href="rule.do?method=delete&id="+id;
	 }
	 return false;
	}
	
</script>
</head>

<body>
<div class="pagecoantiner">
<div class="bmstop">
  <div class="bmstitle">规则管理 </div>
  <div class="bmsadd"><a href="rule.jsp"><img src="<%=request.getContextPath()%>/images/gwbms_03.gif" width="16" height="16" border="0" align="absmiddle" />添加规则</a></div>
  <div class="clear"></div>
</div>
<div class="gwbmscon">
	
	<form name="ruleForm" id="ruleForm" method="post" action="rule.do?method=list">
    <div class="kuang_search">
      <div class="kuang_search01">
        <table width="100%" class="table_sear">
          <tr>
            <th><span style="color:#ff0000">*</span>站点：</th>
            <td><mytld:domainSelectTag name="domainId" domainId="${domainId}" opt="1"></mytld:domainSelectTag>
            	</td>
            <th>城市：</th>
            <td><mytld:citySelectTag name="city" cityId="${city}" opt="1"></mytld:citySelectTag></td>
          </tr>
          <tr>
            <th>生效</th>
            <td><select name="activeFlag"><option value="-1">请选择</option>
            	<option value="0" <c:if test="${activeFlag==0}">selected="selected"</c:if> >有效</option><option value="1" <c:if test="${activeFlag==1}">selected="selected"</c:if>>无效</option></select></td>
            <th></th>
            <td><input name="button" type="submit" class="button_sear" id="button" value="搜索" /></td>
          </tr>
        </table>
      </div>
    </div>
  </form>
	

  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="bmstab" id="list-table">
    <tr>
      <th>网站</th>
      <th>城市</th>
      <th>生效</th>
      <th align="center">操作</th>
      </tr>
    <c:if test="${list!=null&&!empty list}">
      <c:forEach var="obj" items="${list}" varStatus="index">      
    <tr>
      <td class="first-cell" ><mytld:domainTag domainId="${obj.domainId}"></mytld:domainTag></td>
      <td><mytld:cityTag cityId="${obj.city}"></mytld:cityTag></td>
      <td>${obj.activeFlag==0?'是':'否'}</td>
      <td align="center">
      	<a href="rule.do?method=view&id=${obj.id}">编辑</a> | <a href="#" onclick="deleteRule(${obj.id});return false;">删除</a> | <a href="rule.do?method=detail&id=${obj.id}">详细</a>| <a href="rule.do?method=copy&id=${obj.id}">复制</a></td>
      </tr>
	      </c:forEach>
    </c:if>
  </table>
  <div align="right"><mytld:turnPage name="page" action="rule.do?method=list" formName="ruleListForm"></mytld:turnPage></div>
  </div>
</div>
</body>
</html>
