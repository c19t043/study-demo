package cn.cjf.api;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

public class ApiResult extends ApiError implements Serializable {

    private static final long serialVersionUID = 7370805650212491762L;

    private Object data;

    private Boolean isVoid;

    private Integer code;
    private String msg;
    private String result;

    public ApiResult() {
    }

    public ApiResult(Object data) {
        this.data = data;
    }

    public <T> T getResultObject(Class<T> clazz) {
        return JSON.parseObject(data.toString(), clazz);
    }

    public <T> List<T> getResultArray(Class<T> clazz) {
        return JSON.parseArray(data.toString(), clazz);
    }


    public ApiResult(Boolean isVoid) {
        this.isVoid = isVoid;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getIsVoid() {
        return isVoid;
    }

    public void setIsVoid(Boolean isVoid) {
        this.isVoid = isVoid;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
