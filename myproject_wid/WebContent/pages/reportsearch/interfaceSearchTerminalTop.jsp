<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cthq.crm.common.database.ICommonBean"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html:html>
<HEAD>
<link href="<%=request.getContextPath()%>/styles/default.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/styles/common.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/rightlayout.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/layout.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/anole.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.2.1.pack.js"></script>
<SCRIPT>
function compareTime() {
	var a = document.getElementsByTagName("create_time_start").value;
	var b = document.getElementsByTagName("create_time_end").value;

	if(b != null && b != "") {
		if(parseInt(b) < parseInt(a)) {
			alert(" 终止时间要大于起始时间!") ;
			document.all("create_time_end").focus() ;
			document.all("create_time_end").select() ;
			return false ;
		}
	}
	document.frm.target="interfaceSearchTerminalFrame";
	document.frm.method.value="ti_soap_msg_historyList";
	
	document.frm.submit();
	
	return true ;
	
}

function isInteger(str)	{
    if(str == "")
        return true;
    if(/^(\-?)(\d+)$/.test(str))
        return true;
    else
        return false;
}
		
function checkISInteger(obj){
	// 整数
	if(!isInteger(obj.value)){
		obj.className="warning" ;
		alert(" 必须为数字!") ;
		obj.focus() ;
		obj.select() ;
		return false ;
	}
	return true ;
}







</SCRIPT>	
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM Software Development Platform">
<TITLE></TITLE>
</HEAD>

<BODY class="blue2">

<FORM action="<%=request.getContextPath() %>/interfaceSearch.crm?"
	name="frm">

<CENTER>
<table width="90%" border="0" cellpadding="0" cellspacing="0"
	class="table_border">
	<tr>
		<td align="right" width="80">通信协议标识:</td>
		<td align="left"><SELECT name="interface_type" id="interface_type" style="width:140px">
			
			<OPTION value=""></OPTION>
			<OPTION value="BUS14001_SVC14003">综合VPN报竣</OPTION>
			<OPTION value="BUS14001_SVC14002">综合VPN状态查询</OPTION>
			<OPTION value="BUS14001_SVC11001">综合VPN号码状态</OPTION>
			<OPTION value="BUS14002_SVC14003">协同通信报竣</OPTION>
			<OPTION value="BUS14002_SVC14002">协同通信状态查询</OPTION>
			<OPTION value="BUS14003_SVC14003">综合办公报竣</OPTION>
			<OPTION value="BUS14003_SVC14002">综合办公状态查询</OPTION>
			
		</SELECT></td>
		<td align="right" width="80">下发省份:</td>
		<td align="left"><SELECT name="send_province" id="send_province" style="width:140px">
			<option value=""></option>
			<%ICommonBean cb = (ICommonBean)request.getAttribute("cb"); 
				if(cb.getRows() > 0) {
					for(int i = 0 ; i < cb.getRows() ; i ++) {
			%>
			<OPTION value="<%=cb.getString(i,"loc_district_id") %>"><%=cb.getString(i,"loc_distirct_name")%></OPTION>
			<%
					}
				}	
			%>


		</SELECT></td>
		<td align="right">订单流水号:</td>
		<td align="left"><INPUT type="text" name="order_nbr" id="order_nbr" maxlength="20" /></td>
	</tr>
	<tr>
		<td align="right" width="80">交易流水号:</td>
		<td align="left"><INPUT type="text" name="tranid" id="tranid" maxlength="28" /></td>
		<td align="right">起始日期:</td>
		<td align="left"><INPUT type="text" name="create_time_start" id="create_time_start"  maxlength="8"
			onblur="checkISInteger(this)" /></td>
		<td align="right" >终止日期:</td>
		<td align="left"><INPUT type="text" name="create_time_end" id="create_time_end" maxlength="8"
			value="" onblur="checkISInteger(this)" /></td>

	</tr>
	<tr>
		<td align="right" width="80"></td>
		<td align="left"></td>
		<td colspan="1" align="right">注释说明:</td>
		<td colspan="2" align="left"><FONT color="red">时间按数字格式,2008/08/08的正确格式是20080808</FONT>
		</td>
		<td><INPUT type="button" value="搜  索" onclick="compareTime()" /></td>
	</tr>
</table>
</CENTER>
<input type="hidden" name="method" value="ti_soap_msg_historyList" />
<input type="hidden" name="forwardFlg" value="interfaceSearchTerminalFoot" />
<input type="hidden" name="showPage" value="20" />




</FORM>
</BODY>
</html:html>