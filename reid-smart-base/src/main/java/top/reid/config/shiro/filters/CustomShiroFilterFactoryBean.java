package top.reid.config.shiro.filters;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.InvalidRequestFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;

import javax.servlet.Filter;
import java.util.Map;

/**
 * <p>
 * 自定义 ShiroFilterFactoryBean 解决资源中文路径问题
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/1/19
 * @Version V1.0
 **/
public class CustomShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    @Override
    public Class<?> getObjectType() {
        return MySpringShiroFilter.class;
    }

    @Override
    protected AbstractShiroFilter createInstance() throws Exception {

        SecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            throw new BeanInitializationException("必须设置 SecurityManager 属性");
        }

        if (!(securityManager instanceof WebSecurityManager)) {
            throw new BeanInitializationException("安全管理器不实现 WebSecurityManager 接口");
        }

        FilterChainManager manager = createFilterChainManager();
        // 通过首先将构造的 FilterChainManager 包装在 FilterChainResolver 实现中来公开构造的 FilterChainManager。
        // AbstractShiroFilter 实现不知道 FilterChainManagers - 只有解析器
        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
        chainResolver.setFilterChainManager(manager);

        Map<String, Filter> filterMap = manager.getFilters();
        Filter invalidRequestFilter = filterMap.get(DefaultFilter.invalidRequest.name());
        if (invalidRequestFilter instanceof InvalidRequestFilter) {
            // 此处是关键，设置 false 跳过 URL 携带中文400，servletPath 中文校验 bug
            ((InvalidRequestFilter) invalidRequestFilter).setBlockNonAscii(false);
        }
        // 现在创建一个具体的 ShiroFilter 实例并应用获取的 SecurityManager 并构建 FilterChainResolver。
        // 该实例在这里是一个匿名内部类并不重要——我们只是使用它，因为它是一个具体的 AbstractShiroFilter 实例，
        // 它接受 SecurityManager 和 FilterChainResolver 的注入
        return new MySpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
    }

    /**
     * 自定义 Spring Shiro 过滤器
     */
    private static final class MySpringShiroFilter extends AbstractShiroFilter {
        private MySpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager 属性不能为空");
            } else {
                this.setSecurityManager(webSecurityManager);
                if (resolver != null) {
                    this.setFilterChainResolver(resolver);
                }

            }
        }
    }
}
