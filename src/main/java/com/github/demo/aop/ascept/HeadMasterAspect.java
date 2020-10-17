package com.github.demo.aop.ascept;

import com.github.jsoncat.annotation.aop.After;
import com.github.jsoncat.annotation.aop.Aspect;
import com.github.jsoncat.annotation.aop.Before;
import com.github.jsoncat.annotation.aop.Order;
import com.github.jsoncat.annotation.aop.Pointcut;
import com.github.jsoncat.annotation.ioc.Component;
import com.github.jsoncat.core.aop.lang.JoinPoint;
import lombok.extern.slf4j.Slf4j;


@Aspect
@Order(value = 1)
@Component
@Slf4j
public class HeadMasterAspect {

    @Pointcut("com.github.demo.*.*Service*")
    public void oneAspect() {

    }

    @Pointcut("com.github.demo.*.*Controller*")
    public void twoAspect() {

    }

    @Before
    public void beforeAction(JoinPoint params) {
        log.info("aspect headmaster : before to do something");
    }

    @After
    public void afterAction(Object result, JoinPoint joinPoint) {
        log.info("aspect headmaster after to do something");
    }
}
