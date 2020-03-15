<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/14
  Time: 20:36
  Description: ajax练习
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>JSP01</title>
    <script type="text/javascript" src="JS/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#djBtn").click(function () {
                $.ajax({
                    url: "Servlet/JSP/A/Servlet01",//链接
                    data: "",//请求参数
                    type: "get",//请求方式
                    dataType: "text",//返回数据的类型
                    async: true,	 //默认为异步操作
                    success: function (data) {
                        console.log(data);
                        $("#msg").html(data);
                    }
                });
                //异步操作时
                alert("操作");
            });

            //json数据
            $("#djBtnjson").click(function () {
                $.ajax({
                    url: "Servlet/JSP/A/Servlet02",//链接
                    data: "",//请求参数
                    type: "get",//请求方式
                    dataType: "json",//返回数据的类型
                    async: true,	 //默认为异步操作
                    success: function (data) {
                        console.log(data);
                        var str = "";
                        for (var i = 0; i < data.length; i++) {

                            str += data[i].id + "," + data[i].name + "," + data[i].age + "<br/>"
                        }
                        $("#msg").html(str);
                    }
                });
                //异步操作时
                alert("操作");
            })

        })
    </script>
</head>
<body>
    <button id="djBtn">点击</button>
    <button id="djBtnjson">点击，获取JSON</button>
    <br/>
    <br/>
    <div id="msg" style="width: 200px;height: 200px;background-color: yellow">
    </div>
    <br/>
    <br/>
    <div style="width: 200px;height: 200px;background-color: yellow">
        123456
    </div>
</body>
</html>
