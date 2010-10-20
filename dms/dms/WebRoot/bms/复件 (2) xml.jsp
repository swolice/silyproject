<%@ page contentType="text/html; charset=utf-8" language="java" %>
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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link href="<%=request.getContextPath()%>/style/bms.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/style/style.css" rel="stylesheet" type="text/css" />
	<script src="<%=request.getContextPath()%>/js/jquery-1.4.2g.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/rule.js" type="text/javascript"></script>
	<script>
	function deleteRule(id,fid){
	location.href="rule.do?method=deleteRule&id="+id+"&fid="+fid;
	}
	
	function test(id){
		jQuery("#testView").empty();
	 jQuery.ajax({
	 	'url' : 'rule.do?method=crawl',
		'data' : {
							'id' : id
				},
		//'dataType' : 'json',
		'success' : function(data) {
			jQuery("#testView").html(data);
		 }
	 });
	
	
	}
	
	
	</script>
	<style>
	.td{
  line-height:20px;
  border-top:1px solid #FDFFFC;
  border-left:2px solid #ffffff;
  border-right:1px solid #9D9D9D;
  border-bottom:1px solid #B3B3B3;
  background:#E7E7E7;
  valign:"top"
  }
		</style>
<%!public void viewElement(Element e, PrintWriter out, int level,long id) {

//System.out.println("name:"+e.getName());
		String tagName = e.getName();
		if (tagName == null)
			return;

		try {
			if (tagName.equalsIgnoreCase("seg")) {
			//System.out.println("seg");
				String isNew = e.attributeValue("new");
				String isLoop = e.attributeValue("loop");
				String sid = e.attributeValue("ID");
				String start = e.elementText("start");
				String end = e.elementText("end");
				out.write("<div style='margin-left:" + (level + 1) + "em' >");
				out.write("<form id='form" + sid + "' name='form" + sid
						+ "' method='post' action='rule.do?method=editSeg'>");
				out.write("区块:");
				out.write("<input type='hidden' name='id' value='" + id
						+ "'/>");
				out.write("<input type='hidden' name='fid' value='" + sid
						+ "'/>");
				if (isLoop != null && isLoop.equals("0")) {
					out
							.write("是否循环:<input type='radio' name='isLoop' value='0'  checked='true'/>是<input type='radio' name='isLoop' value='1'/>否");
				} else {
					out
							.write("是否循环:<input type='radio' name='isLoop' value='0'/>是<input type='radio' name='isLoop' value='1' checked='true'/>否");
				}
				//out.write("<br/>");
				if (isNew != null && isNew.equals("0")) {
					out
							.write("新记录:<input type='radio' name='isNew' value='0'  checked='true'/>是<input type='radio' name='isNew' value='1'/>否");
				} else {
					out
							.write("新记录:<input type='radio' name='isNew' value='0'/>是<input type='radio' name='isNew' value='1' checked='true'/>否");
				}
				//out.write("<br/>");
				out.write("开始:<input type='text' name='start' value='" + start
						+ "'/>");
				//out.write("<br/>");
				out.write("结束:<input type='text' name='end' value='" + end
						+ "'/>");
				//out.write("<br/>");
				out.write("<input type='submit' name='submit1' value='提交'>");
				out.write("<input type='button' name='add' id='xml"+sid+"' value='添加子规则' onclick='jQuery(this).addRule({\"fid\":\""+sid+"\",\"ftype\":0,\"init\":1,\"id\":"+id+"});'/>");
				out.write("<input type='button' name='add' id='xml_"+sid+"' value='添加后续规则' onclick='jQuery(this).addRule({\"fid\":\""+sid+"\",\"ftype\":1,\"init\":1,\"id\":"+id+"});'/>");
				out.write("<input type='button' name='add' id='xml1"+sid+"' value='删除' onclick='deleteRule("+id+",\""+sid+"\");'/>");
				out.write("</form>");
				
				//out.write("<br/>");
				//out.write("<br/>");
				

				Iterator it = e.elementIterator();
				while (it.hasNext()) {
					viewElement((Element) it.next(), out, level + 1,id);
				}

				out.write("</div>");

			} else if (tagName.equalsIgnoreCase("value")) {
				String sid = e.attributeValue("ID");
				String name = e.elementText("name");
				String reg = e.elementText("reg");
				reg = HtmlUtils.htmlEscape(reg);
				out.write("<div style='margin-left:" + (level + 1) + "em' >");
				out.write("<form id='form" + sid + "' name='form" + sid
						+ "' method='post' action='rule.do?method=editValue'>");

                out.write("设值:");
				out.write("<input type='hidden' name='id' value='" + id
						+ "'/>");
				out.write("<input type='hidden' name='fid' value='" + sid
						+ "'/>");
				out.write("字段:");
				try {
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
				} catch (Exception ex) {
					ex.printStackTrace();
					try {
						out.write("选择标签出错!" + ex.toString());
					} catch (Exception ex1) {
						ex1.printStackTrace();
					}
				}
				//out.write("<br/>");
				out.write("正则:<input type='text' name='reg' value='" + reg
						+ "'/>");
				//out.write("<br/>");
				out.write("<input type='submit' name='submit1' value='提交'>");
				out.write("<input type='button' name='add' id='xml_"+sid+"' value='添加后续规则' onclick='jQuery(this).addRule({\"fid\":\""+sid+"\",\"ftype\":1,\"init\":1,\"id\":"+id+"});'/>");
				out.write("<input type='button' name='add' id='xml1"+sid+"' value='删除' onclick='deleteRule("+id+",\""+sid+"\");'/>");
				out.write("</form>");
								
				//out.write("<br/>");
				//out.write("<input type='button' name='add' id='xml"+sid+"' value='添加子规则' onclick='jQuery(this).addRule({\"fid\":"+sid+",\"ftype\":0,\"init\":1});'/>");
				//out.write("<br/>");
				
				out.write("</div>");

			} else if (tagName.equalsIgnoreCase("spider")) {
				String isNew = e.attributeValue("new");
				String sid = e.attributeValue("ID");
				String reg = e.elementText("reg");
				reg = HtmlUtils.htmlEscape(reg);

				out.write("<div style='margin-left:" + (level + 1) + "em' >");
				out.write("<form id='form" + sid + "' name='form" + sid
						+ "' method='post' action='rule.do?method=editSpider'>");
				out.write("抓取:");
				out.write("<input type='hidden' name='id' value='" + id
						+ "'/>");
				out.write("<input type='hidden' name='fid' value='" + sid
						+ "'/>");
				if (isNew != null && isNew.equals("0")) {
					out
							.write("新记录:<input type='radio' name='isNew' value='0'  checked='true'/>是<input type='radio' name='isNew' value='1'/>否");
				} else {
					out
							.write("新记录:<input type='radio' name='isNew' value='0'/>是<input type='radio' name='isNew' value='1' checked='true'/>否");
				}
				//out.write("<br/>");
				out.write("正则:<input type='text' name='reg' value='" + reg
						+ "'/>");
				//out.write("<br/>");
				out.write("<input type='submit' name='submit1' value='提交'>");
				out.write("<input type='button' name='add' id='xml"+sid+"' value='添加子规则' onclick='jQuery(this).addRule({\"fid\":\""+sid+"\",\"ftype\":0,\"init\":1,\"id\":"+id+"});'/>");
				out.write("<input type='button' name='add' id='xml_"+sid+"' value='添加后续规则' onclick='jQuery(this).addRule({\"fid\":\""+sid+"\",\"ftype\":1,\"init\":1,\"id\":"+id+"});'/>");
				out.write("<input type='button' name='add' id='xml1"+sid+"' value='删除' onclick='deleteRule("+id+",\""+sid+"\");'/>");
				out.write("</form>");
				
				//out.write("<br/>");
				//out.write("<br/>");

				Iterator it = e.elementIterator();
				while (it.hasNext()) {
					viewElement((Element) it.next(), out, level + 1,id);
				}

				out.write("</div>");

			}else{
				Iterator it = e.elementIterator();
				while (it.hasNext()) {
					viewElement((Element) it.next(), out, level + 1,id);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}%>	
</head>
<body>
<%
	String url = "";
	String charset = "UTF-8";
	String city = "";

	Rules rule = (Rules) request.getAttribute("obj");
	if (rule != null && rule.getRuleXml() != null) {
		InputStream in = new ByteArrayInputStream(rule.getRuleXml()
				.getBytes("UTF-8"));
		Document document = new SAXReader().read(in);

		Element root = document.getRootElement();

		url = root.elementText("url");
		if (url==null) url="";
		charset = root.elementText("charset");
		if (charset==null) charset="";
		city = root.elementText("city");
%>
<form name="xmlForm" id="xmlForm" action="rule.do?method=editRule" method="post">
<input type="hidden" name="id" value="${obj.id}"/>
网站：<mytld:domainSelectTag name="domain" domainId="${obj.domainId}"></mytld:domainSelectTag><br/>
城市:<mytld:citySelectTag name="city" cityId="${obj.city}"></mytld:citySelectTag><br/>
地址url:<input type='text' name='url' id='url' value='<%=url%>'/><br/>
编码:<input type='text' name='charset' id='charset' value='<%=charset%>'/><br/>
<input type="submit" name="submit1" value="提交"/>
</form>
<input type='button' name='add' id="xml0" value='添加子规则' onclick='jQuery(this).addRule({"fid":0,"ftype":0,"init":1,"id":${obj.id} });'/>
<%
			 	viewElement(root, response.getWriter(), 0,rule.getId());

			 	} else {
			 %>
<form name="xmlForm" id="xmlForm" action="rule.do?method=editRule" method="post">
<input type="hidden" name="id" value="${obj.id}"/>
网站：<mytld:domainSelectTag name="domain" domainId="${obj.domainId}"></mytld:domainSelectTag><br/>
城市:<mytld:citySelectTag name="city" cityId="${obj.city}"></mytld:citySelectTag><br/>
地址url:<input type='text' name='url' id='url' value='<%=url%>'/><br/>
编码:<input type='text' name='charset' id='charset' value='<%=charset%>'/><br/>
<input type="submit" name="submit1" value="提交"/>
</form>
<input type='button' name='add' id="xml0" value='添加子规则' onclick='jQuery(this).addRule({"fid":0,"ftype":0,"init":1,"id":${obj.id} });'/>
<%
	}
%>
<a href="rule.do?method=list" >返回列表</a>
<a href="#" onclick="test(${obj.id});return false;">测试抓取</a>
<div id="testView"></div>
</body>
</html>