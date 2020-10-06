package com.github.jsoncat.core.aop;

/**
 * @author tom
 * aop 拦截器
 * @createTime 2020年10月6日10:20:26
 */
public interface Interceptor {

    default boolean supports(String beanName) {
        return false;
    }

    /**
     * @param invocation
     * @return
     */
    Object intercept(Invocation invocation);
}
