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

        //默认情况下取消和保存按钮是隐藏的
        var cancelAndSaveBtnDefault = true;

        $(function () {
            $("#remark").focus(function () {
                if (cancelAndSaveBtnDefault) {
                    //设置remarkDiv的高度为130px
                    $("#remarkDiv").css("height", "130px");
                    //显示
                    $("#cancelAndSaveBtn").show("2000");
                    cancelAndSaveBtnDefault = false;
                }
            });

            $("#cancelBtn").click(function () {
                //显示
                $("#cancelAndSaveBtn").hide();
                //设置remarkDiv的高度为130px
                $("#remarkDiv").css("height", "90px");
                cancelAndSaveBtnDefault = true;
            });

            $(".remarkDiv").mouseover(function () {
                $(this).children("div").children("div").show();
            });

            $(".remarkDiv").mouseout(function () {
                $(this).children("div").children("div").hide();
            });

            $(".myHref").mouseover(function () {
                $(this).children("span").css("color", "red");
            });

            $(".myHref").mouseout(function () {
                $(this).children("span").css("color", "#E6E6E6");
            });

            loadActivity();

            $("#deleteRelation").click(function () {
                var id = $("#unbundId").val();
                $.ajax({
                    url: "workbench/clue/deleteRelation.do",
                    data: {
                        "id": id
                    },
                    type: "post",
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        if (data.success) {
                            loadActivity();
                            $("#unbundModal").modal("hide");
                        } else {
                            alert("解除关联失败");
                        }
                    },
                    error: function () {
                        alert("解除关联失败");
                    }
                })
            })

            $("#bundBtn").click(function () {
                var $xz = $("input[name=xz]:checked");
                if ($xz.length === 0) {
                    alert("请选择市场活动")
                } else {
                    var param = "clueId=${clue.id}&";
                    for (var i = 0; i < $xz.length; i++) {
                        param += "id=" + $($xz[i]).val();
                        if (i < $xz.length - 1) {
                            param += "&"
                        }
                    }
                    $.ajax({
                        url: "workbench/clue/bund.do",
                        data: param,
                        type: "get",
                        async: false,
                        dataType: "json",
                        success: function (data) {
                            if (data.success) {
                                loadActivity();
                                $("#bundModal").modal("hide");
                            } else {
                                alert("关联失败");
                            }
                        }
                    });
                }


            })

            $("#activity-name").keydown(function () {
                if (event.keyCode === 13) {
                    var name = $.trim($("#activity-name").val());
                    if (name === "") {
                        alert("请输入市场活动名称");
                        return false;
                    }
                    console.log("${clue.id}");
                    $.ajax({
                        url: "workbench/clue/getActivityListByNameAndNotByClueId.do",
                        data: {
                            "clueId": "${clue.id}",
                            "aname": name
                        },
                        type: "get",
                        async: false,
                        dataType: "json",
                        success: function (data) {
                            var html = "";
                            $.each(data, function (i, item) {
                                html += '<tr>';
                                html += '<td><input type="checkbox" name="xz" value="' + item.id + '"/></td>';
                                html += '<td>' + item.name + '</td>';
                                html += '<td>' + item.startDate + '</td>';
                                html += '<td>' + item.endDate + '</td>';
                                html += '<td>' + item.owner + '</td>';
                                html += '</tr>';
                            });
                            $("#allActivityBody").html(html);
                        }
                    });
                    return false;
                }


            })

            $("#selectAllActivity").click(function () {
                $("input[name=xz]").prop("checked", this.checked)
            });

            $("#allActivityBody").on("click", $("input[name=xz]"), function () {
                //当点击列表上的多选框时，如果列表上的所有多选框都被选中，则全选框被选中，反之，如果有一个没有被选中，这全选框取消选中
                $("#selectAllActivity").prop("checked", $("input[name=xz]").length == $("input[name=xz]:checked").length);
            });
        });

        function loadActivity() {
            $.ajax({
                url: "workbench/clue/getActivityByClueId.do",
                data: {
                    "ClueId": "${clue.id}"
                },
                type: "get",
                async: false,
                dataType: "json",
                success: function (data) {
                    var html = "";
                    $.each(data, function (i, item) {
                        html += '<tr>';
                        html += '<td>' + item.name + '</td>';
                        html += '<td>' + item.startDate + '</td>';
                        html += '<td>' + item.endDate + '</td>';
                        html += '<td>' + item.owner + '</td>';
                        html += '<td><a href="javascript:void(0);" onclick="unbund(\'' + item.id + '\')" style="text-decoration: none;">';
                        html += '<span class="glyphicon glyphicon-remove"></span>解除关联</a></td>';
                        html += '</tr>';
                    });
                    $("#activityBody").html(html);
                }
            })

        }

        //这里的id是市场活动和显示关联表的id
        function unbund(id) {
            $("#unbundModal").modal("show");
            $("#unbundId").val(id);
        }

        //关联市场活动
        function bund() {
            $("#bundModal").modal("show");
            $("#activity-name").val("");
            $("#activity-name").focus();
            $("#allActivityBody").html("");
            $("#selectAllActivity").prop("checked", false);
        }

        //跳转到转换页面
        function convert() {
            var param = "id=${clue.id}&fullname=${clue.fullname}&appellation=${clue.appellation}&company=${clue.company}&owner=${clue.owner}"
            window.location.href = 'workbench/clue/convert.jsp?' + param;
        }
    </script>

