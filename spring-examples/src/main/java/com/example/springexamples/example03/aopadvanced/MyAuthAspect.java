package com.example.springexamples.example03.aopadvanced;

import com.example.springexamples.exception.XException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MyAuthAspect {
    // 方法级
    @Around("@annotation(myAuthority)")
    public Object checkMethod(ProceedingJoinPoint joinPoint, MyAuthority myAuthority) throws Throwable {
        for (MyAuthority.MyAuthorityType au : myAuthority.value()) {
            log.debug("{}", au);
            // 此处模拟用户实际权限为user。应该动态获取
            // 如果用户不是admin则抛出异常
            if(!au.equals(MyAuthority.MyAuthorityType.USER)) {
                log.debug("@annotation not USER");
                throw XException.builder().message("无权限").build();
            }
        }
        log.debug("@annotation");
        return joinPoint.proceed();
    }

    // 类型级
    @Around("@within(myAuthority)")
    public Object checkType(ProceedingJoinPoint joinPoint, MyAuthority myAuthority) throws Throwable {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        // 方法有注解则忽略，由方法级切面处理
        if (ms.getMethod().getAnnotation(MyAuthority.class) != null) {
            log.debug("{}", myAuthority);
            return joinPoint.proceed();
        }
        // 方法没有注解，则按类型权限值
        for (MyAuthority.MyAuthorityType au : myAuthority.value()) {
            log.debug("{}", au);
            // 此处模拟用户实际权限为user。应该动态获取
            // 如果用户不是admin则抛出异常
            if(!au.equals(MyAuthority.MyAuthorityType.USER)) {
                log.debug("@within not admin");
                throw XException.builder().message("无权限").build();
            }
        }
        log.debug("@within");
        return joinPoint.proceed();
    }

    // 切入任何包含自定义注解的类与方法。顺序必须是先判断方法再判断类型
    //@Around("@within(myAuthority) || @annotation(myAuthority)")
    public Object checkMethodAndType(ProceedingJoinPoint joinPoint, MyAuthority myAuthority) throws Throwable {
        // 如果是类型级注解，而方法上没有，则需获取类型注解的值
        if(myAuthority == null) {
            myAuthority = joinPoint.getTarget().getClass().getAnnotation((MyAuthority.class));
        }
        for (MyAuthority.MyAuthorityType au : myAuthority.value()) {
            log.debug("{}", au);
            // 此处模拟用户实际权限为user。应该动态获取
            // 如果用户不是admin则抛出异常
            if(!au.equals(MyAuthority.MyAuthorityType.USER)) {
                throw XException.builder().message("无权限").build();
            }
        }
        log.debug("@within || @annotation");
        return joinPoint.proceed();
    }
}
