package cn.cjf.validation.config;

import cn.cjf.validation.Response.ResponseResult;
import cn.cjf.validation.util.WebUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 如果是调整页面的时候参数校验失败的话，这时可以不做处理，让其调到错误页面。
     * 如果是接口参数校验失败的话，可以在这里进行统一处理，并返回。
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseResult processConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        if (WebUtils.isAjaxRequest(request)) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
            ConstraintViolation<?> cvl = iterator.next();
            String errorMessage = cvl.getMessageTemplate();
            return ResponseResult.fail(errorMessage);
        }
        throw ex;
    }
}