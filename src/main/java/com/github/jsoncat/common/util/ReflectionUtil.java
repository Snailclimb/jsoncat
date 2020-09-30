package com.github.jsoncat.common.util;

import com.github.jsoncat.annotation.Component;
import com.github.jsoncat.annotation.RestController;
import com.github.jsoncat.core.ioc.BeanFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author shuang.kou
 * @createTime 2020年09月25日 14:23:00
 **/
@Slf4j
public class ReflectionUtil {

    /**
     * create object instance through class
     */
    public static Object newInstance(Class<?> cls) {
        Object instance = null;
        try {
            instance = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("new instance failed", e);
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 设置成员变量的值
     */
    public static void setField(Object obj, Field field, Object value) {

        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            log.error("set field failed", e);
            e.printStackTrace();
        }

    }

    /**
     * @param method target method
     * @param args   method parameters
     * @return the result of method execution
     */
    public static Object executeMethod(Method method, Object... args) {
        Object result = null;
        try {
            // get the object that declared this method
            Class<?> declaringClass = method.getDeclaringClass();
            String beanName = "";
            if (declaringClass.isAnnotationPresent(RestController.class)) {
                beanName = declaringClass.getName();
            }
            if (declaringClass.isAnnotationPresent(Component.class)) {
                Component component = declaringClass.getAnnotation(Component.class);
                beanName = "".equals(component.name()) ? declaringClass.getName() : component.name();
            }
            Object targetObject = BeanFactory.BEANS.get(beanName);
            // invoke target method through reflection
            result = method.invoke(targetObject, args);
            log.info("invoke target method successfully ,result is: [{}]", result.toString());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

}
