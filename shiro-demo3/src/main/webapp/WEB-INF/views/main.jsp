<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/18
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/commonjsp/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Title</title>
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        #app {
            width: 1600px;
            margin: 10px auto;
        }

        .header {
            height: 50px;
            border: 1px solid black;
            line-height: 50px;
        }

        .header-left {
            float: left;
            padding-left: 40px;
        }

        .header-right {
            float: right;
            width: 400px;
            height: 50px;
        }

        .content-left ul {
            margin-left: 40px;
            margin-top: 20px;
        }

        .header-right li {
            list-style: none;
            display: inline-block;

        }

        .header-right a {
            text-decoration: none;
        }

        .content {
            height: 600px;
        }

        .content-left {
            width: 200px;
            height: 100%;
            float: left;
        }

        .content-right {
            float: right;
            width: 1397px;
            height: 100%;
            font-weight: bold;
            border-left-width: 0 !important;
        }

        .content-left, .content-right {
            border: 1px solid black;
        }
    </style>
</head>
<body>
<div id="app">
    <!-- 头部信息区 -->
    <div class="header">
        <!-- 头部左 -->
        <div class="header-left">shiro-demo</div>
        <!-- 头部右 -->
        <div class="header-right">
            <ul>
                <li>当前登录用户：
                    ${requestScope.username}
                    <shiro:principal/>
                </li>
                <li><a href="${baseUrl}/logout">退出</a></li>
            </ul>
        </div>
    </div>
    <!-- 内容区 -->
    <div class="content">
        <!-- 菜单栏 -->
        <div class="content-left">
            <ul>
                <li><a href="javascript:" id="employee">员工管理</a></li>
                <li><a href="javascript:" id="department">部门管理</a></li>
            </ul>
        </div>
        <!-- 内容展示区 -->
        <iframe id="content-display" src="${baseUrl}/welcome.jsp" class="content-right"></iframe>
        <%--<div class="content-right">--%>
        <%--欢迎登录系统--%>
        <%--</div>--%>
    </div>
</div>
<script type="text/javascript">
    window.onload = function () {
        $("employee").onclick = function () {
            $("content-display").src = "${baseUrl}/employee";
        }
        $("department").onclick = function () {
            $("content-display").src = "${baseUrl}/department";
        }
    }
</script>
</body>
</html>
