<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            //失去焦点则验证
            $("#code").blur(function () {
                //去掉空格的影响
                var code = $.trim($("#code").val());
                console.log(code);
                if (code === "") {
                    $("#codeMsg").html("编码不能为空");
                    return false;
                }

                //编码只能使用英文和数字（最重要的是不能含有中文）
                var regExp = /^[0-9a-zA-Z]+$/
                var flag = regExp.test(code);
                if (!flag) {
                    $("#codeMsg").html("编码只能使用英文和数字");
                    return false;
                }

                //验证编码是否重复
                $.ajax({
                    url: "settings/dictionary/type/checkCode.do",
                    data: {"code": code},
                    type: "get",
                    dataType: "json",
                    success: function (data) {
                        //{"success":true/false}
                        if (!data.success) {
                            $("#codeMsg").html("编码已重复");
                            return false;
                        }
                    }
                })
            });

            //获得焦点的时候，清空错误信息
            $("#code").focus(function () {
                $("#codeMsg").html("");
            })
        })
    </script>
</head>
<body>

    <div style="position:  relative; left: 30px;">
        <h3>新增字典类型</h3>
        <div style="position: relative; top: -40px; left: 70%;">
            <button type="button" class="btn btn-primary">保存</button>
            <button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
        </div>
        <hr style="position: relative; top: -40px;">
    </div>
    <form class="form-horizontal" role="form">

        <div class="form-group">
            <label for="create-code" class="col-sm-2 control-label">编码<span
                    style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="code" style="width: 200%;" placeholder="编码作为主键，不能是中文">
                <%--错误提示信息要求显示在文本框下方，要求红色字体--%>
                <span id="codeMsg" style="color: #ff0000"></span>
            </div>
        </div>

        <div class="form-group">
            <label for="create-name" class="col-sm-2 control-label">名称</label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="name" style="width: 200%;">
            </div>
        </div>

        <div class="form-group">
            <label for="create-describe" class="col-sm-2 control-label">描述</label>
            <div class="col-sm-10" style="width: 300px;">
                <textarea class="form-control" rows="3" id="description" style="width: 200%;"></textarea>
            </div>
        </div>
    </form>

    <div style="height: 200px;"></div>
</body>
</html>