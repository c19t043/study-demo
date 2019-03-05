package cn.cjf.http;

/**
 * 接口返回常量值
 * @author:Bobbie.Qi
 * @time:2017年2月12日
 */
public class ApiReturnConstant {
	// 公用参数 成功
	public static String SUCCESS = "success";
	// 公用参数 失败
	public static String FAILURE = "failure";
	
	/**
	 * API返回数据
	 */
	public static String API_RESULT_DATA = "data";
	/**
	 * API返回的状态值，1-成功；0-失败
	 */
	public static String API_RESULT_STATUS = "status";
	/**
	 * API返回的错误码
	 */
	public static String API_RESULT_ERRCODE = "errCode";
	/**
	 * API返回的信息
	 */
	public static String API_RESULT_MESSAGE = "message";
	
	
	// 调用接口返回结果 成功
	public static int OPERATION_STATUS_SUCCESS = 1;
	// 调用接口返回结果 失败
	public static int OPERATION_STATUS_FAILUE = 0;
	

	/**
	 * API返回的信息
	 */
	public static String BJY_RESULT_MESSAGE = "msg";
	/**
	 * API返回的状态，0-成功；1-失败
	 */
	public static String BJY_RESULT_STATUS = "code";
	
	// 调用接口返回结果 成功
	public static int BJY_STATUS_SUCCESS = 0;
	// 调用接口返回结果 失败
	public static int BJY_STATUS_FAILUE = 1;
	
}
