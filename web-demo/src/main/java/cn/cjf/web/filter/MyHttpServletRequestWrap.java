package cn.cjf.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyHttpServletRequestWrap extends HttpServletRequestWrapper {

    HttpServletRequest request;
    HttpServletResponse response;


    public MyHttpServletRequestWrap(HttpServletRequest request, HttpServletResponse response) {
        super(request);
        this.request = request;
        this.response = response;
    }


    @Override
    public HttpSession getSession(boolean create) {
        return new MyHttpSession(request, response);
    }

    @Override
    public HttpSession getSession() {
        return new MyHttpSession(request, response);
    }
}
