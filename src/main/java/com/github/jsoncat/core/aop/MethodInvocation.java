package com.github.jsoncat.core.aop;

import com.github.jsoncat.common.util.ReflectionUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author tom
 * @createTime 2020年10月6日10:20:26
 */
@Getter
@AllArgsConstructor
public class MethodInvocation {

    //target object
    private final Object targetObject;
    //target method
    private final Method targetMethod;
    //the parameter of target method
    private final Object[] args;

    public Object proceed() {
        return ReflectionUtil.executeTargetMethod(targetObject, targetMethod, args);
    }
}
