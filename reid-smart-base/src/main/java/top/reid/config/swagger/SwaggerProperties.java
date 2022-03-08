package top.reid.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Swagger 配置项
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/3/8
 * @Version V1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "reid.swagger")
public class SwaggerProperties {
    /**
     * 需要扫描的包
     */
    private String basePackage = "com";

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 企业地址
     */
    private String companyUrl;

    /**
     * 企业邮箱
     */
    private String companyEmail;

    /**
     * 项目应用名
     */
    private String applicationName = "系统";

    /**
     * 项目版本信息
     */
    private String applicationVersion = "1.0";

    /**
     * 项目描述信息
     */
    private String applicationDescription = "后台API接口";
}
