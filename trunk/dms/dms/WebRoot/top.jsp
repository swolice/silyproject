<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
<div class="line_tabs">
      
        <a href="javascript:void(0)" id="sales_volume_list_button" class="tab_radius on">销量排行榜</a>
            
        <a href="javascript:void(0)" id="click_count_list_button" class="tab_radius">点击排行榜</a>
          </div>
    <div class="mod radius_bottom">
      
        <ul class="deal_ads top_deal_ads" id="sales_volume_list">
        	
      <c:if test="${list1!=null&&!empty list1}">
      <c:forEach var="obj1" items="${list1}" varStatus="index1">  
            <li ${index1.index==0?"class='no_border'":""}>
              <div class="count">
                ${obj1.userCount}
              </div>
              <div class="info" >
<a href="index.do?method=site&id=${obj1.siteId}" target="_blank">【<mytld:domainTag domainId="${obj1.siteId}"></mytld:domainTag>】</a>
<a href="index.do?method=detail&id=${obj1.id}" alt="${obj1.title}" title="${obj1.title}"><span class="intro"><mytld:stringTag value="${obj1.title}" length="35"></mytld:stringTag></span></a>
              </div>
            </li>
   </c:forEach>
</c:if>
                  </ul>
            
        <ul class="deal_ads top_deal_ads" id="click_count_list" style="display:none">
          
      <c:if test="${list2!=null&&!empty list2}">
      <c:forEach var="obj2" items="${list2}" varStatus="index2">  
            <li ${index2.index==0?"class='no_border'":""}>
              <div class="count">
                ${obj2.clickCount}
              </div>
              <div class="info" >
<a href="index.do?method=site&id=${obj2.siteId}" target="_blank">【<mytld:domainTag domainId="${obj2.siteId}"></mytld:domainTag>】</a>
<a href="index.do?method=detail&id=${obj2.id}" alt="${obj2.title}" title="${obj2.title}"><span class="intro"><mytld:stringTag value="${obj2.title}" length="35"></mytld:stringTag></span></a>
              </div>
            </li>
  </c:forEach>
</c:if>            
       
                  </ul>
          </div>
  
  <script type="text/javascript">
    jQuery("#sales_volume_list_button").click(function(){
      jQuery('#sales_volume_list').show();
      jQuery('#click_count_list').hide();
      jQuery('#sales_volume_list_button').addClass('on');
      jQuery('#click_count_list_button').removeClass('on');
    });
    jQuery("#click_count_list_button").click(function(){
      jQuery('#sales_volume_list').hide();
      jQuery('#click_count_list').show();
      jQuery('#sales_volume_list_button').removeClass('on');
      jQuery('#click_count_list_button').addClass('on');
    });
  </script>
