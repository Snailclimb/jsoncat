package com.github.jsoncat.core.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class ProxyFactory implements InvocationHandler {

    private final Object target;
    private final Interceptor interceptor;

    private ProxyFactory(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object target, Interceptor filter) {
        ProxyFactory proxyFactory = new ProxyFactory(target, filter);
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), proxyFactory);

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Invocation invocation = new Invocation(target, method, args);
        return interceptor.intercept(invocation);
    }


}
