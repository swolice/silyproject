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
<a href="index.do?method=test" target="_blank"><img src="image/pic20.jpg" width="700" height="140"></a>
	  </div>
        <ul>
          <li style="padding:0 0 5px 20px;">
	 	 折扣：<a href="#" onclick="rangeSearch(0,1);return false;">0-1折</a> <a href="#" onclick="rangeSearch(0,2);return false;">1-3折</a> <a href="#" onclick="rangeSearch(0,3);return false;">3-4折</a>  <a href="#" onclick="rangeSearch(0,4);return false;">4-5折</a> <a href="#" onclick="rangeSearch(0,5);return false;">5折以上</a><br>
     价格：<a href="#" onclick="rangeSearch(1,1);return false;">0-10元</a> <a href="#" onclick="rangeSearch(1,2);return false;">10-100元</a> <a href="#" onclick="rangeSearch(1,3);return false;">100-200元</a> <a href="#" onclick="rangeSearch(1,4);return false;">200-500元</a> <a href="#" onclick="rangeSearch(1,5);return false;">500元以上</a>
          </li>    
        </ul>
     <div class="clean"></div>
     <div class="cur_buy">
        <ul>
		  <li class="cur_buy_left"></li>
          <li class="cur_buy_mid">今日团购</li>
	      <li class="cur_buy_midd">
	       <!--select id="scoring"name="scoring" onchange="sortSelect(this.options[this.selectedIndex].value,this.selectedIndex)"><option value="-mr"selected="selected">默认排序</option><option value="-price-a">价格：从低到高</option><option value="-price-d">价格：从高到低</option><option value="-zk-a">折扣：从低到高</option><option value="-zk-d">折扣：从高到低</option></select-->
           &nbsp;<span id="sprice" class=" " onclick="sortSearch('sell_Price')"><a href="javascript:">价格<img src="${sortType==0?"images/desc.gif":"images/asc.gif"}"/></a></span>
		   &nbsp;<span id="szk" class=" "onclick="sortSearch('discount')"><a href="javascript:">折扣<img src="${sortType==0?"images/desc.gif":"images/asc.gif"}"/></a></span>
           &nbsp;&nbsp;<a href="#" onclick="javascript:sortSearch('user_count');return false;">关注度<img src="${sortType==0?"images/desc.gif":"images/asc.gif"}"/></a>&nbsp;&nbsp;<a href="#" onclick="javascript:sortSearch('start_time');return false">发布时间<img src="${sortType==0?"images/desc.gif":"images/asc.gif"}"/></a>
	      </li>
	      <li class="cur_buy_middle">
	      <img src="image/pic08.jpg" width="15" height="15" align="absmiddle">&nbsp;&nbsp;<a href="index.do?method=list">列表显示</a>&nbsp;&nbsp;&nbsp;&nbsp;<img src="image/pic09.jpg" width="15" height="15" align="absmiddle">&nbsp;&nbsp;<a href="index.do?method=list&type=1">大图显示</a>
	      </li>
		  <li class="cur_buy_right"></li>
        </ul>
      <div class="clean"></div>
	  <!--div class="page">找到相关 ${page.sumCounts} 条结果</div-->
<%
if (request.getParameter("type")!=null&&"1".equals(request.getParameter("type"))){
%>
<%@include file="_list1.jsp"%>
<%}else{%>
<%@include file="_list.jsp"%>
<%}%>      
    </div>
	<div class="main_bottom"></div>
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