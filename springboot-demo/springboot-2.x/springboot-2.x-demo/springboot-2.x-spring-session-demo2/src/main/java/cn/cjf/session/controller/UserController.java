package cn.cjf.session.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	@Value("${server.port}")
	private String port;

	@RequestMapping("/createSession")
	public String createSession(HttpServletRequest request,String name){
		HttpSession session = request.getSession();
		session.setAttribute("name", name);
		log.info("port:"+port+",sessonId:"+session.getId());
		return "创建Session成功-对应的服务器端口号为："+port;
	}
	
	@RequestMapping("/getSession")
	public String getSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session==null){
			return "没有获取到Session"+"-服务器端口号为:"+port;
		}
		log.info("port:"+port+",sessonId:"+session.getId());
		String name = (String) session.getAttribute("name");
		return "获取Session成功："+name+"-对应的服务器端口号为："+port;
	}
}