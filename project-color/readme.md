# project-color

## 项目技术机构

本轮项目不采用spring boot

基础项目框架为 

web:spring mvc

service: spring

dao: mybatis

微服务框架 dubbo

数据库  mysql

## 项目介绍

+ `orange-base-service` 

提供所有项目公有基础服务

+ `red-base-web`

修改公有基础服务信息，提供可供外部访问的api

+ `red-base-data-service`

所有项目数据持久到json文件中，提供对对应服务json文件修改的接口

所有服务相关项目重新启动时，都需要初始化服务项目数据

## 颜色相关诗

```text
《菩萨蛮·大柏地》现代-毛泽东

原文：

赤橙黄绿青蓝紫，谁持彩练当空舞？

雨后复斜阳，关山阵阵苍。

当年鏖战急，弹洞前村壁。

装点此关山，今朝更好看。

译文：天上挂着一条七色的彩虹，像是有人拿着彩色的丝绸在翩翩起舞。
阵雨之后又升起了希望的太阳，苍翠的群山又时隐时现。
当年这里曾经进行了一次激烈的战斗，子弹穿透了前面村子的墙壁。
那前村墙壁上留下的累累弹痕，把这里的景色打扮得更加美丽。
```