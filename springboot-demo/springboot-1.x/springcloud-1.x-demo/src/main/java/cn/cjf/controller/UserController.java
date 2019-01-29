package cn.cjf.controller;

import cn.cjf.domain.entity.User;
import cn.cjf.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{username}")
    @ApiIgnore
    public User getUser(@PathVariable("username") String username) {
        return userService.findUserByName(username);
    }

    /**
     * value:接口的名称
     * notes：接口的详细说明
     * 页面访问 /swagger-ui.html
     */
    @ApiOperation(value = "用户列表", notes = "用户列表")
    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    @ApiOperation(value = "创建用户", notes = "创建用户")
    @PostMapping
    public User postUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取详细信息")
    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @ApiOperation(value = "更新信息", notes = "根据url的id来指定更新用户信息")
    @PutMapping(value = "/{id}")
    public User putUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除用户")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "success";
    }

    @ApiIgnore
    @GetMapping("/hi")
    public String jsonTest() {
        return "hi you!";
    }
}
