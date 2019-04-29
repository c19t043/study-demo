package cn.cjf.api.exception;

import java.io.Serializable;

public class ErrorInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2115698200390180891L;
	
	public static final String NUM = "num";
	public static final String CODE = "code";
	public static final String DESC = "desc";
	
	private Integer num;
	private String code;
	private String desc;

	public ErrorInfo() {
	}
	
	public ErrorInfo(Integer num, String code, String desc) {
		this.num = num;
		this.code = code;
		this.desc = desc;
	}
	
	public static ErrorInfo getInstance(Integer num, String code, String desc) {
		return new ErrorInfo(num, code, desc);
	}
	
	public void issue() {
		issue(new String[] {});
	}

	public void issue(String... info) {
		issue(null, info);
	}
	
	public void issue(Throwable t) {
		issue(t, new String[] {});
	}
	
	public void issue(Throwable t, String... info) {
		Object[] objects = (Object[]) info;
		String _desc = null;
		if (objects != null && objects.length > 0) {
			_desc = String.format(desc, (Object[]) info);
		} else {
			_desc = desc;
		}
		throw new OpenapiException(num, code, _desc, _desc, t);
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
