package top.reid.config.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Web 配置项
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/3/9
 * @Version V1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "reid.web")
public class WebProperties {

    /**
     * file 静态资源路径，支持多个地址<br>
     * 如：file:/tmp1/,file:/tmp2/
     */
    private String staticFiles;

    /**
     * classpath 静态资源路径，支持多个地址<br>
     * 默认静态地址：classpath:/static/,classpath:/public/<br>
     * 如：classpath:/static/,classpath:/public/
     */
    private String staticClasspath = "classpath:/static/,classpath:/public/";
}
