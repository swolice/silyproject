 <div class="line"></div>
<c:if test="${list0!=null&&!empty list0}">
<div>休闲娱乐</div><div class="page">找到相关 ${page0.sumCounts}条结果 <c:if test="${category==null}"><a href="index.do?method=list&category=0">&gt;&gt;更多</a></c:if></div>
      <c:forEach var="obj" items="${list0}" varStatus="index">  
      <div class="picture">
        <a href="index.do?method=detail&id=${obj.id}" target="_blank"><img src="${obj.picUrl}" width="150" height="110"></a>
      </div>
      <div class="content">
          <ul>
	       <li style="width:490px;">
	 [ <a href="index.do?method=site&id=${obj.siteId}" target="_blank"><mytld:domainTag domainId="${obj.siteId}"></mytld:domainTag></a> ]&nbsp;&nbsp;<a href="index.do?method=detail&id=${obj.id}" target="_blank"><span class="intro"><mytld:stringTag value="${obj.title}" length="90"></mytld:stringTag></span></a><br>现价：<span class="discount">${obj.sellPrice/100}元</span>&nbsp;&nbsp;原价：<del>${obj.orgPrice/100}元</del>&nbsp;&nbsp;折扣：<span class="discount">${obj.discount/100}折</span>
	      </li>
	     </ul>
		 <div class="clean"></div>
	     <ul>
	      <li>
     <span class="discount">已有${obj.userCount}人购买</span>&nbsp; <a href="#" onclick="promote(${obj.id},'promote${obj.id}');return false;">顶</a>（<span id='promote${obj.id}'>${obj.promoteCount}</span>）&nbsp;<a href="index.do?method=detail&id=${obj.id}#commentDiv1" target="_blank">评</a>（${obj.commentCount}）
         <div class="count-down cd4" id="countDown${obj.id}">距离结束还有：<span></span></div>
