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
<link rel="stylesheet" type="text/css" href="style/style.css" />
<script type="text/javascript" src="js/jquery-1.4.2g.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script src="js/formtools.js"></script>
<script src="js/c.js"></script>
<script src="js/li.js"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script src="js/markers.js" ></script>
<script src="js/g.js"></script>
<script>
  jQuery(function(){
  	initialize();
    $form(<%=utils.FormUtil.json(request)%>,document.searchForm);
    
    
    
    var b=document.searchForm.group.value.split(",");
    
    for(var i=0;i<document.tForm.elements.length;i++)
{
	
	if(document.tForm.elements[i].type=="checkbox")
{
for (var j=0;j<b.length;j++){
if(document.tForm.elements[i].value==b[j])
{
document.tForm.elements[i].checked=true; 
}
}
  }
}
  
 });
</script>
</head>
<body>

<%@include file="header.jsp"%>

<div>
  <form name="tForm" method="post" action="">
        <ul>
          <li style="padding:0 0 5px 20px;">
	 	 折扣：<a href="#" onclick="rangeSearch(0,1);return false;">0-1折</a> <a href="#" onclick="rangeSearch(0,2);return false;">1-3折</a> <a href="#" onclick="rangeSearch(0,3);return false;">3-4折</a>  <a href="#" onclick="rangeSearch(0,4);return false;">4-5折</a> <a href="#" onclick="rangeSearch(0,5);return false;">5折以上</a><br>
     价格：<a href="#" onclick="rangeSearch(1,1);return false;">0-10元</a> <a href="#" onclick="rangeSearch(1,2);return false;">10-100元</a> <a href="#" onclick="rangeSearch(1,3);return false;">100-200元</a> <a href="#" onclick="rangeSearch(1,4);return false;">200-500元</a> <a href="#" onclick="rangeSearch(1,5);return false;">500元以上</a>
     <c:if test="${gList!=null&&!empty gList}">
     <br/>商圈：
     	<c:forEach var="g" items="${gList}"> 
     		<input type="checkbox" name="group" value="${g.id}" onclick="groupSearch(this)"/>${g.name}&nbsp;&nbsp;
      </c:forEach>  
    </c:if>
          </li>    
        </ul>
      </form>
  </div>


<div id="main">

<!--地图区域开始-->	
<div class="main_left_map" id="main_left_map" align="left" style="overflow:auto;overflow-x:hidden;height:500px;width:250px;" >
    <c:if test="${list!=null&&!empty list}">
      <c:forEach var="obj" items="${list}" varStatus="index">  
<p><div style="float:left;height:32px;margin-right:4px;width:20px;background:url('images/green.png') repeat scroll 0pt -${index.index*32}px transparent;" title=""></div>
[<a href="index.do?method=site&id=${obj.siteId}" target="_blank"><mytld:domainTag domainId="${obj.siteId}"></mytld:domainTag></a>]
&nbsp;&nbsp;<span class="discount">仅售${obj.sellPrice/100}元&nbsp;&nbsp;${obj.discount/100}折 </span>
<br> 
<a href="#" target="_blank" onclick="return false;" id="href${obj.id}" onMouseover="ddrivetip('${obj.title}', 250)"; onMouseout="hideddrivetip()"><mytld:stringTag value="${obj.title}" length="18"></mytld:stringTag></a><br></p>
	
<c:forEach var="obj1" items="${obj.addressList}" varStatus="index1">  
	
