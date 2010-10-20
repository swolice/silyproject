<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
    <c:if test="${list!=null&&!empty list}">
      <c:forEach var="obj" items="${list}" varStatus="index">  
<p><div style="float:left;height:32px;margin-right:4px;width:20px;background:url('images/green.png') repeat scroll 0pt -${index.index*32}px transparent;" title=""></div>
[<a href="index.do?method=site&id=${obj.siteId}" target="_blank"><mytld:domainTag domainId="${obj.siteId}"></mytld:domainTag></a>]
&nbsp;&nbsp;<span class="discount">仅售${obj.sellPrice/100}元&nbsp;&nbsp;${obj.discount/100}折 </span>
<br> 
<a href="index.do?method=detail&id=${obj.id}" onclick="alert(11);return false;" onMouseover="ddrivetip('${obj.title}', 250)"; onMouseout="hideddrivetip()"><mytld:stringTag value="${obj.title}" length="20"></mytld:stringTag></a><br></p>
	
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
		var info="\n<div class=\"map_items\" style=\"overflow:visible;_zoom:1;\">\n  <div class=\"deal_tabs\">\n    \n        <a href=\"#\" onclick=\"switchToDealTab(this, 12605);return false;\" class=\"deal_tab current\">${obj1.viewName}</a>\n    \n  </div>\n  \n      \n<div style=\"\" class=\"map_item deal12605\" >\n    <div style=\"overflow:visible;margin-top:4px;\">\n    <div class=\"overflow:visible;\">\n      <img src=\"${obj.picUrl}\" class=\"small_image\" style=\"float:left;margin-right:6px;width:91px;height:60px;\"/>\n\n    <span class=\"title\">\n      <a target=\"_blank\" href=\"index.do?method=detail&id=${obj.id}\" title=\"${obj.title}\">\n        ${obj.title}\n      </a>\n    </span>\n    </div>\n\n    <div class=\"price\" style=\"float:right;margin-top:4px;\">\n        <span class=\"original\" style=\"text-decoration:line-through;margin-right:20px;\">(原价￥${obj.orgPrice/100})</span>\n\n        <span class=\"current\" style=\"font-size:110%;font-weight:bold;;\">现价￥${obj.sellPrice/100}</span>\n    </div>\n\n    <div style=\"float:right;clear:both;margin-top:10px;margin-bottom:10px;\">\n      <a class=\"view_button\" href=\"${obj.url}\" rel=\"nofollow\" target=\"_blank\"  >去购买&gt;&gt;</a>\n    </div>\n\n    <div style=\"clear:both;overflow:auto;_zoom:1;background-color:#e0e0e0;padding:2px;\">\n      <div style=\"float:left;\">\n        <div>\n                        \u3010<span class=\"time_div timeleft\" id=\"cd${obj.id}\" alt=\"1283184000000\">\n          \u3010<em>0</em>\u5929<em>0</em>\u5c0f\u65f6<em>0</em>\u5206<em>0</em>\u79d2\u3011\n        </span>\u3011\n                  </div>\n      </div>\n\n      <div class=\"sales_count\" style=\"float:right;\">\n                        已有<em>${obj.userCount}</em>人购买\n                  </div>\n    </div>    \n  </div>\n\n</div>               \n  \n\n  <div class=\"addr\">\n    <div style=\"font-size:12px;display:block;\">\n      <strong></strong>${obj1.address}\n    </div>\n    <div style=\"height:10px;\">\n    </div>\n  </div>\n</div>\n";
		addInfoWindow(marker,info);
  	
  	var str = '';
var myDate = new Date();
var startTime = myDate.getTime(); 
var endTime = ${obj.endTime1};
if (startTime>=endTime){
	jQuery('#cd${obj.id}').html("\n已经结束。\n");
}else{
new CD({startTime:startTime, endTime:endTime, callback:function(day, hour, minute, second){
str = '' + (day != 0 ? day + '天' : '') + formatNum(hour) + '时' + formatNum(minute) + '分' + (day == 0 ? formatNum(second) + '秒' : '') ;
jQuery('#cd${obj.id}').html(str);

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