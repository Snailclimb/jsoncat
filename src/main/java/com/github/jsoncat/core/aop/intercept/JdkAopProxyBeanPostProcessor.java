package com.github.jsoncat.core.aop.intercept;

import com.github.jsoncat.core.aop.proxy.JdkAspectProxy;

/**
 * @author tom
 * JDK implementation of dynamic proxy
 * @createTime 2020年10月6日10:20:26
 */
public class JdkAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {


    @Override
    public Object wrapperBean(Object target, Interceptor interceptor) {
        return JdkAspectProxy.wrap(target, interceptor);
    }
}