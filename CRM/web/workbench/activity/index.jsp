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
                        });
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
            });

            //全选操作1
            /*$("#selectAll").click(function () {
                //所有name为checkboxActivity的checkbox
                var $checkbox = $("input[name=checkboxActivity]");

                for (var i = 0; i < $checkbox.length; i++) {
                    $checkbox[i].checked = $("#selectAll")[0].checked
                }
            });*/
            $("#selectAll").click(function () {
                $("input[name=checkboxActivity]").prop("checked", this.checked)
            });
            /*js动态生成的元素是不能够以传统的形式来绑定事件的
            直接绑click事件 直接绑change事件等等都不好使了*/
            /*js动态生成的元素以  on 的形式来绑事件
            $(需要绑定元素的有效的外层元素).on(绑定事件的方式，绑定的元素，回调函数)*/
            $("#activityBody").on("click", $("input[name=checkboxActivity]"), function () {
                //当点击列表上的多选框时，如果列表上的所有多选框都被选中，则全选框被选中，反之，如果有一个没有被选中，这全选框取消选中
                $("#selectAll").prop("checked",
                    $("input[name=checkboxActivity]").length == $("input[name=checkboxActivity]:checked").length);
            });


            $("#deleteBtn").click(function () {
                //deleteActivity.do?id=A0001&id=A0002&id=A0003
                //获取选中的复选框
                var $checkbox = $("input[name=checkboxActivity]:checked");
                if ($checkbox.length === 0) {
                    alert("请选择需要删除的记录");
                } else {
                    if (!confirm("你确定要删除选中数据！！！")) {
                        return;
                    }
                    var param = "";
                    for (var i = 0; i < $checkbox.length; i++) {
                        //param += "id" + $checkbox[i].value;

                        //将dom对象包装为jquery对象 $(dom)
                        param += "id=" + $($checkbox[i]).val();

                        if (i < $checkbox.length - 1) {
                            param += "&";
                        }
                    }
                    console.log(param);

                    $.ajax({
                        url: "workbench/activity/deleteActivity.do",
                        data: param,
                        type: "post",
                        dataType: "json",
                        success: function (data) {
                            if (data.success) {
                                alert("删除成功");

                                //$("#activityPage").bs_pagination('getOption', 'rowsPerPage')这句是获取设置的每页多少记录
                                pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                            } else {
                                alert("删除失败");
                            }
                        }

                    })
                }
            })

            //修改操作
            $("#editBtn").click(function () {
                var $checkbox = $("input[name=checkboxActivity]:checked");
                if ($checkbox.length === 0) {
                    alert("请选择需要修改的记录");
                } else if ($checkbox.length > 1) {
                    alert("只能选择一条记录执行修改");
                } else {
                    //如果能够确定jquery对象只选中了一个dom，就可以直接以.val方法的形式来取值
                    //$checkbox[0].value
                    //$($checkbox[0]).val()
                    var id = $checkbox.val();
                    $.ajax({
                        url: "workbench/activity/showEditActivity.do",
                        data: {
                            "id": id
                        },
                        type: "get",
                        async: false,
                        dataType: "json",
                        success: function (data) {
                            /*data:{"userList":[{用户1},{用户2},{用户3}],"activity" : 市场活动对象}*/
                            var html = "<option value=''></option>";
                            $.each(data.userList, function (i, item) {
                                html += "<option value='" + item.id + "'>" + item.name + "</option>"
                            });
                            $("#edit-owner").html(html);

                            var activity = data.activity;
                            $("#edit-owner").val(activity.owner);
                            $("#edit-name").val(activity.name);
                            $("#edit-startDate").val(activity.startDate);
                            $("#edit-endDate").val(activity.endDate);
                            $("#edit-cost").val(activity.cost);
                            $("#edit-description").val(activity.description);
                            $("#edit-id").val(activity.id);

                            $("#editActivityModal").modal("show");
                        }
                    });

                }

            });
            $("#updateBtn").click(function () {
                var id = $.trim($("#edit-id").val());
                var owner = $.trim($("#edit-owner").val());
                var name = $.trim($("#edit-name").val());
                var startDate = $.trim($("#edit-startDate").val());
                var endDate = $.trim($("#edit-endDate").val());
                var cost = $.trim($("#edit-cost").val());
                var description = $.trim($("#edit-description").val());
                $.ajax({
                    url: "workbench/activity/updateActivity.do",
                    data: {
                        "id": id,
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
                            //前一个是当前页码，后面一个是一页的记录数量
                            //停留着当前页
                            pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                                , $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                            $("#editActivityModal").modal("hide");
                        } else {
                            alert("市场活动修改失败");
                        }
                    },
                    error: function () {
                        alert("市场活动修改失败");
                    }
                });

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
            //换页的时候取消选中
            $("#selectAll")[0].checked = false;

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
                        html += '<td><input name="checkboxActivity" type="checkbox" value="' + item.id + '"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;"';
                        html += 'onclick="window.location.href=\'workbench/activity/detailActivity.do?id=' + item.id + '\';">';
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
                        <input type="hidden" id="edit-id">
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
                                <input type="text" class="form-control" id="edit-name" value="">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control time" id="edit-startDate" value="">
                            </div>
                            <label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control time" id="edit-endDate" value="">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-cost" value="">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-description" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-description"></textarea>
                            </div>
                        </div>

                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="updateBtn">更新</button>
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
                    <button type="button" class="btn btn-default" id="editBtn">
                        <span class="glyphicon glyphicon-pencil"></span> 修改
                    </button>
                    <button type="button" class="btn btn-danger" id="deleteBtn">
                        <span class="glyphicon glyphicon-minus"></span> 删除
                    </button>
                </div>

            </div>
            <div style="position: relative;top: 10px;">
                <table class="table table-hover">
                    <thead>
                        <tr style="color: #B3B3B3;">
                            <td><input type="checkbox" id="selectAll"/></td>
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