<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/15
  Time: 22:16
  Description: 欢迎使用转账系统
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>TAccount</title>
    <script type="text/javascript" src="JS/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        function Account() {
            var text = $(":text")
            console.log(text)
            console.log(text[0].value)
            if (text[0].value === "") {
                alert("转出账号不能为空");
                return;
            }
            if (text[1].value === "") {
                alert("转入账号不能为空");
                return;
            }
            if (text[2].value === "") {
                alert("转账金额不能为空");
                return;
            }
            $.ajax({
                type: "post",
                dataType: "json",
                url: "com/Controller/TAccount",
                data: $("#from1").serialize(),
                success: function (data) {
                    if (data) {
                        alert("转账成功");
                    } else {
                        alert("转账失败");
                    }
                },
                error: function () {
                    alert("异常")
                }
            });
            $(":text").val("");
        }
    </script>
</head>
<body>
    <h3>欢迎使用转账系统</h3>

    <form id="from1" method="post" onsubmit="return false">

        请输入转出账号:<input type="text" name="outAccount"/><br/><br/>
        请输入转入账号:<input type="text" name="intoAccount"/><br/><br/>
        请输入转账金额:<input type="text" name="Balance"/><br/><br/>

        <input type="button" value="提交" onclick="Account()"/>

    </form>
</body>
</html>
