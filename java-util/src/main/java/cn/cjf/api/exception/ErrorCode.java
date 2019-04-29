package cn.cjf.api.exception;

public interface ErrorCode extends SystemErrorCode {
    /**
     * 常用名词
     *
     * ILLEGAL : 错误、非法（可预料）
     * ERROR : 错误、异常（不可预料）
     * UNABLE : 不可用（无能力的；不能胜任的）
     * FAILED : 失败了的，不成功的
     * LIMIT : 限制
     */

    /**
     * 错误描述
     */
    public static final ErrorInfo ERROR_DESCRIPTION = ErrorInfo.getInstance(30001, "ERROR_DESCRIPTION", "%s");


    public static final ErrorInfo NEED_LOGIN = ErrorInfo.getInstance(30002, "NEED_LOGIN", "用户需要登录");

    public static final ErrorInfo LIMIT_ACCESS = ErrorInfo.getInstance(30003, "LIMIT_ACCESS", "该用户没有访问权限");

    public static final ErrorInfo NEED_BIND = ErrorInfo.getInstance(30004, "NEED_BIND", "该用户需要绑定手机号");

    public static final ErrorInfo NO_RECORD = ErrorInfo.getInstance(30005, "NO_RECORD", "该记录不存在");

    public static final ErrorInfo NEED_OFF_SHELVES = ErrorInfo.getInstance(30006, "NEED_OFF_SHELVES", "课程需要先下架再删除");

    public static final ErrorInfo HAS_NOT_EXPIRED_ORDER  = ErrorInfo.getInstance(30007, "HAS_NOT_EXPIRED_ORDER", "存在未过期订单");

}
