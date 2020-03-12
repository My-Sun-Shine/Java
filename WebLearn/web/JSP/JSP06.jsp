<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="D.Dept" %><%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/11
  Time: 16:16
  Description: 部门显示主页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        //通过jsp文件中内置request对象读取DeptFindServlet共享数据
        List<Dept> deptList = (List<Dept>) request.getAttribute("deptList");
    %>
    <base href="<%=basePath%>">
    <title>JSP06</title>
</head>
<body>
    <center>
        <table border="2">
            <tr>
                <td>部门编号</td>
                <td>部门名称</td>
                <td>部门位置</td>
                <td>部门删除</td>
                <td>部门更新</td>
            </tr>
            <% for (Dept dept : deptList) {%>
            <tr>
                <td><%=dept.getDeptNo()%>
                </td>
                <td><%=dept.getDname()%>
                </td>
                <td><%=dept.getLoc()%>
                </td>
                <td><a href="E/Servlet02?deptno=<%=dept.getDeptNo() %>">删除部门</a></td>
                <td><a href="E/Servlet03?deptno=<%=dept.getDeptNo() %>">部门更新</a></td>
            </tr>
            <%}%>
        </table>
        <br/>
        <hr/>
        使用JSTL
        <br/>
        <table border="2">
            <tr>
                <td>部门编号</td>
                <td>部门名称</td>
                <td>部门位置</td>
                <td>部门删除</td>
                <td>部门更新</td>
            </tr>
           <c:forEach items="${requestScope.deptList}" var="item">
            <tr>
                <td>${item.deptNo}
                </td>
                <td>${item.dname}
                </td>
                <td>${item.loc}
                </td>
                <td><a href="E/Servlet02?deptno=${item.deptNo}">删除部门</a></td>
                <td><a href="E/Servlet03?deptno=${item.deptNo}">部门更新</a></td>
            </tr>
           </c:forEach>
        </table>
    </center>

</body>
</html>
