<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/4/7
  Time: 22:03
  Description: 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <%
	    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>index.jsp发起请求</title>
  </head>
  <body>
    <a href="learn01/some.do">发起learn01/some.do的请求</a>
  </body>
</html>
