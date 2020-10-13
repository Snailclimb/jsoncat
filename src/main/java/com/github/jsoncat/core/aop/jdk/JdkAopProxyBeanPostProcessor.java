package com.github.jsoncat.core.aop.jdk;

import com.github.jsoncat.core.aop.AbstractAopProxyBeanPostProcessor;
import com.github.jsoncat.core.aop.Interceptor;

/**
 * @author tom
 * JDK implementation of dynamic proxy
 * @createTime 2020年10月6日10:20:26
 */
public class JdkAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {


    @Override
    public Object wrapperBean(Object target, Interceptor interceptor) {
        return JdkInvocationHandler.wrap(target, interceptor);
    }
}