package cn.cjf.ok2.api;

import cn.cjf.ok2.controller.ValidateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * 公共响应结果成功失败的静态方法调用
 */
public class ResultUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateController.class);

    /**
     * validate params
     *
     * @param bindingResult
     * @return
     */
    public static CommonResult validParams(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            return processBindingError(fieldError);
        }
        return ResultUtil.returnSuccess("");
    }

    /**
     * 根据spring binding 错误信息自定义返回错误码和错误信息
     *
     * @param fieldError
     * @return
     */
    private static CommonResult processBindingError(FieldError fieldError) {
        String code = fieldError.getCode();
        LOGGER.debug("validator error code: {}", code);
        switch (code) {
            case "NotEmpty":
            case "NotBlank":
            case "NotNull":
                return ResultUtil.returnError(ErrorCodeEnum.PARAM_EMPTY.getCode(), fieldError.getField(), fieldError.getDefaultMessage());
            case "Pattern":
            case "Min":
            case "Max":
            case "Length":
            case "Range":
            case "Email":
            case "DecimalMin":
            case "DecimalMax":
            case "Size":
            case "Digits":
            case "Past":
            case "Future":
                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getField(), fieldError.getDefaultMessage());
            default:
                return ResultUtil.returnError(ErrorCodeEnum.UNKNOWN_ERROR);
        }
    }

    public static CommonResult processBindResult(BindingResult bindingResult) {
        return validParams(bindingResult);
    }


    /**
     * return success
     *
     * @param data
     */
    public static <T> CommonResult<T> returnSuccess(T data) {
        CommonResult<T> result = new CommonResult();
        result.setCode(ErrorCodeEnum.SUCCESS.getCode());
        result.setSuccess(true);
        result.setData(data);
        result.setMessage(ErrorCodeEnum.SUCCESS.getDesc());
        return result;
    }

    /**
     * return error
     *
     * @param code error code
     * @param msg  error message
     */
    public static CommonResult returnError(String code, String msg) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setData("");
        result.setMessage(msg);
        return result;

    }


    /**
     * return error
     *
     * @param code error code
     * @param msg  error message
     */
    public static CommonResult returnError(String code, String fieldName, String msg) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setData("");
        result.setMessage(fieldName + ": " + msg);
        return result;

    }

    /**
     * use enum
     *
     * @param status
     */
    public static CommonResult returnError(ErrorCodeEnum status) {
        return returnError(status.getCode(), status.getDesc());
    }

    /**
     * use enum
     *
     * @param status
     */
    public static CommonResult returnError(ErrorCodeEnum status, String message) {
        return returnError(status.getCode(), status.getDesc());
    }

    /**
     * use enum
     *
     * @param status
     */
    public static CommonResult returnErrorFormatMsg(ErrorCodeEnum status, String replaceStr) {
        return returnError(status.getCode(), String.format(status.getDesc(), replaceStr));
    }
}