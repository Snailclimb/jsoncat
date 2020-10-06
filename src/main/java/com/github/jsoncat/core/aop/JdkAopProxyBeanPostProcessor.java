package com.github.jsoncat.core.aop;

import com.github.jsoncat.core.ioc.BeanPostProcessor;
import com.github.jsoncat.exception.CannotInitializaeConstructorException;
import org.reflections.Reflections;

import java.util.Objects;
import java.util.Set;

/**
 * @author tom
 * jdk 实现动态代理
 * @createTime 2020年10月6日10:20:26
 */
public class JdkAopProxyBeanPostProcessor implements BeanPostProcessor {

    /**
     * 支持拦截器的执行顺序
     */
    private static Set<Class<? extends Interceptor>> interceptorSets = null;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (Objects.isNull(interceptorSets)) {
            Reflections reflections = new Reflections(bean.getClass().getClassLoader());
            interceptorSets = reflections.getSubTypesOf(Interceptor.class);
        }
        //链式包装
        Object wrapperProxyBean = bean;
        for (Class<? extends Interceptor> interceptorClass : interceptorSets) {
            try {
                Interceptor interceptor = interceptorClass.newInstance();
                if (interceptor.supports(beanName)) {
                    wrapperProxyBean = ProxyFactory.wrap(wrapperProxyBean, interceptor);
                }
            } catch (IllegalAccessException | InstantiationException e) {
                throw new CannotInitializaeConstructorException("not init constructor , the interceptor name :" + interceptorClass.getSimpleName());
            }
        }
        return wrapperProxyBean;
    }
}
