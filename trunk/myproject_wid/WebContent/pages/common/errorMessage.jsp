
<%
	String errMsg  = "";
	if (request.getAttribute("ERROR_MESSAGE") != null) {
		errMsg= (String)request.getAttribute("ERROR_MESSAGE");
	}   
%>

 <font style="color=red"><%=errMsg%> </font>
