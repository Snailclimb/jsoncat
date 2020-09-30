package com.github.jsoncat.core.ioc;

import com.github.jsoncat.annotation.Component;
import com.github.jsoncat.annotation.RestController;
import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.core.factory.ClassFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BeanFactory {
    public static final Map<String, Object> BEANS = new ConcurrentHashMap<>(128);

    public static void loadBeans() {

        ClassFactory.CLASSES.forEach((annotation, classes) -> {
            if (annotation == Component.class) {
                //将bean实例化, 并放入bean容器中
                for (Class<?> aClass : classes) {
                    Component component = aClass.getAnnotation(Component.class);
                    String beanName = "".equals(component.name()) ? aClass.getName() : component.name();
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
        });
    }

}