package top.reid.smart.base.spring.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import top.reid.smart.base.exception.ReidException;

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
@Component
@Aspect
@Slf4j
public class ServiceExceptionHandler {

    @Around("@annotation(top.reid.smart.base.spring.annotation.ServiceExceptionCatch) || @within(top.reid.smart.base.spring.annotation.ServiceExceptionCatch)")
    public Object serviceExceptionHandler(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Object proceed = proceedingJoinPoint.proceed();
        }catch (Throwable throwable) {
            throw new ReidException(throwable.getMessage());
        }
        return null;
    }
}
