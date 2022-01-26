package top.reid.smart.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 检查导入的一个或多个对象是否满足创建。<br>
 * 针对 @Autowired 和 @Resource 注解标注进行检查
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/1/25
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Documented
@Import(CheckBeanInitialize.class)
public @interface CheckBean {
    Class<?>[] value();
}
