<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/10
  Time: 13:52
  Description : 第一个JSP页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP01</title>
</head>
<body>
    This is JSP01
    <%
        String value = request.getParameter("name");
    %>
    Hello <%=value %>


</body>
</html>
