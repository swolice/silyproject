<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jstl/functions" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<%=request.getContextPath()%>/style/bms.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/style/style.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/js/jquery-1.4.2g.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/li.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/bmsPage.js" type="text/javascript"></script>
<script>
</script>
</head>

<body>
<a href="site.do?method=list">网站列表</a><br/>
<a href="city.do?method=list">城市列表</a><br/>
<a href="rule.do?method=list">规则列表</a><br/>
</body>
</html>
