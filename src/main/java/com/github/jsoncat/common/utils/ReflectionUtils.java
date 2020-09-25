package com.github.jsoncat.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author shuang.kou
 * @createTime 2020年09月25日 14:23:00
 **/
@Slf4j
public class ReflectionUtils {
    /**
     * @param method target method
     * @param args   method parameters
     * @return the result of method execution
     */
    public static Object executeMethod(Method method, Object... args) {
        Object result = null;
        try {
            // get the object that declared this method
            Object targetObject = method.getDeclaringClass().newInstance();
            // invoke target method through reflection
            result = method.invoke(targetObject, args);
            log.info("invoke target method successfully ,result is: [{}]", result.toString());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Object cast(String s, Class<?> aClass) {
        return aClass.cast(s);
    }
}
