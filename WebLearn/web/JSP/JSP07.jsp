<%@ page import="D.Dept" %><%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/11
  Time: 23:00
  Description: 数据更新页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>JSP07</title>
</head>
<body>
    <%
        //1.从request对象读取Servlet27提供的共享数据
        Dept dept = (Dept) request.getAttribute("dept");
    %>
    <center>
        <form action="D/Servlet04">
            <table border="2">
                <!--
                     readOnly与disabled
                     1.readOnley修饰表单域标签，可以作为请求参数。可以获得光标
                     2.disabled修饰表单域标签，没有机会作为请求参数，无法获得光标
                 -->
                <tr>
                    <td>部门编号</td>
                    <td><input type="text" name="deptno" value="<%=dept.getDeptNo()%>" readOnly></td>
                </tr>
                <tr>
                    <td>部门名称</td>
                    <td><input type="text" name="dname" value="<%=dept.getDname()%>"></td>
                </tr>

                <tr>
                    <td>部门位置</td>
                    <td><input type="text" name="loc" value="<%=dept.getLoc()%>"></td>
                </tr>

                <tr>
                    <td><input type="submit" value="更新"/></td>
                    <td><input type="reset"></td>
                </tr>

            </table>
        </form>

    </center>
</body>
</html>
