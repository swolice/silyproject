<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> </title>
<meta name="robots" content="noindex, nofollow">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>

<h1>
<div style="clear:both"></div>
</h1>
<div class="list-div">
  <div style="background:#FFF; padding: 20px 50px; margin: 2px;">
    <table align="center" width="400">
      <tr>
        <td width="50" valign="top">
                    <img src="/images/gwinformation.gif" width="32" height="32" border="0" alt="information" />
                  </td>
        <td style="font-size: 14px; font-weight: bold">操作失败</td>
      </tr>
      <tr>
        <td></td>
        <td id="redirectionMsg">${errMsg}</td>
      </tr>
      <tr>
        <td></td>
        <td>
          <ul style="margin:0; padding:0 10px" class="msg-link">
          <li><a href="#" onclick="history.go(-1);return false;" >返回</a></li>
          </ul>

        </td>
      </tr>
    </table>
  </div>
</div>

</body>
</html>