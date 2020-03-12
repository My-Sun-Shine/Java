<%@ page import="D.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/12
  Time: 11:58
  Description: JSTL的基本应用，jstl.jar和standard。jar需要放在Tomcat的lib文件夹下
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--需要导入JSTL中core配置文件约束--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>JSP09</title>
</head>
<body>
    修改域对象中内容<br/>
    <%--
    <c:set>:
    [作用]：修改域对象中内容
    [属性]：scope：用于表示操作的域对象是谁
           scope="application/session/request/page"
           var: 设置关键字名字
           value：设置数据

    [使用]：<c:set scope="session" var="name" value="mike"/>
           session.setAttribute("name","mike")
    --%>
    <c:set scope="session" var="age" value="20"></c:set>
    ${sessionScope.age}<br/>
    在在JSTL中可以直接使用EL,是先执行EL表达式，然后赋值给JSTL<br/>
    <c:set scope="session" var="age" value="${sessionScope.age+1}"></c:set>
    ${sessionScope.age}<br/>
    <hr/>
    if判断<br/>
    <%--
    <c:if>:
    [作用]：进行简单的if判断（if(){}）;来动态决定内容是否可以推送到用户的浏览器上

    [属性]：test，用于接收一个EL表达式返回的判断结果
           test = true,此时就会将if标签内部的内容输送到浏览器上
           test = false,不会将if标签内部的内容输送到浏览器上
     --%>
    <c:if test="${sessionScope.age gt 20}">
        <font color="red">你的年龄已经超过20;</font>
    </c:if>
    <br/>
    <hr/>

    多分支选择判断：<br/>
    <%--
    【使用】
    <c:choose>
        <c:when test="${}"> 选择1</c:when>
        <c:when test="${}"> 选择2</c:when>
        <c:when test="${}"> 选择3</c:when>
        <c:otherwise> 不符合上述判断的默认情况</c:otherwise>
    </c:choose>
    --%>
    <c:set scope="application" var="age" value="23"></c:set>
    <c:choose>
        <c:when test="${applicationScope.age ge 60}">老年</c:when>
        <c:when test="${applicationScope.age ge 40}">中年</c:when>
        <c:when test="${applicationScope.age ge 23}">青年</c:when>
        <c:otherwise>少年</c:otherwise>
    </c:choose>
    <br/>
    <hr/>
    <%--
    <c:forEach>:
    [用法1]：<c:forEach var="循环变量" begin="初始值"
            end="循环变量可以接受的最大值" step="循环变量递增/递减">

            step可以不写，默认递增加1
            ${pageScpe.循环变量}/${循环变量}
            </c:forEach>

     [用法2]：<c:forEach items="从EL表达式得到遍历的集合（list,set,map）"
            var="循环变量" varStatus="循环计数器">

            如果遍历的集合【list,set】,【循环变量】存放就是一个对象
            如果遍历的集合【map】，【循环变量】存放就是【键值对】
            </c:forEach>
    --%>
    <select>
        <c:forEach begin="1" var="i" end="10" step="1">
            <option>第${pageScope.i}页</option>
        </c:forEach>
    </select>
    <br/>
    <%
        Student s1 = new Student(10, "mike1", 12);
        Student s2 = new Student(11, "mike2", 12);
        Student s3 = new Student(12, "mike3", 13);

        List list = new ArrayList();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        request.setAttribute("stuList", list);

        Map map = new HashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");
        request.setAttribute("map", map);

        List<Student> newList = (List) request.getAttribute("stuList");
        for (Student stu : newList) {
            out.write(stu.getId() + "  " + stu.getName() + "<br/>");
        }

        //Map newMap=(Map)request.getAttribute("map");
    %>
    <table border="2">
        <tr>
            <td>循环次数</td>
            <td>学员编号</td>
            <td>学员姓名</td>
            <td>学员年龄</td>
        </tr>
        <c:forEach items="${requestScope.stuList}" var="item" varStatus="i">
            <tr>
                <td>${i.count}</td>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.age}</td>
            </tr>
        </c:forEach>
    </table>

    <br/>
    遍历Map集合
    <%--
    forEach标签循环Map集合时
    每次将一个【键值对】交给循环循环变量
    【键值对中关键字】：${key_Value.key}
    【键值对中value】：${key_Value.value}
    --%>
    <br/>
    <c:forEach items="${requestScope.map}" varStatus="count" var="item">
        关键字 ${item.key}：内容 ${item.value}<br/>
    </c:forEach>

</body>
</html>
