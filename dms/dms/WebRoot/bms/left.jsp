<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<sql:setDataSource dataSource="proxool.palmtech_stat"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>siderbar-left</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<LINK REL="stylesheet" TYPE="text/css" HREF="../style/style.css" />
<link href="../style/navbar-pix.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="../js/li_navbar.js"></script>

<style type="text/css">
html {
overflow-x:hidden!important;
*overflow-x:hidden;
}

</style>
</head>
<body>

<script language="JavaScript" type="text/JavaScript">
var show = true;
var hide = false;
//修改菜单的上下箭头符号
function my_on(head,body)
{
	var tag_a;
	for(var i=0;i<head.childNodes.length;i++)
	{
		if (head.childNodes[i].nodeName=="A")
		{
			tag_a=head.childNodes[i];
			break;
		}
	}
	tag_a.className="on";
}
function my_off(head,body)
{
	var tag_a;
	for(var i=0;i<head.childNodes.length;i++)
	{
		if (head.childNodes[i].nodeName=="A")
		{
			tag_a=head.childNodes[i];
			break;
		}
	}
	tag_a.className="off";
}

</script>

<div id="left_tit"><img src="../images/left_tit1.gif" alt="" /></div>

<noscript title="系统提示">
<div class="tit" id="menu0" ><a href="" class="on"  title="折叠菜单" name="nojs" id="nojs" >系统提示</a> </div>
<div class="list" id="menu0_child">
  <p><strong>您的设备不支持js,不能使用菜的折叠功能</strong></p>
</div>
</noscript>

<div id="menu">
    <div class="tit" id="menu1" title="菜单标题">
		<div class="titpic" id="pc1"></div>
		<a href="#nojs" title="折叠菜单" target="" class="on" id="menu1_a" tabindex="1" >网站管理</a> </div>
	  <div class="list" id="menu1_child" >
		<ul>
			<li><a href="site.do?method=list" target="mainFrame"><img src="../images/ico/2.gif" alt="" />网站列表</a></li>
			<li><a href="site.jsp" target="mainFrame"><img src="../images/ico/2.gif" alt="" />添加网站</a></li>
    </ul>
	</div>

   <div class="tit" id="menu2" title="菜单标题">
		<div class="titpic" id="pc2"></div>
		<a href="#nojs" title="折叠菜单" target="" class="on" id="menu2_a" tabindex="2" >城市管理</a> </div>
	  <div class="list" id="menu2_child" >
		<ul>
			<li><a href="city.do?method=list" target="mainFrame"><img src="../images/ico/2.gif" alt="" />城市列表</a></li>
			<li><a href="city.jsp" target="mainFrame"><img src="../images/ico/2.gif" alt="" />添加城市</a></li>
			<li><a href="service.do?method=list" target="mainFrame"><img src="../images/ico/2.gif" alt="" />保障服务列表</a></li>
			<li><a href="service.jsp" target="mainFrame"><img src="../images/ico/2.gif" alt="" />添加保障服务</a></li>
			<li><a href="group.do?method=list" target="mainFrame"><img src="../images/ico/2.gif" alt="" />商圈列表</a></li>
			<li><a href="group.jsp" target="mainFrame"><img src="../images/ico/2.gif" alt="" />添加商圈</a></li>
    </ul>
	</div>


    <div class="tit" id="menu3" title="菜单标题">
		<div class="titpic" id="pc3"></div>
		<a href="#nojs" title="折叠菜单" target="" class="on" id="menu3_a" tabindex="3" >规则管理</a> </div>
	  <div class="list" id="menu3_child" >
		<ul>
			<li><a href="rule.do?method=list" target="mainFrame"><img src="../images/ico/2.gif" alt="" />规则列表</a></li>
			<li><a href="rule.do?method=list&activeFlag=1" target="mainFrame"><img src="../images/ico/2.gif" alt="" />无效规则</a></li>
			<li><a href="rule.jsp" target="mainFrame"><img src="../images/ico/2.gif" alt="" />添加规则</a></li>
    </ul>
	</div>

    <div class="tit" id="menu4" title="菜单标题">
		<div class="titpic" id="pc4"></div>
		<a href="#nojs" title="折叠菜单" target="" class="on" id="menu4_a" tabindex="4" >团购管理</a> </div>
	  <div class="list" id="menu4_child" >
		<ul>
			<li><a href="info.do?method=list&p=1" target="mainFrame"><img src="../images/ico/2.gif" alt="" />团购列表</a></li>
    </ul>
	</div>



    <div class="tit" id="menu5" title="菜单标题">
		<div class="titpic" id="pc5"></div>
		<a href="#nojs" title="折叠菜单" target="" class="on" id="menu5_a" tabindex="5" >站点城市管理</a> </div>
	  <div class="list" id="menu5_child" >
		<ul>
			<li><a href="cd.do?method=list" target="mainFrame"><img src="../images/ico/2.gif" alt="" />站点城市列表</a></li>
			<li><a href="cityDomain.jsp" target="mainFrame"><img src="../images/ico/2.gif" alt="" />添加站点城市</a></li>
    </ul>
	</div>
	
	
    <div class="tit" id="menu6" title="菜单标题">
		<div class="titpic" id="pc6"></div>
		<a href="#nojs" title="折叠菜单" target="" class="on" id="menu6_a" tabindex="6" >用户评论管理</a> </div>
	  <div class="list" id="menu6_child" >
		<ul>
			<li><a href="user.do?method=list" target="mainFrame"><img src="../images/ico/2.gif" alt="" />用户列表</a></li>
			<li><a href="comment.do?method=list" target="mainFrame"><img src="../images/ico/2.gif" alt="" />评论列表</a></li>
			<li><a href="complain.do?method=list" target="mainFrame"><img src="../images/ico/2.gif" alt="" />投诉列表</a></li>
    </ul>
	</div>

</div>
</body> 
<script>
	//添加菜单	
window.onload=function()
{

	m1 =new Menu("menu1",'menu1_child','dtu','100',hide,my_on,my_off);
	m1.init();

	m2 =new Menu("menu2",'menu2_child','dtu','100',hide,my_on,my_off);
	m2.init();
	
  m3 =new Menu("menu3",'menu3_child','dtu','100',hide,my_on,my_off);
	m3.init();
	
	  m4 =new Menu("menu4",'menu4_child','dtu','100',hide,my_on,my_off);
	m4.init();

	  m5 =new Menu("menu5",'menu5_child','dtu','100',hide,my_on,my_off);
	m5.init();	
		  m6 =new Menu("menu6",'menu6_child','dtu','100',hide,my_on,my_off);
	m6.init();	
}
</script>
</html> 