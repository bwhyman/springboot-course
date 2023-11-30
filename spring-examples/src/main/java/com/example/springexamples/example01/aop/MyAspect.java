package com.example.springexamples.example01.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MyAspect {
    @Pointcut("execution(* com.example.springexamples.example01.aop..*.*(..))")
    public void pointcut(){}

    @Before("pointcut()")
    public void beforeAdvice() {
        log.debug("beforeAdvice()");
    }
    @After("pointcut()")
    public void afterAdvice() {
        log.debug("afterAdvice()");
    }

}
