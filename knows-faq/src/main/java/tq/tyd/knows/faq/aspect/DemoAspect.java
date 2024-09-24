package tq.tyd.knows.faq.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DemoAspect {
    @Pointcut("execution(public * tq.tyd.knows.faq.controller"+".TagController.tags(..))")
    public void pointCut(){}
    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("前置advice执行");
        System.out.println(joinPoint.getSignature().getName());
    }
    @After("pointCut()")
    public void after(){
        System.out.println(
                "后置advice执行"
        );
    }
    @AfterThrowing("pointCut()")
    public void throwing(){
        System.out.println("方法发生异常");
    }
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("环绕advice，运行之前");
        Object obj = joinPoint.proceed();
        System.out.println("环绕advice，运行之后");
        return obj;
    }


}
