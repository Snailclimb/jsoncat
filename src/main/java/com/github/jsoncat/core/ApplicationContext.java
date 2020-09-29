package com.github.jsoncat.core;

import com.github.jsoncat.annotation.Component;
import com.github.jsoncat.annotation.RestController;
import com.github.jsoncat.core.factory.RouterFactory;
import com.github.jsoncat.core.scanner.AnnotatedClassScanner;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将路由和方法对应起来
 *
 * @author shuang.kou
 * @createTime 2020年09月24日 16:49:00
 **/
public final class ApplicationContext {
    private static final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext();
    public static final Map<Class<? extends Annotation>, Set<Class<?>>> CLASSES = new ConcurrentHashMap<>();

    public void loadClass(String packageName) {
        Set<Class<?>> restControllerSets = AnnotatedClassScanner.scan(packageName, RestController.class);
        Set<Class<?>> componentsSets = AnnotatedClassScanner.scan(packageName, Component.class);
        CLASSES.put(RestController.class, restControllerSets);
        CLASSES.put(Component.class, componentsSets);
    }


    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

}