<script type="text/javascript">
jQuery(function(){
var str = '';
var myDate = new Date();
var startTime = myDate.getTime(); 
var endTime = ${obj.endTime1};
if (startTime>=endTime){
	jQuery('#countDown${obj.id}').html("已经结束。");
}else{
new CD({startTime:startTime, endTime:endTime, callback:function(day, hour, minute, second){
str = '距离结束还有：<span>' + (day != 0 ? day + '天' : '') + formatNum(hour) + '时' + formatNum(minute) + '分' + (day == 0 ? formatNum(second) + '秒' : '') + '</span>';
jQuery('#countDown${obj.id}').html(str);
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
          </li>
	      <li class="buy-button"><a href="${obj.url1==null?obj.url:obj.url1}" target="_blank"><img src="image/buyicon.jpg" width="50" height="50" alt="购买"></a></li>
        </ul>
      </div>
      <div class="clean"></div>
      
      
      <div class="line"></div>
      </c:forEach>
    </c:if>
    
        <c:if test="${list1!=null&&!empty list1}">
    	<div>美容健康</div><div class="page">找到相关 ${page1.sumCounts} 条结果<c:if test="${category==null}"><a href="index.do?method=list&category=1">&gt;&gt;更多</a></c:if></div>
      <c:forEach var="obj" items="${list1}" varStatus="index">  
      <div class="picture">
        <a href="index.do?method=detail&id=${obj.id}" target="_blank"><img src="${obj.picUrl}" width="150" height="110"></a>
      </div>
      <div class="content">
          <ul>
	       <li style="width:490px;">
	 [ <a href="index.do?method=site&id=${obj.siteId}" target="_blank"><mytld:domainTag domainId="${obj.siteId}"></mytld:domainTag></a> ]&nbsp;&nbsp;<a href="index.do?method=detail&id=${obj.id}" target="_blank"><span class="intro"><mytld:stringTag value="${obj.title}" length="90"></mytld:stringTag></span></a><br>现价：<span class="discount">${obj.sellPrice/100}元</span>&nbsp;&nbsp;原价：<del>${obj.orgPrice/100}元</del>&nbsp;&nbsp;折扣：<span class="discount">${obj.discount/100}折</span>
	      </li>
	     </ul>
		 <div class="clean"></div>
	     <ul>
	      <li>
     <span class="discount">已有${obj.userCount}人购买</span>&nbsp; <a href="#" onclick="promote(${obj.id},'promote${obj.id}');return false;">顶</a>（<span id='promote${obj.id}'>${obj.promoteCount}</span>）&nbsp;<a href="index.do?method=detail&id=${obj.id}#commentDiv1" target="_blank">评</a>（${obj.commentCount}）
         <div class="count-down cd4" id="countDown${obj.id}">距离结束还有：<span></span></div>
<script type="text/javascript">
jQuery(function(){
var str = '';
var myDate = new Date();
var startTime = myDate.getTime(); 
var endTime = ${obj.endTime1};
if (startTime>=endTime){
	jQuery('#countDown${obj.id}').html("已经结束。");
}else{
new CD({startTime:startTime, endTime:endTime, callback:function(day, hour, minute, second){
str = '距离结束还有：<span>' + (day != 0 ? day + '天' : '') + formatNum(hour) + '时' + formatNum(minute) + '分' + (day == 0 ? formatNum(second) + '秒' : '') + '</span>';
jQuery('#countDown${obj.id}').html(str);
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
          </li>
	      <li class="buy-button"><a href="${obj.url1==null?obj.url:obj.url1}" target="_blank"><img src="image/buyicon.jpg" width="50" height="50" alt="购买"></a></li>
        </ul>
      </div>
      <div class="clean"></div>
      
      
      <div class="line"></div>
      </c:forEach>
    </c:if>
    
    
           <c:if test="${list2!=null&&!empty list2}">
    	<div>餐饮美食</div><div class="page">找到相关 ${page2.sumCounts} 条结果 <c:if test="${category==null}"><a href="index.do?method=list&category=2">&gt;&gt;更多</a></c:if></div>
      <c:forEach var="obj" items="${list2}" varStatus="index">  
      <div class="picture">
        <a href="index.do?method=detail&id=${obj.id}" target="_blank"><img src="${obj.picUrl}" width="150" height="110"></a>
      </div>
      <div class="content">
          <ul>
	       <li style="width:490px;">
	 [ <a href="index.do?method=site&id=${obj.siteId}" target="_blank"><mytld:domainTag domainId="${obj.siteId}"></mytld:domainTag></a> ]&nbsp;&nbsp;<a href="index.do?method=detail&id=${obj.id}" target="_blank"><span class="intro"><mytld:stringTag value="${obj.title}" length="90"></mytld:stringTag></span></a><br>现价：<span class="discount">${obj.sellPrice/100}元</span>&nbsp;&nbsp;原价：<del>${obj.orgPrice/100}元</del>&nbsp;&nbsp;折扣：<span class="discount">${obj.discount/100}折</span>
	      </li>
	     </ul>
		 <div class="clean"></div>
	     <ul>
	      <li>
     <span class="discount">已有${obj.userCount}人购买</span>&nbsp; <a href="#" onclick="promote(${obj.id},'promote${obj.id}');return false;">顶</a>（<span id='promote${obj.id}'>${obj.promoteCount}</span>）&nbsp;<a href="index.do?method=detail&id=${obj.id}#commentDiv1" target="_blank">评</a>（${obj.commentCount}）
         <div class="count-down cd4" id="countDown${obj.id}">距离结束还有：<span></span></div>
<script type="text/javascript">
jQuery(function(){
var str = '';
var myDate = new Date();
var startTime = myDate.getTime(); 
var endTime = ${obj.endTime1};
if (startTime>=endTime){
	jQuery('#countDown${obj.id}').html("已经结束。");
}else{
new CD({startTime:startTime, endTime:endTime, callback:function(day, hour, minute, second){
str = '距离结束还有：<span>' + (day != 0 ? day + '天' : '') + formatNum(hour) + '时' + formatNum(minute) + '分' + (day == 0 ? formatNum(second) + '秒' : '') + '</span>';
jQuery('#countDown${obj.id}').html(str);
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
          </li>
	      <li class="buy-button"><a href="${obj.url1==null?obj.url:obj.url1}" target="_blank"><img src="image/buyicon.jpg" width="50" height="50" alt="购买"></a></li>
        </ul>
      </div>
      <div class="clean"></div>
      
      
      <div class="line"></div>
      </c:forEach>
    </c:if>
    
    
        
      <c:if test="${list3!=null&&!empty list3}">
    	<div>精品团购</div><div class="page">找到相关 ${page3.sumCounts} 条结果 <c:if test="${category==null}"><a href="index.do?method=list&category=3">&gt;&gt;更多</a></c:if></div>
      <c:forEach var="obj" items="${list3}" varStatus="index">  
      <div class="picture">
        <a href="index.do?method=detail&id=${obj.id}" target="_blank"><img src="${obj.picUrl}" width="150" height="110"></a>
      </div>
      <div class="content">
          <ul>
	       <li style="width:490px;">
	 [ <a href="index.do?method=site&id=${obj.siteId}" target="_blank"><mytld:domainTag domainId="${obj.siteId}"></mytld:domainTag></a> ]&nbsp;&nbsp;<a href="index.do?method=detail&id=${obj.id}" target="_blank"><span class="intro"><mytld:stringTag value="${obj.title}" length="90"></mytld:stringTag></span></a><br>现价：<span class="discount">${obj.sellPrice/100}元</span>&nbsp;&nbsp;原价：<del>${obj.orgPrice/100}元</del>&nbsp;&nbsp;折扣：<span class="discount">${obj.discount/100}折</span>
	      </li>
	     </ul>
		 <div class="clean"></div>
	     <ul>
	      <li>
     <span class="discount">已有${obj.userCount}人购买</span>&nbsp; <a href="#" onclick="promote(${obj.id},'promote${obj.id}');return false;">顶</a>（<span id='promote${obj.id}'>${obj.promoteCount}</span>）&nbsp;<a href="index.do?method=detail&id=${obj.id}#commentDiv1" target="_blank">评</a>（${obj.commentCount}）
         <div class="count-down cd4" id="countDown${obj.id}">距离结束还有：<span></span></div>
<script type="text/javascript">
jQuery(function(){
var str = '';
var myDate = new Date();
var startTime = myDate.getTime(); 
var endTime = ${obj.endTime1};
if (startTime>=endTime){
	jQuery('#countDown${obj.id}').html("已经结束。");
}else{
new CD({startTime:startTime, endTime:endTime, callback:function(day, hour, minute, second){
str = '距离结束还有：<span>' + (day != 0 ? day + '天' : '') + formatNum(hour) + '时' + formatNum(minute) + '分' + (day == 0 ? formatNum(second) + '秒' : '') + '</span>';
jQuery('#countDown${obj.id}').html(str);
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
          </li>
	      <li class="buy-button"><a href="${obj.url1==null?obj.url:obj.url1}" target="_blank"><img src="image/buyicon.jpg" width="50" height="50" alt="购买"></a></li>
        </ul>
      </div>
      <div class="clean"></div>
      
      
      <div class="line"></div>
      </c:forEach>
    </c:if>