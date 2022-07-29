package top.reid.smart.config.mybatis;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 多租户 tenant_id 存储器
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/2/9
 * @Version V1.0
 **/
@Slf4j
public class TenantContext {
    /**
     * 多租户 tenant_id 存储器
     */
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    public static void setTenant(String tenant) {
        log.debug(" setting tenant to " + tenant);
        CURRENT_TENANT.set(tenant);
    }

    public static String getTenant() {
        return CURRENT_TENANT.get();
    }

    public static void clear(){
        CURRENT_TENANT.remove();
    }
}
