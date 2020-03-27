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
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <%--分页插件引入--%>
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">

        $(function () {

            const PAGESIZE = 3;

            //以下日历插件在FF中存在兼容问题，在IE浏览器中可以正常使用。
            $(".time").datetimepicker({
                minView: "month",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "top-left"
            });

            //定制字段
            $("#definedColumns > li").click(function (e) {
                //防止下拉菜单消失
                e.stopPropagation();
            });

            $("#addBtn").click(function () {
                $("#createClueForm")[0].reset();
                //加载用户列表
                $.ajax({
                    url: "settings/user/getUserList.do",
                    data: {},
                    type: "post",
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        var html = "";
                        $.each(data, function (i, item) {
                            html += "<option value='" + item.id + "'>" + item.name + "</option>"
                        });
                        $("#create-owner").html(html);
                        var id = "${user.id}";
                        $("#create-owner").val(id)
                        $("#createClueModal").modal("show");
                    }
                });
            });

            $("#saveBtn").click(function () {
                var fullname = $.trim($("#create-fullname").val());
                var appellation = $.trim($("#create-appellation").val());
                var owner = $.trim($("#create-owner").val());
                var company = $.trim($("#create-company").val());
                var job = $.trim($("#create-job").val());
                var email = $.trim($("#create-email").val());
                var phone = $.trim($("#create-phone").val());
                var website = $.trim($("#create-website").val());
                var mphone = $.trim($("#create-mphone").val());
                var state = $.trim($("#create-state").val());
                var source = $.trim($("#create-source").val());
                var description = $.trim($("#create-description").val());
                var contactSummary = $.trim($("#create-contactSummary").val());
                var nextContactTime = $.trim($("#create-nextContactTime").val());
                var address = $.trim($("#create-address").val());

                $.ajax({
                    url: "settings/clue/saveClue.do",
                    data: {
                        "fullname": fullname,
                        "appellation": appellation,
                        "owner": owner,
                        "company": company,
                        "job": job,
                        "email": email,
                        "phone": phone,
                        "website": website,
                        "mphone": mphone,
                        "state": state,
                        "source": source,
                        "description": description,
                        "contactSummary": contactSummary,
                        "nextContactTime": nextContactTime,
                        "address": address

                    },
                    type: "post",
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        if (data.success) {
                            $("#createClueModal").modal("hide");
                            window.location.href = "workbench/clue/index.jsp";
                        }
                        else {
                            alert("线索添加失败");
                        }
                    },
                    error: function () {
                        alert("线索添加失败");
                    }
                });

            });

            $("#editBtn").click(function () {
                $.ajax({
                    url: "settings/user/getUserList.do",
                    data: {},
                    type: "post",
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        var html = "";
                        $.each(data, function (i, item) {
                            html += "<option value='" + item.id + "'>" + item.name + "</option>"
                        });
                        $("#edit-owner").html(html);
                        $("#editClueModal").modal("show");
                    }
                });
            });


            //页面加载完毕之后，默认展示第一页数据，第一页展示2条数据
            pageList(1, PAGESIZE);

            $("#searchBtn").click(function () {
                //点击查询的时候，将搜索框上的值放到隐藏域中
                //只有点击查询按钮，才能修改隐藏域的值
                $("#hidden-fullname").val($("#search-fullname").val());
                $("#hidden-company").val($("#search-company").val());
                $("#hidden-phone").val($("#search-phone").val());
                $("#hidden-source").val($("#search-source").val());
                $("#hidden-owner").val($("#search-owner").val());
                $("#hidden-mphone").val($("#search-mphone").val());
                $("#hidden-state").val($("#search-state").val());
                pageList(1, PAGESIZE)
            });
        });

        /*
       pageNo:页码
       pageSize：每页展现的记录数
       name
       owner
       startDate
       endDate
       */
        function pageList(pageNo, pageSize) {
            //换页的时候取消选中
            $("#selectAll")[0].checked = false;

            //普通换页的时候(不是点击查询按钮)，获取隐藏域的值赋值到搜索框中
            $("#search-fullname").val($("#hidden-fullname").val());
            $("#search-company").val($("#hidden-company").val());
            $("#search-phone").val($("#hidden-phone").val());
            $("#search-source").val($("#hidden-source").val());
            $("#search-owner").val($("#hidden-owner").val());
            $("#search-mphone").val($("#hidden-mphone").val());
            $("#search-state").val($("#hidden-state").val());

            var fullname = $.trim($("#search-fullname").val());
            var company = $.trim($("#search-company").val());
            var phone = $.trim($("#search-phone").val());
            var source = $.trim($("#search-source").val());
            var owner = $.trim($("#search-owner").val());
            var mphone = $.trim($("#search-mphone").val());
            var state = $.trim($("#search-state").val());
            $.ajax({
                url: "workbench/clue/pageList.do",
                data: {
                    "pageNo": pageNo,
                    "pageSize": pageSize,
                    "fullname": fullname,
                    "company": company,
                    "phone": phone,
                    "source": source,
                    "owner": owner,
                    "mphone": mphone,
                    "state": state,
                    cache: false//加入时间戳，确保每次数据都是最新的，不是浏览器的缓存数据
                },
                type: "get",
                async: false,
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    // data:{"total":?,"dataList":[{},{},{}...]}
                    var html = "";
                    $.each(data.dataList, function (i, item) {
                        html += '<tr>';
                        html += '<td><input type="checkbox" id="' + item.id + '"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;"';
                        html += 'onclick="window.location.href=\'workbench/clue/detailClue.do?id=' + item.id + '\';">' + item.fullname + '</a></td>';
                        html += '<td>' + item.company + '</td>';
                        html += '<td>' + item.phone + '</td>';
                        html += '<td>' + item.mphone + '</td>';
                        html += '<td>' + item.source + '</td>';
                        html += '<td>' + item.owner + '</td>';
                        html += '<td>' + item.state + '</td>';
                        html += '</tr>';
                    });
                    $("#cluebody").html(html);

                    //引入分页控件
                    var totalPages = Math.ceil(data.total / pageSize);//向上取整
                    //data.total % pageSize == 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 5, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        onChangePage: function (event, data) {
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });


                }
            });
        }
    </script>
