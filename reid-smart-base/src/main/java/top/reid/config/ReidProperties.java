package top.reid.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
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
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/3/9
 * @Version V1.0
 **/
@Data
@Configuration
//@ConfigurationProperties(prefix = "reid")
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
