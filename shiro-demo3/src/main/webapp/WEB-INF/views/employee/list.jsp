<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/20
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commonjsp/common.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <div>员工列表</div>
    <shiro:hasPermission name="employee:add">
        <div><a href="${baseUrl}/employee?cmd=input">新增</a></div>
    </shiro:hasPermission>
    <table>
        <thead>
        <tr>
            <th>主键</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>邮件</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${not empty requestScope.employeeList && fn:length(requestScope.employeeList) > 0}">
            <c:forEach items="${requestScope.employeeList}" var="employee">
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.name}</td>
                    <td>${employee.age}</td>
                    <td>${employee.email}</td>
                    <td>
                        <shiro:hasPermission name="department:edit">
                            <a href="${baseUrl}/employee?cmd=input&id=${employee.id}">编辑</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="department:delete">
                            <a href="${baseUrl}/employee?cmd=delete&id=${employee.id}">删除</a>
                        </shiro:hasPermission>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>
</body>
</html>
