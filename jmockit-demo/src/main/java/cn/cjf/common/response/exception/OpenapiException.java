package cn.cjf.common.response.exception;

/**
 * @param
 * @Description 自定义异常类。
 * @Author wangwei
 * @Date 2018/12/27 0027 下午 3:39
 * @return
 */
public class OpenapiException extends RuntimeException implements Error {

    private static final long serialVersionUID = 2171837899430815579L;

    private String[] info;

    private ErrorEnum errorEnum;

    public OpenapiException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.errorEnum = errorEnum;
    }

    public OpenapiException(ErrorEnum errorEnum, String... info) {
        super(errorEnum.getMessage(info));
        this.errorEnum = errorEnum;
        this.info = info;
    }

    public OpenapiException(ErrorEnum errorEnum, Throwable cause) {
        super(errorEnum.getMessage(), cause);
        this.errorEnum = errorEnum;
    }

    public OpenapiException(ErrorEnum errorEnum, Throwable cause, String... info) {
        super(errorEnum.getMessage(info), cause);
        this.errorEnum = errorEnum;
        this.info = info;
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    @Override
    public String getErrCode() {
        return errorEnum.getErrCode();
    }

    @Override
    public String getDesc() {
        return errorEnum.getDesc();
    }

    @Override
    public String getMessage() {
        return errorEnum.getMessage(info);
    }
}
