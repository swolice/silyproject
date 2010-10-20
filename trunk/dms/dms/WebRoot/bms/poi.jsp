<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@page import="dms.data.Info"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jstl/functions" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="description" content=" ">
<meta name="keywords" content=" ">
<title>地址</title>
<script type="text/javascript" src="../js/jquery-1.4.2g.js"></script>
<script src="../js/My97DatePicker46/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script src="../js/markers.js" ></script>
<script>
var map;
	  function initialize() {
    geocoder = new google.maps.Geocoder();
    var lat=${obj==null?(3990872):(obj.lat)}/100000;
    var lon=${obj==null?(11639728):(obj.lon)}/100000;
    var latlng = new google.maps.LatLng(lat,lon);
    var myOptions = {
      zoom: 12,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    
   google.maps.event.addListener(map, 'click', function(event) {
   	map.clearMarkers();
    addPoint(event.latLng);
  }); 
    
  }
  
  function addPoint(latlon){
  	  var marker = new google.maps.Marker({ 
      position: latlon,  
      map: map,  
      title:"新标点" 
    });
    //alert(latlon.lat()+","+latlon.lng());
    jQuery("#lat").val(parseInt(latlon.lat()*100000));
    jQuery("#lon").val(parseInt(latlon.lng()*100000));    
  }
  
  function addPoint1(lat,lon){
  	var latlng = new google.maps.LatLng(lat,lon);
  	addPoint(latlng);
  }
  
  
  jQuery(function(){
  	initialize();
  	<c:if test="${obj!=null}">
  	var latlng = new google.maps.LatLng(${obj.lat}/100000,${obj.lon}/100000);
  	addPoint(latlng);
  	map.setCenter(latlng);
  	</c:if>
  });
  
  
</script>
</head>
<body>
<br>
<%
String sinfoId=request.getParameter("infoId");
Long infoId=0l;
try{
infoId=Long.parseLong(sinfoId);
}catch(Exception e){}
request.setAttribute("infoId",infoId);
%>
<form name="addressForm" method="post" action="poi.do?method=edit" id="addressForm"> 
<input type="hidden" name="id" id="id" value="${obj.id}"/>
<input type="hidden" name="infoId" id="infoId" value="${obj!=null?(obj.infoId):(infoId)}"/>
<input type="hidden" name="lat" id="lat" value="${obj.lat}"/>
<input type="hidden" name="lon" id="lon" value="${obj.lon}"/>
<table width="90%" border="1" align="center" cellpadding="0"
	cellspacing="0" bordercolorlight=#AAAAAA bordercolordark=white>
	<tr>
		<td width="80">
			地址
		</td>
		<td>
			<input type="text" name="address" value="${obj.address}"  size="80"/>
		</td>
	</tr>
	<tr>
		<td width="80">
			显示名称
		</td>
		<td>
			<input type="text" name="viewName" value="${obj.viewName}"  size="80"/>
		</td>
	</tr>
	<tr>
		<td width="80">
			地图标点
		</td>
		<td>
			<div id="map_canvas" style="height:500px;width:800px" ></div>
		</td>
	</tr>
</table>
<p style="text-align:center"><input type="submit" name="submit1" value="提交">&nbsp;&nbsp;<input type="reset" name="reset1" value="取消">&nbsp;&nbsp;<input type="button" name="back" value="返回" onclick="history.go(-1);"></p>
</form>
</body>
</html>