package com.github.jsoncat.core.aop.lang;

public interface JoinPoint {

    /**
     * get point this
     *
     * @return
     */
    Object getAdviceBean();

    /**
     * get target object
     *
     * @return
     */
    Object getTarget();

    /**
     * get parameters for object array
     *
     * @return
     */
    Object[] getArgs();
}
