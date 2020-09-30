package com.github.jsoncat.core.factory;

import com.github.jsoncat.annotation.Component;
import com.github.jsoncat.annotation.RestController;
import com.github.jsoncat.core.scanner.AnnotatedClassScanner;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shuang.kou
 * @createTime 2020年09月30日 15:26:00
 **/
public class ClassFactory {
    public static final Map<Class<? extends Annotation>, Set<Class<?>>> CLASSES = new ConcurrentHashMap<>();

    public static void loadClass(String packageName) {
        Set<Class<?>> restControllerSets = AnnotatedClassScanner.scan(packageName, RestController.class);
        Set<Class<?>> componentSets = AnnotatedClassScanner.scan(packageName, Component.class);
        CLASSES.put(RestController.class, restControllerSets);
        CLASSES.put(Component.class, componentSets);
    }

}
