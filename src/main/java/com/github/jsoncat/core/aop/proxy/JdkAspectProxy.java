package com.github.jsoncat.core.aop.proxy;

import com.github.jsoncat.core.aop.intercept.Interceptor;
import com.github.jsoncat.core.aop.intercept.MethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class JdkAspectProxy implements InvocationHandler {

    private final Object target;
    private final Interceptor interceptor;

    private JdkAspectProxy(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object target, Interceptor interceptor) {
        JdkAspectProxy jdkAspectProxy = new JdkAspectProxy(target, interceptor);
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), jdkAspectProxy);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, args);
        // the return value is still the result of the proxy class execution
        return interceptor.intercept(methodInvocation);
    }

}
