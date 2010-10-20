<%
javax.servlet.http.Cookie[] cookies = request.getCookies(); 


for (javax.servlet.http.Cookie c:cookies){
out.println(c.getName()+":"+c.getValue()+"<br/>");
}
%>