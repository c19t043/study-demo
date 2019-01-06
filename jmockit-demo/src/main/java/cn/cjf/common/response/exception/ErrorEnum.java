package cn.cjf.common.response.exception;


/**
 * @author wangwei
 * @date 2018/6/27 0027 18:14.
 */
public enum ErrorEnum implements Error {

    UN_KNOWN_EXCEPTION("100001", "UN_KNOWN_EXCEPTION", "未知错误"),
    SYSTEM_ERROR("500", "SYSTEM_ERROR", "系统错误"),
    DATABASE_EXCEPTION("100003", "DATABASE_EXCEPTION", "数据库异常"),
    DATABASE_EXECUTE_EXCEPTION("100004", "DATABASE_EXECUTE_EXCEPTION", "数据库执行异常"),
    DATA_CONVERT_EXCEPTION("100005", "DATA_CONVERT_EXCEPTION", "数据转换异常"),
    CONNECTION_REFUSED("100006", "CONNECTION_REFUSED", "拒绝访问"),
    SYSTEM_RESOURCES_UNABLE("100007", "SYSTEM_RESOURCES_UNABLE", "服务端资源不可用"),
    ILLEGAL_REQUEST("100008", "ILLEGAL_REQUEST", "非法请求"),
    API_EXECUTE_FAILED("100009", "API_EXECUTE_FAILED", "API执行错误"),
    ILLEGAL_SIGN("100010", "ILLEGAL_SIGN", "非法签名"),
    OVERDUE_REQUEST("100011", "OVERDUE_REQUEST", "过期请求"),
    CONNECTION_LIMIT("200001", "CONNECTION_LIMIT", "无权调用API"),
    API_NOT_EXISTS("200002", "API_NOT_EXISTS", "API不存在"),
    API_DISCARDED("200003", "API_DISCARDED", "API已经废弃"),
    LESS_PARAMS("200005", "LESS_PARAMS", "缺少参数(%s)，请参考API文档"),
    PARAMS_ERROR("200006", "PARAMS_ERROR", "参数(%s)错误，请参考API文档"),
    PARAMS_FORMAT_ERROR("200007", "PARAMS_FORMAT_ERROR", "参数(%s)格式错误"),
    UNUPLOAD_BANNER("200008", "UNUPLOAD_BANNER", "发布失败，当前还有banner图未上传！");

    private String errCode;

    private String desc;

    private String message;

    ErrorEnum(String errCode, String desc, String message) {
        this.errCode = errCode;
        this.desc = desc;
        this.message = message;
    }

    @Override
    public String getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getMessage(String... info) {
        Object[] objects = (Object[]) info;
        if (objects != null && objects.length > 0) {
            return String.format(desc, (Object[]) info);
        } else {
            return message;
        }
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
