<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/4/7
  Time: 23:02
  Description: 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>show.jsp</title>
</head>
<body>
    /WEB-INF/view/learn02/show.jsp ，显示Model中的数据（request作用域），逻辑名称 <br>
    msg: ${msg}
    <br/>
    fun：${fun}
</body>
</html>
