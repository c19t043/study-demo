package cn.cjf.ok2.config;

import cn.cjf.ok2.api.CommonResult;
import cn.cjf.ok2.api.ErrorCodeEnum;
import cn.cjf.ok2.api.ResultUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public CommonResult processException(Exception ex) {
        if (ex instanceof BindingResult) {
            BindingResult bindingResult = (BindingResult) ex;
            return ResultUtil.processBindResult(bindingResult);
        }
        return ResultUtil.returnError(ErrorCodeEnum.UNKNOWN_ERROR, ex.getMessage());
    }

    /**
     *
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult processConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        List<String> msgList = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<?> cvl = iterator.next();
            msgList.add(cvl.getMessageTemplate());
        }
        return ResultUtil.returnError(ErrorCodeEnum.UNKNOWN_ERROR, JSON.toJSONString(msgList));
    }

    /**
     * 空参数校验
     */
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public CommonResult processMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return ResultUtil.returnErrorFormatMsg(ErrorCodeEnum.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION,
                ex.getParameterName());
    }

    /**
     * 请求方法校验（GET/POST/DELETE...）
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public CommonResult processHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return ResultUtil.returnErrorFormatMsg(ErrorCodeEnum.HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION,
                ex.getMethod());
    }
}