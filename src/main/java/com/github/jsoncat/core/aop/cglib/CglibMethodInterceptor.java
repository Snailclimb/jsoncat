package com.github.jsoncat.core.aop.cglib;

import com.github.jsoncat.core.aop.Interceptor;
import com.github.jsoncat.core.aop.MethodInvocation;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author shuang.kou
 * @createTime 2020年10月09日 21:43:00
 **/
public class CglibMethodInterceptor implements MethodInterceptor {
    private final Object target;
    private final Interceptor interceptor;

    public CglibMethodInterceptor(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object target, Interceptor interceptor) {
        Class<?> rootClass = target.getClass();
        Class<?> proxySuperClass = rootClass;
        if (target.getClass().getName().contains("$$")) {
            proxySuperClass = rootClass.getSuperclass();
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(target.getClass().getClassLoader());
        enhancer.setSuperclass(proxySuperClass);
        enhancer.setCallback(new CglibMethodInterceptor(target, interceptor));
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, args);
        // the return value is still the result of the proxy class execution
        return interceptor.intercept(methodInvocation);
    }
}
