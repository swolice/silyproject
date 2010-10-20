<script src="/js/h.js"></script>
<table cellSpacing=0 cellPadding=2 width="98%" border=0 align="center">
  <tr>
  <td width="89" rowspan="2"><img src="image/logo.jpg" width="202" height="96"></td>
   <td width="100">
   <%
   if (request.getParameter("sCity")!=null){
   try{
		Long sc=Long.parseLong(request.getParameter("city"));   
		session.setAttribute("SESSION_CITY",sc);
   }catch(Exception e){}
   }
   
   if (session.getAttribute("SESSION_CITY")!=null){
   	
   }else{
   		session.setAttribute("SESSION_CITY",2l);	
   }
   
   request.setAttribute("SESSION_CITY",session.getAttribute("SESSION_CITY"));
   %>
   	
  <span class="cur_city"><mytld:cityTag cityId="${SESSION_CITY}"></mytld:cityTag></span>&nbsp;&nbsp;<span class="more_city"><a href="javascript:void(0)" onmouseover="jQuery('#city_list').show();"  onmouseout="jQuery('#city_list').hide();">切换城市</a>
  </span>
<%@include file="city.jsp"%>
  </td>
  <td width="260"></td>
  <td>
<div class="top_left"></div>
<div class="top_middle">
	<%
	if (session.getAttribute("SESSION_USER")==null){
	%>
	<a href="/login.jsp">登录</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="/regist.jsp">注册</a>
	<%}else{%>
	<a href="/home.jsp">我的团结客</a>
	<%}%>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="setHomepage();return false;">设为首页</a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="addCookie();return false;">收藏站点</a>
		<%
	if (session.getAttribute("SESSION_USER")!=null){
	%>
	|&nbsp;&nbsp;&nbsp;&nbsp;<a href="logout.jsp">退出</a>
	<%}%>
	</div>
  <div class="top_right"></div> 
  </td>
  </tr>
  <tr>
   <td colspan="3" valign="bottom" align="center">
   <table cellSpacing=0 cellPadding=0 width="600" border=0 align="center">
  <tr>
  <td width="171" height="29" class="Nav">
    <a href="index.do?method=list">今日团购</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="index.do?method=city&id=${SESSION_CITY}">团购大全</a>
  </td>
  <td class="Navmap">
 <a href="index.do?method=mapView">团购地图</a>
   </td>
   <td width="200"></td>
   </tr>
   </table>
  </td>
</tr>
</table>
<div class="search_left"></div>
  <div class="search_middle">
   <form action="index.do?method=<%="mapView".equals(request.getParameter("method"))?"mapView":"list"%>" method="post" name="searchForm" id="searchForm">
   	<input type="hidden" name="pd" />
   	<input type="hidden" name="dd" />
   	<input type="hidden" name="sort" />
   	<input type="hidden" name="sortType" />
   	<input type="hidden" name="type"/>
   	<input type="hidden" name="group"/>
            <strong>团购活动</strong> <input type="text" class="keyword ac_input" size="60" value="" name="key" id="key"  style="color:#999;" autocomplete="off">
			<select name="category" id="category"><option value="-1">所有团购</option><option value="0">休闲娱乐</option><option value="1">美容健康</option><option value="2">餐饮美食</option><option value="3">精品团购</option></select>
           <input type="button" class="button" value="搜索"  name="button" id="button" onclick="submitForm();" />
   	</form>    
  </div>
  <div class="search_right"></div> 
<div class="clean"></div>