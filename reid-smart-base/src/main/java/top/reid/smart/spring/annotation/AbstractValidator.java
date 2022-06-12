package top.reid.smart.spring.annotation;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * <p>
 * 抽象验证器（这里采用模板的设计模式）
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/2/11
 * @Version V1.0
 **/
public abstract class AbstractValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {
    /**
     * 初始化由具体类实现
     * @param constraintAnnotation 约束注解
     */
    @Override
    public abstract void initialize(A constraintAnnotation);

    /**
     * 初始化具体由实现类实现
     * @param value 值
     * @param context 约束验证器上下文
     * @return 是、否
     */
    @Override
    public boolean isValid(T value, ConstraintValidatorContext context){
        // 获取验证结果，采用模板方法
        boolean result = doIsValid(value, context);
        // 当验证错误时修改默认信息
        if(!result){
            // 改变默认提示信息
            if(ConstraintValidatorContextImpl.class.isAssignableFrom(context.getClass())){
                ConstraintValidatorContextImpl constraintValidatorContext = (ConstraintValidatorContextImpl)context;
                // 获取默认提示信息
                String defaultConstraintMessageTemplate = context.getDefaultConstraintMessageTemplate();
                Object key = constraintValidatorContext.getConstraintDescriptor().getAttributes().get("key");
                // 禁用默认提示信息
                context.disableDefaultConstraintViolation();
                // 设置提示语（在 message 前面加上 key）
                context.buildConstraintViolationWithTemplate(key + defaultConstraintMessageTemplate).addConstraintViolation();
            }
        }
        return result;
    }
    /**
     * 真正验证方法
     * @param value 值
     * @param context 约束验证器上下文
     * @return 是、否
     */
    public abstract boolean doIsValid(T value, ConstraintValidatorContext context);
}
