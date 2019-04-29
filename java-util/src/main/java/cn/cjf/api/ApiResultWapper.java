package cn.cjf.api;

import cn.cjf.api.exception.ErrorCode;
import cn.cjf.api.exception.ErrorInfo;

public class ApiResultWapper {

    public static ApiResult getVoidInstance() {
        return new ApiResult(true);
    }

    public static ApiResult getVoidInstance(int code, String message) {
        ApiResult result = new ApiResult(true);
        result.setStatus(code);
        result.setMessage(message);
        return result;
    }

    public static ApiResult getInstance(Object result) {
        if (result == null) {
            throw new NullPointerException("result not null");
        }
        return new ApiResult(result);
    }

    public static ApiResult failVoidInstance(String message) {
        ApiResult result = new ApiResult();
        result.setStatus(ApiConstant.FAIL_STATUS);
        result.setMessage(message);
        result.setErrCode(ErrorCode.PARAMS_ERROR.getNum() + "");
        return result;
    }

    public static ApiResult failVoidInstance(ErrorInfo errorInfo) {
        return failVoidInstance(errorInfo, null);
    }

    public static ApiResult failVoidInstance(ErrorInfo errorInfo, Object o) {
        ApiResult result = new ApiResult();
        result.setStatus(ApiConstant.FAIL_STATUS);
        if (null != o) {
            result.setMessage(String.format(errorInfo.getDesc(), o));
        } else {
            result.setMessage(errorInfo.getDesc());
        }
        result.setErrCode(errorInfo.getNum() + "");
        return result;
    }
}
