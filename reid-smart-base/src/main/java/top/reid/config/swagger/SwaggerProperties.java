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
     * 需要扫描的包<br>
     * 默认：扫描“com”路径下的全部包
     */
    private String basePackage = "com";

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 联系地址
     */
    private String contactUrl;

    /**
     * 联系邮箱
     */
    private String contactEmail;

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

    /**
     * 许可证<br>
     * 默认：The Apache License, Version 2.0
     */
    private String license = "The Apache License, Version 2.0";

    /**
     * 许可证地址<br>
     * 默认：https://www.apache.org/licenses/LICENSE-2.0.html
     */
    private String licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0.html";

    /**
     * 服务条款网址<br>
     * 默认：没有服务条款
     */
    private String termsOfServiceUrl = "没有服务条款";
}
