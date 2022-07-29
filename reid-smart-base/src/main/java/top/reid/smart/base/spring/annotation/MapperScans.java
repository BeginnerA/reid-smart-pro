package top.reid.smart.base.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 聚合多个 {@link MapperScan} 注释的 {@code Container} 注释。<br>
 * 可以原生使用，声明几个嵌套 {@link MapperScan} 注释。 <br>
 * 也可以与 Java 8 对可重复注释的支持结合使用，其中 {@link MapperScan} 可以简单地在同一方法上声明多次，隐式生成此容器注释。<br>
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
@Import(MapperScannerRegistrar.RepeatingRegistrar.class)
public @interface MapperScans {
    MapperScan[] value();
}
