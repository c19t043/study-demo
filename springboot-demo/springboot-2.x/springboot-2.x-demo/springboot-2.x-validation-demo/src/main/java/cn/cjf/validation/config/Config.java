package cn.cjf.validation.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author chenjunfan
 * @date 2019/5/14
 */
@Configuration
public class Config {

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return new ErrorPageRegistrar() {
            @Override
            public void registerErrorPages(ErrorPageRegistry registry) {
                registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400.html"));
                registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403.html"));
                registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
                registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html"));
            }
        };
    }
}
