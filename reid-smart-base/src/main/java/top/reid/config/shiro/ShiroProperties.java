package top.reid.config.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.reid.smart.core.util.CommonCharacter;
import top.reid.smart.core.util.StrTools;

import java.util.*;

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
@ConfigurationProperties(prefix = "reid.shiro")
public class ShiroProperties {

    /**
     * 是否启用 Shiro
     */
    private boolean enableShiro;

    /**
     * 无需认证地址
     */
    private Set<String> excludeUrls;

    /**
     * 应用程序的登录地址，以方便所有发现的 AccessControlFilter 实例
     */
    private String loginUrl;

    /**
     * 应用程序的“未授权”地址，以方便所有已发现的 AuthorizationFilter 实例
     */
    private String unauthorizedUrl;

    /**
     * 会话踢出后到的地址
     */
    private String kickoutUrl;

    /**
     * 是否启用前缀判断，不匹配任何 Advisor
     */
    private boolean usePrefix;

    /**
     * 添加前缀判断，不匹配任何 Advisor
     */
    private String advisorBeanNamePrefix;

    /**
     * 主体 ID 字段名称，redis 中针对不同用户缓存(此处的 id 需要对应 user 实体中的 id 字段，用于唯一标识)
     */
    private String principalIdFieldName;

    public ShiroProperties() {
        initDefaultExcludeUrls();
    }

    /**
     * 设置无需认证地址
     * @param excludeUrls 无需认证地址（多个地址","分割）
     */
    public void setExcludeUrls(String excludeUrls) {
        if (StrTools.isNotEmpty(excludeUrls)) {
            String[] permissionUrl = excludeUrls.split(String.valueOf(CommonCharacter.COMMA));
            this.excludeUrls.addAll(Arrays.asList(permissionUrl));
        }
    }

    /**
     * 初始化默认无需认证地址
     */
    private void initDefaultExcludeUrls() {
        this.excludeUrls = new HashSet<>();
        // 默认无需认证就能访问的地址
        excludeUrls.add("/**/*.js");
        excludeUrls.add("/**/*.css");
        excludeUrls.add("/**/*.htm");
        excludeUrls.add("/**/*.svg");
        excludeUrls.add("/**/*.pdf");
        excludeUrls.add("/**/*.jpg");
        excludeUrls.add("/**/*.png");
        excludeUrls.add("/**/*.ico");
        // swagger 静态资源
        excludeUrls.add("/doc.html");
        excludeUrls.add("/swagger-ui.html");
        excludeUrls.add("/swagger**/**");
        excludeUrls.add("/webjars/**");
        excludeUrls.add("/v2/**");
    }
}
