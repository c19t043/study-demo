package cn.cjf.api;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 
 * @dateTime 2015年11月3日 下午2:49:32
 */
public class WebUtil {
	
	public static void outPutResult(ServletResponse response, Charset charset, String ContentType, String result) throws IOException {
		response.setContentType(ContentType);
		OutputStream os = response.getOutputStream();
		Writer out = new OutputStreamWriter(os, charset.toString());
		out.write(result);
		out.flush();
		out.close();
		os.flush();
		os.close();
	}
	
	public static void setCharacterEncoding(ServletRequest request, Charset charset) throws UnsupportedEncodingException {
		request.setCharacterEncoding(charset.toString());
	}
	
	public static void setCharacterEncoding(ServletResponse response, Charset charset) throws UnsupportedEncodingException {
		response.setCharacterEncoding(charset.toString());
	}
	
	public static void setCharacterEncoding(ServletRequest request, ServletResponse response, Charset charset) throws UnsupportedEncodingException {
		setCharacterEncoding(request, charset);
		setCharacterEncoding(response, charset);
	}
	
	public static void outPutJsonResult(ServletResponse response, Charset charset, Object result) throws IOException {
		setCharacterEncoding(response, charset);
		String jsonStr = JSON.toJSONString(result);
		outPutResult(response, charset, "application/json", jsonStr);
	}
	
	public static String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}
}
