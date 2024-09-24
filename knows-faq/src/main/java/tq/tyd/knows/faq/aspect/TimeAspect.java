package tq.tyd.knows.faq.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TimeAspect {
    @Pointcut("execution(public * tq.tyd.knows.faq.service.*.*(..))")
    public void timer(){}

    @Around("timer()")
    public Object timeRecord(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object obj = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long time = end-start;
        String methodName = joinPoint.getSignature().getName();
        System.out.println(methodName+"用时"+time+"ms");
        return obj;
    }

}
