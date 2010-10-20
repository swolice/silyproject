<%
javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("a","aaaaaaa"); 
cookie.setMaxAge(3600);  
cookie.setDomain(".test.com"); 
response.addCookie(cookie); 
%>