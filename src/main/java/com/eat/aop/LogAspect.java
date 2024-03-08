package com.eat.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    Logger log = Logger.getLogger(LogAspect.class);

    @Pointcut("execution(* com.eat.service.SellerServiceImpl.*(..))")
    public void aop() {
    }

    // 此方法将用作前置通知
    @Before(value = "aop()")
    public void BeforeAdvice(JoinPoint joinpoint) {
        String classAndMethod = joinpoint.getTarget().getClass().getName() + "类的" + joinpoint.getSignature().getName();
        log.info("前置通知:" + classAndMethod + "方法开始执行！");
    }

    // 此方法将用作后置通知
    @AfterReturning(value = "aop()")
    public void AfterReturningAdvice(JoinPoint joinpoint) {
        String classAndMethod = joinpoint.getTarget().getClass().getName() + "类的" + joinpoint.getSignature().getName();
        log.info("后置通知:" + classAndMethod + "方法执行正常结束！");
    }

    // 此方法将用作异常通知
    @AfterThrowing(value = "aop()", throwing = "e")
    public void AfterThrowingAdvice(JoinPoint joinpoint, Throwable e) {
        String classAndMethod = joinpoint.getTarget().getClass().getName() + "类的" + joinpoint.getSignature().getName();
        log.info("异常通知:" + classAndMethod + "方法抛出异常：" + e.getMessage());
    }

    // 此方法将用作最终通知
    @After("aop()")
    public void AfterAdvice(JoinPoint joinpoint) {
        String classAndMethod = joinpoint.getTarget().getClass().getName() + "类的" + joinpoint.getSignature().getName();
        log.info("最终通知:" + classAndMethod + "方法执行结束！");
    }

    // 此方法将用作环绕通知
    @Around("aop()")
    public Object AroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        long begintime = System.currentTimeMillis();// 记下开始时间
        // 传递给连接点对象进行接力处理
        Object result = pjp.proceed();
        long endtime = System.currentTimeMillis();// 记下结束时间
        String classAndMethod = pjp.getTarget().getClass().getName() + "类的" + pjp.getSignature().getName();
        log.info("环绕通知:" + classAndMethod + "方法执行结束,耗时" + (endtime - begintime) + "毫秒！");
        return result;
    }
}