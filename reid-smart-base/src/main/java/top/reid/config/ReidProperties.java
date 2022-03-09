package top.reid.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import top.reid.config.mybatis.MybatisProperties;
import top.reid.config.shiro.ShiroProperties;
import top.reid.config.swagger.SwaggerProperties;
import top.reid.config.web.WebProperties;

/**
 * <p>
 * 配置项
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/3/9
 * @Version V1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "reid")
public class ReidProperties {
    /**
     * Mybatis 配置项
     */
    MybatisProperties mybatis;
    /**
     * Shiro 配置项
     */
    ShiroProperties shiro;
    /**
     * Swagger 配置项
     */
    SwaggerProperties swagger;
    /**
     * web 配置项
     */
    WebProperties web;
}
