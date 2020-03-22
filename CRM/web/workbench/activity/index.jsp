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
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

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
                pickerPosition: "bottom-left"
            });


            //定制字段
            $("#definedColumns > li").click(function (e) {
                //防止下拉菜单消失
                e.stopPropagation();
            });


            //打开创建市场活动的模态窗口
            $("#addBtn").click(function () {
                $("#create-marketActivity")[0].reset();
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
                        })
                        $("#create-owner").html(html);
                    }
                });

                $("#createActivityModal").modal("show");
            });

            //保存市场活动操作
            $("#saveBtn").click(function () {
                var owner = $.trim($("#create-owner").val());
                var name = $.trim($("#create-name").val());
                var startDate = $.trim($("#create-startDate").val());
                var endDate = $.trim($("#create-endDate").val());
                var cost = $.trim($("#create-cost").val());
                var description = $.trim($("#create-description").val());

                $.ajax({
                    url: "workbench/activity/saveActivity.do",
                    data: {
                        "owner": owner,
                        "name": name,
                        "startDate": startDate,
                        "endDate": endDate,
                        "cost": cost,
                        "description": description
                    },
                    type: "post",
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        if (data.success) {
                            window.location.href = "workbench/activity/index.jsp";
                        }
                        else {
                            alert("市场活动添加失败");
                        }
                    },
                    error: function () {
                        alert("市场活动添加失败");
                    }
                });
                $("#createActivityModal").modal("hide");
            })

            //页面加载完毕之后，默认展示第一页数据，第一页展示2条数据
            pageList(1, PAGESIZE);

            $("#searchBtn").click(function () {
                //点击查询的时候，将搜索框上的值放到隐藏域中
                //只有点击查询按钮，才能修改隐藏域的值
                $("#hidden-name").val($("#search-name").val());
                $("#hidden-owner").val($("#search-owner").val());
                $("#hidden-startDate").val($("#search-startDate").val());
                $("#hidden-endDate").val($("#search-endDate").val());
                pageList(1, PAGESIZE)
            })
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
            //普通换页的时候(不是点击查询按钮)，获取隐藏域的值赋值到搜索框中
            $("#search-name").val($("#hidden-name").val());
            $("#search-owner").val($("#hidden-owner").val());
            $("#search-startDate").val($("#hidden-startDate").val());
            $("#search-endDate").val($("#hidden-endDate").val());

            var name = $.trim($("#search-name").val());
            var owner = $.trim($("#search-owner").val());
            var startDate = $.trim($("#search-startDate").val());
            var endDate = $.trim($("#search-endDate").val());
            $.ajax({
                url: "workbench/activity/pageList.do",
                data: {
                    "pageNo": pageNo,
                    "pageSize": pageSize,
                    "name": name,
                    "owner": owner,
                    "startDate": startDate,
                    "endDate": endDate,
                    cache: false//加入时间戳，确保每次数据都是最新的，不是浏览器的缓存数据
                },
                type: "get",
                async: false,
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    // data:{"total":?,"dataList":[{市场活动1},{市场活动2},{市场活动3}...]}
                    var html = "";
                    $.each(data.dataList, function (i, item) {
                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" value="' + item.id + '"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;"';
                        html += 'onclick="window.location.href=\'workbench/activity/detail.jsp\';">';
                        html += item.name + '</a></td>';
                        html += '<td>' + item.owner + '</td>';
                        html += '<td>' + item.startDate + '</td>';
                        html += '<td>' + item.endDate + '</td>';
                        html += '</tr>';
                    });
                    $("#activityBody").html(html);

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
    <%--隐藏域--%>
    <input type="hidden" id="hidden-name"/>
    <input type="hidden" id="hidden-owner"/>
    <input type="hidden" id="hidden-startDate"/>
    <input type="hidden" id="hidden-endDate"/>

    <!-- 创建市场活动的模态窗口 -->
    <div class="modal fade" id="createActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
                </div>
                <div class="modal-body">

                    <form id="create-marketActivity" class="form-horizontal" role="form">

                        <div class="form-group">
                            <label for="create-owner" class="col-sm-2 control-label">所有者<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="create-owner">
                                </select>
                            </div>
                            <label for="create-name" class="col-sm-2 control-label">名称<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control time" id="create-startDate">
                            </div>
                            <label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control time" id="create-endDate">
                            </div>
                        </div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="create-description" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="create-description"></textarea>
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

    <!-- 修改市场活动的模态窗口 -->
    <div class="modal fade" id="editActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
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
                            <label for="edit-name" class="col-sm-2 control-label">名称<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name" value="发传单">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control time" id="edit-startDate" value="2020-10-10">
                            </div>
                            <label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control time" id="edit-endDate" value="2020-10-20">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-cost" value="5,000">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-description" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-description">
                                    市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、
                                    客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、
                                    行业交流会、颁奖典礼等</textarea>
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
                <h3>市场活动列表</h3>
            </div>
        </div>
    </div>
    <div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
        <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

            <div class="btn-toolbar" role="toolbar" style="height: 80px;">
                <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">名称</div>
                            <input class="form-control" type="text" id="search-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">所有者</div>
                            <input class="form-control" type="text" id="search-owner">
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">开始日期</div>
                            <input class="form-control time" type="text" id="search-startDate"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">结束日期</div>
                            <input class="form-control time" type="text" id="search-endDate">
                        </div>
                    </div>

                    <button id="searchBtn" type="button" class="btn btn-default">查询</button>

                </form>
            </div>
            <div class="btn-toolbar" role="toolbar"
                 style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
                <div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-primary" id="addBtn"><span
                            class="glyphicon glyphicon-plus"></span> 创建
                    </button>
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#editActivityModal">
                        <span class="glyphicon glyphicon-pencil"></span> 修改
                    </button>
                    <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除
                    </button>
                </div>

            </div>
            <div style="position: relative;top: 10px;">
                <table class="table table-hover">
                    <thead>
                        <tr style="color: #B3B3B3;">
                            <td><input type="checkbox"/></td>
                            <td>名称</td>
                            <td>所有者</td>
                            <td>开始日期</td>
                            <td>结束日期</td>
                        </tr>
                    </thead>
                    <tbody id="activityBody">
                    </tbody>
                </table>
            </div>
            <%--在列表下面导入分页组件div（将原有默认分页div去掉）--%>
            <div id="activityPage"></div>
        </div>

    </div>
</body>
</html>