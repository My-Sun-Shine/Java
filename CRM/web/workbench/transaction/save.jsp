<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    Map<String, String> stageMap = (Map<String, String>) application.getAttribute("stageMap");
    Set<String> keys = stageMap.keySet();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <%--自动补全控件--%>
    <script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
    <script type="text/javascript">
        //将键值对转换为JSON格式数据
        var json = {
            <%
            for (String key:keys){
                String value=stageMap.get(key);%>
            "<%=key%>":<%=value%>,
            <%}%>
        };
        console.log(json);
        $(function () {
            $("#create-customerName").typeahead({
                source: function (query, process) {
                    $.post(
                        "workbench/transaction/getCustomerListByName.do",
                        {"name": query},
                        function (data) {
                            //alert(data);
                            process(data);
                        },
                        "json"
                    );
                },
                delay: 500
            });

            //以下日历插件在FF中存在兼容问题，在IE浏览器中可以正常使用。
            $(".time1").datetimepicker({
                minView: "month",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });
            $(".time2").datetimepicker({
                minView: "month",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "top-left"
            });

            getUserList();

            $("#create-stage").change(function () {
                var stage = $("#create-stage").val();
                var value = json[stage];//json数据根据key取值 json.key或者json[key]
                $("#create-possibility").val(value);

            })

            $("#searchactivityBtn").click(function () {
                $("#findMarketActivity").modal("show");
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
                    $("#create-activityId").val(id);

                    var name = $("#" + id).html();
                    $("#create-activityName").val(name);

                    $("#findMarketActivity").modal("hide");
                }
            })

            $("#searchcontactsBtn").click(function () {
                $("#findContacts").modal("show");
                $("#searchContactsName").val("");
                $("#searchContactsName").focus();
                $("#contactsBody").html("");
            })

            $("#searchContactsName").keydown(function () {
                if (event.keyCode == 13) {
                    var name = $.trim($("#searchContactsName").val());
                    if (name === "") {
                        alert("请输入联系人名称");
                        return false;
                    }
                    $.ajax({
                        url: "workbench/transaction/getContactsListByName.do",
                        data: {
                            "cname": name
                        },
                        type: "get",
                        async: false,
                        dataType: "json",
                        success: function (data) {
                            var html = "";
                            $.each(data, function (i, item) {
                                html += '<tr>';
                                html += '<td><input type="radio" name="contacts" value="' + item.id + '"/></td>';
                                html += '<td id="' + item.id + '">' + item.fullname + '</td>';
                                html += '<td>' + item.email + '</td>';
                                html += '<td>' + item.mphone + '</td>';
                                html += '</tr>';

                            });
                            $("#contactsBody").html(html);
                        }
                    });
                    return false;
                }
            })

            $("#submitContactsBtn").click(function () {
                var $xz = $("input[name=contacts]:checked");
                if ($xz.lenght == 0) {
                    alert("请选择联系人");
                } else {
                    var id = $xz.val();
                    $("#create-contactsId").val(id);

                    var name = $("#" + id).html();
                    $("#create-contactsName").val(name);

                    $("#findContacts").modal("hide");
                }
            })

            $("#saveBtn").click(function () {
                $("#tranForm").submit();
            })
        })

        function getUserList() {
            $.ajax({
                url: "settings/user/getUserList.do",
                data: {},
                type: "get",
                async: false,
                dataType: "json",
                success: function (data) {
                    var html = "";
                    $.each(data, function (i, item) {
                        html += '<option value="' + item.id + '"' + (item.id == "${user.id}" ? "selected" : "") + '>' + item.name + '</option>';
                    });
                    //console.log(html);
                    $("#create-owner").html(html);
                }
            })
        }
    </script>

