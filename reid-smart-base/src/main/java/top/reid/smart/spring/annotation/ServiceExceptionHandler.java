package top.reid.smart.spring.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import top.reid.smart.exception.ReidException;

/**
 * <p>
 * 异常处理注解，注解标注的对象异常进行特殊处理
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2022/1/26
 * @Version V1.0
 **/
@Component
@Aspect
@Slf4j
public class ServiceExceptionHandler {

    @Around("@annotation(top.reid.smart.spring.annotation.ServiceExceptionCatch) || @within(top.reid.smart.spring.annotation.ServiceExceptionCatch)")
    public Object serviceExceptionHandler(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Object proceed = proceedingJoinPoint.proceed();
        }catch (Throwable throwable) {
            throw new ReidException(throwable.getMessage());
        }
        return null;
    }
}
