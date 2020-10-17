package com.github.jsoncat.core.aop.proxy;

import com.github.jsoncat.core.aop.intercept.Interceptor;
import com.github.jsoncat.core.aop.intercept.MethodInvocation;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author shuang.kou & tom
 * @createTime 2020年10月09日 21:43:00
 **/
public class CglibAspectProxy implements MethodInterceptor {
    private final Object target;
    private final Interceptor interceptor;

    public CglibAspectProxy(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object target, Interceptor interceptor) {
        Class<?> rootClass = target.getClass();
        Class<?> proxySuperClass = rootClass;
        // cglib 多级代理处理
        if (target.getClass().getName().contains("$$")) {
            proxySuperClass = rootClass.getSuperclass();
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(target.getClass().getClassLoader());
        enhancer.setSuperclass(proxySuperClass);
        enhancer.setCallback(new CglibAspectProxy(target, interceptor));
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, args);
        // the return value is still the result of the proxy class execution
        return interceptor.intercept(methodInvocation);
    }
}
