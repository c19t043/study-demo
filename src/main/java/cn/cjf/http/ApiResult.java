package cn.cjf.http;

import com.alibaba.fastjson.JSON;
import com.pxjy.common.paginator.IPage;
import com.pxjy.common.paginator.Page;
import com.pxjy.uks.domain.qiniu.QiNiuInfoStream;
import com.pxjy.uks.domain.qiniu.QinNiuInfoFormat;

import java.io.Serializable;
import java.util.List;

public class ApiResult extends ApiError implements Serializable {
	
	private static final long serialVersionUID = 7370805650212491762L;
	
	private Object data;
	
	private Boolean isVoid;
	// 整合百家云添加的返回参数
	private Integer code;
	private String msg;
	//添加七牛回调信息的配置
	private List<QiNiuInfoStream> streams;
	private QinNiuInfoFormat  format;
	private String result;
	public ApiResult() {
	}

	public ApiResult(Object data) {
		this.data = data;
	}
	
	public <T> T getResultObject(Class<T> clazz){
		return JSON.parseObject(data.toString(), clazz);
	}
	
	public <T> List<T> getResultArray(Class<T> clazz){
		return JSON.parseArray(data.toString(), clazz);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Serializable> IPage<T> getResultPage(Class<T> clazz){
		Page<T> page = JSON.parseObject(data.toString(), Page.class);
		List<T> list = JSON.parseArray(page.getResult().toString(), clazz);
		page.setResult(list);
		return page;
	}
	
	public ApiResult(Boolean isVoid) {
		this.isVoid = isVoid;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getIsVoid() {
		return isVoid;
	}

	public void setIsVoid(Boolean isVoid) {
		this.isVoid = isVoid;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<QiNiuInfoStream> getStreams() {
		return streams;
	}

	public void setStreams(List<QiNiuInfoStream> streams) {
		this.streams = streams;
	}

	public QinNiuInfoFormat getFormat() {
		return format;
	}

	public void setFormat(QinNiuInfoFormat format) {
		this.format = format;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
