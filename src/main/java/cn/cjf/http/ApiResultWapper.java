package cn.cjf.http;

public class ApiResultWapper {

	public static ApiResult getVoidInstance() {
		return new ApiResult(true);
	}

	public static ApiResult getInstance(Object result) {
		if (result == null) {
			throw new NullPointerException("result not null");
		}
		return new ApiResult(result);
	}
}
