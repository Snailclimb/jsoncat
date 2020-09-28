package com.github.jsoncat.core;

import com.github.jsoncat.annotation.GetMapping;
import com.github.jsoncat.annotation.PostMapping;
import com.github.jsoncat.annotation.RestController;
import com.github.jsoncat.common.util.UrlUtil;
import com.github.jsoncat.core.entity.MethodDetail;
import com.github.jsoncat.core.scanner.AnnotatedClassScanner;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 将路由和方法对应起来
 *
 * @author shuang.kou
 * @createTime 2020年09月24日 16:49:00
 **/
public final class ApplicationContext {
    private static final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext();
    private final Map<String, Method> getMappings = new HashMap<>();
    private final Map<String, Method> postMappings = new HashMap<>();
    private final Map<String, MethodDetail> getMethodMappings = new HashMap<>();
    private final Map<String, MethodDetail> postMethodMappings = new HashMap<>();
    private final Map<String, String> getUrlMappings = new HashMap<>();
    private final Map<String, String> postUrlMappings = new HashMap<>();

    private ApplicationContext() {

    }

    public void loadRoutes(String packageName) {
        Set<Class<?>> classes = AnnotatedClassScanner.scan(packageName, RestController.class);
        for (Class<?> aClass : classes) {
            RestController restController = aClass.getAnnotation(RestController.class);
            if (null != restController) {
                Method[] methods = aClass.getMethods();
                String baseUrl = restController.value();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        GetMapping getMapping = method.getAnnotation(GetMapping.class);
                        if (getMapping != null) {
                            String url = baseUrl + getMapping.value();
                            String formattedUrl = UrlUtil.formatUrl(url);
                            getMappings.put(formattedUrl, method);
                            getUrlMappings.put(formattedUrl, url);
                        }
                    }
                    if (method.isAnnotationPresent(PostMapping.class)) {
                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        if (postMapping != null) {
                            String url = baseUrl + postMapping.value();
                            String formattedUrl = UrlUtil.formatUrl(url);
                            postMappings.put(formattedUrl, method);
                            postUrlMappings.put(formattedUrl, url);
                        }
                    }
                }
            }
        }
    }

    public MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod) {
        MethodDetail methodDetail = null;
        if (httpMethod == HttpMethod.GET) {
            methodDetail = handle(requestPath, getMappings, getUrlMappings, getMethodMappings);
        }

        if (httpMethod == HttpMethod.POST) {
            methodDetail = handle(requestPath, postMappings, postUrlMappings, postMethodMappings);
        }
        return methodDetail;
    }

    private MethodDetail handle(String requestPath, Map<String, Method> postMappings, Map<String, String> postUrlMappings, Map<String, MethodDetail> postMethodMappings) {
        MethodDetail methodDetail = new MethodDetail();
        postMappings.forEach((key, value) -> {
            Pattern pattern = Pattern.compile(key);
            boolean b = pattern.matcher(requestPath).find();
            if (b) {
                methodDetail.setMethod(value);
                String url = postUrlMappings.get(key);
                Map<String, String> urlParameterMappings = UrlUtil.getUrlParameterMappings(requestPath, url);
                methodDetail.setUrlParameterMappings(urlParameterMappings);
                postMethodMappings.put(key, methodDetail);
            }
        });
        return methodDetail;
    }


    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

}
