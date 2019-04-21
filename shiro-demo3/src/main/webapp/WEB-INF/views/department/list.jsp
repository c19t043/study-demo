<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/20
  Time: 10:51
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
    <div>部门列表</div>
    <shiro:hasPermission name="department:add">
        <div><a href="${baseUrl}/department?cmd=input">新增</a></div>
    </shiro:hasPermission>
    <table>
        <thead>
        <tr>
            <th>主键</th>
            <th>部门名</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${not empty requestScope.departmentList && fn:length(requestScope.departmentList) > 0}">
            <c:forEach items="${requestScope.departmentList}" var="department">
                <tr>
                    <td>${department.id}</td>
                    <td>${department.name}</td>
                    <td>
                        <shiro:hasPermission name="department:edit">
                            <a href="${baseUrl}/department?cmd=input&id=${department.id}">编辑</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="department:delete">
                            <a href="${baseUrl}/department?cmd=delete&id=${department.id}">删除</a>
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
