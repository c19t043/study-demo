package cn.cjf.ok2.controller;

import cn.cjf.ok2.api.CommonResult;
import cn.cjf.ok2.api.ResultUtil;
import cn.cjf.ok2.domain.Leader;
import cn.cjf.ok2.domain.ValidateGroupBean;
import cn.cjf.ok2.validate.ValidateGroupBeanTest;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * @Validated 注解放在类上，针对方法上单个参数校验有效
 */
@RestController
@RequestMapping("/validate")
@Validated
public class ValidateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateController.class);


    /**
     * <p>
     *
     * @param bindingResult 有BindingResult，在MockMvc的时候方便校验
     *                      2、没有，走统一异常处理的时候，MockMvc处理不到
     * @Valid 1、验证对象不加注解@Valid，不启用作用，需要加注解，
     * 2、校验结果在BindingResult对象中
     * @Validated 相比@Valid，增加了可以分组的校验的功能
     * </p>
     */
    @PostMapping("/test")
    public CommonResult testSimpleValidate(@Validated Leader leader, BindingResult bindingResult) {
        LOGGER.debug("ReqParams:{}", JSON.toJSONString(leader));
        CommonResult result = ResultUtil.validParams(bindingResult);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtil.returnSuccess("");
    }

    /**
     * 3、参数中没有BindingResult，交由统一异常处理
     * <p>
     *
     * @Valid 1、验证对象不加注解@Valid，不启用作用，需要加注解，
     * 2、校验结果在BindingResult对象中
     * </p>
     */
    @GetMapping("/test2")
    public CommonResult testSimpleValidate2(@Validated Leader leader) {
        LOGGER.debug("ReqParams:{}", JSON.toJSONString(leader));
        return ResultUtil.returnSuccess("");
    }

    /**
     * json格式
     */
    @PostMapping("/json")
    @ResponseBody
    public CommonResult json(@Validated @RequestBody Leader leader, BindingResult bindingResult) {
        LOGGER.debug("ReqParams:{}", JSON.toJSONString(leader));
        CommonResult result = ResultUtil.validParams(bindingResult);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtil.returnSuccess("");
    }

    /**
     * <p>
     * 空参数校验：全局异常
     *
     * @RequestParam 1、注解有必传校验，2、指定传入参数的名称
     * </p>
     */
    @GetMapping("/param")
    public String param(@RequestParam("email") String email) {
        return email;
    }

    /**
     * <p>
     * 空参数校验：全局异常
     *
     * @Validated 1、不加注解验证@Email不启作用
     * 2、加载方法上，或加载参数上不起作用
     * 3、加载类上启作用
     * </p>
     */
    @GetMapping("/param2")
    public String param2(@NotEmpty(message = "邮件不能为空") String email) {
        return email;
    }

    /**
     * 分组验证
     */
    @GetMapping("/groupValidate")
    public CommonResult groupValidate(@Validated ValidateGroupBean validateGroupBean) {
        return ResultUtil.returnSuccess("");
    }

    /**
     * 分组验证
     */
    @GetMapping("/groupValidate1")
    public CommonResult groupValidate1(@Validated(value = {ValidateGroupBeanTest.class}) ValidateGroupBean validateGroupBean) {
        return ResultUtil.returnSuccess("");
    }
}