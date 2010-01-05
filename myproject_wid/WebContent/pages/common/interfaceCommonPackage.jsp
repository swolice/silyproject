<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	String urlPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath()%>/styles/default.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/styles/common.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/styles/pagefloat.css"
	rel="stylesheet" type="text/css" />

<link href="<%=request.getContextPath()%>/pages/css/Style.css"
	rel="stylesheet" type="text/css" />
<STYLE>
table{border-top:0px solid black;border-left:0px solid black;cursor:default;}
td{border-bottom:0px solid black;border-right:0px solid black;}
th{border-bottom:0px solid black;border-right:0px solid black;background-color:#999999;}
.trOdd{background-color:#FFFFFF;}
.trEven{background-color:#CCCCCC;}
</STYLE>

<%Map allMap = (Map) request.getAttribute("allMap");
			String cnt = "0";
			String start = "0";
			String end = "0";
			String showPage = "10";
			if (allMap == null) {
				allMap = new HashMap();
			} else {
				cnt = getParaVal(allMap, "cnt");
				if("".equals(cnt))cnt="0";
				start = getParaVal(allMap, "start");
				if("".equals(start))start="0";
				end = getParaVal(allMap, "end");
				if("".equals(end))end="0";
				showPage = getParaVal(allMap, "showPage");
				if("".equals(showPage))showPage="0";
			}
			if (cnt == null || cnt.equals("")) {
				cnt = "0";
			}
			if (start == null || start.equals("")) {
				start = "0";
			}

			%>
<%!private String getParaVal(Map map, String key) {
		if (map.get(key) == null) {
			return "";
		}
		if (map.get(key) instanceof String[]) {
			String[] arrStr = (String[]) map.get(key);
			if (arrStr[0] == null) {
				return "";
			}
			if (arrStr[0].equals("null")) {
				return "";
			}
			return arrStr[0];
		}

		if (map.get(key) instanceof String) {
			return (String) map.get(key);
		}
		return "";
	}

	private String getDispVal(Map map, String key) {
		if (map.get(key) == null) {
			return " ";
		}
		if (map.get(key) instanceof String[]) {
			String[] arrStr = (String[]) map.get(key);
			if (arrStr[0] == null) {
				return " ";
			}
			if (arrStr[0].equals("null")) {
				return " ";
			}
			return arrStr[0];
		}

		if (map.get(key) instanceof String) {
			return (String) map.get(key);
		}
		return " ";
	}

	%>
	
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rightlayout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/layout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/anole.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.2.1.pack.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Script.js"></script>

<SCRIPT language="javascript">
/*********************** setTableColor.js ***********************************/
/**
* added by LxcJie 2004.6.25
* ???????????????
* oTable????? oddClass?????css?? evenClass?????css??
*/


function setRowColor(oTable,oddClass,evenClass)
{
//resetTableColor(oTable);
	for(var i=1; i<oTable.rows.length; i++)
	{
		if(i%2 == 0)
			oTable.rows[i].className = evenClass;
		else
			oTable.rows[i].className = oddClass;
	}
}

/**
* added by LxcJie 2004.6.28
* ???????????????
* oTable????? oddClass?????css?? evenClass?????css??
*/


//????tr?td???
function resetTableColor(oTable)
{
	for(var i=1; i<oTable.rows.length; i++)
	{ 
		oTable.rows[i].className = "";
		for(var j=0; j<oTable.rows[i].cells.length; j++)
			oTable.rows[i].cells[j].className = "";
	} 
}
/*********************** setTableColor.js ?? ***********************************/
</SCRIPT>
<SCRIPT language="javascript">
window.changeTag = true;

function init()
{

	if (document.getElementById("demo") == null) {
		return;
	}
	setRowColor(document.getElementById("demo"),'trOdd','trEven');

	
	var a = <%=start%>;
	var b = <%=cnt%>;
	var c = <%=end%>;
	if (b <= <%=showPage%> ) {
		document.getElementById("up").disabled="disabled";
		document.getElementById("down").disabled="disabled";
		return;
	}

	if (a <= 1) {
		document.getElementById("up").disabled="disabled";
		return;
	}

	if (c >= b) {
		document.getElementById("down").disabled="disabled";
		return;
	}
	
}
function downPage() {
	document.getElementById("flag").value="0";
	document.frm.submit();
	
}


function upPage() {
	document.getElementById("flag").value="1";
	document.frm.submit();
	
}

</SCRIPT>
</head>
</html>
