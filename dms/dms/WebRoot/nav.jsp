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
  
//  jQuery("#_top").load("index.do?method=top");
//  jQuery("#_new").load("index.do?method=newComment");
 });
</script>
</head>
<body>

<%@include file="header.jsp"%>

<div id="main">
	
	
  <div class="main_left">

<div id="main1" class="radius_bottom">
    <div class="all_sites_nav">
      <div id="js_offline" class="sites_container">

   <div class="city_nav">

		<c:if test="${list!=null&&!empty list}">
			<c:forEach var="obj" items="${list}" varStatus="index">
            <a href="index.do?method=city&id=${obj.id}" ${(obj.hot==0)?("class=hot_city"):("")} >${obj.cityName}</a>          
	     </c:forEach>
    </c:if>    
          <div class="clear"></div>
     </div>
        
          <div class="category_sites changsha_sites" >
            <h3 class="title"><mytld:cityTag cityId="${cityId}"></mytld:cityTag>地区团购网站导航</h3>
              <ul class="sites_ul">
		<c:if test="${list1!=null&&!empty list1}">
			<c:forEach var="obj1" items="${list1}" varStatus="index1">    
      <li>
        <a href="${obj1.url}"  class="site_link" target="_blank"><mytld:domainTag domainId="${obj1.siteId}"></mytld:domainTag></a>
      </li>
    	</c:forEach>
    </c:if>    
  </ul>

          </div>

 </div>
    </div>
  </div>
   
 
 
 
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