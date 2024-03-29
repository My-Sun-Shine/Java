<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            //使login.jsp始终在顶层窗口中打开
            if (window.top != window) {
                window.top.location = window.location;
            }

            $("#loginAct").html("");
            $("#loginPwd").html("");

            $("#loginAct").focus();
            $("#loginBtn").click(function () {
                login()
            });

            $(window).keydown(function (event) {
                if (event.keyCode === 13) {
                    login()
                }
            });
        });

        function login() {
            var loginAct = $.trim($("#loginAct").val());
            var loginPwd = $.trim($("#loginPwd").val());
            //console.log(loginAct);
            //console.log(loginPwd);
            if (loginAct === "" || loginPwd === "") {
                $("#msg").html("用户名和密码不能为空");
                return false;
            }
            $.ajax({
                url: "settings/user/login.do",
                data: {
                    "loginAct": loginAct,
                    "loginPwd": loginPwd
                },
                type: "post",
                async: false,
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        window.location.href = "workbench/index.jsp";
                    } else {
                        $("#msg").html(data.msg);
                    }
                }
            })
        }
    </script>
</head>
<body>
    <div style="position: absolute; top: 0px; left: 0px; width: 60%;">
        <img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
    </div>
    <div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
        <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
            CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
    </div>

    <div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
        <div style="position: absolute; top: 0px; right: 60px;">
            <div class="page-header">
                <h1>登录</h1>
            </div>
            <form class="form-horizontal" role="form" method="post">
                <div class="form-group form-group-lg">
                    <div style="width: 350px;">
                        <input id="loginAct" class="form-control" type="text" placeholder="登录账号" name="loginAct">
                    </div>
                    <div style="width: 350px; position: relative;top: 20px;">
                        <input id="loginPwd" class="form-control" type="password" placeholder="密码" name="loginPwd">
                    </div>
                    <div class="checkbox" style="position: relative;top: 30px; left: 10px;">
                        <span id="msg" style="color: red"></span>
                    </div>
                    <button id="loginBtn" type="button" class="btn btn-primary btn-lg btn-block"
                            style="width: 350px; position: relative;top: 45px;">登录
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>