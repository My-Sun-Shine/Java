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
    <a href="learn02/some.do">发起learn02/some.do的请求</a>
    <a href="learn02/first.do">发起learn02/first.do的请求</a>
    <a href="learn02/a.do">发起learn02/a.do的请求</a>
    <a href="learn02/other.do">发起learn02/other.do的请求</a>
    <a href="learn02/second.do">发起learn02/second.do的请求</a>
    <a href="learn02/b.do">发起learn02/b.do的请求</a>
  </body>
</html>
