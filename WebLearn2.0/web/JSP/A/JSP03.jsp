<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/15
  Time: 16:30
  Description: 模板模式的应用
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>JSP04</title>
</head>
<body>
    <a href="Servlet/JSP/A/Servlet03/save.do">
        添加操作
    </a>
    <br/>
    <br/>
    <a href="Servlet/JSP/A/Servlet03/delete.do">
        删除操作
    </a>
    <br/>
    <br/>
    <a href="Servlet/JSP/A/Servlet03/update.do">
        修改操作
    </a>
    <br/>
    <br/>
    <a href="Servlet/JSP/A/Servlet03/select.do">
        查询操作
    </a>
    <br/>
    <br/>
</body>
</html>