<c:if test="${obj1.lat!=null&&obj1.lon!=null}">
<script>
	jQuery(function(){
		var latlon=new google.maps.LatLng(${obj1.lat}/100000,${obj1.lon}/100000);
		var title='${obj1.viewName}';
		var icon = new google.maps.MarkerImage("images/green.png",
                                new google.maps.Size(20, 32),
                                new google.maps.Point(0,${index.index*32}))
		var marker=addPoint(latlon,icon,title);
		var info="\n<div class=\"map_items\" style=\"overflow:visible;_zoom:1;\">\n  <div class=\"deal_tabs\">\n    \n        <a href=\"#\" onclick=\"switchToDealTab(this, 12605);return false;\" class=\"deal_tab current\">${obj1.viewName}</a>\n    \n  </div>\n  \n      \n<div style=\"\" class=\"map_item deal12605\" >\n    <div style=\"overflow:visible;margin-top:4px;\">\n    <div class=\"overflow:visible;\">\n      <img src=\"${obj.picUrl}\" class=\"small_image\" style=\"float:left;margin-right:6px;width:91px;height:60px;\"/>\n\n    <span class=\"title\">\n      <a target=\"_blank\" href=\"index.do?method=detail&id=${obj.id}\" title=\"${obj.title}\">\n        ${obj.title}\n      </a>\n    </span>\n    </div>\n\n    <div class=\"price\" style=\"float:right;margin-top:4px;\">\n        <span class=\"original\" style=\"text-decoration:line-through;margin-right:20px;\">(原价￥${obj.orgPrice/100})</span>\n\n        <span class=\"current\" style=\"font-size:110%;font-weight:bold;;\">现价￥${obj.sellPrice/100}</span>\n    </div>\n\n    <div style=\"float:right;clear:both;margin-top:10px;margin-bottom:10px;\">\n      <a class=\"view_button\" href=\"${obj.url}\" rel=\"nofollow\" target=\"_blank\"  >去购买&gt;&gt;</a>\n    </div>\n\n    <div style=\"clear:both;overflow:auto;_zoom:1;background-color:#e0e0e0;padding:2px;\">\n      <div style=\"float:left;\">\n        <div>\n                        \u3010<span class=\"time_div timeleft\" id=\"cd${obj1.id}\" alt=\"1283184000000\">\n          \u3010<em>0</em>\u5929<em>0</em>\u5c0f\u65f6<em>0</em>\u5206<em>0</em>\u79d2\u3011\n        </span>\u3011\n                  </div>\n      </div>\n\n      <div class=\"sales_count\" style=\"float:right;\">\n                        已有<em>${obj.userCount}</em>人购买\n                  </div>\n    </div>    \n  </div>\n\n</div>               \n  \n\n  <div class=\"addr\">\n    <div style=\"font-size:12px;display:block;\">\n      <strong></strong>${obj1.address}\n    </div>\n    <div style=\"height:10px;\">\n    </div>\n  </div>\n</div>\n";
		addInfoWindow(marker,info);
		bounds.extend(latlon);
		if(count == 0)
  	{ 
  		count++;
  		map.setCenter(latlon);
  	}else
  	{
  		map.fitBounds(bounds);
  	}
  	
  	jQuery("#href${obj.id}").click(function(){
  		moveToMarkerAndShowInfo(marker);
  		return false;
  	});
  	
  	
  	var str = '';
var myDate = new Date();
var startTime = myDate.getTime(); 
var endTime = ${obj.endTime1};
if (startTime>=endTime){
	jQuery('#cd${obj1.id}').html("\n已经结束。\n");
}else{
new CD({startTime:startTime, endTime:endTime, callback:function(day, hour, minute, second){
str = '' + (day != 0 ? day + '天' : '') + formatNum(hour) + '时' + formatNum(minute) + '分' + (day == 0 ? formatNum(second) + '秒' : '') ;
jQuery('#cd${obj1.id}').html(str);

}});
}
function formatNum(n){
return n < 10 ? '0' + n : n;
}
  	
  	
  	
	});
</script>
</c:if>      
</c:forEach>

      </c:forEach>
    </c:if>      		

  </div>
  
  <div class="main_right_map">
 
 <div id="gmap_container">
<div id="map_canvas" style="height:500px;width:700px;position: relative; background-color: rgb(229, 227, 223); overflow: hidden; z-index: 0;">
</div>
      </div>
   </div>

<!--地图区域结束-->

</div>


<div class="clean"></div>

<%@include file="bottom.jsp"%>

</body>
</html>