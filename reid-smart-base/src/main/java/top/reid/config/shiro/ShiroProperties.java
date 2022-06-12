package top.reid.config.shiro;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Shiro 配置项
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/3/8
 * @Version V1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "reid.shiro")
public class ShiroProperties {

    /**
     * 是否启用 Shiro
     */
    private boolean enableShiro;

    /**
     * 无需认证地址，支持多个地址
     */
    private String excludeUrls;

    /**
     * 应用程序的登录 URL，以方便所有发现的 AccessControlFilter 实例
     */
    private String loginUrl;

    /**
     * 应用程序的“未授权” URL，以方便所有已发现的 AuthorizationFilter 实例
     */
    private String unauthorizedUrl;
}
