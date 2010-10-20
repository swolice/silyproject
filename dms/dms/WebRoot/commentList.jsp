<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="mytld" uri="/tld/MyTld" %>	    
 <table cellSpacing=2 cellPadding=2 width="700" border=0 id="c1" align="center">
	    <c:if test="${list!=null&&!empty list}">
      <c:forEach var="obj" items="${list}" varStatus="index">
<tr><td>
${obj.content}<br/><fmt:formatDate value="${obj.createTime}" pattern="MM月dd日 HH点mm分"/> <a href="home.do?method=sendMsg&tid=${obj.userId}"><mytld:userTag userId="${obj.userId}"></mytld:userTag></a>
</td></tr>
    </c:forEach>
    </c:if>
    <!--tr>
    	<td>
    		<mytld:turnPage name="page" action="index.do?method=listComment&type=1" formName="c1Form"></mytld:turnPage>
    	</td>
  </tr-->
  </table>