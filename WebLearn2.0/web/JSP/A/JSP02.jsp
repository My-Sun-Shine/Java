<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/14
  Time: 23:53
  Description: 域对象存取值的应用
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>JSP02</title>
</head>
<body>
    <p>当值存放到域对象中之后，只有当域对象销毁了，里面的值才会随之销毁</p>
    <p>1.先发送请求访问/Servlet/JSP/A/Servlet03，然后在发出请求访问JSP/A/JSP02.jsp<br/>
        结果：request域的值无法取到</p>
    <p>2.先发送请求访问/Servlet/JSP/A/Servlet03，然后在切换浏览器发出请求访问JSP/A/JSP02.jsp<br/>
        结果：request域的值无法取到，session域的值无法取到</p>
    <p>3.先发送请求访问/Servlet/JSP/A/Servlet03，然后在由Servlet03请求转发到JSP/A/JSP02.jsp<br/>
        结果：所有值都可能取到</p>
    <p>4.先发送请求访问/Servlet/JSP/A/Servlet03，然后在由Servlet0重定向到JSP/A/JSP02.jsp<br/>
        结果：request域的值无法拿到</p>
    <p><h1>域对象的生命周期</h1></p>
    <p>application：在服务器启动的时候自动创建，在服务器关闭的时候自动销毁</p>
    <p>session：在执行代码request.getSession()创建，<br/>
        有效的session需要满足:：(1).服务器中有session对象，<br/>
        (2).浏览器中有JSESSIONID，<br/>
        (3).服务器中的session对象的JSESSIONID与浏览器中的JSESSIONID需要保持一致<br/>
        销毁：(1).执行代码销毁session.invalidate()<br/>
        (2).超过空闲失效时间<br/>
        (3).注意：关闭浏览器，session对象是不销毁的，仅仅只是浏览器中的JSESSIONID销毁<br/>
    </p>
    <p>request：当浏览器发出请求，只要请求发送到项目，那么服务器会自动的为我们创建一个request对象<br/>
        当服务器将请求处理完毕，准备响应的时候，将request对象销毁
    </p>

    request：${str1}<br/>
    session：${str2}<br/>
    application：${str3}<br/>
</body>
</html>
