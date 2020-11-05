package com.github.jsoncat.core.springmvc.factory;

import com.github.jsoncat.annotation.springmvc.GetMapping;
import com.github.jsoncat.annotation.springmvc.PostMapping;
import com.github.jsoncat.annotation.springmvc.RestController;
import com.github.jsoncat.core.springmvc.entity.MethodDetail;
import com.github.jsoncat.factory.ClassFactory;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Save routing-related mapping information
 *
 * @author shuang.kou
 * @createTime 2020年09月29日 13:27:00
 **/
public class RouteMethodMapper {
    public static final HttpMethod[] HTTP_METHODS = {HttpMethod.GET, HttpMethod.POST};

    // key : http method
    // value : url -> method
    private static final Map<HttpMethod, Map<String, Method>> REQUEST_METHOD_MAP = new HashMap<>(2);
    // key : http method
    // value : formatted url -> original url
    private static final Map<HttpMethod, Map<String, String>> REQUEST_URL_MAP = new HashMap<>(2);


    static {
        for (HttpMethod httpMethod : HTTP_METHODS) {
            REQUEST_METHOD_MAP.put(httpMethod, new HashMap<>(128));
            REQUEST_URL_MAP.put(httpMethod, new HashMap<>(128));
        }
    }

    public static void loadRoutes() {
        Set<Class<?>> classes = ClassFactory.CLASSES.get(RestController.class);
        for (Class<?> aClass : classes) {
            RestController restController = aClass.getAnnotation(RestController.class);
            if (null != restController) {
                Method[] methods = aClass.getDeclaredMethods();
                String baseUrl = restController.value();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        GetMapping getMapping = method.getAnnotation(GetMapping.class);
                        if (getMapping != null) {
                            mapUrlToMethod(baseUrl + getMapping.value(), method, HttpMethod.GET);
                        }
                    }
                    if (method.isAnnotationPresent(PostMapping.class)) {
                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        if (postMapping != null) {
                            mapUrlToMethod(baseUrl + postMapping.value(), method, HttpMethod.POST);
                        }
                    }
                }
            }
        }
    }

    public static MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod) {
        MethodDetail methodDetail = new MethodDetail();
        methodDetail.build(requestPath, REQUEST_METHOD_MAP.get(httpMethod), REQUEST_URL_MAP.get(httpMethod));
        return methodDetail;
    }

    /**
     * correspond url to method
     */
    private static void mapUrlToMethod(String url, Method method, HttpMethod httpMethod) {
        String formattedUrl = formatUrl(url);
        Map<String, Method> urlToMethodMap = REQUEST_METHOD_MAP.get(httpMethod);
        Map<String, String> formattedUrlToUrlMap = REQUEST_URL_MAP.get(httpMethod);
        if (urlToMethodMap.containsKey(formattedUrl)) {
            throw new IllegalArgumentException(String.format("duplicate url: %s", url));
        }
        urlToMethodMap.put(formattedUrl, method);
        formattedUrlToUrlMap.put(formattedUrl, url);
        REQUEST_METHOD_MAP.put(httpMethod, urlToMethodMap);
        REQUEST_URL_MAP.put(httpMethod, formattedUrlToUrlMap);
    }

    /**
     * format the url
     * for example : "/user/{name}" -> "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$"
     */
    private static String formatUrl(String url) {
        // replace {xxx} placeholders with regular expressions matching Chinese, English letters and numbers, and underscores
        String originPattern = url.replaceAll("(\\{\\w+})", "[\\\\u4e00-\\\\u9fa5_a-zA-Z0-9]+");
        String pattern = "^" + originPattern + "/?$";
        return pattern.replaceAll("/+", "/");
    }

}
