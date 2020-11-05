package com.github.jsoncat.common.util;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import javax.validation.ConstraintViolationException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * the common methods of reflection
 *
 * @author shuang.kou
 * @createTime 2020年09月25日 14:23:00
 **/
@Slf4j
public class ReflectionUtil {

    /**
     * scan the classes marked by the specified annotation in the specified package
     *
     * @param packageNames specified package name
     * @param annotation   specified annotation
     * @return the classes marked by the specified annotation in the specified package
     */
    public static Set<Class<?>> scanAnnotatedClass(String[] packageNames, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageNames, new TypeAnnotationsScanner());
        Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(annotation, true);
        log.info("The number of class Annotated with @" + annotation.getSimpleName() + ":[{}]", annotatedClass.size());
        return annotatedClass;
    }

    /**
     * Get the implementation class of the interface
     *
     * @param packageNames   specified package name
     * @param interfaceClass specified interface
     */
    public static <T> Set<Class<? extends T>> getSubClass(Object[] packageNames, Class<T> interfaceClass) {
        Reflections reflections = new Reflections(packageNames);
        return reflections.getSubTypesOf(interfaceClass);

    }

    /**
     * create object instance through class
     *
     * @param cls target class
     * @return object created by the target class
     */
    public static Object newInstance(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
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
        } catch (IllegalAccessException impossible) {
            throw new AssertionError(impossible);
        }

    }

    /**
     * execute the target method
     *
     * @param method target method
     * @param args   method parameters
     * @return the result of method execution
     */
    public static Object executeTargetMethod(Object targetObject, Method method, Object... args) {
        try {
            return method.invoke(targetObject, args);
        } catch (Throwable t) {
            if (t.getCause() != null && t.getCause() instanceof ConstraintViolationException) {
                throw (ConstraintViolationException) t.getCause();
            }
        }
        return null;
    }

    /**
     * execute the void method
     *
     * @param method target method
     * @param args   method parameters
     */
    public static void executeTargetMethodNoResult(Object targetObject, Method method, Object... args) {
        try {
            // invoke target method through reflection
            method.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
    }
}
