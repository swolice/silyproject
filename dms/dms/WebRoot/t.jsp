
<%@ page contentType="text/html; charset=GBK" %>
<%@page import="dms.data.Sites"%>
<%@page import="dms.data.Rules"%>
<%@page import="dms.data.City"%>
<%@page import="java.util.List"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.Element"%>
<%@page import="org.dom4j.io.SAXReader"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.Iterator"%>
<%@page import="util.DbUtil"%>
<%@page import="org.springframework.web.util.HtmlUtils"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>
<%
String name="";
StringBuffer buf = new StringBuffer();

					buf.append("<select name='").append("name").append("' ");
					buf.append(">");

					String sql = "select column_name, column_comment from information_schema.columns where table_schema ='tuan'  and table_name = 'info'";
					List list = DbUtil.getDao().list(sql, null, -1, -1);

					for (Object o : list) {
						Object[] os = (Object[]) o;
						buf.append("<option value='").append(os[0])
								.append("' ");

						if (name != null
								&& name.equalsIgnoreCase(os[0].toString())) {
							buf.append(" selected='true' ");
						}

						buf.append(">").append(os[1]).append("</option>");
					}

					buf.append("</select>");

					out.write(buf.toString());
%>