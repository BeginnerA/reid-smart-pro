package top.reid.config;

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
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
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
