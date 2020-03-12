<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/12
  Time: 23:37
  Description: $.get()请求
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>JSP12</title>
    <script type="text/javascript" src="JS/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        // $.post(url,data,success,dataType)
        // $.get(url,data,success,dataType)

        $(function () {
            //为第一个text控件添加光标失去控件
            $(":text:first").blur(function () {
                $.get("E/Servlet06",
                    {uname: $(":text:first").val()},
                    fun1,
                    "text");
            });

        });

        function fun1(data) {
            //为第二个text控件赋值
            $(":text:eq(1)").val(data)
        }
    </script>
</head>
<body>
    用户名:<input type="text"><br/>
    sayHello:<input type="text">
</body>
</html>
