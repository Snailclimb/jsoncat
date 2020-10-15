package com.github.jsoncat.core.aop.factory;

import com.github.jsoncat.core.aop.intercept.BeanPostProcessor;
import com.github.jsoncat.core.aop.intercept.CglibAopProxyBeanPostProcessor;
import com.github.jsoncat.core.aop.intercept.JdkAopProxyBeanPostProcessor;

public class BeanPostProcessorFactory {

    public static BeanPostProcessor getBeanPostProcessor(Class<?> beanClass) {
        if (beanClass.isInterface() || beanClass.getInterfaces().length > 0) {
            return new JdkAopProxyBeanPostProcessor();
        } else {
            return new CglibAopProxyBeanPostProcessor();
        }
    }
}
