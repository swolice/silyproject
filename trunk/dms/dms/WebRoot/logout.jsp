<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
<%
session.removeAttribute("SESSION_USER");
%>
<script>
	location.href="index.do?method=list";
</script>