<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <script type="text/javascript">
        $(function () {
            //以下日历插件在FF中存在兼容问题，在IE浏览器中可以正常使用。
            $(".time").datetimepicker({
                minView: "month",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "top-left"
            });

            $("#isCreateTransaction").click(function () {
                if (this.checked) {
                    $("#create-transaction2").show(200);
                } else {
                    $("#create-transaction2").hide(200);
                }
            });

            $("#convertBtn").click(function () {
                if ($("#isCreateTransaction").prop("checked")) {
                    //需要创建交易
                    $("#tranForm").submit();
                } else {
                    //不需要创建交易
                    window.location.href = 'workbench/clue/convert.do?clueId=${param.id}';
                }
            })

            $("#searchactivityBtn").click(function () {
                $("#searchActivityModal").modal("show");
                $("#searchActivityName").val("");
                $("#searchActivityName").focus();
                $("#activityBody").html("");
            })

            $("#searchActivityName").keydown(function () {
                if (event.keyCode == 13) {
                    var name = $.trim($("#searchActivityName").val());
                    if (name === "") {
                        alert("请输入市场活动名称");
                        return false;
                    }
                    $.ajax({
                        url: "workbench/clue/getActivityListByName.do",
                        data: {
                            "aname": name
                        },
                        type: "get",
                        async: false,
                        dataType: "json",
                        success: function (data) {
                            var html = "";
                            $.each(data, function (i, item) {
                                html += '<tr>';
                                html += '<td><input type="radio" name="activity" value="' + item.id + '"/></td>';
                                html += '<td id="' + item.id + '">' + item.name + '</td>';
                                html += '<td>' + item.startDate + '</td>';
                                html += '<td>' + item.endDate + '</td>';
                                html += '<td>' + item.owner + '</td>';
                                html += '</tr>';

                            });
                            $("#activityBody").html(html);
                        }
                    });
                    return false;
                }
            })

            $("#submitActivityBtn").click(function () {
                var $xz = $("input[name=activity]:checked");
                if ($xz.lenght == 0) {
                    alert("请选择市场活动");
                } else {
                    var id = $xz.val();
                    $("#activityId").val(id);

                    var name = $("#" + id).html();
                    $("#activityName").val(name);

                    $("#searchActivityModal").modal("hide");
                }
            })
        });
    </script>

</head>
<body>

    <!-- 搜索市场活动的模态窗口 -->
    <div class="modal fade" id="searchActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 90%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">搜索市场活动</h4>
                </div>
                <div class="modal-body">
                    <div class="btn-group" style="position: relative; top: 18%; left: 8px;">
                        <form class="form-inline" role="form">
                            <div class="form-group has-feedback">
                                <input id="searchActivityName" type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
                                <span class="glyphicon glyphicon-search form-control-feedback"></span>
                            </div>
                        </form>
                    </div>
                    <table id="activityTable" class="table table-hover"
                           style="width: 900px; position: relative;top: 10px;">
                        <thead>
                            <tr style="color: #B3B3B3;">
                                <td></td>
                                <td>名称</td>
                                <td>开始日期</td>
                                <td>结束日期</td>
                                <td>所有者</td>
                                <td></td>
                            </tr>
                        </thead>
                        <tbody id="activityBody"></tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="submitActivityBtn">提交</button>
                </div>
            </div>
        </div>
    </div>

    <div id="title" class="page-header" style="position: relative; left: 20px;">
        <h4>转换线索
            <small>${param.fullname}${param.appellation}-${param.company}</small>
        </h4>
    </div>
    <div id="create-customer" style="position: relative; left: 40px; height: 35px;">
        新建客户：${param.company}
    </div>
    <div id="create-contact" style="position: relative; left: 40px; height: 35px;">
        新建联系人：${param.fullname}${param.appellation}
    </div>
    <div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
        <input type="checkbox" id="isCreateTransaction"/>为客户创建交易
    </div>
    <div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;">
        <form action="workbench/clue/convert.do" method="post" id="tranForm">
            <input type="hidden" name="flag" value="a"/>
            <input type="hidden" id="clueId" name="clueId" value="${param.id}">
            <div class="form-group" style="width: 400px; position: relative; left: 20px;">
                <label for="amountOfMoney">金额</label>
                <input type="text" class="form-control" id="money" name="money">
            </div>
            <div class="form-group" style="width: 400px;position: relative; left: 20px;">
                <label for="tradeName">交易名称</label>
                <input type="text" class="form-control" id="tradeName" name="tradeName">
            </div>
            <div class="form-group" style="width: 400px;position: relative; left: 20px;">
                <label for="expectedClosingDate">预计成交日期</label>
                <input type="text" class="form-control time" id="expectedDate" name="expectedDate">
            </div>
            <div class="form-group" style="width: 400px;position: relative; left: 20px;">
                <label for="stage">阶段</label>
                <select id="stage" class="form-control" name="stage">
                    <option></option>
                    <c:forEach items="${applicationScope.stage}" var="item">
                        <option value="${item.value}">${item.text}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group" style="width: 400px;position: relative; left: 20px;">
                <label for="activity">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" id="searchactivityBtn" style="text-decoration: none;">
                    <span class="glyphicon glyphicon-search"></span></a></label>
                <input type="text" class="form-control" id="activityName" placeholder="点击上面搜索" readonly>
                <input type="hidden" id="activityId" name="activityId">
            </div>
        </form>

    </div>

    <div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
        记录的所有者：<br>
        <b>${param.owner}</b>
    </div>
    <div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
        <input class="btn btn-primary" type="button" value="转换" id="convertBtn">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input class="btn btn-default" type="button" value="取消" onclick="window.history.back();">
    </div>
</body>
</html>