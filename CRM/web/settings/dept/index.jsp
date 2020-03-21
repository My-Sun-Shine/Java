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

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#addBtn").click(function () {
                //打开模态窗口前，可以将添加模态窗口中的数据清空
                //方法1：一个一个的清除
                //方法2：获取表单，直接清空
                /*
                jquery对象有submit方法，但是没有reset方法
                dom对象有reset方法
                将$("#deptSaveFrom")转换为dom对象
                */
                $("#create-dept")[0].reset();

                //找到指定的模态窗口（通过id来找），找到后操作模态窗口（打开show/关闭hide）
                $("#createDeptModal").modal("show");
            });

            $("#saveBtn").click(function () {
                var no = $.trim($("#create-no").val());
                var manager = $.trim($("#create-manager").val());
                var name = $.trim($("#create-name").val());
                var phone = $.trim($("#create-phone").val());
                var description = $.trim($("#create-description").val());
                $.ajax({
                    url: "settings/dept/saveDept.do",
                    data: {
                        no: no,
                        manager: manager,
                        name: name,
                        phone: phone,
                        description: description
                    },
                    type: "post",
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        if (data.success) {
                            //保存成功后，将模态窗口隐藏掉
                            $("#createDeptModal").modal("hide");
                        } else {
                            alert("部门保存失败");
                        }
                    },
                    error: function () {
                        alert("部门保存失败");
                    }
                });

            })
        })
    </script>
