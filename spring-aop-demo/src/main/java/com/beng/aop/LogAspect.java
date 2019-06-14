package com.beng.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 切面
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 切点
     */
    @Pointcut("execution (* com.beng.controller.HelloController.*(..))")
    public void logAround() {
    }

    @Around("logAround()")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        Object obj = null;
        try {
            log.info("==========>>>>> Around Before");
            obj = pjp.proceed();
            log.info("==========>>>>> Around After");
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
        return obj;
    }

}