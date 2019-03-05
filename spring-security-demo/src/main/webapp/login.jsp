<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/2/12
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="login.do" method="post">
        <table>
            <tr>
                <td> 用户名：</td>
                <td><input type="text" name="username"/></td>
            </tr>
            <tr>
                <td> 密码：</td>
                <td><input type="password" name="password"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value=" 登录 "/>
                    <input type="reset" value=" 重置 "/>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
