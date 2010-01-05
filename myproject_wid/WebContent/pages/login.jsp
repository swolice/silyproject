<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%String path = request.getContextPath();			
 Object initObj = request.getAttribute("PAGE_INIT_VALUE");
 String userId = "";
 String errMsg = "";
 if (initObj != null && initObj instanceof Map) {
 	 Map paramMap = (Map) initObj;
	 if ( null != paramMap.get("USER_ID")) {
	 	userId = (String)paramMap.get("USER_ID");
	 }
	 if ( null != paramMap.get("ERROR_MSG")) {
	 	errMsg = (String)paramMap.get("ERROR_MSG");
	 }
	
 }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<TITLE>中国电信集团级CRM系统</TITLE>
<style type="text/css">
<!--
BODY {
	BACKGROUND-COLOR: #92c9f2; PADDING-BOTTOM: 0px; MARGIN: 0px; FONT: 12px Verdana, Arial, Helvetica, sans-serif; VERTICAL-ALIGN: middle; COLOR: #000000; PADDING-TOP: 0px; align: center
}
	
TD {
	FONT-SIZE: 12px; LINE-HEIGHT: 25px
}
.textfield {
	BORDER-RIGHT: #108abd 1px solid; BORDER-TOP: #108abd 1px solid; BORDER-LEFT: #108abd 1px solid; COLOR: #333333; BORDER-BOTTOM: #108abd 1px solid; width:150px; HEIGHT: 15px; BACKGROUND-COLOR: #ffffff; TEXT-DECORATION: none
}
.red {
	COLOR: #ff0000
}

.style1 {
	text-align: right;
}

-->
</style>
<script>
	function goLogin(){
		document.login.submit();
	}
</script>


<META content="MSHTML 6.00.2900.3199" name=GENERATOR>
</HEAD>
<BODY onload = "">
<form action="<%=path %>/login.crm?method=login"
	method="post" name="login"><input type="hidden" name="order"
	value="/pages/order/req_manage/index.jsp">
<TABLE height=612 cellSpacing=0 width="100%" background=<%=path %>
	/pages/img/login_back.jpg border=0>
	<TBODY>
		<TR>
			<TD><BR>
			<BR>
			<TABLE height=509 cellSpacing=0 width=748 align=center
				background=<%=path %>/pages/img/login_main.jpg border=0>
				<TBODY>
					<TR>
						<TD vAlign=top>
						<TABLE height=308 cellSpacing=0 width=636 border=0>
							<TBODY>
								<TR>
									<TD width=437 height=162 rowSpan=2>&nbsp;</TD>
									<TD width=41>&nbsp;</TD>
									<TD colSpan=2><BR>
									<BR>
									<BR>
									<BR>
									<BR>
									</TD>
								</TR>
								<TR>
									<TD >&nbsp;</TD>
									<TD class=red NOWRAP colSpan=2><%=errMsg%></TD>
								<TR>
									<TD height=25>&nbsp;</TD>
									<TD NOWRAP align="right">用户名:</TD>
									<TD colSpan=2><INPUT type="text" class=textfield maxLength=40
										size=18 name="UID" id="user_id" value="<%=userId%>"></TD>
								</TR>


								<TR>
									<TD>&nbsp;</TD>
									<TD align="right">密码:</TD>
									<TD colSpan=2><INPUT class=textfield type=password maxLength=40
										size=19 name="password" id="password" value=""></TD>
								</TR>
								
								<TD>&nbsp;</TD>
								<TD rowSpan=2>&nbsp;</TD>
								<TD borderColor=#0 width=75 rowSpan=2>
								<div class="style1"><input type="image" onclick="goLogin();"
									src="<%=path %>/pages/img/login.jpg" width=38 border=0 /></div>
								</TD>
								<TD width=75 rowSpan=2></TD>
								
								
							</TBODY>
							
						</TABLE>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>

</form>
</BODY>
</HTML>
