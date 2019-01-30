package cn.cjf.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceConfig {
    @Bean(name = "datasource")
    public DataSource dataSource(Environment env) {
        return null;
    }
}