</head>
<body>

    <!-- 我的资料 -->
    <div class="modal fade" id="myInformation" role="dialog">
        <div class="modal-dialog" role="document" style="width: 30%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">我的资料</h4>
                </div>
                <div class="modal-body">
                    <div style="position: relative; left: 40px;">
                        姓名：<b>张三</b><br><br>
                        登录帐号：<b>zhangsan</b><br><br>
                        组织机构：<b>1005，市场部，二级部门</b><br><br>
                        邮箱：<b>zhangsan@bjpowernode.com</b><br><br>
                        失效时间：<b>2017-02-14 10:10:10</b><br><br>
                        允许访问IP：<b>127.0.0.1,192.168.100.2</b>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改密码的模态窗口 -->
    <div class="modal fade" id="editPwdModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 70%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">修改密码</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="oldPwd" class="col-sm-2 control-label">原密码</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="oldPwd" style="width: 200%;">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="newPwd" class="col-sm-2 control-label">新密码</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="newPwd" style="width: 200%;">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="confirmPwd" class="col-sm-2 control-label">确认密码</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="confirmPwd" style="width: 200%;">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal"
                            onclick="window.location.href='login.html';">更新
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- 退出系统的模态窗口 -->
    <div class="modal fade" id="exitModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 30%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">离开</h4>
                </div>
                <div class="modal-body">
                    <p>您确定要退出系统吗？</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal"
                            onclick="window.location.href='login.html';">确定
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- 顶部 -->
    <div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
        <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
            CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
        <div style="position: absolute; top: 15px; right: 15px;">
            <ul>
                <li class="dropdown user-dropdown">
                    <a href="javascript:void(0)" style="text-decoration: none; color: white;" class="dropdown-toggle"
                       data-toggle="dropdown">
                        <span class="glyphicon glyphicon-user"></span> zhangsan <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="workbench/index.html"><span class="glyphicon glyphicon-home"></span> 工作台</a>
                        </li>
                        <li><a href="index.html"><span class="glyphicon glyphicon-wrench"></span> 系统设置</a></li>
                        <li><a href="javascript:void(0)" data-toggle="modal" data-target="#myInformation"><span
                                class="glyphicon glyphicon-file"></span> 我的资料</a></li>
                        <li><a href="javascript:void(0)" data-toggle="modal" data-target="#editPwdModal"><span
                                class="glyphicon glyphicon-edit"></span> 修改密码</a></li>
                        <li><a href="javascript:void(0);" data-toggle="modal" data-target="#exitModal"><span
                                class="glyphicon glyphicon-off"></span> 退出</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>

    <!-- 创建部门的模态窗口 -->
    <div class="modal fade" id="createDeptModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 80%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="createDept"><span class="glyphicon glyphicon-plus"></span> 新增部门</h4>
                </div>
                <div class="modal-body">

                    <form class="form-horizontal" role="form" id="create-dept">

                        <div class="form-group">
                            <label for="create-no" class="col-sm-2 control-label">编号<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-no" style="width: 200%;"
                                       placeholder="编号不能为空，具有唯一性" name="no">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="create-name" class="col-sm-2 control-label">名称</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name" style="width: 200%;"
                                       name="name">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="create-manager" class="col-sm-2 control-label">负责人</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-manager" style="width: 200%;"
                                       name="manager">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="create-phone" class="col-sm-2 control-label">电话</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-phone" style="width: 200%;"
                                       name="phone">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="create-description" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 55%;">
                                <textarea class="form-control" rows="3" id="create-description"
                                          name="description"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <%--data-dismiss="modal" 表示将模态窗口干掉--%>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改部门的模态窗口 -->
    <div class="modal fade" id="editDeptModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 80%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="editDept"><span class="glyphicon glyphicon-edit"></span> 编辑部门</h4>
                </div>
                <div class="modal-body">

                    <form class="form-horizontal" role="form">

                        <div class="form-group">
                            <label for="edit-no" class="col-sm-2 control-label">编号<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-no" style="width: 200%;"
                                       placeholder="不能为空，具有唯一性" value="1110">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-name" class="col-sm-2 control-label">名称</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name" style="width: 200%;"
                                       value="财务部">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-manager" class="col-sm-2 control-label">负责人</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-manager" style="width: 200%;"
                                       value="张飞">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-phone" class="col-sm-2 control-label">电话</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-phone" style="width: 200%;"
                                       value="010-84846004">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-description" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 55%;">
                                <textarea class="form-control" rows="3"
                                          id="edit-description" name="description">description info</textarea>
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

    <div style="width: 95%">
        <div>
            <div style="position: relative; left: 30px; top: -10px;">
                <div class="page-header">
                    <h3>部门列表</h3>
                </div>
            </div>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px; top:-30px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <%--
                data-toggle="modal" 打开一个模态窗口
                data-target="#createDeptModal" 表示打开那个模态窗口
                使用#来对模态窗口的id进行指定

			  	一般在开发过程中，从点击按钮到打开模态窗口期间，有可能会处理一些其他的事情

			  	现在的模板是将data-toggle="modal" data-target="#createDeptModal"直接写在了button标签的属性中，
			  	所以在此期间我们不能做其他的操作

			  	需要自己手动的去操作打开模态窗口的时机
                --%>
                <button type="button" class="btn btn-primary" id="addBtn">
                    <span class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#editDeptModal"><span
                        class="glyphicon glyphicon-edit"></span> 编辑
                </button>
                <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
            </div>
        </div>
        <div style="position: relative; left: 30px; top: -10px;">
            <table class="table table-hover">
                <thead>
                    <tr style="color: #B3B3B3;">
                        <td><input type="checkbox"/></td>
                        <td>编号</td>
                        <td>名称</td>
                        <td>负责人</td>
                        <td>电话</td>
                        <td>描述</td>
                    </tr>
                </thead>
                <tbody>
                    <tr class="active">
                        <td><input type="checkbox"/></td>
                        <td>1110</td>
                        <td>财务部</td>
                        <td>张飞</td>
                        <td>010-84846005</td>
                        <td>description info</td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"/></td>
                        <td>1120</td>
                        <td>销售部</td>
                        <td>关羽</td>
                        <td>010-84846006</td>
                        <td>description info</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 0px; left:30px;">
            <div>
                <button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
            </div>
            <div class="btn-group" style="position: relative;top: -34px; left: 110px;">
                <button type="button" class="btn btn-default" style="cursor: default;">显示</button>
                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                        10
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">20</a></li>
                        <li><a href="#">30</a></li>
                    </ul>
                </div>
                <button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
            </div>
            <div style="position: relative;top: -88px; left: 285px;">
                <nav>
                    <ul class="pagination">
                        <li class="disabled"><a href="#">首页</a></li>
                        <li class="disabled"><a href="#">上一页</a></li>
                        <li><a href="#">下一页</a></li>
                        <li class="disabled"><a href="#">末页</a></li>
                    </ul>
                </nav>
            </div>
        </div>

    </div>

</body>
</html>