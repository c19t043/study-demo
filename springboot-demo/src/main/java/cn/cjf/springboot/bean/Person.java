package cn.cjf.springboot.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 * @ConfigurationProperties：告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；
 *      prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *
 * 只有这个组件是容器中的组件，才能使用容器提供的@ConfigurationProperties功能；
 *
 * 如果需要javabean和配置文件进行映射，使用configurationProperties
 * 默认从全局配置文件中获取值
 *
 * @Validated 支持校验
 * @Email：邮箱验证
 *
 */
/**
 * @Value() 支持spEl:#{},${key}环境变量，配置文件中取值
 * 不支持校验
 * 不支持复杂类型封装
 * 如果只是获取配置文件中的某相值，使用@value
 */

/**
 * @PropertySource(value={class:xxx.perperties}) 获取指定配置文件中的内容
 */

/**
 * @importResource:导入spring的配置文件，使配置生效
 * 标注在一个配置类上
 */

/**
 * @Configuration:标记一个类是配置类，相当于以前的spring配置文件
 */

/**
 * @Bean
 * public HelloService helloService(){}
 *
 * @Bean:相当于spring配置文件中的<bean></bean>,id是方法名helloService
 */
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private String lastName;
    private Integer age;
    private Boolean boss;
    private Date birth;

    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
}
