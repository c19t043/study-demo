package cn.cjf.http;

import java.io.Serializable;

public class ApiError implements Serializable {
	
	private static final long serialVersionUID = 569554924303639248L;

	private Integer status = ApiConstant.STATUS;
	private String message = ApiConstant.NONE;
	private String errCode = ApiConstant.ERRCODE;

	public ApiError() {
	}
	
	public ApiError(Integer status, String message, String errCode) {
		this.status = status;
		this.message = message;
		this.errCode = errCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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
