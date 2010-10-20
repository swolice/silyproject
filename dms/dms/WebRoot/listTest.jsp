<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content=" ">
<meta name="keywords" content=" ">
<title> </title>
<link rel="stylesheet" type="text/css" href="css/tuan.css" />
<link rel="stylesheet" type="text/css" href="css/front_new.css" />
<script type="text/javascript" src="js/jquery-1.4.2g.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script src="js/formtools.js"></script>
<script src="js/c.js"></script>
<script>
	jQuery(function(){
  $form(<%=utils.FormUtil.json(request)%>,document.searchForm);
  
  jQuery("#_top").load("index.do?method=top");
  jQuery("#_new").load("index.do?method=newComment");
 });
</script>
</head>
<body>

<%@include file="header.jsp"%>

<div id="main">
	
<div class="main_left">
      <div>
<img src="image/pic20.jpg" width="700" height="140">
	  </div>
      <div>
试团说明文字
	  </div>
   <%@include file="_list.jsp"%>
   <div class="line_tabs">
    <span class="cur_city"><a href="javascript:void(0)" id="sales_volume_list_button" class="tab_radius on">团购活动评论</a><a href="javascript:void(0)" id="click_count_list_button" class="tab_radius">团购网站评论</a></span>
	</div>
	<div class="mod radius_bottom">  
     <ul class="deal_ads top_deal_ads" id="sales_volume_list">
     	
     	    <c:if test="${list1!=null&&!empty list1}">
      <c:forEach var="obj1" items="${list1}" varStatus="index1">  
		 <li>
${obj1.content}<br><fmt:formatDate value="${obj1.createTime}" pattern="MM月dd日 hh点mm分"/>
<a href="index.do?method=user&id=${obj1.userId}" target="_blank"><mytld:userTag userId="${obj1.userId}"></mytld:userTag></a>
     </li>
           </c:forEach>
    </c:if>

</ul>


 <ul class="deal_ads top_deal_ads" id="click_count_list" style="display:none">
      	    <c:if test="${list2!=null&&!empty list2}">
      <c:forEach var="obj2" items="${list2}" varStatus="index2">  
		 <li>
${obj2.content}<br><fmt:formatDate value="${obj2.createTime}" pattern="MM月dd日 hh点mm分"/>
<a href="index.do?method=user&id=${obj2.userId}" target="_blank"><mytld:userTag userId="${obj2.userId}"></mytld:userTag></a>
     </li>
      </c:forEach>
    </c:if>
   
</ul>
  </div>
  <script type="text/javascript">
    $("#sales_volume_list_button").click(function(){
      $('#sales_volume_list').show();
      $('#click_count_list').hide();
      $('#sales_volume_list_button').addClass('on');
      $('#click_count_list_button').removeClass('on');
    });
    $("#click_count_list_button").click(function(){
      $('#sales_volume_list').hide();
      $('#click_count_list').show();
      $('#sales_volume_list_button').removeClass('on');
      $('#click_count_list_button').addClass('on');
    });
  </script>
 </div>
 
 <div class="main_right">
 
 <div id="_top"></div>
 <!--%@include file="top.jsp"%-->
 <div class="clean"></div>
  <div id="_new"></div>
 <!--%@include file="newComment.jsp"%-->
 
  
  <%@include file="ad.html"%>
  </div>
  
  
</div>


<div class="clean"></div>
<%@include file="bottom.jsp"%>

</body>
</html>