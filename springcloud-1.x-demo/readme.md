#常用的maven命令

> mvn clean

> mvn package

> `mvn package -Dmaven.test.skip=true` 打包时跳过测试

> `mvn compile` 编译工程代码，不生成jar包

> `mvn install` 命令包含了`mvn package`的所有过程，并且将生成的jar包安装到本地仓库

> `mvn spring-boot:run` 使用spring-boot插件启动spring boot工程

> `mvn test` 测试

> `mvn idea:idea` 生成idea项目

> `mvn jar:jar` 只打jar包

> `mvn validate` 校验资源是否可用

# swagger文档注解
- @Api
> 修饰整个类，用于描述Controller类

- @ApiOperation
> 描述类的方法，或者说一个接口

- @ApiParam
> 单个参数描述

- @ApiModel
> 用对象来接受参数

- @ApiProperty
> 用对象接受参数时，描述对象的一个字段

- @ApiResponse
> HTTP响应的一个描述

- @ApiResponses
> HTTP响应的整个描述

- @ApiIgnore
> 使用该注解，标识Swagger2忽略这个API

- @ApiError
> 发生错误返回的信息

- @ApiParamImplicit
> 一个请求参数

- @ApiParamsImplicit
> 多个请求参数
