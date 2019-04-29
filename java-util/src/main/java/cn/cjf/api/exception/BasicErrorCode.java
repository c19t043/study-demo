package cn.cjf.api.exception;

public interface BasicErrorCode {
	/**
	 * <!-- 非业务类 -->
	 */
	/** 无权调用API */
	public static final ErrorInfo CONNECTION_LIMIT 			= ErrorInfo.getInstance(20001, "CONNECTION_LIMIT", "无权调用API");
	/** API不存在 */
	public static final ErrorInfo API_NOT_EXISTS 			= ErrorInfo.getInstance(20002, "API_NOT_EXISTS", "API不存在");
	/** API已经废弃 */
	public static final ErrorInfo API_DISCARDED 			= ErrorInfo.getInstance(20003, "API_DISCARDED", "API已经废弃");
	/** 非法请求 */
	public static final ErrorInfo ILLEGAL_REQUEST 			= ErrorInfo.getInstance(20004, "ILLEGAL_REQUEST", "非法请求");
	/** 缺少参数 */
	public static final ErrorInfo LESS_PARAMS 				= ErrorInfo.getInstance(20005, "LESS_PARAMS", "缺少参数(%s)，请参考API文档");
	/** 参数错误 */
	public static final ErrorInfo PARAMS_ERROR 				= ErrorInfo.getInstance(20006, "PARAMS_ERROR", "参数(%s)错误，请参考API文档");
	/** 参数格式错误 */
	public static final ErrorInfo PARAMS_FORMAT_ERROR 		= ErrorInfo.getInstance(20007, "PARAMS_FORMAT_ERROR", "参数(%s)格式错误");
}
