package com.github.jsoncat.core.aop.cglib;

import com.github.jsoncat.core.aop.BeanPostProcessor;
import com.github.jsoncat.core.aop.Interceptor;

import java.util.List;

/**
 * @author tom
 * JDK implementation of dynamic proxy
 * @createTime 2020年10月6日10:20:26
 */
public class CglibAopProxyBeanPostProcessor implements BeanPostProcessor {
    private List<Interceptor> interceptors;

    public CglibAopProxyBeanPostProcessor(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean) {
        //chain
        Object wrapperProxyBean = bean;
        for (Interceptor interceptor : interceptors) {
            if (interceptor.supports(bean)) {
                wrapperProxyBean = CglibMethodInterceptor.wrap(wrapperProxyBean, interceptor);
            }
        }
        return wrapperProxyBean;
    }

}