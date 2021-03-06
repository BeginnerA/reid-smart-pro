package top.reid.smart.base.spring.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 异常处理注解，注解标注的对象异常进行特殊处理
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
public @interface ServiceExceptionCatch {
}
