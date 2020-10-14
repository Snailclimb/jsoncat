package com.github.jsoncat.core.aop;

import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.exception.CannotInitializeConstructorException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shuang.kou
 * @createTime 2020年10月09日 22:24:00
 **/
public class InterceptorFactory {
    private static List<Interceptor> interceptors = new ArrayList<>();

    public static void loadInterceptors(String packageName) {
        // get the implementations of the Interceptor in the specified package
        Set<Class<? extends Interceptor>> subClasses = ReflectionUtil.getSubClass(packageName, Interceptor.class);
        for (Class<? extends Interceptor> subClass : subClasses) {
            try {
                interceptors.add(subClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new CannotInitializeConstructorException("not init constructor , the interceptor name :" + subClass.getSimpleName());
            }
        }
        interceptors = interceptors.stream().sorted(Comparator.comparing(Interceptor::getOrder)).collect(Collectors.toList());
    }

    public static List<Interceptor> getInterceptors() {
        return interceptors;
    }
}
