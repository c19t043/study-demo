package cn.cjf.springboot.api;

/**
 * 公共响应结果成功失败的静态方法调用
 *
 */
public class ResultUtil {


    /**
     * return success
     *
     * @param data
     * @return
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
     * @return
     */
    public static CommonResult returnError(String code, String msg) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setData("");
        result.setMessage(msg);
        return result;

    }

    /**
     * use enum
     *
     * @param status
     * @return
     */
    public static CommonResult returnError(ErrorCodeEnum status) {
        return returnError(status.getCode(), status.getDesc());
    }
}