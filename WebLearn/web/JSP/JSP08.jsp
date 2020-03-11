<%@ page import="D.Student" %><%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/11
  Time: 23:41
  Description: EL表达式
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>JSP08</title>
</head>
<body>
    <b>采取路径格式，定位域对象中资源，主要用于在JSP文件中从域对象读取并输出数据</b>
    <br/>
    <b>JSP文件的域对象：application，session，request，pageContext：当前页作用域对象</b>
    <br/>
    <b>对应EL的对象是：applicationScope，sessionScope，requestScope，pageScope</b>
    <br/>
    <hr/>

    <b>读取不同作用域的对象</b>
    <br/>
    <%
        pageContext.setAttribute("key", "pageContext");
        request.setAttribute("key", "request");
        session.setAttribute("key", "session");
        application.setAttribute("key", "application");
        Student stu = new Student(1, "a", 12);
        session.setAttribute("stu", stu);
    %>
    数据：${pageScope.key}
    <br/>
    数据：${requestScope.key}
    <br/>
    数据：${sessionScope.key}
    <br/>
    数据：${applicationScope.key}
    <br/>
    <hr/>

    <b>EL表达式通过反射机制，找到与当前属性对应的get方法，通过get方法获取属性内容</b>
    <br/>
    <b>EL表达式不会为不遵守驼峰写法的属性提供服务</b>
    <br/>
    数据：${sessionScope.stu.id},${sessionScope.stu.name},${sessionScope.stu.age}
    <br/>
    <hr/>

    <b>EL表达式的简写<br/>
    首先会到pageContext中寻找关键字，如果找到，则输出，<br/>
    如果没有找到，则再去request中寻找，如果找到，则输出，<br/>
    如果没有找到，则再去session中寻找，如果找到，则输出，<br/>
    如果没有找到，则再去application中寻找，如果找到，则输出，<br/>
    如果没有找到，则返回null<br/>
    简化版，运行效率过慢，数据定位容易错误<br/>
    主要针对pageContext中的数据使用</b><br/>
    简化写法:${key}
    <br/>
    简化写法:${key1}
    <br/>
    <hr/>

    <b>EL表达式的运算</b>
    <br/>
    <%
        pageContext.setAttribute("num1", "1000");
        pageContext.setAttribute("num2", "2000");
        pageContext.setAttribute("num3", 20);
    %>
    ${num1}+${num2}=${num1+num2}
    <br/>
    <b>逻辑运算符
    <br/>
    > >= < <= == !=
    <br/>
    gt ge lt le eq !=</b>
    <br/>
    ${num3>=18?"大于等于18":"小于18"}
    <br/>
    ${num3 ge 18?"大于等于18":"小于18"}
    <br/>
    <hr/>

    <b>EL表达式的其他工具对象</b><br/>
    <br/>
    <b>1.param对象：读取浏览器向jsp发生的请求参数内容</b>
    <br/>
    读取参数为name的值：${param.name}
    <br/>
    <b>2.initParam：application.getInitParameter("共享变量")，在web.xml设置</b>
    <br/>
    读取共享变量JSP08：${initParam.JSP08}
    <br/>
    <b>3.pageContext：读取当前页域对象用来存储其他JSP内置对象地址的内存</b>
    <br/>
    下面写法与basePath一样:
    ${pageContext.request.contextPath}/
    <br/>
</body>
</html>
