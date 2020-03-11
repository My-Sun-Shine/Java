<%@ page import="D.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%--导入类--%>
<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/10
  Time: 23:13
  Description :小脚本scriptlet
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>JSP04</title>
</head>
<body>
    <%--在<% %>中可以使用表达式，语句--%>
    <%
        int age = 22;
        age++;
        String name = "张三";
    %>
    年龄：<%=age%><%--相当于输出out.print()--%>
    <br/>
    <%
        for (int i = 0; i <= 5; i++) {
            out.print(i);
            out.print("<br/>");
        }
    %>
    <hr/>
    <%
        if ("张三".equals(name)) {
    %>
    <font color="red">张三</font>
    <%
    } else {
    %>
    <font color="red">李四</font>
    <%
        }

    %>
    <hr/>
    <%
        Student stu1 = new Student(10, "mike", 26);
        Student stu2 = new Student(20, "allen", 28);
        Student stu3 = new Student(30, "tom", 21);

        List<Student> list = new ArrayList();

        list.add(stu1);
        list.add(stu2);
        list.add(stu3);
    %>
    <%
        for (Student stu : list) {
            out.write(stu.getId() + "  " + stu.getName() + "  " + stu.getAge());
            out.write("<br/>");
        }
    %>
    <hr/>
    <table border="2">
        <tr>
            <td>学员编号</td>
            <td>学员姓名</td>
            <td>学员年龄</td>
        </tr>
        <%
            for (Student stu : list) {
        %>
        <tr>
            <td><%=stu.getId()%>
            </td>
            <td><%=stu.getName()%>
            </td>
            <td><%=stu.getAge()%>
            </td>
        </tr>
        <%}%>
    </table>
    <%--<%! %> 定义的变量相当于类的属性,不会使用--%>
    <%!
        String name1 = "张三";
    %>


</body>
</html>
