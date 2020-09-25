package com.github.jsoncat.core;

import com.github.jsoncat.annotation.GetMapping;
import com.github.jsoncat.annotation.PostMapping;
import com.github.jsoncat.annotation.RestController;
import com.github.jsoncat.core.scanner.AnnotatedClassScanner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 将路由和方法对应起来
 *
 * @author shuang.kou
 * @createTime 2020年09月24日 16:49:00
 **/
public class Router {

    public static Map<String, Method> getMappings = new HashMap<>();
    public static Map<String, Method> postMappings = new HashMap<>();
    public static Map<String, Class<?>> classMappings = new HashMap<>();


    public void loadRoutes(String packageName) {
        Set<Class<?>> classes = AnnotatedClassScanner.scan(packageName, RestController.class);
        for (Class<?> aClass : classes) {
            RestController restController = aClass.getAnnotation(RestController.class);
            if (null != restController) {
                Method[] methods = aClass.getMethods();
                String baseUrl = restController.value();
                traverseMethods(baseUrl, methods);
                classMappings.put(baseUrl, aClass);
            }
        }
    }

    private void traverseMethods(String baseUrl, Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                if (getMapping != null) {
                    getMappings.put(baseUrl + getMapping.value(), method);
                }
            }
            if (method.isAnnotationPresent(PostMapping.class)) {
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                if (postMapping != null) {
                    postMappings.put(baseUrl + postMapping.value(), method);
                }
            }
        }
    }

}
