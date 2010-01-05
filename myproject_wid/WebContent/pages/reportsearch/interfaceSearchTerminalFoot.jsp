<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/interfaceCommonPackage.jsp" %>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<% 
	List flagList = new ArrayList();
	flagList.add("BUS14001_SVC14003");
	flagList.add("BUS14002_SVC14003");
	flagList.add("BUS14003_SVC14003");
%>
<html>	
<body class="blue2" onload="init()">


<form action="<%=request.getContextPath() %>/interfaceSearch.crm?" name="frm">
<center>总记录<%=cnt%>条，当前第<%=start%>条记录
 <input type="button" value="上一页"	name="up" id="up"  onclick="upPage()" style= "height:20px;width:50px " />
<input type="button" value="下一页" name="down" id="down" onclick="downPage()" style= "height:20px;width:50px " />
<!--翻页标识 -->
 <input	type="hidden" name="flag" id="flag" value="" />
 <!--返回下次查询的起始页数 -->
 <input	type="hidden" name="start" value="<%=start %>" /> 
    <!--返回下次查询的终止页数 -->
 <input	type="hidden" name="end" value="<%=end %>" /> 
   <!--显示分页页数 -->
 <input	type="hidden" name="showPage" value="<%=showPage %>" /> 
  <!--action执行的方法 -->
 <input type="hidden" name="method" value="ti_soap_msg_historyList" />
  <!--保留查询的条件 -->
 <input	type="hidden" name="tranid" value="<%=getParaVal(allMap , "tranid") %>" /> 
 <input	type="hidden" name="send_province" value="<%=getParaVal(allMap , "send_province") %>" /> 
 <input	type="hidden" name="create_time_start" value="<%=getParaVal(allMap , "create_time_start") %>" /> 
 <input	type="hidden" name="create_time_end" value="<%=getParaVal(allMap , "create_time_end") %>" /> 
 <input	type="hidden" name="order_nbr" value="<%=getParaVal(allMap , "order_nbr") %>" /> 
 <input	type="hidden" name="interface_type" value="<%=getParaVal(allMap , "interface_type") %>" /> 
 <input type="hidden" name="forwardFlg" value="<%=getParaVal(allMap ,"forwardFlg") %>" />
</center>
<br/>

	<div id="outerDiv">
		<div id="innerDiv">
<table styleClass="table_border" border="1" id="demo">
	<tr>
		<td width="1%" nowrap>序号</td>
		<td width="10%" nowrap>交易流水号</td>
		<td width="10%" nowrap>通信协议类型</td>
		<td width="10%" nowrap>省份</td>
		<td width="10%" nowrap>订单流水号</td>
		<td width="10%" nowrap>请求报文信息摘要</td>
		<td width="10%" nowrap>发送时间</td>
		<td width="10%" nowrap>接收时间</td>
		<td width="10%" nowrap>应答描述</td>
		<td width="10%" nowrap>请求报文</td>
		<td width="10%" nowrap>反馈报文</td>
		<td width="10%" nowrap>报竣情况</td>
		<td width="10%" nowrap>触发</td>
	</tr>
	<%List list = (List) request.getAttribute("list");
			if (list == null) {
				list = new ArrayList();
			}

			for (int i = 1; i <= list.size(); i++) {

				Map map = (Map) list.get(i - 1);

				%>

	<tr>
		<td><%=i%></td>
		<td><%=getDispVal(map,"tranid")%></td>
		<td><%=getDispVal(map,"interface_type")%></td>
		<td><%=getDispVal(map,"send_province")%></td>
		<td><%=getDispVal(map,"order_nbr")%></td>
		<td><%=getDispVal(map,"digestMsg")%></td>
		<td><%=getDispVal(map,"SOAP_REQUEST_DATE")%></td>
		<td><%=getDispVal(map,"SOAP_RESPONSE_DATE")%></td>
		<td><%=getDispVal(map,"SOAP_REQUEST_ERR_MSG")%></td>
		<td><textarea><%=map.get("soap_request_msg")%></textarea></td>
		<td><textarea><%=map.get("soap_response_msg")%></textarea></td>
		<td><%=getDispVal(map,"FINISHED_INFO")%></td>
		
		<td width="10%" nowrap>
		<% 
		String type = "";
		if (map.get("interface_type1") != null){
			type = (String)map.get("interface_type1");
		} 
		if(map.get("trigger").equals("0") && flagList.contains(type) ) { %>
		<a  style="cursor:hand;"  onclick="testAjax('<%=map.get("ROW_ID")%>')"><font
		color="blue">触发</font></a>
		<%} %>
		</td>
		
	</tr>
	<%}

		%>
</table>
</div>
</div>
</form>
</body>

<script>
	/**
*功能： 创建XML AJAX处理对象
*返回： AJAX对象
**/
function createXmlHttp() {  
     var xmlhttp = false;
  	 	//非IE浏览器创建XmlHttpRequest对象
    	if(window.XmlHttpRequest) {
    		 xmlhttp=new XmlHttpRequest();
    	}
    	//IE浏览器创建XmlHttpRequest对象
    	 if(window.ActiveXObject){
	   		 try{
	     		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); 
	    	} catch(e){
	    		try{
	     			xmlhttp=new ActiveXObject("msxml2.XMLHTTP");
	     		} catch(ex){}
	    	}
    	}
    	return xmlhttp;
}
/**
*功能：处理异步相应功能对应的数据显示
*     AJAX发送消息的事件处理类
*参数：功能树中节点对应的编码
**/
function ajaxSendEventHandler() {    
    this.xmlhttp = null;
    this.parameter = "";
    
    this.send =  function() {
        if(!this.xmlhttp){
         	return false;
    	}   
	    //要发送的url，用来取数据
	     url="/CRM-DEP/interfaceSearch.crm?" + this.parameter;	  
    	 var obj = new Object();
    	 obj.xmlhttp = this.xmlhttp;
	     this.xmlhttp.open("POST",url,true); 
	     
	     this.xmlhttp.onreadystatechange=function() {
	         if ( obj.xmlhttp.readyState == 4 ) {    
	             if( obj.xmlhttp.status == 200 ) {  
	               var str = obj.xmlhttp.responseText;
	             }
	          } 
	      }           
	      this.xmlhttp.send();
    }
} 
/**
* 功能： AJAX数据发送功能代理处理类
**/
function DelegateObject() {
	this.xmlHttp;
	this.parameter;
	this.requestSend=function(){
		  var sendObj = new ajaxSendEventHandler();
		  sendObj.xmlhttp = this.xmlHttp;
          sendObj.parameter = this.parameter ;
		  sendObj.send();
	}; 
}
/**
* 功能： 处理订单功能数据显示数据逻辑
* 参数： message -- 功能树的中选中的节点名称
*/
function testAjax(row_id){
	
	 var obj = new DelegateObject();
	 obj.xmlHttp = new createXmlHttp();
	 obj.parameter = "method=interfaceSearchTrigger&row_id=" + row_id;
	 obj.requestSend();
}
<%
if (list.size() != 0) {
%>
CreateScrollHeader(document.getElementById("innerDiv"), true,true);
if(document.getElementById("demo") !=null){
			anole('demo',1,'#ffffff','#f1f1f1','#d9e8fb','#B4D0F8');
}
<%
}
%>

</script>
</html>
