package cn.cjf.ok2.controller;

import cn.cjf.ok2.domain.swagger.SwaggerUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * swagger
 */
@RestController
@RequestMapping("/users")
@Api(tags = "1.1", description = "用户管理", value = "用户管理")
public class SwaggerController {

    private static final Logger log = LoggerFactory.getLogger(SwaggerController.class);

    @GetMapping
    @ApiOperation(value = "条件查询（DONE）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "Query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "Query"),
    })
    public SwaggerUser query(String username, String password) {
        log.info("多个参数用  @ApiImplicitParams");
        return new SwaggerUser(1L, username, password);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询（DONE）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号", dataType = "Long", paramType = "Path"),
    })
    public SwaggerUser get(@PathVariable Long id) {
        log.info("单个参数用  @ApiImplicitParam");
        return new SwaggerUser(id, "u1", "p1");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户（DONE）")
    @ApiImplicitParam(name = "id", value = "用户编号", dataType = "Long", paramType = "Path")
    public void delete(@PathVariable Long id) {
        log.info("单个参数用 ApiImplicitParam");
    }

    @PostMapping
    @ApiOperation(value = "添加用户（DONE）")
    public SwaggerUser post(@RequestBody SwaggerUser user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");
        return user;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户（DONE）")
    public void put(@PathVariable Long id, @RequestBody SwaggerUser user) {
        log.info("如果你不想写 @ApiImplicitParam 那么 swagger 也会使用默认的参数名作为描述信息 ");
    }
}