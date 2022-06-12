package top.reid.smart.spring.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import top.reid.smart.core.util.CommonCharacter;
import top.reid.smart.exception.ReidException;

import javax.annotation.Nonnull;
import java.util.Objects;

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
public class CheckBeanInitialize implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, @Nonnull BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(CheckBean.class.getName()));
        if (mapperScanAttrs != null) {
            try {
                DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) registry;
                String[] values = (String[]) mapperScanAttrs.get(CommonCharacter.VALUE_FIELD);
                for (String value : values) {
                    beanFactory.getBean(value);
                }
            }catch (BeansException ex) {
                int numberOfBeansFound = ((NoUniqueBeanDefinitionException) ex).getNumberOfBeansFound();
                String typeName = Objects.requireNonNull(((NoSuchBeanDefinitionException) ex).getResolvableType()).getType().getTypeName();
                String eMessage = "";
                if (numberOfBeansFound == 0) {
                    eMessage = "["+ typeName +"]"+ mapperScanAttrs.get("message");
                }

                throw new ReidException(eMessage + ex.getMessage());
            }
        }
    }
}
