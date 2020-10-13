package com.github.demo.aop.ascept;

import com.github.jsoncat.annotation.aop.After;
import com.github.jsoncat.annotation.aop.Aspect;
import com.github.jsoncat.annotation.aop.Before;
import com.github.jsoncat.annotation.aop.Pointcut;
import com.github.jsoncat.annotation.ioc.Component;
import com.github.jsoncat.core.aop.lang.JoinPoint;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class TeacherAspect {

    @Pointcut("com.github.demo.*.*Service*")
    public void perAspect() {

    }

    @Before
    public void beforeAction(JoinPoint joinPoint) {
        log.info("aspect teacher before to do something");
    }

    @After
    public void afterAction(Object result, JoinPoint joinPoint) {
        log.info("aspect teacher after to do something");
    }
}
