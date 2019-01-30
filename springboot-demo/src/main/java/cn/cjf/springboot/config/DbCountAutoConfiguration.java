package cn.cjf.springboot.config;

import cn.cjf.springboot.runner.DbCountRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

@Configuration
public class DbCountAutoConfiguration {

    @Bean
    public DbCountRunner dbCountRunner(Collection<CrudRepository> repositories) {
        return new DbCountRunner(repositories);
    }
}