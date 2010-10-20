<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
<div class="line_tabs">
    <a href="javascript:void(0)" id="latest_deal_comments_button" class="tab_radius on">最新团购评论</a>
    <a href="javascript:void(0)" id="latest_site_comments_button" class="tab_radius">最新网站评论</a>
  </div>
  <div class="mod radius_bottom" >

    
      <ul class="deal_ads comment_ads on" id="latest_deal_comments">
        
      <c:if test="${list1!=null&&!empty list1}">
      <c:forEach var="obj1" items="${list1}" varStatus="index1">  
            <li ${index1.index==0?"class='no_border'":""}>
<div class="image">
  <a href="index.do?method=detail&id=${obj1[0].id}" title="${obj1[0].title}" target="_blank"><img src="${obj1[0].picUrl}" alt="${obj1[0].title}" class="ad_image"></a>
</div>
<div class="info" >
  <a href="index.do?method=detail&id=${obj1[0].id}" title="${obj1[1].content}" alt="${obj1[1].content}" target="_blank">${obj1[1].content}</a>
</div>

          </li>
             </c:forEach>
</c:if>
              </ul>
    
    
      <ul class="deal_ads comment_ads" id="latest_site_comments" style="display:none">
        
      <c:if test="${list2!=null&&!empty list2}">
      <c:forEach var="obj2" items="${list2}" varStatus="index2">  
            <li ${index2.index==0?"class='no_border'":""}>
            
<div class="image">
  <a href="index.do?method=site&id=${obj2[0].id}" title="${obj2[0].siteName}" target="_blank">
    <img class="ad_image" src="${obj2[0].picUrl}" alt="${obj2[0].siteName}"/>
  </a>
</div>
<div class="info" >
  <a href="index.do?method=site&id=${obj2[0].id}" title="${obj2[1].content}" alt="${obj2[1].content}" target="_blank">${obj2[1].content}</a>
</div>
</li>
</c:forEach>
</c:if> 
              </ul>
      </div>
  <script type="text/javascript">
    jQuery("#latest_deal_comments_button").click(function(){
      jQuery('#latest_deal_comments').show();
      jQuery('#latest_site_comments').hide();
      jQuery('#latest_deal_comments_button').addClass('on');
      jQuery('#latest_site_comments_button').removeClass('on');
    });
    jQuery("#latest_site_comments_button").click(function(){
      jQuery('#latest_deal_comments').hide();
      jQuery('#latest_site_comments').show();
      jQuery('#latest_deal_comments_button').removeClass('on');
      jQuery('#latest_site_comments_button').addClass('on');
    });
  </script>
