package top.reid.smart.spring.annotation;

import org.springframework.context.annotation.Conditional;
import org.springframework.core.env.Environment;

import java.lang.annotation.*;

/**
 * <p>
 *  {@link Conditional @Conditional} 检查指定属性是否具有特定值。<br>
 *  默认情况下，属性必须存在于 {@link Environment} 中并且不等于 {@code false} 。<br>
 *  {@link #havingValue()} 和 {@link #matchIfMissing()} 属性允许进一步自定义。<br>
 *  {@link #havingValue} 属性可用于指定属性应具有的值。<br>
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/6/12
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@Conditional(OnPropertyCondition.class)
public @interface ConditionalOnProperty {

    /**
     * {@link #name()} 的别名
     * @return 名字
     */
    String[] value() default {};

    /**
     * 应应用于每个属性的前缀。如果未指定，前缀会自动以点结尾。<br>
     * 有效前缀由一个或多个用点分隔的单词定义（例如 {@code acme.system.feature} ）。
     * @return 前缀
     */
    String prefix() default "";

    /**
     * 要测试的属性的名称。如果已定义前缀，则将其应用于计算每个属性的完整键。<br>
     * 例如，如果前缀是 {@code app.config} 而一个值是{@code my-value} ，那么完整的键就是 {@code app.config.my-value}
     * <p>
     * 使用虚线符号来指定每个属性，即全部小写，用“-”分隔单词（例如 {@code my-long-property} ）。
     * @return 名字
     */
    String[] name() default {};

    /**
     * 属性预期值的字符串表示形式。如果未指定，则该属性不得等于 {@code false}.
     * @return 期望值
     */
    String havingValue() default "";

    /**
     * 如果未设置属性，则指定条件是否应匹配。默认为 {@code false}.
     * @return 如果缺少属性，条件是否应该匹配
     */
    boolean matchIfMissing() default false;

}
