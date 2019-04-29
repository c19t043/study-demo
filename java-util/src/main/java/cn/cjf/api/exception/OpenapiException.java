package cn.cjf.api.exception;

public class OpenapiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2171837899430815579L;
	private Integer num;
	private String code;
	private String desc;
	
	public OpenapiException(Integer num, String code, String desc) {
		super();
		fill(num, code, desc);
	}

	public OpenapiException(Integer num, String code, String desc, String message) {
		super(message);
		fill(num, code, desc);
	}

	public OpenapiException(Integer num, String code, String desc, String message, Throwable cause) {
		super(message, cause);
		fill(num, code, desc);
	}

	public OpenapiException(Integer num, String code, String desc, Throwable cause) {
		super(cause);
		fill(num, code, desc);
	}
	
	private void fill(Integer num, String code, String desc) {
		this.num = num;
		this.code = code;
		this.desc = desc;
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
