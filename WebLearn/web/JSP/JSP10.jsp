<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/12
  Time: 13:22
  Description: JSTL的应用
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>JSP10</title>
</head>
<body>
    <center>
        <table border="2" width="500px">
            <tr>
                <td>部门名称</td>
                <td>职位名称</td>
                <td>职位人数</td>
            </tr>
            <!-- 数据行 -->
            <!-- item 存放键值对  key ：部门名称   value：小map -->
            <!-- data  存放键值对  key ：职位名称   value: 职位人数 -->
            <c:forEach items="${requestScope.bigMap}" var="item">
                <%--计数，算出需要合并几行--%>
                <c:forEach items="${item.value}" var="data" varStatus="myCount">
                    <c:set scope="page" var="count" value="${myCount.count}"></c:set>
                </c:forEach>

                <c:forEach items="${item.value}" var="data" varStatus="myCount">
                    <tr>
                        <%--只有第一行输出--%>
                        <c:if test="${myCount.count eq 1 }">
                            <td rowspan="${pageScope.count}">${item.key}</td>
                        </c:if>
                        <td>${data.key}</td>
                        <td>${data.value}</td>
                    </tr>
                </c:forEach>
            </c:forEach>
        </table>
    </center>
</body>
</html>
