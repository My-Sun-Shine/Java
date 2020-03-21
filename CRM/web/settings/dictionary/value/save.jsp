<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            //同时验证字典类型编码下拉框
            $("#value").blur(function () {

                var value = $.trim($("#value").val());
                var typeCode = $("#typeCode").val();
                console.log(value);
                console.log(typeCode);

                if (value === "") {
                    $("#valueMsg").html("字典值不能为空");
                }
                if (typeCode === "") {
                    $("#typeCodeMsg").html("字典类型编码不能为空");
                }


            });

            $("#typeCode").change(function () {
                var typeCode = $("#typeCode").val();
                if (typeCode === "") {
                    $("#typeCodeMsg").html("字典类型编码不能为空");
                    //return false
                } else {
                    $("#typeCodeMsg").html("");
                }
            });

            $("#orderNo").blur(function () {
                var orderNo = $.trim($("#orderNo").val());
                if (orderNo != "") {
                    var regExp = /^[1-9][0-9]*$/;
                    var flag = regExp.test(orderNo);
                    if (!flag) {
                        $("#orderNoMsg").html("排序号可以为空，但不为空的时候必须是正整数");
                        return false;
                    }


                }
            });

            //错误信息清空
            $("#value").focus(function () {
                $("#valueMsg").html("");
                $("#typeCodeMsg").html("");

            });

            $("#orderNo").focus(function () {
                $("#orderNoMsg").html("");
            });

            $("#saveBtn").click(function () {
                $("#value").blur();
                $("#orderNo").blur();
                var typeCodeMsg = $("#typeCodeMsg").html();
                var orderNoMsg = $("#orderNoMsg").html();
                var valueMsg = $("#valueMsg").html();
                console.log(typeCodeMsg + orderNoMsg + valueMsg);
                if (typeCodeMsg === "" && orderNoMsg === "" && valueMsg === "") {
                    $("#dicValueForm").submit();
                }

            });
        })
    </script>
</head>
<body>

    <div style="position:  relative; left: 30px;">
        <h3>新增字典值</h3>
        <div style="position: relative; top: -40px; left: 70%;">
            <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            <button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
        </div>
        <hr style="position: relative; top: -40px;">
    </div>
    <form id="dicValueForm" class="form-horizontal" role="form" method="post" action="settings/dictionary/value/saveDicValue.do">

        <div class="form-group">
            <label for="typeCode" class="col-sm-2 control-label">字典类型编码<span
                    style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
                <select class="form-control" id="typeCode" style="width: 200%;" name="typeCode">
                    <option value=""></option>
                    <c:forEach items="${dicTypeList}" var="item">
                        <option value="${item.code}">${item.name}</option>
                    </c:forEach>
                </select>
                <span id="typeCodeMsg" style="color: red"></span>
            </div>
        </div>

        <div class="form-group">
            <label for="value" class="col-sm-2 control-label">字典值<span
                    style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="value" style="width: 200%;" name="value">
                <span id="valueMsg" style="color: red"></span>
            </div>
        </div>

        <div class="form-group">
            <label for="text" class="col-sm-2 control-label">文本</label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="text" style="width: 200%;" name="text">
            </div>
        </div>

        <div class="form-group">
            <label for="orderNo" class="col-sm-2 control-label">排序号</label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="orderNo" style="width: 200%;" name="orderNo">
                <span id="orderNoMsg" style="color: red"></span>
            </div>
        </div>
    </form>

    <div style="height: 200px;"></div>
</body>
</html>