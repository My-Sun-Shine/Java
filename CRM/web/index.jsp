<%--
  Created by IntelliJ IDEA.
  User: Falling Stars
  Date: 2020/3/20
  Time: 16:02
  Description: 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <%
	    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
    %>
    <base href="<%=basePath%>">
    <title>${NAME}</title>
  </head>
  <body>
  $END$
  </body>
</html>
