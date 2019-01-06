package cn.cjf.common.response.exception;

/**
 * @author wangwei
 * @date 2018/6/27 0027 18:12.
 */
public interface Error {

    String getErrCode();

    String getMessage();

    String getDesc();
}
