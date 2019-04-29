package cn.cjf.api.exception;

public interface SystemErrorCode extends BasicErrorCode {
	/**
	 * <!-- 系统级，代码中请勿调用 -->
	 */
	/** 未知错误 */
	public static final ErrorInfo UN_KNOWN_EXCEPTION 		= ErrorInfo.getInstance(10001, "UN_KNOWN_EXCEPTION", "未知错误");
	/** 系统错误 */
	public static final ErrorInfo SYSTEM_ERROR 				= ErrorInfo.getInstance(10002, "SYSTEM_ERROR", "系统错误");
	/** 数据库异常 */
	public static final ErrorInfo DATABASE_EXCEPTION		= ErrorInfo.getInstance(10003, "DATABASE_EXCEPTION", "数据库异常");
	/** 数据库执行异常 */
	public static final ErrorInfo DATABASE_EXECUTE_EXCEPTION= ErrorInfo.getInstance(10004, "DATABASE_EXECUTE_EXCEPTION", "数据库执行异常");
	/** 数据转换异常 */
	public static final ErrorInfo DATA_CONVERT_EXCEPTION    = ErrorInfo.getInstance(10005, "DATA_CONVERT_EXCEPTION", "数据转换异常");
	/** 拒绝访问 */
	public static final ErrorInfo CONNECTION_REFUSED 		= ErrorInfo.getInstance(10006, "CONNECTION_REFUSED", "拒绝访问");
	/** 服务端资源不可用 */
	public static final ErrorInfo SYSTEM_RESOURCES_UNABLE 	= ErrorInfo.getInstance(10007, "SYSTEM_RESOURCES_UNABLE", "服务端资源不可用");
	/** 非法请求 */
	public static final ErrorInfo ILLEGAL_REQUEST 			= ErrorInfo.getInstance(10008, "ILLEGAL_REQUEST", "非法请求");
	/** API执行错误 */
	public static final ErrorInfo API_EXECUTE_FAILED 		= ErrorInfo.getInstance(10009, "API_EXECUTE_FAILED", "API执行错误");
	/** 非法签名 */
	public static final ErrorInfo ILLEGAL_SIGN				= ErrorInfo.getInstance(10010, "ILLEGAL_SIGN", "非法签名");
	/** 过期请求 */
	public static final ErrorInfo OVERDUE_REQUEST 			= ErrorInfo.getInstance(10011, "OVERDUE_REQUEST", "过期请求");
}
