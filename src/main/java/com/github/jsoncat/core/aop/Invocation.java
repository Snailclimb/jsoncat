package com.github.jsoncat.core.aop;

import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author tom
 * 拦截器调用
 * @createTime 2020年10月6日10:20:26
 */
@Getter
public class Invocation {

    private final Object target;
    private final Method method;
    private final Object[] args;

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(target, args);
    }
}
