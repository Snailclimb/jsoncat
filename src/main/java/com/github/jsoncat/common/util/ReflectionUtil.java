package com.github.jsoncat.common.util;

import com.github.jsoncat.annotation.ioc.Component;
import com.github.jsoncat.annotation.springmvc.RestController;
import com.github.jsoncat.core.ioc.BeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author shuang.kou
 * @createTime 2020年09月25日 14:23:00
 **/
@Slf4j
public class ReflectionUtil {
    /**
     * scan the classes marked by the specified annotation in the specified package
     *
     * @param packageName specified package name
     * @param annotation  specified annotation
     * @return the classes marked by the specified annotation in the specified package
     */
    public static Set<Class<?>> scanAnnotatedClass(String packageName, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageName, new TypeAnnotationsScanner());
        Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(annotation, true);
        log.info("The number of class Annotated with  @RestController :[{}]", annotatedClass.size());
        return annotatedClass;
    }

    /**
     * Get the implementation class of the interface
     *
     * @param packageName    specified package name
     * @param interfaceClass specified interface
     */
    public static <T> Set<Class<? extends T>> getSubClass(String packageName,  Class<T> interfaceClass) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(interfaceClass);

    }

    /**
     * create object instance through class
     *
     * @param cls target class
     * @return object created by the target class
     */
    public static Object newInstance(Class<?> cls) {
        Object instance = null;
        try {
            instance = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("new instance failed", e);
        }
        return instance;
    }

    /**
     * set the value of a field in the object
     *
     * @param obj   target object
     * @param field target field
     * @param value the value assigned to the field
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
