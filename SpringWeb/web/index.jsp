<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/4/6
  Time: 20:59
  Description: 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>Insert title here</title>
</head>
<body>
    index.jsp <br>
    <form action="RegisterServlet" method="post">
        姓名：<input type="text" name="name"><br>
        年龄：<input type="text" name="age"><br>
        <input type="submit" value="注册学生">
    </form>
</body>
</html>
