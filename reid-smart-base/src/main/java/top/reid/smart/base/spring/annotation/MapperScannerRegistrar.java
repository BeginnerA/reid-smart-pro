package top.reid.smart.base.spring.annotation;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import top.reid.smart.base.core.util.CommonCharacter;
import top.reid.smart.base.core.util.StrTools;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 一个 {@link ImportBeanDefinitionRegistrar} 允许 MyBatis 映射器扫描的注解配置。<br>
 * 使用 @Enable 注解允许通过 @Component 配置注册 bean，而实现 {@code BeanDefinitionRegistryPostProcessor} 将适用于 XML 配置。<br>
 * 对官方注解进行改造，解决扫描路径声明不可配置在配置文件问题。<br>
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/1/25
 * @Version V1.0
 **/
@Slf4j
public class MapperScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    /**
     * 上下文
     */
    private Environment environment;

    /**
     * 资源加载器
     */
    private ResourceLoader resourceLoader;

    @Override
    public void setEnvironment(@Nonnull Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(@Nonnull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, @Nonnull BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(MapperScan.class.getName()));
        if (mapperScanAttrs != null) {
            this.registerBeanDefinitions(importingClassMetadata, mapperScanAttrs, registry, generateBaseBeanName(importingClassMetadata, 0));
        }
    }

    void registerBeanDefinitions(AnnotationMetadata annoMeta, AnnotationAttributes annoAttrs, BeanDefinitionRegistry registry, String beanName) {

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
        builder.addPropertyValue("processPropertyPlaceHolders", true);

        Class<? extends Annotation> annotationClass = annoAttrs.getClass("annotationClass");
        if (!Annotation.class.equals(annotationClass)) {
            builder.addPropertyValue("annotationClass", annotationClass);
        }

        Class<?> markerInterface = annoAttrs.getClass("markerInterface");
        if (!Class.class.equals(markerInterface)) {
            builder.addPropertyValue("markerInterface", markerInterface);
        }

        Class<? extends BeanNameGenerator> generatorClass = annoAttrs.getClass("nameGenerator");
        if (!BeanNameGenerator.class.equals(generatorClass)) {
            builder.addPropertyValue("nameGenerator", BeanUtils.instantiateClass(generatorClass));
        }

        Class<? extends MapperFactoryBean> mapperFactoryBeanClass = annoAttrs.getClass("factoryBean");
        if (!MapperFactoryBean.class.equals(mapperFactoryBeanClass)) {
            builder.addPropertyValue("mapperFactoryBeanClass", mapperFactoryBeanClass);
        }

        String sqlSessionTemplateRef = annoAttrs.getString("sqlSessionTemplateRef");
        if (StringUtils.hasText(sqlSessionTemplateRef)) {
            builder.addPropertyValue("sqlSessionTemplateBeanName", annoAttrs.getString("sqlSessionTemplateRef"));
        }

        String sqlSessionFactoryRef = annoAttrs.getString("sqlSessionFactoryRef");
        if (StringUtils.hasText(sqlSessionFactoryRef)) {
            builder.addPropertyValue("sqlSessionFactoryBeanName", annoAttrs.getString("sqlSessionFactoryRef"));
        }

        List<String> basePackages = new ArrayList<>();
        for (String pkg : annoAttrs.getStringArray(CommonCharacter.VALUE_FIELD)) {
            if (StringUtils.hasText(pkg)) {
                String value = parsePlaceHolder(pkg);
                if(StringUtils.hasText(value)){
                    String[] valueList = value.split(String.valueOf(CommonCharacter.COMMA));
                    Collections.addAll(basePackages, valueList);
                }
            }
        }
        for (String pkg : annoAttrs.getStringArray(CommonCharacter.BASE_PACKAGES_FIELD)) {
            if (StringUtils.hasText(pkg)) {
                String value = parsePlaceHolder(pkg);
                if(StringUtils.hasText(value)){
                    String[] valueList = value.split(String.valueOf(CommonCharacter.COMMA));
                    Collections.addAll(basePackages, valueList);
                }
            }
        }

        basePackages.addAll(Arrays.stream(annoAttrs.getStringArray("basePackages")).filter(StringUtils::hasText).toList());

        basePackages.addAll(Arrays.stream(annoAttrs.getClassArray("basePackageClasses")).map(ClassUtils::getPackageName).toList());

        if (basePackages.isEmpty()) {
            basePackages.add(getDefaultBasePackage(annoMeta));
        }

        String lazyInitialization = annoAttrs.getString("lazyInitialization");
        if (StringUtils.hasText(lazyInitialization)) {
            builder.addPropertyValue("lazyInitialization", lazyInitialization);
        }

        String defaultScope = annoAttrs.getString("defaultScope");
        if (!AbstractBeanDefinition.SCOPE_DEFAULT.equals(defaultScope)) {
            builder.addPropertyValue("defaultScope", defaultScope);
        }

        builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(basePackages));

        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());

    }

    /**
     * 解析占位符
     * @param pro 占位符字符串
     * @return 内容
     */
    private String parsePlaceHolder(String pro) {
        if (StringUtils.hasText(pro) && pro.contains(PropertySourcesPlaceholderConfigurer.DEFAULT_PLACEHOLDER_PREFIX)) {
            String packageStr = pro.substring(2, pro.length() - 1);
            String value = environment.getProperty(packageStr);
            if (null == value) {
                packageStr = packageStr.substring(0, packageStr.lastIndexOf(CommonCharacter.DOT) + 1) +
                        StrTools.toSymbolCase(packageStr.substring(packageStr.lastIndexOf(CommonCharacter.DOT) + 1), CommonCharacter.DASHED);
                value = environment.getProperty(packageStr);
                if (null == value) {
                    log.warn("在包中没有找到 MyBatis 映射器。请检查您的配置[" + pro + "]");
                }
            }
            return value;
        }
        return pro;
    }

    /**
     * 生成基础 Bean 名称
     * @param importingClassMetadata 导入类元数据
     * @param index 下标
     * @return Bean 名称
     */
    private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
        return importingClassMetadata.getClassName() + "#" + org.mybatis.spring.annotation.MapperScannerRegistrar.class.getSimpleName() + "#" + index;
    }

    /**
     * 获取默认基础包
     * @param importingClassMetadata 导入类元数据
     * @return 基础包
     */
    private static String getDefaultBasePackage(AnnotationMetadata importingClassMetadata) {
        return ClassUtils.getPackageName(importingClassMetadata.getClassName());
    }

    /**
     * A {@link org.mybatis.spring.annotation.MapperScannerRegistrar} for {@link MapperScans}.
     *
     * @since 2.0.0
     */
    static class RepeatingRegistrar extends MapperScannerRegistrar {
        /**
         * {@inheritDoc}
         */
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, @Nonnull BeanDefinitionRegistry registry) {
            AnnotationAttributes mapperScansAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(MapperScans.class.getName()));
            if (mapperScansAttrs != null) {
                AnnotationAttributes[] annotations = mapperScansAttrs.getAnnotationArray("value");
                for (int i = 0; i < annotations.length; i++) {
                    registerBeanDefinitions(importingClassMetadata, annotations[i], registry, generateBaseBeanName(importingClassMetadata, i));
                }
            }
        }
    }
}
