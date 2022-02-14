package top.reid.smart.spring.annotation;

import javax.validation.ConstraintValidatorContext;

/**
 * <p>
 *
 * </p>
 *
 * @Author 杨明春
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
