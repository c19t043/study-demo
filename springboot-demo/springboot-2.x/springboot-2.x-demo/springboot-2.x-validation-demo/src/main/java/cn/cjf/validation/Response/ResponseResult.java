package cn.cjf.validation.Response;

import lombok.Data;

/**
 * @author chenjunfan
 * @date 2019/5/14
 */
@Data
public class ResponseResult {
    private final static int success = 0;
    private final static int error = 1;

    private Object data;
    private int code = success;
    private String msg;


    public static ResponseResult success() {
        return new ResponseResult();
    }

    public static ResponseResult success(Object data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(data);
        return responseResult;
    }

    public static ResponseResult fail(String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(error);
        responseResult.setMsg(msg);
        return responseResult;
    }
}

