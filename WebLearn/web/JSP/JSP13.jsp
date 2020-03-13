<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/13
  Time: 13:46
  Description: 服务端产生高级类型对象交给异步请求对象，转换为JSON
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>JSP13</title>
    <script type="text/javascript" src="JS/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            //为第一个text控件添加光标失去控件
            $(":text:first").blur(function () {
                $.get("E/Servlet07",
                    {},
                    fun1,
                    "json");
            });

        });

        function fun1(data) {
            //为第二个text控件赋值
            //alert(data)
            console.log(data)
            for (var i = 0; i < data.length; i++) {
                alert(data[i].id + "," + data[i].name + "," + data[i].age)
                console.log(data[i].id + "," + data[i].name + "," + data[i].age)
            }
        }
    </script>
</head>
<body>
    用户名:<input type="text"><br/>
    sayHello:<input type="text">
</body>
</html>
