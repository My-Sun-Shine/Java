<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/13
  Time: 14:18
  Description: 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>JSP14</title>
    <script type="text/javascript" src="JS/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#dept").change(function () {
                $.get(
                    "E/Servlet08",
                    "deptno=" + $("#dept").val(),
                    fun1,
                    'json'
                );
            });
        });

        function fun1(data) {
            //table标签中的数据行删除
            console.log(data);
            console.log(data.length);
            $("table tr:gt(0)").remove();
            for (var i = 0; i < data.length; i++) {
                var jsonObj = data[i];
                console.log(jsonObj);
                var $tr = $("<tr></tr>");
                if(i==0){
                    var $td1 = $("<td></td>");
                    $td1.attr("rowspan",data.length);
                    $td1.text(jsonObj.dname);
                    var $td2 = $("<td></td>");
                    var $td3 = $("<td></td>");

                    $td2.text(jsonObj.job);
                    $td3.text(jsonObj.jobNum);
                    $tr.append($td1).append($td2).append($td3);
                }
                else{
                    var $td2 = $("<td></td>");
                    var $td3 = $("<td></td>");

                    $td2.text(jsonObj.job);
                    $td3.text(jsonObj.jobNum);
                    $tr.append($td2).append($td3);
                }
                $("table").append($tr);
            }

            console.log($tr);

        }
    </script>
</head>
<body>
    <center>
        部门名称:
        <select id="dept">
            <option value="0">SELECT DEPT----</option>
            <option value="10">ACCOUNTING</option>
            <option value="20">RESEARCH</option>
            <option value="30">SALES</option>
            <option value="40">OPERATION</option>
        </select>
        <br/>
        <br/>
        <table border="2">
            <tr>
                <td>部门名称</td>
                <td>职位名称</td>
                <td>职位人数</td>
            </tr>
        </table>

    </center>
</body>
</html>
