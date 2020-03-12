<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/12
  Time: 23:11
  Description:  $.ajax()使用
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>JSP11</title>
    <script type="text/javascript" src="JS/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            //为第一个text控件添加光标失去控件
            $(":text:first").blur(function () {
                $.ajax({
                    type: 'get',
                    url: 'E/Servlet06',
                    data: 'uname=' + $(":text:first").val(),
                    dataType: 'text',
                    success: function (data) {
                        //为第二个text控件赋值
                        $(":text:eq(1)").val(data);
                    },
                    error:function () {
                        alert("数据失败。。。。")
                    }
                });
            });

        })
    </script>
</head>
<body>
    用户名:<input type="text"><br/>
    sayHello:<input type="text">
</body>
</html>
