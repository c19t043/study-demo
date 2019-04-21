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
<div>员工新增</div>
<div>
    <form action="${baseUrl}/employee?cmd=saveOrUpdate" method="post">
        <input type="hidden" name="id" value="${requestScope.employee.id}">
        <table>
            <tr>
                <td>姓名：</td>
                <td><input type="text" name="name" value="${requestScope.employee.name}"></td>
            </tr>
            <tr>
                <td>年龄</td>
                <td><input type="text" name="age" value="${requestScope.employee.age}"></td>
            </tr>
            <tr>
                <td>邮件</td>
                <td><input type="text" name="email" value="${requestScope.employee.email}"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" name="提交"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