</head>
<body>

    <!-- 解除关联的模态窗口 -->
    <div class="modal fade" id="unbundModal" role="dialog">
        <input type="hidden" id="unbundId">
        <div class="modal-dialog" role="document" style="width: 30%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">解除关联</h4>
                </div>
                <div class="modal-body">
                    <p>您确定要解除该关联关系吗？</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-danger" id="deleteRelation">确定</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 关联市场活动的模态窗口 -->
    <div class="modal fade" id="bundModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 80%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">关联市场活动</h4>
                </div>
                <div class="modal-body">
                    <div class="btn-group" style="position: relative; top: 18%; left: 8px;">
                        <form class="form-inline" role="form">
                            <div class="form-group has-feedback">
                                <input id="activity-name" type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
                                <span class="glyphicon glyphicon-search form-control-feedback"></span>
                            </div>
                        </form>
                    </div>
                    <table id="activityTable" class="table table-hover"
                           style="width: 900px; position: relative;top: 10px;">
                        <thead>
                            <tr style="color: #B3B3B3;">
                                <td><input type="checkbox" id="selectAllActivity"/></td>
                                <td>名称</td>
                                <td>开始日期</td>
                                <td>结束日期</td>
                                <td>所有者</td>
                                <td></td>
                            </tr>
                        </thead>
                        <tbody id="allActivityBody">


                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="bundBtn">关联</button>
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
                    <h4 class="modal-title" id="myModalLabel">修改线索</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">

                        <div class="form-group">
                            <label for="edit-clueOwner" class="col-sm-2 control-label">所有者<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-clueOwner">
                                    <option>zhangsan</option>
                                    <option>lisi</option>
                                    <option>wangwu</option>
                                </select>
                            </div>
                            <label for="edit-company" class="col-sm-2 control-label">公司<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-company" value="动力节点">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-call" class="col-sm-2 control-label">称呼</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-call">
                                    <option></option>
                                    <option selected>先生</option>
                                    <option>夫人</option>
                                    <option>女士</option>
                                    <option>博士</option>
                                    <option>教授</option>
                                </select>
                            </div>
                            <label for="edit-surname" class="col-sm-2 control-label">姓名<span
                                    style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-surname" value="李四">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-job" class="col-sm-2 control-label">职位</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-job" value="CTO">
                            </div>
                            <label for="edit-email" class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-email" value="lisi@bjpowernode.com">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-phone" value="010-84846003">
                            </div>
                            <label for="edit-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-website"
                                       value="http://www.bjpowernode.com">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-mphone" class="col-sm-2 control-label">手机</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-mphone" value="12345678901">
                            </div>
                            <label for="edit-status" class="col-sm-2 control-label">线索状态</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-status">
                                    <option></option>
                                    <option>试图联系</option>
                                    <option>将来联系</option>
                                    <option selected>已联系</option>
                                    <option>虚假线索</option>
                                    <option>丢失线索</option>
                                    <option>未联系</option>
                                    <option>需要条件</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-source" class="col-sm-2 control-label">线索来源</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-source">
                                    <option></option>
                                    <option selected>广告</option>
                                    <option>推销电话</option>
                                    <option>员工介绍</option>
                                    <option>外部介绍</option>
                                    <option>在线商场</option>
                                    <option>合作伙伴</option>
                                    <option>公开媒介</option>
                                    <option>销售邮件</option>
                                    <option>合作伙伴研讨会</option>
                                    <option>内部研讨会</option>
                                    <option>交易会</option>
                                    <option>web下载</option>
                                    <option>web调研</option>
                                    <option>聊天</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-describe">这是一条线索的描述信息</textarea>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3"
                                              id="edit-contactSummary">这个线索即将被转换</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control" id="edit-nextContactTime"
                                           value="2017-05-01">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address">北京大兴区大族企业湾</textarea>
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

    <!-- 返回按钮 -->
    <div style="position: relative; top: 35px; left: 10px;">
        <a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
    </div>

    <!-- 大标题 -->
    <div style="position: relative; left: 40px; top: -30px;">
        <div class="page-header">
            <h3>${clue.fullname}${c.appellation}
                <small>${clue.company}</small>
            </h3>
        </div>
        <div style="position: relative; height: 50px; width: 500px;  top: -72px; left: 700px;">
            <button type="button" class="btn btn-default" onclick="convert()"><span class="glyphicon glyphicon-retweet"></span> 转换</button>
            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#editClueModal"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
            <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
        </div>
    </div>

    <!-- 详细信息 -->
    <div style="position: relative; top: -70px;">
        <div style="position: relative; left: 40px; height: 30px;">
            <div style="width: 300px; color: gray;">名称</div>
            <div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.fullname}</b></div>
            <div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">所有者</div>
            <div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.owner}</b></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 10px;">
            <div style="width: 300px; color: gray;">公司</div>
            <div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.company}</b></div>
            <div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">职位</div>
            <div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.job}</b></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 20px;">
            <div style="width: 300px; color: gray;">邮箱</div>
            <div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.email}</b></div>
            <div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">公司座机</div>
            <div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.phone}</b></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 30px;">
            <div style="width: 300px; color: gray;">公司网站</div>
            <div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.website}</b>
            </div>
            <div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">手机</div>
            <div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.mphone}</b></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 40px;">
            <div style="width: 300px; color: gray;">线索状态</div>
            <div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.state}</b></div>
            <div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">线索来源</div>
            <div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.source}</b></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 50px;">
            <div style="width: 300px; color: gray;">创建者</div>
            <div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${clue.createBy}&nbsp;&nbsp;</b>
                <small style="font-size: 10px; color: gray;">${clue.createTime}</small>
            </div>
            <div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 60px;">
            <div style="width: 300px; color: gray;">修改者</div>
            <div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${clue.editBy}&nbsp;&nbsp;</b>
                <small style="font-size: 10px; color: gray;">${clue.editTime}</small>
            </div>
            <div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 70px;">
            <div style="width: 300px; color: gray;">描述</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;"><b>${clue.description}</b></div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 80px;">
            <div style="width: 300px; color: gray;">联系纪要</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;"><b>${clue.contactSummary}</b></div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 90px;">
            <div style="width: 300px; color: gray;">下次联系时间</div>
            <div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.nextContactTime}&nbsp;</b></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px; "></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 100px;">
            <div style="width: 300px; color: gray;">详细地址</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;"><b>${clue.address}</b></div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
    </div>

    <!-- 备注 -->
    <div style="position: relative; top: 40px; left: 40px;">
        <div class="page-header">
            <h4>备注</h4>
        </div>

        <!-- 备注1 -->
        <div class="remarkDiv" style="height: 60px;">
            <img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
            <div style="position: relative; top: -40px; left: 40px;">
                <h5>哎呦！</h5>
                <font color="gray">线索</font> <font color="gray">-</font> <b>李四先生-动力节点</b>
                <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
                <div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
                    <a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit"
                                                                       style="font-size: 20px; color: #E6E6E6;"></span></a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove"
                                                                       style="font-size: 20px; color: #E6E6E6;"></span></a>
                </div>
            </div>
        </div>


        <div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
            <form role="form" style="position: relative;top: 10px; left: 10px;">
                <textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"
                          placeholder="添加备注..."></textarea>
                <p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
                    <button id="cancelBtn" type="button" class="btn btn-default">取消</button>
                    <button type="button" class="btn btn-primary">保存</button>
                </p>
            </form>
        </div>
    </div>

    <!-- 市场活动 -->
    <div>
        <div style="position: relative; top: 60px; left: 40px;">
            <div class="page-header">
                <h4>市场活动</h4>
            </div>
            <div style="position: relative;top: 0px;">
                <table class="table table-hover" style="width: 900px;">
                    <thead>
                        <tr style="color: #B3B3B3;">
                            <td>名称</td>
                            <td>开始日期</td>
                            <td>结束日期</td>
                            <td>所有者</td>
                            <td></td>
                        </tr>
                    </thead>
                    <tbody id="activityBody">

                    </tbody>
                </table>
            </div>

            <div>
                <a href="javascript:void(0);" onclick="bund()"
                   style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>关联市场活动</a>
            </div>
        </div>
    </div>


    <div style="height: 200px;"></div>
</body>
</html>