<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=GB18030"
	pageEncoding="GB18030"%>
<%
	String errMsg  = "";
	if (request.getAttribute("ERROR_MESSAGE") != null) {
		errMsg= (String)request.getAttribute("ERROR_MESSAGE");
	}   
%>
<META http-equiv="Content-Type" content="text/html; charset=GB18030">
<META name="GENERATOR" content="IBM Software Development Platform">
<TITLE>ErrorException.jsp</TITLE>
</HEAD>
<BODY>
<P><font style="color=red"><TEXTAREA style="width:100%;overflow-y:visible;overflow-x:auto"><%=errMsg%></TEXTAREA> </font>
</P>
</BODY>
</HTML>
