package cn.cjf.config;

import cn.cjf.api.ApiResult;
import cn.cjf.api.ApiResultWapper;
import cn.cjf.api.exception.ErrorCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
            return processBindResult(bindingResult);
        } else {
            return ApiResultWapper.failVoidInstance(ErrorCode.SYSTEM_ERROR);
        }
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
        return ApiResultWapper.failVoidInstance(errorMessage);
    }

    /**
     * processBindResult
     *
     * @param bindingResult bindingResult
     * @return ApiResult
     */
    public static ApiResult processBindResult(BindingResult bindingResult) {
        return validParams(bindingResult);
    }

    /**
     * validate params
     *
     * @param bindingResult bindingResult
     * @return ApiResult
     */
    public static ApiResult validParams(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            return processBindingError(fieldError);
        }
        return ApiResultWapper.getVoidInstance();
    }

    /**
     * 根据spring binding 错误信息自定义返回错误码和错误信息
     *
     * @param fieldError fieldError
     * @return ApiResult
     */
    private static ApiResult processBindingError(FieldError fieldError) {
        String code = fieldError.getCode();
        switch (code) {
            case "NotEmpty":
            case "NotBlank":
            case "NotNull":
                return ApiResultWapper.failVoidInstance(ErrorCode.LESS_PARAMS,
                        fieldError.getField() + ":" + fieldError.getDefaultMessage());
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
                return ApiResultWapper.failVoidInstance(ErrorCode.PARAMS_ERROR,
                        fieldError.getField() + ":" + fieldError.getDefaultMessage());
            default:
                return ApiResultWapper.failVoidInstance(ErrorCode.UN_KNOWN_EXCEPTION);
        }
    }
}