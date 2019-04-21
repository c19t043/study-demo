<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/20
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commonjsp/common.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>部门列表</div>
<form action="${baseUrl}/department?cmd=saveOrUpdate" method="post">
    <input type="hidden" name="id" value="${requestScope.department.id}">
    <table>
        <tr>
            <td>部门名：</td>
            <td><input type="text" name="name" value="${requestScope.department.name}"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" name="提交"></td>
        </tr>
    </table>
</form>
</body>
</html>
