package top.reid.smart.config.mybatis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * Mybatis 配置项
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/3/8
 * @Version V1.0
 **/
@Data
@ConfigurationProperties(prefix = "reid.mybatis")
public class MybatisProperties {

    /**
     * 用于扫描 MyBatis 接口的基础包。<br>
     * 请注意，只有具有至少一种方法的接口才会被注册；具体的类将被忽略。
     */
    private String mapperScan;
}
