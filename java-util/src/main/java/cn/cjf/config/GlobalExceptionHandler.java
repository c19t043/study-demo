package cn.cjf.config;

import cn.cjf.api.ApiResult;
import com.pxjy.common.api.exception.ErrorEnum;
import com.pxjy.common.api.exception.OpenapiException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

    private final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class})
    public ApiResult processException(Exception ex, HttpServletRequest request) {
        logger.error(ex);
        if (ex instanceof BindingResult) {
            BindingResult bindingResult = (BindingResult) ex;
            return ApiResult.processBindResult(bindingResult);
        } else if (ex instanceof OpenapiException) {
            OpenapiException exception = (OpenapiException) ex;
            return ApiResult.failMsg(exception);
        } else {
            return ApiResult.failMsg(ErrorEnum.SYSTEM_ERROR);
        }
//        return ApiResult.failMsgMsgReplace(ErrorEnum.UN_KNOWN_EXCEPTION, ex.getMessage());
    }

    /**
     *
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ApiResult processConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        ConstraintViolation<?> cvl = iterator.next();
        String errorMessage = cvl.getMessageTemplate();
        return ApiResult.failMsgMsgReplace(ErrorEnum.UN_KNOWN_EXCEPTION, errorMessage);
    }

    /**
     * 空参数校验
     */
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ApiResult processMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return ApiResult.failMsg(ErrorEnum.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION,
                ex.getParameterName());
    }

    /**
     * 请求方法校验（GET/POST/DELETE...）
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ApiResult processHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return ApiResult.failMsg(ErrorEnum.HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION,
                ex.getMethod());
    }
}