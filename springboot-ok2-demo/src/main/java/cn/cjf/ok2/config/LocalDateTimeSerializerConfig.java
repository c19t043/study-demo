package cn.cjf.ok2.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 在里面定义两个 Bean 即可完成全局日期格式化处理，同时还兼顾了 Date 和 LocalDateTime 并存
 */
@Configuration
public class LocalDateTimeSerializerConfig {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    @Bean
    public LocalDateTimeSerializer localDateTimeDeserializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
    }

    /*
     * 方案二（强烈推荐）
     * 有时候，我们对日期格式要做特殊的处理，全局的格式化方式无法满足我们需求是，
     * 使用该方案是非常好的选择，通过 @JsonFormat 注解我们可以更为精准的为日期字段格式化，它也是优先级最高的
     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private LocalDateTime payTime;
}