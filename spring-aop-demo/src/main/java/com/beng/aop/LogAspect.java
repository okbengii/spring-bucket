package com.beng.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 切面
 */
/**
 * 切面的运行顺序：@Around -> @Before -> @Around -> @After  -> @AfterReturning/@AfterReturning
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

    /**
     * 方法执行前和方法执行后
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("logAround()")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        Object obj = null;
        try {
            log.info("==========>>>>> Around Before");
            obj = pjp.proceed(); // 执行方法
            log.info("==========>>>>> Around After");
        } catch (Exception e) {
            throw e;
        }
        return obj;
    }


    @Before("logAround()")
    public void before(JoinPoint jp){
        log.info("========>>>>>>>>Before");
    }

    @After("logAround()")
    public void after(JoinPoint jp){
        log.info("=========>>>>>>>After");
    }

    /**
     * 返回值
     */
    @AfterReturning(pointcut="logAround()",returning="returnVal")
    public void afterReturn(JoinPoint joinPoint,Object returnVal){
        log.info("AOP AfterReturning Advice:" + returnVal);
    }

    /**
     * 捕获异常
     * @param joinPoint
     * @param error
     */
    @AfterThrowing(pointcut="logAround()",throwing="error")
    public void afterThrowing(JoinPoint joinPoint,Throwable error){
        log.error("AOP AfterThrowing Advice..." + error);
        log.error("AfterThrowing...");
    }
}