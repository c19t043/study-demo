package cn.cjf.cn.cjf.listener;

import cn.cjf.base.BaseSpringBootTest;
import cn.cjf.listener.WebInitListener;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class WebInitListenerTest extends BaseSpringBootTest {

    @Tested
    WebInitListener webInitListener;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * {@link WebInitListener#contextInitialized(javax.servlet.ServletContextEvent)}
     */
    @Test
    public void testcontextInitialized(@Injectable ServletContextEvent sce) {
        new Expectations(sce,WebApplicationContextUtils.class) {
            {
                WebApplicationContextUtils.getWebApplicationContext((ServletContext)any);
                result = applicationContext;
            }
        };
        webInitListener.contextInitialized(sce);

        Assert.assertNotNull(WebInitListener.getSpringContext());
    }

}
