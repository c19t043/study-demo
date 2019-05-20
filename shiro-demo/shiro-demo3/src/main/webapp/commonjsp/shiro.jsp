<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/20
  Time: 17:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commonjsp/common.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%-- 登录之后显示内容 --%>
    <shiro:authenticated></shiro:authenticated>
<%--不在登录状态时--%>
<shiro:notAuthenticated></shiro:notAuthenticated>
<%--用户在没有RememberMe时--%>
<shiro:guest></shiro:guest>
<%--用户在有RememberMe时--%>
<shiro:user></shiro:user>
<%--在有abc或123角色时--%>
<shiro:hasAnyRoles name="abc,123"></shiro:hasAnyRoles>
<%--在拥有abc角色时--%>
<shiro:hasRole name="abc"></shiro:hasRole>
<%--在没有abc角色时--%>
<shiro:lacksRole name="abc"></shiro:lacksRole>
<%--在拥有abc权限资源时--%>
<shiro:hasPermission name="abc"></shiro:hasPermission>
<%--没有abc权限资源--%>
<shiro:lacksPermission name="abc"></shiro:lacksPermission>
<%--显示用户身份名称--%>
<shiro:principal></shiro:principal>
<%--显示用户身份中的属性--%>
<shiro:principal property="username"></shiro:principal>
</body>
</html>
