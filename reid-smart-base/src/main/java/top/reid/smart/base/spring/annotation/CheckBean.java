package top.reid.smart.base.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 检查导入的一个或多个对象是否满足创建。<br>
 * 针对 @Autowired 和 @Resource 注解标注进行检查。<br>
 * 可用于任何直接或间接使用 @Component 注释的类或使用 @Bean 注释的方法。
 * 如果 @Component 或 @Bean 定义上不存在此注解，则会发生急切初始化。如果存在并设置为 true ，
 * 则 @Bean 或 @Component 将不会被初始化，直到被另一个 bean 引用或从封闭的 BeanFactory 显式检索。
 * 如果存在并设置为 false ，则 bean 将在启动时由执行单例初始化的 bean 工厂实例化
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/1/26
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Documented
@Import(CheckBeanInitialize.class)
public @interface CheckBean {

    /**
     * 需要检查的 Bean 的别名。 允许更简洁的注释声明，<br>
     * 例如：{@code @CheckBean("ISysBaseApi")} 而不是 {@code @CheckBean(className = "ISysBaseApi"})}。<br>
     */
    String[] value() default {};

    /**
     * 需要检查的 Bean 的别名
     */
    String[] className() default {};

    /**
     * 提示信息
     */
    String message() default "Bean 无法满足初始化：";
}
