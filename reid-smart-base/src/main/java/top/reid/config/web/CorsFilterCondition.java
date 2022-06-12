package top.reid.config.web;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.annotation.Nonnull;

/**
 * <p>
 * 跨域配置加载条件
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/1/14
 * @Version V1.0
 **/
public class CorsFilterCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, @Nonnull AnnotatedTypeMetadata metadata) {
        Object object = context.getEnvironment().getProperty("spring.cloud.nacos.discovery.server-addr");
        //如果没有服务注册发现的配置 说明是单体应用 则加载跨域配置 返回true
        return object == null;
    }
}
