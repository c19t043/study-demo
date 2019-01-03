package cn.cjf.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class WebInitListener implements ServletContextListener {
    private static WebApplicationContext springContext;
    private Logger logger = LoggerFactory.getLogger(WebInitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        setSpringContext(WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()));
    }

    public static WebApplicationContext getSpringContext() {
        return springContext;
    }

    private static void setSpringContext(WebApplicationContext springContext) {
        WebInitListener.springContext = springContext;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
