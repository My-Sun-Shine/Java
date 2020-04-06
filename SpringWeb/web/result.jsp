<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/4/6
  Time: 21:17
  Description: 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>result</title>
</head>
<body>
    注册成功！！！
</body>
</html>
