package com.github.jsoncat.core.ioc;

import com.github.jsoncat.annotation.ioc.Component;
import com.github.jsoncat.annotation.springmvc.RestController;
import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.exception.DoGetBeanException;
import com.github.jsoncat.factory.ClassFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BeanFactory {

    // ioc bean 容器
    public static final Map<String, Object> BEANS = new ConcurrentHashMap<>(128);

    private static Map<String, String[]> singleBeanNamesTypeMap = new ConcurrentHashMap<>(128);

    public static void loadBeans() {
        ClassFactory.CLASSES.get(Component.class).forEach(aClass -> {
            String beanName = IocUtil.getBeanName(aClass);
            Object obj = ReflectionUtil.newInstance(aClass);
            BEANS.put(beanName, obj);
        });
        ClassFactory.CLASSES.get(RestController.class).forEach(aClass -> {
            Object obj = ReflectionUtil.newInstance(aClass);
            BEANS.put(aClass.getName(), obj);
        });
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
                Class<?> beanClass = beanEntry.getValue().getClass();
                if (type.isInterface()) {
                    Class<?>[] interfaces = beanClass.getInterfaces();
                    for (Class<?> c : interfaces) {
                        if (type.getName().equals(c.getName())) {
                            beanNamesList.add(beanEntry.getKey());
                            break;
                        }
                    }
                } else if (beanClass.isAssignableFrom(type)) {
                    beanNamesList.add(beanEntry.getKey());
                }
            }
            beanNames = beanNamesList.toArray(new String[0]);
            singleBeanNamesTypeMap.put(beanName, beanNames);
        }
        return beanNames;
    }

}