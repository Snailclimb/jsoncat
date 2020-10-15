package com.github.jsoncat.core.aop;

import com.github.jsoncat.core.aop.cglib.CglibAopProxyBeanPostProcessor;
import com.github.jsoncat.core.aop.jdk.JdkAopProxyBeanPostProcessor;

public class BeanPostProcessorFactory {

    public static BeanPostProcessor getBeanPostProcessor(Class<?> beanClass) {
        if (beanClass.isInterface() || beanClass.getInterfaces().length > 0) {
            return new JdkAopProxyBeanPostProcessor();
        } else {
            return new CglibAopProxyBeanPostProcessor();
        }
    }
}
