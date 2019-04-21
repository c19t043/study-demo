<%@ page import="cn.cjf.shiro.constant.MainConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="baseUrl" value="<%=MainConstants.WEB_PROJECT_ACCESS_BASE_URL%>"/>
<c:set var="baseName" value="<%=MainConstants.WEB_BASE_NAME%>"/>

<%--<link rel="stylesheet" href="${baseName}/static/css/common.css">--%>

<style>
    table, th, td {
        border: 1px solid #222;
    }
</style>



<script>
    $ = function $(id) {
        return document.getElementById(id);
    }
</script>

<%--<script type="text/javascript" src="${baseName}/static/js/common.js"></script>--%>
