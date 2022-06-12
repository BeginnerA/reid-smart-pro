package top.reid.smart.spring.annotation;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 使用 Java Config 时使用该注解注册 MyBatis 映射器接口。 它通过 MapperScannerRegistrar 在与 MapperScannerConfigurer 相同的工作时执行。<br>
 * 可以 basePackageClasses 或 basePackages（或其别名value ）来定义要扫描的特定包。<br>
 * 从 2.0.4 开始，如果没有定义特定的包，将从声明该注解的类的包开始进行扫描。<br>
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/1/25
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MapperScannerRegistrar.class)
@Repeatable(MapperScans.class)
public @interface MapperScan {
    /**
     * {@link #basePackages()} 属性的别名。 允许更简洁的注释声明，<br>
     * 例如：{@code @MapperScan("org.my.pkg")} 而不是 {@code @MapperScan(basePackages = "org.my.pkg"})}。<br>
     * @return 基础包名
     */
    String[] value() default {};

    /**
     * 用于扫描 MyBatis 接口的基础包。<br>
     * 请注意，只有具有至少一种方法的接口才会被注册； 具体的类将被忽略。<br>
     * @return 扫描映射器接口的基本包名称
     */
    String[] basePackages() default {};

    /**
     * {@link #basePackages()} 的类型安全替代方案，用于指定要扫描带注释组件的包。 将扫描指定的每个类的包。<br>
     * 考虑在每个包中创建一个特殊的无操作标记类或接口，除了被此属性引用之外没有其他用途。<br>
     * @return 指示用于扫描映射器接口的基本包的类
     */
    Class<?>[] basePackageClasses() default {};

    /**
     * 该 {@link BeanNameGenerator} 类用于命名 Spring 容器中检测到的组件。
     *
     * @return {@link BeanNameGenerator}
     */
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    /**
     * 此属性指定扫描仪将搜索的注释。<br>
     * 扫描器将注册基础包中也具有指定注释的所有接口。<br>
     * 请注意，这可以与 {@code markerInterface} 结合使用。<br>
     * @return 扫描仪将搜索的注释
     */
    Class<? extends Annotation> annotationClass() default Annotation.class;

    /**
     * 此属性指定扫描仪将搜索的父级。<br>
     * 扫描器将注册基础包中的所有接口，这些接口也具有指定的接口类作为父级。<br>
     * 请注意，这可以与 {@code annotationClass} 结合使用。<br>
     * @return 扫描仪将搜索的父级
     */
    Class<?> markerInterface() default Class.class;

    /**
     * 指定在 spring 上下文中存在多个的情况下使用 {@code SqlSessionTemplate}。<br>
     * 通常只有当您有多个数据源时才需要这样做。<br>
     * @return {@code SqlSessionTemplate} 的 bean 名称
     */
    String sqlSessionTemplateRef() default "";

    /**
     * 指定在 spring 上下文中存在多个 {@code SqlSessionFactory} 的情况下使用 {@code SqlSessionFactory}。 <br>
     * 通常只有当您有多个数据源时才需要这样做。<br>
     * @return {@code SqlSessionFactory} 的 bean 名称
     */
    String sqlSessionFactoryRef() default "";

    /**
     * 指定一个自定义的 {@code MapperFactoryBean} 以返回一个 mybatis 代理作为 spring bean。
     * @return {@code MapperFactoryBean}
     */
    Class<? extends MapperFactoryBean> factoryBean() default MapperFactoryBean.class;

    /**
     * 是否启用映射器 {@code bean} 的延迟初始化。<br>
     * 默认为false 。<br>
     * @return 设置为 {@code true} 以启用延迟初始化
     * @since 2.0.2
     */
    String lazyInitialization() default "";

    /**
     * 指定扫描映射器的默认范围。<br>
     * 默认为 {@code ""} （相当于单例）。<br>
     * @return 默认范围
     */
    String defaultScope() default AbstractBeanDefinition.SCOPE_DEFAULT;
}
