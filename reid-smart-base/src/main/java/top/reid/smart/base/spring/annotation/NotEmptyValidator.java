package top.reid.smart.base.spring.annotation;

import javax.validation.ConstraintValidatorContext;

/**
 * <p>
 * 空（null）验证器
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/2/11
 * @Version V1.0
 **/
public class NotEmptyValidator extends AbstractValidator<NotEmpty, Object> {

    @Override
    public void initialize(NotEmpty constraintAnnotation) {

    }

    @Override
    public boolean doIsValid(Object value, ConstraintValidatorContext context) {
        return value != null;
    }
}
