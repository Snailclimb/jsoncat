package com.github.jsoncat.core.ioc;

import com.github.jsoncat.annotation.aop.Aspect;
import com.github.jsoncat.annotation.ioc.Component;
import com.github.jsoncat.annotation.springmvc.RestController;
import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.exception.DoGetBeanException;
import com.github.jsoncat.factory.ClassFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class BeanFactory {

    public static final Map<String, Object> BEANS = new ConcurrentHashMap<>(128);

    //缓存type与实例之间的关系
    private static Map<String, String[]> singleBeanNamesTypeMap = new ConcurrentHashMap<>(128);

    public static void loadBeans() {

        // 遍历所有被特定注解标记的类
        ClassFactory.CLASSES.forEach((annotation, classes) -> {
            if (annotation == Component.class) {
                //将bean实例化, 并放入bean容器中
                for (Class<?> aClass : classes) {
                    String beanName = IocUtil.getBeanName(aClass);
                    Object obj = ReflectionUtil.newInstance(aClass);
                    BEANS.put(beanName, obj);
                }
            }

            if (annotation == RestController.class) {
                for (Class<?> aClass : classes) {
                    Object obj = ReflectionUtil.newInstance(aClass);
                    BEANS.put(aClass.getName(), obj);
                }
            }

            if (annotation == Aspect.class) {
                List<String> beanNamesList = new ArrayList<>();
                for (Class<?> aClass : classes) {
                    Object obj = ReflectionUtil.newInstance(aClass);
                    BEANS.put(aClass.getName(), obj);
                    beanNamesList.add(aClass.getName());
                }
                singleBeanNamesTypeMap.put(Aspect.class.getName(), beanNamesList.toArray(new String[0]));
            }
        });
    }

    public static <T> T getBean(Class<T> type) {
        String[] beanNames = getBeanNamesForType(type);
        if (beanNames.length == 0) {
            throw new DoGetBeanException("not fount bean implement，the bean :" + type.getName());
        }
        Object beanInstance = BEANS.get(beanNames[0]);
        if (!type.isInstance(beanInstance)) {
            throw new DoGetBeanException("not fount bean implement，the bean :" + type.getName());
        }
        return type.cast(beanInstance);
    }

    public static Set<Object> getBeansForName(String name) {
        String[] beanNames = singleBeanNamesTypeMap.get(name);
        if (beanNames == null) {
            List<String> beanNamesList = new ArrayList<>();
            for (Map.Entry<String, Object> beanEntry : BEANS.entrySet()) {
                if (beanEntry.getValue().getClass().getName().equals(name)) {
                    beanNamesList.add(beanEntry.getKey());
                }
            }
            beanNames = beanNamesList.toArray(new String[0]);
            singleBeanNamesTypeMap.put(name, beanNames);
        }
        Set<Object> result = new HashSet<>();
        for (String beanName : beanNames) {
            Object beanInstance = BEANS.get(beanName);
            result.add(beanInstance);
        }
        return result;
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> result = new HashMap<>();
        String[] beanNames = getBeanNamesForType(type);
        for (String beanName : beanNames) {
            Object beanInstance = BEANS.get(beanName);
            if (!type.isInstance(beanInstance)) {
                throw new DoGetBeanException("not fount bean implement，the bean :" + type.getName());
            }
            result.put(beanName, type.cast(beanInstance));
        }
        return result;
    }

    private static String[] getBeanNamesForType(Class<?> type) {
        String beanName = type.getName();
        String[] beanNames = singleBeanNamesTypeMap.get(beanName);
        if (beanNames == null) {
            List<String> beanNamesList = new ArrayList<>();
            for (Map.Entry<String, Object> beanEntry : BEANS.entrySet()) {
                if (beanEntry.getValue().getClass().isAssignableFrom(type)) {
                    beanNamesList.add(beanEntry.getKey());
                }
            }
            beanNames = beanNamesList.toArray(new String[0]);
            singleBeanNamesTypeMap.put(beanName, beanNames);
        }
        return beanNames;
    }
}