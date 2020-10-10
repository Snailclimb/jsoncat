package com.github.jsoncat.core.aop.jdk;

import com.github.jsoncat.core.aop.BeanPostProcessor;
import com.github.jsoncat.core.aop.Interceptor;

import java.util.List;

/**
 * @author tom
 * JDK implementation of dynamic proxy
 * @createTime 2020年10月6日10:20:26
 */
public class JdkAopProxyBeanPostProcessor implements BeanPostProcessor {
    private List<Interceptor> interceptors;

    public JdkAopProxyBeanPostProcessor(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean) {
        //chain
        Object wrapperProxyBean = bean;
        for (Interceptor interceptor : interceptors) {
            if (interceptor.supports(bean)) {
                wrapperProxyBean = JdkInvocationHandler.wrap(wrapperProxyBean, interceptor);
            }
        }
        return wrapperProxyBean;
    }
}