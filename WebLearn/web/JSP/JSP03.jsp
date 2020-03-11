<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/10
  Time: 22:26
  Description :jsp中base标签的使用：相对路径与绝对路径
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>JSP03</title>
</head>
<body>
    <h1>绝对路径：以web根目录为起点，寻找其他资源</h1>
    <a href="/JSP/JSP01.jsp">JSP02.jsp</a>
    <br/>
    <a href="/index.jsp">index.jsp</a>
</body>
</html>
