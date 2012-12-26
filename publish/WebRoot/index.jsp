<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.min.js"></script>
		<script>
		jQuery(function(){
			jQuery("#b1").click(function(){
				var radio = jQuery(":radio[checked]").size();
				if(radio == 0){
					alert("请选择一个发布工程！！！");
					return ;
				}
				jQuery("#pform").submit();
			});
		});
		</script>
	</head>
	<body>
		<form name="pform"
			action="<%=request.getContextPath()%>/servlet/PublishServlet"
			method="post">
			请选择对应的工程文件
			<br>
			<label for="all">
				<input type="radio" name="flag" value="0" id="all" />
				All
			</label>
			<label for="account">
				<input type="radio" name="flag" value="1" id="account" />
				Account
			</label>
			<label for="account_admin">
				<input type="radio" name="flag" value="2" id="account_admin" />
				AccountAdmin
			</label>
			<label for="account_report">
				<input type="radio" name="flag" value="3" id="account_report" />
				AccountReport
			</label>
			<label for="account_interface">
				<input type="radio" name="flag" value="4" id="account_interface" />
				AccountInterface
			</label>
			<input name="button" type="button" id="b1" value="发布账务系统项目" />
		</form>
	</body>
</html>
