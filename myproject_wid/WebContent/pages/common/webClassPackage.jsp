<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%

	Object obj = request.getSession(true).getAttribute("LOGIN_INFO_BEAN");
	if (obj == null) {
		request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
	}
 %>
