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
            //定制字段
            $("#definedColumns > li").click(function (e) {
                //防止下拉菜单消失
                e.stopPropagation();
            });


            //页面加载完毕之后，默认展示第一页数据，第一页展示2条数据
            pageList(1, PAGESIZE);

            $("#searchBtn").click(function () {
                //点击查询的时候，将搜索框上的值放到隐藏域中
                //只有点击查询按钮，才能修改隐藏域的值
                $("#hidden-owner").val($("#search-owner").val());
                $("#hidden-name").val($("#search-name").val());
                $("#hidden-customerName").val($("#search-customerName").val());
                $("#hidden-stage").val($("#search-stage").val());
                $("#hidden-type").val($("#search-type").val());
                $("#hidden-source").val($("#search-source").val());
                $("#hidden-contactsName").val($("#search-contactsName").val());
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
            $("#search-owner").val($("#hidden-owner").val());
            $("#search-name").val($("#hidden-name").val());
            $("#search-customerName").val($("#hidden-customerName").val());
            $("#search-stage").val($("#hidden-stage").val());
            $("#search-type").val($("#hidden-type").val());
            $("#search-source").val($("#hidden-source").val());
            $("#search-contactsName").val($("#hidden-contactsName").val());

            var owner = $.trim($("#search-owner").val());
            var name = $.trim($("#search-name").val());
            var customerName = $.trim($("#search-customerName").val());
            var stage = $.trim($("#search-stage").val());
            var type = $.trim($("#search-type").val());
            var source = $.trim($("#search-source").val());
            var contactsName = $.trim($("#search-contactsName").val());
            $.ajax({
                url: "workbench/transaction/pageList.do",
                data: {
                    "pageNo": pageNo,
                    "pageSize": pageSize,
                    "name": name,
                    "customerName": customerName,
                    "stage": stage,
                    "source": source,
                    "owner": owner,
                    "type": type,
                    "contactsName": contactsName,
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
                        html += 'onclick="window.location.href=\'workbench/transaction/detailClue.do?id=' + item.id + '\';">' + item.name + '</a></td>';
                        html += '<td>' + item.customerId + '</td>';
                        html += '<td>' + item.stage + '</td>';
                        html += '<td>' + item.type + '</td>';
                        html += '<td>' + item.owner + '</td>';
                        html += '<td>' + item.source + '</td>';
                        html += '<td>' + item.contactsId + '</td>';
                        html += '</tr>';

                    });
                    $("#transactionbody").html(html);

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


    <div>
        <div style="position: relative; left: 10px; top: -10px;">
            <div class="page-header">
                <h3>交易列表</h3>
            </div>
        </div>
    </div>

    <div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">

        <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

            <div class="btn-toolbar" role="toolbar" style="height: 80px;">
                <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
                    <input type="hidden" id="hidden-owner">
                    <input type="hidden" id="hidden-name">
                    <input type="hidden" id="hidden-customerName">
                    <input type="hidden" id="hidden-stage">
                    <input type="hidden" id="hidden-type">
                    <input type="hidden" id="hidden-source">
                    <input type="hidden" id="hidden-contactsName">

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">所有者</div>
                            <input class="form-control" type="text" id="search-owner">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">名称</div>
                            <input class="form-control" type="text" id="search-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">客户名称</div>
                            <input class="form-control" type="text" id="search-customerName">
                        </div>
                    </div>

                    <br>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">阶段</div>
                            <select class="form-control" id="search-stage">
                                <option></option>
                                <c:forEach items="${applicationScope.stage}" var="item">
                                    <option value="${item.value}">${item.text}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">类型</div>
                            <select class="form-control" id="search-type">
                                <option></option>
                                <option>已有业务</option>
                                <option>新业务</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">来源</div>
                            <select class="form-control" id="search-source">
                                <option></option>
                                <c:forEach items="${applicationScope.source}" var="item">
                                    <option value="${item.value}">${item.text}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">联系人名称</div>
                            <input class="form-control" type="text" id="search-contactsName">
                        </div>
                    </div>

                    <button type="button" class="btn btn-default" id="searchBtn">查询</button>

                </form>
            </div>
            <div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
                <div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-primary" onclick="window.location.href='workbench/transaction/save.jsp';"><span class="glyphicon glyphicon-plus"></span>
                        创建
                    </button>
                    <button type="button" class="btn btn-default" onclick="window.location.href='workbench/transaction/edit.jsp';"><span class="glyphicon glyphicon-pencil"></span>
                        修改
                    </button>
                    <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
                </div>


            </div>
            <div style="position: relative;top: 10px;">
                <table class="table table-hover">
                    <thead>
                        <tr style="color: #B3B3B3;">
                            <td><input type="checkbox" id="selectAll"/></td>
                            <td>名称</td>
                            <td>客户名称</td>
                            <td>阶段</td>
                            <td>类型</td>
                            <td>所有者</td>
                            <td>来源</td>
                            <td>联系人名称</td>
                        </tr>
                    </thead>
                    <tbody id="transactionbody">
                    </tbody>
                </table>
            </div>

            <div style="height: 50px; position: relative;top: 20px;">
                <%--在列表下面导入分页组件div（将原有默认分页div去掉）--%>
                <div id="activityPage"></div>
            </div>

        </div>

    </div>
</body>
</html>