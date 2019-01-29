package cn.cjf.base.config;

import cn.cjf.base.MainApplication;
import cn.cjf.base.servlet.SecondServlet;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan(basePackageClasses = MainApplication.class)
//启动器启动时，扫描本目录以及子目录带有的webservlet注解的
public class Config {


    @Bean  //一定要加，不然这个方法不会运行
    public ServletRegistrationBean getServletRegistrationBean() {
        //一定要返回ServletRegistrationBean
        //放入自己的Servlet对象实例
        ServletRegistrationBean bean = new ServletRegistrationBean(new SecondServlet());
        //访问路径值
        bean.addUrlMappings("/secondServlet");
        return bean;
    }
}
