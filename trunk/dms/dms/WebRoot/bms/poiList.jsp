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
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script src="../js/markers.js" ></script>
<script>
var map;
var bounds;
var count=0;
	  function initialize() {
    var lat=3990872/100000;
    var lon=11639728/100000;
    var latlng = new google.maps.LatLng(lat,lon);
    var myOptions = {
      zoom: 12,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    bounds = new google.maps.LatLngBounds();
  }
  
  function addPoint(latlon,icon,title){
  	  var marker = new google.maps.Marker({
      position: latlon,  
      map: map,  
      icon:icon,
      title:title
    });
  }
  
  
  jQuery(function(){
  	initialize();
  });
  
  
</script>
</head>
<body style="background:#deeef6;">
<div>
  <div>
      <table width="100%" cellpadding="0" cellspacing="0" >
        <tr>
          <td width="25" height="23"><img src="../images/list_ico.gif" alt="" width="20" height="23" /></td>
          <td><span class="bold">地址列表</span></td>
          <td></td>
          <td width="120"><a href="info.do?method=list&p=1">团购列表</a>&nbsp;&nbsp;<a href="poi.jsp?infoId=${infoId}">新增</a></td>
        </tr>
      </table>
 <table width="100%" border="1" cellpadding="0" cellspacing="0">
 <tr>
<th>地址</th>
<th>经度</th>
<th>纬度</th>
<th>商家名称</th>
<th>标点</th>
<th>操作</th>
</tr>
    <c:if test="${list!=null&&!empty list}">
      <c:forEach var="obj" items="${list}" varStatus="index">      
<tr>
<td>${obj.address}</td>
<td>${obj.lon}</td>
<td>${obj.lat}</td>
<td>${obj.viewName}</td>
<td>
<div style="float:left;height:32px;margin-right:4px;width:20px;background:url('../images/green.png') repeat scroll 0pt -${index.index*32}px transparent;" title=""></div>
</td>
<td>
<a href="poi.do?method=view&id=${obj.id}">编辑</a>&nbsp;&nbsp;
<a href="#" onclick='if (confirm("确认删除？")){location.href="poi.do?method=delete&id=${obj.id}";};return false;'>删除</a>&nbsp;&nbsp;
<c:if test="${obj.lat!=null&&obj.lon!=null}">
<script>
	jQuery(function(){
		var latlon=new google.maps.LatLng(${obj.lat}/100000,${obj.lon}/100000);
		var title='${obj.address}';
		var icon = new google.maps.MarkerImage("../images/green.png",
                                new google.maps.Size(20, 32),
                                new google.maps.Point(0,${index.index*32}))
		addPoint(latlon,icon,title);
		bounds.extend(latlon);
		if(count == 0)
  	{
  		map.setCenter(latlon);
  	}else
  	{
  		map.fitBounds(bounds);
  	}
	});
</script>
</c:if>
</td>
</tr>
	      </c:forEach>
    </c:if> 
  </table>

    </div>
  </div>
<p></p>

<div id="map_canvas" style="height:500px;width:800px" ></div>
<div></div>

</body>
<script>
jQuery(function(){
});
</script>

</html>
