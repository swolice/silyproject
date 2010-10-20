<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
<script src="<%=request.getContextPath()%>/js/bmsPage.js" type="text/javascript"></script>
<script src="js/pageEffect.js"></script>
<script>
	function comment(){
		if (jQuery('#reply_content').val().length<1){
		 alert("请输入评论内容！");
		 jQuery('#reply_content').focus();
		 return false;
		}
		if (jQuery('#reply_content').val().length>1000){
		 alert("评论内容应少于1000字！");
		 jQuery('#reply_content').focus();
		 return false;
		}
		
		jQuery.ajax({
			type:'post',
			url : 'index.do?method=comment',
			data : {'content':jQuery('#reply_content').val(),'id':'${obj.id}','type':1},
			dataType : 'html',
			success : function(data){
				var tr=jQuery("<tr></tr>");
				var td=jQuery("<td></td>");
				td.html(data);
				td.appendTo(tr);
				jQuery("#c1").prepend(tr);
				jQuery('#reply_content').val("");
				//var count=parseInt(jQuery("#commentCount").text());
				//jQuery("#commentCount").text(count+1);				
			},
			error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									try{
									alert(XMLHttpRequest.responseText);
									}catch(e){
									alert("评论失败！");
								 }
			}

		});
	}
	
	
  jQuery(function(){
    $form(<%=utils.FormUtil.json(request)%>,document.searchForm);
    
    jQuery("#cc").load("index.do?method=listComment&type=1&id=${obj.id}");
    
 });
</script>
</head>
<body>

<%@include file="header.jsp"%>

<div id="main">
  <div class="main_left">
  <table cellSpacing=1 cellPadding=2 width="660" border=0 align="center">
     <tr>
       <td width=" "><a href="${obj.webPage}" target="_blank"><img src="${list!=null&&!empty list?list[0].picUrl:obj.picUrl}" width="200" height="56"></a></td>
	   <td>${obj.siteName}&nbsp;&nbsp;<a href="${obj.webPage}" target="_blank">${obj.webPage}</a>
	   <br>
     <c:if test="${service!=null}">
						<img src='<mytld:serviceTag service="${service.id}"></mytld:serviceTag>'/>
					</c:if>
					 <br>
	   地址：${obj.address}
       <br>
	   电话：${obj.phone}<br/>
	   城市星级：${cityStar} &nbsp;&nbsp;城市满意度：${cityView}<br/>
	   总星级：${star} &nbsp;&nbsp;总满意度：${view}
       </td>
	 </tr>
   </table>
   <br>


   <table cellSpacing=4 cellPadding=4 width="660" border=0 align="center" style="line-height:20px;">
     <tr>
	<td colspan=2 class="title">近期团购活动</td>
	</tr>
	<tr>
	<td colspan=2 background="image/bg_line.jpg" height="1">
	</td>
	</tr>
	
	  
	    <c:if test="${list!=null&&!empty list}">
      <c:forEach var="obj1" items="${list}" varStatus="index">  
	 <tr>
       <td width=" ">
      <c:choose>
	   	<c:when test="${obj1.endTime<now}">
	   		     <c:choose>
	   	       <c:when test="${obj1.url1!=null}">
	   	       	<a href="${obj1.url1}" target="_blank" id="href${obj1.id}"><img src="${obj1.picUrl}" width="110" height="80"></a>
	   	       	</c:when>
	          <c:otherwise>
	          	<img src="${obj1.picUrl}" width="110" height="80">
	           </c:otherwise>
	           </c:choose>
	    </c:when>
	    <c:otherwise>
	    	<a href="${obj1.url}" target="_blank" id="href${obj1.id}"><img src="${obj1.picUrl}" width="110" height="80"></a>
	    </c:otherwise>
	   </c:choose>
       </td>
	   <td valign="top">【<mytld:domainTag domainId="${obj1.siteId}"></mytld:domainTag>】 
	   	<c:choose>
	   	<c:when test="${obj1.endTime<now}">
	   		【<font color="red">过期</font>】 <fmt:formatDate value="${obj1.startTime}" pattern="yyyy年M月d日"/>发布
	   			<script>
	   				jQuery(function(){
	   					jQuery("#href${obj1.id}").removeAttr("href");
	   				});
	   			</script>
	   	</c:when>
	   	<c:otherwise>
	   	<span id="countDown${obj1.id}"></span>
	   	<script type="text/javascript">
jQuery(function(){
var str = '';
var myDate = new Date();
var startTime = myDate.getTime(); 
var endTime = ${obj1.endTime1};
if (startTime>=endTime){
	jQuery('#countDown${obj1.id}').html("已经结束。");
}else{
new CD({startTime:startTime, endTime:endTime, callback:function(day, hour, minute, second){
str = '<span>' + (day != 0 ? day + '天' : '') + formatNum(hour) + '时' + formatNum(minute) + '分' + (day == 0 ? formatNum(second) + '秒' : '') + '</span>';
jQuery('#countDown${obj1.id}').html(str);
if(day == 0 && hour == 0 && minute == 0 && second == 0){
window.location.reload();
}
}});
}
function formatNum(n){
return n < 10 ? '0' + n : n;
}
})
</script>
	    </c:otherwise>
	    </c:choose>
	   	<br> 
			${obj1.title}
       </td>
	 </tr>
	       </c:forEach>
    </c:if>
     
	  
	  
	  
    <tr>
    	<td colspan="2"><mytld:turnPage name="page" action="index.do?method=site&id=${obj.id}" formName="info1Form"></mytld:turnPage></td>
    </tr>
	
 </table>

 
 <br>
   
   
   	<div class="line_tabs">
    <span class="cur_city">用户评论</span>
	</div>
	<a name="commentDiv"></a>
	<div id="cc">
	</div>


<table cellSpacing=2 cellPadding=2 width="700" border=0 align="center">
   <tr><td class="cur_city">发表评论</td></tr>
	<tr>
     <td>不超过1000个汉字<br>
<textarea name="content" id="reply_content" rows="10" cols="58"></textarea>
 </td>
	 </tr>
	 	<tr>
     <td><input type="radio" name="viewType" value="0" checked="checked">提示<input type="radio" name="viewType" value="1" >差评<input type="radio" name="viewType" value="2">好评</td>
	 </tr>
	<tr>
     <td><input type="button" onclick="comment();" value="发表评论" class='submit_button'/></td>
	 </tr>
	 </table>	
   
  </div>
 <div class="main_right">
   <table cellSpacing=0 cellPadding=0 width="236" border=0 align="center">
    <tr>
     <td><%@include file="ad.html"%>
	 </td>
	 </tr>
   </table>
  </div>
</div>
<div class="clean"></div>
<%@include file="bottom.jsp"%>
</body>
</html>