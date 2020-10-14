package com.github.jsoncat.core.aop.cglib;

import com.github.jsoncat.core.aop.AbstractAopProxyBeanPostProcessor;
import com.github.jsoncat.core.aop.Interceptor;

/**
 * @author tom
 * JDK implementation of dynamic proxy
 * @createTime 2020年10月6日10:20:26
 */
public class CglibAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {

    @Override
    public Object wrapperBean(Object target, Interceptor interceptor) {
        return CglibMethodInterceptor.wrap(target, interceptor);
    }

}