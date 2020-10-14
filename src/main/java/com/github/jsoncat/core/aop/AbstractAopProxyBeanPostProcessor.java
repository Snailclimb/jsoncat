package com.github.jsoncat.core.aop;

import com.github.jsoncat.annotation.aop.Aspect;
import com.github.jsoncat.core.ioc.BeanFactory;

import java.util.*;

public abstract class AbstractAopProxyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean) {

        Map<String, Interceptor> interceptorMap = BeanFactory.getBeansOfType(Interceptor.class);
        //chain
        Object wrapperProxyBean = bean;
        for (Map.Entry<String, Interceptor> interceptorEntry : interceptorMap.entrySet()) {
            Interceptor interceptor = interceptorEntry.getValue();
            if (interceptor.supports(bean)) {
                wrapperProxyBean = wrapperBean(wrapperProxyBean, interceptor);
            }
        }
        //aspect
        Set<Object> adviceBeans = BeanFactory.getBeansForName(Aspect.class.getName());
        for (Object adviceBean : adviceBeans) {
            //Don't instantiate it every time
            Interceptor aspectInterceptor = new InternallyAspectInterceptor(adviceBean);
            if (aspectInterceptor.supports(bean)) {
                wrapperProxyBean = wrapperBean(wrapperProxyBean, aspectInterceptor);
            }
        }
        return wrapperProxyBean;
    }

    public abstract Object wrapperBean(Object target, Interceptor interceptor);
}
