<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/11
  Time: 0:01
  Description：include指令
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP05</title>
</head>
<body>
    <%--include指令--%>
    <%--包含作用，可以使一个jsp包含另一个jsp--%>
    <%--使用include指令的包含形式为一种静态的包含形式--%>

    This is JSP05.jsp
    <br/>
    <%@ include file="JSP04.jsp"%>
</body>
</html>
