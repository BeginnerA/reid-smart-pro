package top.reid.smart.spring.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>
 * 带注释的元素不能为 null 也不能为空
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/2/11
 * @Version V1.0
 **/
@Documented
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = NotEmptyValidator.class)
public @interface NotEmpty {

    /**
     * 提示信息
     */
    String message() default "Bean 无法满足初始化：";

    /**
     * 指定这个约束条件属于哪个校验组。
     * 这个的默认值必须是 Class<?> 类型到空到数组。
     */
    Class<?>[] groups() default { };

    /**
     * Bean Validation API 的使用者可以通过此属性来给约束条件指定严重级别。
     * 这个属性并不被 API 自身所使用。
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * 标识带注释的内容属性
     */
    String key() default "";
}
