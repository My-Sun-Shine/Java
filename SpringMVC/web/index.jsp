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
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>index.jsp发起请求</title>
</head>
<body>
<a href="learn01/some.do">发起learn01/some.do的请求</a>
<br/>
<a href="learn02/some.do">发起learn02/some.do的请求</a>
<br/>
<a href="learn02/first.do">发起learn02/first.do的请求</a>
<br/>
<a href="learn02/a.do">发起learn02/a.do的请求</a>
<br/>
<a href="learn02/other.do">发起learn02/other.do的请求</a>
<br/>
<a href="learn02/second.do">发起learn02/second.do的请求</a>
<br/>
<a href="learn02/b.do">发起learn02/b.do的请求</a>
<br/>
<img alt="不能显示" src="images/p1.jpg" style="width: 100px">
<hr/>
指定请求的方式，使用@RequestMapping的method属性
<br/>
<a href="learn04/some.do">发起learn04/some.do的get请求</a>
<br/>
<form action="learn04/other.do" method="post">
  <input type="submit" value="post请求learn04/other.do">
</form>
<br/>
<a href="learn04/first.do">发起learn04/first.do的get请求</a>
<br/>
<form action="learn04/first.do" method="post">
  <input type="submit" value="post请求learn04/first.do">
</form>
</body>
</html>
