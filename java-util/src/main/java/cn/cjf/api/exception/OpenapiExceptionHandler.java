package cn.cjf.api.exception;

import cn.cjf.api.ApiError;
import cn.cjf.api.WebUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

public class OpenapiExceptionHandler implements HandlerExceptionResolver {

    private static final Log logger = LogFactory.getLog(OpenapiExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof OpenapiException) {
            OpenapiException exception = (OpenapiException) ex;
            ApiError error = new ApiError(0, exception.getDesc(), String.valueOf(exception.getNum()));
            try {
                WebUtil.outPutJsonResult(response, Charset.defaultCharset(), error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ApiError error = new ApiError(0, ErrorCode.SYSTEM_ERROR.getDesc(), ErrorCode.SYSTEM_ERROR.getCode());
            try {
                WebUtil.outPutJsonResult(response, Charset.defaultCharset(), error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.error("", ex);
        return null;
    }

}
