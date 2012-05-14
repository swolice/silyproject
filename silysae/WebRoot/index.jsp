<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="js/station-1.1.js"></script>
	<script type="text/javascript" src="js/search_result-1.10.js"></script>
	
	<script type="text/javascript">
		jQuery(function(){
			setProvinceSelect("54499");
			changeProvince("ABJ");
			
			jQuery("#cityValue").change(function(){
				//直接引用，方便， 有跨域的问题				
				//openCity();
				//通过后台获取，源码，直接通过js打印到页面上
				//getWeatherAJAX();
				//提交form到iframe中去
				getWeatherForm();
			});
		});

		var getWeatherForm = function(){
			var url =  "http://www.weather.gov.cn/publish/forecast/" + $("#cityValue :selected").val() + "_iframe.html";
			var cityname = $("#cityValue :selected").text();
			jQuery("#cityname").val(cityname);
			jQuery("#url").val(url);
			jQuery("#w1").html(url);
			jQuery("#f1").submit();
		}
		
		var getWeatherAJAX = function(){
			var url =  "http://www.weather.gov.cn/publish/forecast/" + $("#cityValue :selected").val() + "_iframe.html";
			jQuery("#w1").html(url);
			 jQuery.ajax({
		         type: "POST",
		         url: "<%=basePath%>servlet/WeatherServlet",
		         data: "url="+url,
		         async: false,
		         dataType:"html",
		         success: function(msg){
		            jQuery(window.frames["if1"].document).find("body").html(msg);
		         }
		      });
		}
	</script>
  </head>
  
  <body>
    <form name="f1" id="f1" action="servlet/WeatherServlet" method="post" target="if1">
    <input type="hidden" id="url" name="url" />
    <input type="hidden" id="cityname" name="cityname" />
      	最新天气实况<div id="realdate"></div>
      <div style="border: 1px black solid; " >
      		<div id="tdprovince" style="float: left;">
      		
      		</div>
      		<div>
      			城市:
      			<select id="cityValue">
      		
      			</select>
      		</div>
      </div>
      <div>
      	<iframe framespacing="0" marginheight="0" frameborder="0" src="w.html" scrolling="no" id="if1" name="if1" width="100%" height="400px"></iframe>
      </div>
      <div id="w1" style="padding:8px" align="left">
      </div>
    </form>
  </body>
</html>
