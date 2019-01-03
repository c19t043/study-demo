package cn.cjf.common.response;

import cn.cjf.common.response.exception.ErrorEnum;
import cn.cjf.common.response.exception.OpenapiException;
import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

public class ApiResult implements Serializable {

    private static final long serialVersionUID = 7370805650212491762L;

    private Object data;
    private Boolean isVoid;
    private int status = ApiConstant.STATUS;
    private String message = ApiConstant.NONE;
    private String errCode = ApiConstant.ERRCODE;


    private ApiResult(){
        this.isVoid = true;
    }

    private ApiResult(Object obj){
        this.isVoid = false;
        this.data = obj;
    }

    private ApiResult(String code, String message) {
        this.errCode = code;
        this.message = message;
    }

    public static ApiResult failMsg(ErrorEnum error){
        ApiResult apiResult = new ApiResult(error.getErrCode(), error.getMessage());
        apiResult.setStatus(ApiConstant.STATUS_FAILUE);
        return apiResult;
    }
    public static ApiResult failMsg(OpenapiException e){
        ApiResult apiResult = new ApiResult(e.getErrCode(), e.getMessage());
        apiResult.setStatus(ApiConstant.STATUS_FAILUE);
        return apiResult;
    }
    public static ApiResult failMsg(ErrorEnum error, String... info){
        ApiResult apiResult = new ApiResult(error.getErrCode(), error.getMessage(info));
        apiResult.setStatus(ApiConstant.STATUS_FAILUE);
        return apiResult;
    }
    public static ApiResult succ(){
        ApiResult apiResult = new ApiResult();
        return apiResult;
    }
    public static ApiResult succ(Object obj){
        ApiResult apiResult = new ApiResult(obj);
        return apiResult;
    }

    public <T> T getResultObject(Class<T> clazz) {
        return JSON.parseObject(data.toString(), clazz);
    }

    public <T> List<T> getResultArray(Class<T> clazz) {
        return JSON.parseArray(data.toString(), clazz);
    }

    @SuppressWarnings("unchecked")
//    public <T extends Serializable> IPage<T> getResultPage(Class<T> clazz) {
//        Page<T> page = JSON.parseObject(data.toString(), Page.class);
//        List<T> list = JSON.parseArray(page.getResult().toString(), clazz);
//        page.setResult(list);
//        return page;
//    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
