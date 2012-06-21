<%@ page language="java" import="java.util.*,com.swjsj.silysae.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'timer.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
	<script>
	jQuery(function(){
		jQuery("#f1").submit();
	});
	</script>
  </head>
  
  <body>
    <%
   //XmlReaderByJsoup.sendMail("昌平");
    %>
    <form name="f1" id="f1" action="servlet/WeatherServlet" method="post" target="if1">
    <input type="hidden" id="url" name="url" value="http://www.weather.gov.cn/publish/forecast/ABJ/changping_iframe.html"/>
    <input type="hidden" id="cityname" name="cityname" value="昌平"/>
    </form>
    <div>
     	<iframe framespacing="0" marginheight="0" frameborder="0" src="w.html" scrolling="no" id="if1" name="if1" width="100%" height="400px"></iframe>
     </div>
  </body>
</html>
