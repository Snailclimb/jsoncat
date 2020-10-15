package com.github.jsoncat.core.aop.factory;

import com.github.jsoncat.annotation.aop.Aspect;
import com.github.jsoncat.annotation.aop.Order;
import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.core.aop.intercept.Interceptor;
import com.github.jsoncat.core.aop.intercept.InternallyAspectInterceptor;
import com.github.jsoncat.exception.CannotInitializeConstructorException;
import com.github.jsoncat.factory.ClassFactory;

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
        subClasses.forEach(subClass -> {
            try {
                interceptors.add(subClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new CannotInitializeConstructorException("not init constructor , the interceptor name :" + subClass.getSimpleName());
            }
        });
        ClassFactory.CLASSES.get(Aspect.class).forEach(aClass -> {
            Object obj = ReflectionUtil.newInstance(aClass);
            Interceptor interceptor = new InternallyAspectInterceptor(obj);
            if (aClass.isAnnotationPresent(Order.class)) {
                Order order = aClass.getAnnotation(Order.class);
                interceptor.setOrder(order.value());
            }
            interceptors.add(interceptor);
        });
        interceptors = interceptors.stream().sorted(Comparator.comparing(Interceptor::getOrder)).collect(Collectors.toList());
    }

    public static List<Interceptor> getInterceptors() {
        return interceptors;
    }
}