</head>
<body>

    <!-- 查找市场活动 -->
    <div class="modal fade" id="findMarketActivity" role="dialog">
        <div class="modal-dialog" role="document" style="width: 80%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">查找市场活动</h4>
                </div>
                <div class="modal-body">
                    <div class="btn-group" style="position: relative; top: 18%; left: 8px;">
                        <form class="form-inline" role="form">
                            <div class="form-group has-feedback">
                                <input type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询" id="searchActivityName">
                                <span class="glyphicon glyphicon-search form-control-feedback"></span>
                            </div>
                        </form>
                    </div>
                    <table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
                        <thead>
                            <tr style="color: #B3B3B3;">
                                <td></td>
                                <td>名称</td>
                                <td>开始日期</td>
                                <td>结束日期</td>
                                <td>所有者</td>
                            </tr>
                        </thead>
                        <tbody id="activityBody">
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="submitActivityBtn">提交</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 查找联系人 -->
    <div class="modal fade" id="findContacts" role="dialog">
        <div class="modal-dialog" role="document" style="width: 80%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">查找联系人</h4>
                </div>
                <div class="modal-body">
                    <div class="btn-group" style="position: relative; top: 18%; left: 8px;">
                        <form class="form-inline" role="form">
                            <div class="form-group has-feedback">
                                <input type="text" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询" id="searchContactsName">
                                <span class="glyphicon glyphicon-search form-control-feedback"></span>
                            </div>
                        </form>
                    </div>
                    <table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
                        <thead>
                            <tr style="color: #B3B3B3;">
                                <td></td>
                                <td>名称</td>
                                <td>邮箱</td>
                                <td>手机</td>
                            </tr>
                        </thead>
                        <tbody id="contactsBody">
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="submitContactsBtn">提交</button>
                </div>
            </div>
        </div>
    </div>


    <div style="position:  relative; left: 30px;">
        <h3>创建交易</h3>
        <div style="position: relative; top: -40px; left: 70%;">
            <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            <button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
        </div>
        <hr style="position: relative; top: -40px;">
    </div>
    <form class="form-horizontal" role="form" style="position: relative; top: -30px;" action="workbench/transaction/saveTransaction.do" method="post" id="tranForm">
        <div class="form-group">
            <label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
                <select class="form-control" id="create-owner" name="owner">
                </select>
            </div>
            <label for="create-money" class="col-sm-2 control-label">金额</label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-money" name="money">
            </div>
        </div>

        <div class="form-group">
            <label for="create-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-name" name="name">
            </div>
            <label for="create-expectedDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control time1" id="create-expectedDate" name="expectedDate">
            </div>
        </div>

        <div class="form-group">
            <label for="create-customerName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-customerName" placeholder="支持自动补全，输入客户不存在则新建" autocomplete="off" name="customerName">
            </div>
            <label for="create-stage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
                <select class="form-control" id="create-stage" name="stage">
                    <option></option>
                    <c:forEach items="${applicationScope.stage}" var="item">
                        <option value="${item.value}">${item.text}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="create-type" class="col-sm-2 control-label">类型</label>
            <div class="col-sm-10" style="width: 300px;">
                <select class="form-control" id="create-type" name="type">
                    <option></option>
                    <option>已有业务</option>
                    <option>新业务</option>
                </select>
            </div>
            <label for="create-possibility" class="col-sm-2 control-label">可能性</label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-possibility" readonly>
            </div>
        </div>

        <div class="form-group">
            <label for="create-source" class="col-sm-2 control-label">来源</label>
            <div class="col-sm-10" style="width: 300px;">
                <select class="form-control" id="create-source" name="source">
                    <option></option>
                    <c:forEach items="${applicationScope.source}" var="item">
                        <option value="${item.value}">${item.text}</option>
                    </c:forEach>
                </select>
            </div>
            <label for="create-activitySrc" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" id="searchactivityBtn"><span
                    class="glyphicon glyphicon-search"></span></a></label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-activityName" readonly>
                <input type="hidden" id="create-activityId" name="create-activityId">
            </div>
        </div>

        <div class="form-group">
            <label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" id="searchcontactsBtn"><span
                    class="glyphicon glyphicon-search"></span></a></label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-contactsName" readonly>
                <input type="hidden" id="create-contactsId" name="create-contactsId">
            </div>
        </div>

        <div class="form-group">
            <label for="create-description" class="col-sm-2 control-label">描述</label>
            <div class="col-sm-10" style="width: 70%;">
                <textarea class="form-control" rows="3" id="create-description" name="description"></textarea>
            </div>
        </div>

        <div class="form-group">
            <label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
            <div class="col-sm-10" style="width: 70%;">
                <textarea class="form-control" rows="3" id="create-contactSummary" name="contactSummary"></textarea>
            </div>
        </div>

        <div class="form-group">
            <label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
            <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control time2" id="create-nextContactTime" name="nextContactTime">
            </div>
        </div>

    </form>
</body>
</html>