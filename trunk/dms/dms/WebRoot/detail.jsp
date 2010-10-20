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
<script src="js/pageEffect.js"></script>

<script>
	function comment(){
		jQuery.ajax({
			type:'post',
			url : 'index.do?method=comment',
			data : {'content':jQuery('#reply_content').val(),'id':'${obj.id}','type':0},
			dataType : 'html',
			success : function(data){
				var tr=jQuery("<tr></tr>");
				var td=jQuery("<td></td>");
				td.html(data);
				td.appendTo(tr);
				jQuery("#c1").prepend(tr);
				jQuery('#reply_content').val("");
				var count=parseInt(jQuery("#commentCount").text());
				jQuery("#commentCount").text(count+1);
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
    
    jQuery("#cc").load("index.do?method=listComment&type=0&id=${obj.id}");
    
 });
</script>
</head>
<body>

<%@include file="header.jsp"%>

<div id="main">
  <div class="main_left">
   <table cellSpacing=0 cellPadding=0 width="702" border=0 align="center">
    <tr>
	  <td background="image/pic04.jpg" width="8" height="30">	  </td>
	  <td background="image/pic05.jpg" width="687" class="title">&nbsp;&nbsp;&nbsp;&nbsp;团购详细</td>
	  <td background="image/pic06.jpg" width="7" height="30">	  </td>
	</tr>
	<tr>
	  <td colspan="3"  background="image/pic07.jpg">
        <table cellSpacing=1 cellPadding=5 width="700" border=0 align="center" bgcolor="#ECF5F8">
     <tr>
       <td colspan="2">
        <h2><FONT color="#ff0000"><mytld:cityTag cityId="${obj.city}"></mytld:cityTag></FONT>今日团购: ${obj.title}</h2>
        </td>   
	 </tr>
	 <tr>
	 <td><img src="${obj.picUrl}" />
	 <br>该团购活动由 <a href="index.do?method=site&id=${obj.siteId}" target="_blank"><mytld:domainTag domainId="${obj.siteId}"></mytld:domainTag></a> 组织，参与此团购将会与<mytld:domainTag domainId="${obj.siteId}"></mytld:domainTag>产生直接买卖关系。
	 </td>
	 <td valign="top" align="left" width="300">
   <div class="buy">￥<fmt:formatNumber pattern="###,###.##">${obj.sellPrice/100}</fmt:formatNumber>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${obj.url1==null?obj.url:obj.url1}" target="_blank"><img src="image/pic171.jpg" width="85px" height="43px" align="absmiddle"></a></div>
   <table width="100%">
   <tr><td colspan="3" class="discount"><h3>已有${obj.userCount}人购买</h3></td></tr>
   <tr><td width="33%" align="center">原价</td><td width="34%" align="center">折扣</td><td width="33%" align="center">节省金额</td></tr>
   <tr><td align="center"><h3><del><fmt:formatNumber pattern="###,###.##">${obj.orgPrice/100}</fmt:formatNumber>元</del></h3></td>
   	<td align="center"><h3><fmt:formatNumber pattern="###,###.##">${obj.discount/100}</fmt:formatNumber>折</h3></td>
   		<td align="center"><h3><fmt:formatNumber pattern="###,###.##">${(obj.orgPrice-obj.sellPrice)/100}</fmt:formatNumber>元</h3></td></tr></table>
	<div class="count-down cd4" id="cdcd">距离结束还有：<span></span></div>
<script type="text/javascript">
jQuery(function(){
var str = '';
var myDate = new Date();
var startTime = myDate.getTime(); 
var endTime = ${obj.endTime1};
if (startTime>=endTime){
	jQuery('#cdcd').html("卖光了。");
}else{
new CD({startTime:startTime, endTime:endTime, callback:function(day, hour, minute, second){
str = '距离结束还有：<span>' + (day != 0 ? day + '天' : '') + formatNum(hour) + '时' + formatNum(minute) + '分' + (day == 0 ? formatNum(second) + '秒' : '') + '</span>';
jQuery('#cdcd').html(str);
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
<br>
<a href="#" onclick="promote(${obj.id},'promote${obj.id}');return false;">顶</a>（<span id='promote${obj.id}'>${obj.promoteCount}</span>）&nbsp;&nbsp;已有评论(<a href="#commentDiv" id="commentCount">${obj.commentCount}</a>)&nbsp;&nbsp;<a href="#" onclick="jQuery('#reply_content').focus();return false;" target="_blank">我要评论</a>&nbsp;&nbsp;<a href="feedback.jsp?id=${obj.id}&type=0" target="_blank">我要投诉</a><br> 
<!--a href="feedback.htm" target="_blank">我要举报</a-->	 
	 </td>
	</tr>
	 <tr>
       <td class="title_text">
<!--h4>北京蕉叶（三元桥店）</h4-->
			<div>
				<c:choose>
					<c:when test="${obj.service>0}">
						<img src='<mytld:serviceTag service="${obj.service}"></mytld:serviceTag>'/>
					</c:when>
					<c:when test="${service!=null}">
						<img src='<mytld:serviceTag service="${service.id}"></mytld:serviceTag>'/>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				<br/>
				
				${obj.address1}<br />
								电话： ${obj.phone}<br />
												网址： <a href="${obj.url}" target="_blank">${obj.url}</a><br />
												</div>
	</td>
	<td>
	<!--b>特别提示：</b><br> 
有效期截止至2010年10月30日<br-->
<b>友情提示</b><br>
目前国内的团购网站良莠不齐，为了避免上当受骗，提醒您注意...

	</td>
	</tr>
</table>
	</td>
	</tr>
	<tr>
	  <td colspan=3 background="image/pic10.jpg" width="702" height="12"></td>
	</tr>
	</table>
	
	
	
	<div class="line_tabs">
    <span class="cur_city">团购活动评论</span>
	</div>
	<a name="commentDiv"></a>
	<div id="cc">
	</div>


<table cellSpacing=2 cellPadding=2 width="700" border=0 align="center">
   <tr><td class="cur_city">发表评论</td></tr>
	<tr>
     <td>不超过1000个汉字<a name="commentDiv1"></a><br>
<textarea name="content" id="reply_content" rows="10" cols="58"></textarea>
 </td>
	 </tr>
	 	<tr>
     <td><input type="radio" name="viewType" value="0" checked="checked">提示<input type="radio" name="viewType" value="1" >差评<input type="radio" name="viewType" value="2" >好评</td>
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