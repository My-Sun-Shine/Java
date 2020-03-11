<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/10
  Time: 14:20
  Description :jsp中base标签的使用：相对路径与绝对路径
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--根目录地址一定以斜线为结尾--%>
    <base href="/">
    <title>JSP01</title>
</head>
<body>
    <p>在web开发中，禁止使用相对路径，所有资源都需要使用绝对路径</p>
    <p>在html中&lt;base&gt;这个标签导致当前页面所有的相对路径失效</p>
    <h1>相对路径：以当前文件为起点，寻找其他资源</h1>
    <a href="JSP01.jsp">JSP02.jsp</a>
    <br/>
    <a href="../index.jsp">index.jsp</a>
    <br/>
    <h1>绝对路径：以web根目录为起点，寻找其他资源</h1>
    <a href="/JSP/JSP01.jsp">JSP02.jsp</a>
    <br/>
    <a href="/index.jsp">index.jsp</a>
</body>
</html>
