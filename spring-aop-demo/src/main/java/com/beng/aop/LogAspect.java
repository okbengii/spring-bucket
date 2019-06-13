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
@Aspect
@Slf4j
@Component
public class LogAspect {

    /**
     * 切点
     */
    @Pointcut("execution (* com.beng.controller.*.*(..))")
    public void logAround() {
    }

    @Around("logAround()")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        log.info("hello world!");
        try {
            return pjp.proceed();
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
    }

}