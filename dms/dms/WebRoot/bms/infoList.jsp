<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jstl/functions" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>团购列表</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script src="../js/li_ddrivetip_2.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.2g.js"></script>
<script src="../js/My97DatePicker46/WdatePicker.js" defer="defer"></script>
<script src="../js/formtools.js"></script>
<script src="<%=request.getContextPath()%>/js/bmsPage.js" type="text/javascript"></script>
</head>
<body style="background:#deeef6;">
<!--div id="current">
  <div class="left"></div>
  <div class="center">当前位置：团购列表</div>
  <div class="right"></div>
</div-->
<div id="content">
  <div class="kuang">
  <form name="infoForm" id="infoForm" method="post" action="info.do?method=list">
    <div class="kuang_search">
      <div class="kuang_search01">
        <table width="100%" class="table_sear">
          <tr>
            <th><span style="color:#ff0000">*</span>日期：</th>
            <td><input name="stime" type="text" class="input" id="stime" size="16" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
              至
              <input name="etime" type="text" class="input" id="etime" size="16" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'stime\')}'})"/>
              </td>
            <th>站点：</th>
            <td><mytld:domainSelectTag name="domain" opt="1"></mytld:domainSelectTag></td>
          </tr>
          <tr>
            <th>城市：</th>
            <td><mytld:citySelectTag name="city"  opt="1"></mytld:citySelectTag></td>
            <th></th>
            <td><input name="button" type="submit" class="button_sear" id="button" value="搜索" /></td>
          </tr>
        </table>
      </div>
    </div>
  </form>
      <!--table width="100%" cellpadding="0" cellspacing="0" class="table_caozuo">
        <tr>
          <td width="25" height="23"><img src="../images/list_ico.gif" alt="" width="20" height="23" /></td>
          <td><span class="bold">团购列表</span></td>
          <td></td>
          <td width="20">&nbsp;</td>
        </tr>
      </table-->
 <table width="100%" border="1" align="left" cellpadding="0" cellspacing="0">
 <tr>
<th>团购类型</th>
<th>城市</th>
<th>开始时间</th>
<th>结束时间</th>
<th>原价，分</th>
<th>售价，分</th>
<th>折扣,折扣*100</th>
<th>标题</th>
<th>站点</th>
<th>商家地址</th>
<th>购买人数</th>
<th>商家电话</th>
<th>商家网址</th>
<th>公交信息</th>
<th>简介</th>
<th>经纬度</th>
<th>地区</th>
<th>操作</th>
 </tr>
    <c:if test="${list!=null&&!empty list}">
      <c:forEach var="obj" items="${list}" varStatus="index">      
    <tr valign="top">
      <!--td class="first-cell" >${obj.createTime}</td>
<td>${obj.editTime}</td-->
<td><mytld:categoryTag catId="${obj.category}"></mytld:categoryTag></td>
<td><mytld:cityTag cityId="${obj.city}"></mytld:cityTag></td>
<td><fmt:formatDate value="${obj.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
<td><fmt:formatDate value="${obj.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
<td>${obj.orgPrice}</td>
<td>${obj.sellPrice}</td>
<td>${obj.discount}</td>
<td>${obj.title}</td>
<td><mytld:domainTag domainId="${obj.siteId}"></mytld:domainTag></td>
<td>${obj.address}</td>
<td>${obj.userCount}</td>
<td>${obj.phone}</td>
<td>${obj.webSite}</td>
<td>${obj.trafficInfo}</td>
<td>${obj.biefe}</td>
<td>${obj.latlon}</td>
<td>${obj.area}</td>
<td>
<a href="info.do?method=view&id=${obj.id}">编辑</a>
<a href="#" onclick='if (confirm("确认删除？")){location.href="info.do?method=delete&id=${obj.id}";};return false;'>删除</a>
<a href="poi.do?method=list&infoId=${obj.id}">地图</a>
</td>
      </tr>
	      </c:forEach>
    </c:if>
    <tr>
    	<td colspan="18">
    		<div  class="kuang" align="right"><mytld:turnPage name="page" action="info.do?method=list" formName="info1Form"></mytld:turnPage></div>
    	</td>
    </tr>
    
  </table>
  

    </div>
  </div>
    	


</body>
<script>
jQuery(function(){
$form(<%=utils.FormUtil.json(request)%>,document.infoForm);
});
</script>

</html>
