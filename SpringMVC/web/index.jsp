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
<hr/>
请求参数，controller方法中的入参
<br/>
处理器方法的形参名和请求中参数名一样，按名称对应接收参数
<form action="learn05/form1.do" method="post">
    姓名:<input  type="text" name="name"><br>
    年龄:<input type="text" name="age"> <br>
    <input type="submit" value="提交参数">
</form>
<br/>
如果请求中参数名和处理器方法的形参名不一样，需要在处理器方法形参前面加入@RequestParam(value="请求中参数名")
<form action="learn05/form2.do" method="post">
    姓名:<input  type="text" name="rname"><br>
    年龄:<input type="text" name="rage"> <br>
    <input type="submit" value="提交参数">
</form>
<br/>
使用java对象接收请求的多个参数
<form action="learn05/form3.do" method="post">
    姓名:<input  type="text" name="name"><br>
    年龄:<input type="text" name="age"> <br>
    <input type="submit" value="提交参数">
</form>
</body>
</html>
