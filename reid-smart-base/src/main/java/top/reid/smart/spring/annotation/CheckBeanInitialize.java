package top.reid.smart.spring.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import top.reid.smart.exception.ReidException;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * <p>
 * 检查导入的一个或多个对象是否满足创建。<br>
 * 针对 @Autowired 和 @Resource 注解标注进行检查
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/1/26
 * @Version V1.0
 **/
public class CheckBeanInitialize implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, @Nonnull BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(CheckBean.class.getName()));
        if (mapperScanAttrs != null) {
            try {
                DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) registry;
                Class<?>[] values = mapperScanAttrs.getClassArray("value");
                for (Class<?> value : values) {
                    beanFactory.getBean(value);
                }
            }catch (BeansException ex) {
                String typeName = Objects.requireNonNull(((NoSuchBeanDefinitionException) ex).getResolvableType()).getType().getTypeName();
                throw new ReidException("["+ typeName +"] 需要被继承或实现：" + ex.getMessage());
            }
        }
    }
}