</head>
<body>

    <!-- 创建线索的模态窗口 -->
    <div class="modal fade" id="createClueModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 90%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">创建线索</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form" id="createClueForm">

                        <div class="form-group">
                            <label for="create-owner" class="col-sm-2 control-label">所有者<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="create-owner">
                                </select>
                            </div>
                            <label for="create-company" class="col-sm-2 control-label">公司<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-company">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="create-appellation" class="col-sm-2 control-label">称呼</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="create-appellation">
                                    <option></option>
                                    <c:forEach items="${applicationScope.appellation}" var="item">
                                        <option value="${item.value}">${item.text}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label for="create-fullname" class="col-sm-2 control-label">姓名<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-fullname">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="create-job" class="col-sm-2 control-label">职位</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-job">
                            </div>
                            <label for="create-email" class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-email">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="create-phone" class="col-sm-2 control-label">公司座机</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-phone">
                            </div>
                            <label for="create-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-website">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="create-mphone" class="col-sm-2 control-label">手机</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-mphone">
                            </div>
                            <label for="create-state" class="col-sm-2 control-label">线索状态</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="create-state">
                                    <option></option>
                                    <c:forEach items="${applicationScope.clueState}" var="item">
                                        <option value="${item.value}">${item.text}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="create-source" class="col-sm-2 control-label">线索来源</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="create-source">
                                    <option></option>
                                    <c:forEach items="${applicationScope.source}" var="item">
                                        <option value="${item.value}">${item.text}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>


                        <div class="form-group">
                            <label for="create-description" class="col-sm-2 control-label">线索描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="create-description"></textarea>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control time" id="create-nextContactTime">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address"></textarea>
                                </div>
                            </div>
                        </div>
                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改线索的模态窗口 -->
    <div class="modal fade" id="editClueModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 90%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">修改线索</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">

                        <div class="form-group">
                            <label for="edit-owner" class="col-sm-2 control-label">所有者<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-owner">
                                </select>
                            </div>
                            <label for="edit-company" class="col-sm-2 control-label">公司<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-company" value="">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-call" class="col-sm-2 control-label">称呼</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-call">
                                    <option></option>
                                    <c:forEach items="${applicationScope.appellation}" var="item">
                                        <option value="${item.value}">${item.text}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label for="edit-fullname" class="col-sm-2 control-label">姓名<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-fullname" value="">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-job" class="col-sm-2 control-label">职位</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-job" value="">
                            </div>
                            <label for="edit-email" class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-email" value="">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-phone" value="">
                            </div>
                            <label for="edit-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-website" value="">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-mphone" class="col-sm-2 control-label">手机</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-mphone" value="">
                            </div>
                            <label for="edit-state" class="col-sm-2 control-label">线索状态</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-state">
                                    <option></option>
                                    <c:forEach items="${applicationScope.clueState}" var="item">
                                        <option value="${item.value}">${item.text}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-source" class="col-sm-2 control-label">线索来源</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-source">
                                    <option></option>
                                    <c:forEach items="${applicationScope.source}" var="item">
                                        <option value="${item.value}">${item.text}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-description" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-description"></textarea>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="edit-contactSummary"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control time" id="edit-nextContactTime" value="">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address"></textarea>
                                </div>
                            </div>
                        </div>
                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
                </div>
            </div>
        </div>
    </div>


    <div>
        <div style="position: relative; left: 10px; top: -10px;">
            <div class="page-header">
                <h3>线索列表</h3>
            </div>
        </div>
    </div>

    <div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">

        <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

            <div class="btn-toolbar" role="toolbar" style="height: 80px;">
                <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
                    <input type="hidden" id="hidden-fullname">
                    <input type="hidden" id="hidden-company">
                    <input type="hidden" id="hidden-phone">
                    <input type="hidden" id="hidden-source">
                    <input type="hidden" id="hidden-owner">
                    <input type="hidden" id="hidden-mphone">
                    <input type="hidden" id="hidden-state">

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">名称</div>
                            <input class="form-control" type="text" id="search-fullname">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">公司</div>
                            <input class="form-control" type="text" id="search-company">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">公司座机</div>
                            <input class="form-control" type="text" id="search-phone">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">线索来源</div>
                            <select class="form-control" id="search-source">
                                <option></option>
                                <<c:forEach items="${applicationScope.source}" var="item">
                                <option value="${item.value}">${item.text}</option>
                            </c:forEach>
                            </select>
                        </div>
                    </div>

                    <br>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">所有者</div>
                            <input class="form-control" type="text" id="search-owner">
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">手机</div>
                            <input class="form-control" type="text" id="search-mphone">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">线索状态</div>
                            <select class="form-control" id="search-state">
                                <option></option>
                                <c:forEach items="${applicationScope.clueState}" var="item">
                                    <option value="${item.value}">${item.text}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <button type="button" class="btn btn-default" id="searchBtn">查询</button>

                </form>
            </div>
            <div class="btn-toolbar" role="toolbar"
                 style="background-color: #F7F7F7; height: 50px; position: relative;top: 40px;">
                <div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-primary" id="addBtn">
                        <span class="glyphicon glyphicon-plus"></span> 创建
                    </button>
                    <button type="button" class="btn btn-default" id="editBtn"><span
                            class="glyphicon glyphicon-pencil"></span> 修改
                    </button>
                    <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除
                    </button>
                </div>


            </div>
            <div style="position: relative;top: 50px;">
                <table class="table table-hover">
                    <thead>
                        <tr style="color: #B3B3B3;">
                            <td><input type="checkbox" id="selectAll"/></td>
                            <td>名称</td>
                            <td>公司</td>
                            <td>公司座机</td>
                            <td>手机</td>
                            <td>线索来源</td>
                            <td>所有者</td>
                            <td>线索状态</td>
                        </tr>
                    </thead>
                    <tbody id="cluebody">

                    </tbody>
                </table>
            </div>
            <div style="height: 50px; position: relative;top: 60px;">
                <%--在列表下面导入分页组件div（将原有默认分页div去掉）--%>
                <div id="activityPage"></div>
            </div>
        </div>

    </div>
</body>
</html>