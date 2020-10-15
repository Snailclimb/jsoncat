package com.github.jsoncat.core.aop.lang;

public interface JoinPoint {

    /**
     * get point this
     */
    Object getAdviceBean();

    /**
     * get target object
     */
    Object getTarget();

    /**
     * get parameters for object array
     */
    Object[] getArgs();
}
