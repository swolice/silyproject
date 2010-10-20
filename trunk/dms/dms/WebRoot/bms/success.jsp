<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>

<body>
<div class="pagecoantiner">
<div class="bmstop">
  <div class="bmstitle"><span>${title}</span> </div>
  <div class="clear"></div>
</div>
<div class="gwbmscon">
  <table width="400" align="center" class="bmstab">
    <tbody>
      <tr>
        <td valign="top" width="50"><img height="32" alt="information" src="<%=request.getContextPath()%>/images/gwinformation.gif" width="32" border="0" /> </td>
        <td><strong>${title}</strong></td>
      </tr>
      <tr>
        <td></td>

      </tr>
      <tr>
        <td></td>
        <td><ul>
            <li><a href="${backUrl}" >返回</a></li>
        </ul></td>
      </tr>
    </tbody>
  </table>
</div>
</div>
</body>
</html>