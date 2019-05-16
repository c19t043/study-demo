package cn.cjf.session.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class SessionConfig {
	// 冒号后的值为没有配置文件时，自动动装载的默认值
	@Value("${redis.hostname:192.168.57.101}")
	String host;
	@Value("${redis.port:6379}")
	int port;
	@Value("${redis.password:123456}")
	String password;

	@Bean
	public JedisConnectionFactory connectionFactory() {
		RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
		standaloneConfig.setHostName(host);
		standaloneConfig.setPort(port);
		standaloneConfig.setPassword(RedisPassword.of(password));
		return new JedisConnectionFactory(standaloneConfig);
	}
}