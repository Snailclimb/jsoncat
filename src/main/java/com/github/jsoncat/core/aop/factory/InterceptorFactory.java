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
 * 存放所有拦截器的工厂类
 *
 * @author shuang.kou
 * @createTime 2020年10月09日 22:24:00
 **/
public class InterceptorFactory {
    private static List<Interceptor> interceptors = new ArrayList<>();

    public static void loadInterceptors(String packageName) {
        // 获取指定包中实现了 Interceptor 接口的类
        Set<Class<? extends Interceptor>> interceptorClasses = ReflectionUtil.getSubClass(packageName, Interceptor.class);
        // 获取被 @Aspect 标记的类
        Set<Class<?>> aspects = ClassFactory.CLASSES.get(Aspect.class);
        // 遍历所有拦截器类
        interceptorClasses.forEach(interceptorClass -> {
            try {
                interceptors.add(interceptorClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new CannotInitializeConstructorException("not init constructor , the interceptor name :" + interceptorClass.getSimpleName());
            }
        });
        aspects.forEach(aClass -> {
            Object obj = ReflectionUtil.newInstance(aClass);
            Interceptor interceptor = new InternallyAspectInterceptor(obj);
            if (aClass.isAnnotationPresent(Order.class)) {
                Order order = aClass.getAnnotation(Order.class);
                interceptor.setOrder(order.value());
            }
            interceptors.add(interceptor);
        });
        // 根据 order 为拦截器排序
        interceptors = interceptors.stream().sorted(Comparator.comparing(Interceptor::getOrder)).collect(Collectors.toList());
    }

    public static List<Interceptor> getInterceptors() {
        return interceptors;
    }
}
