<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8"/>
    <title>首页</title>
</head>
<body>
${nickname}
    <#if old=="18">
                太假了吧哥们
    <#elseif old=="21">
                你是真的21岁
    <#else>
            其他

    </#if>
	 <#list userlist as user>
         ${user}
     </#list>
</body>
</html>
