package com.github.jsoncat.core.springmvc.factory;

import com.github.jsoncat.annotation.springmvc.GetMapping;
import com.github.jsoncat.annotation.springmvc.PostMapping;
import com.github.jsoncat.annotation.springmvc.RestController;
import com.github.jsoncat.common.util.UrlUtil;
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
    // get request url -> target method.
    // eg: "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> UserController.get(java.lang.Integer)
    private static final Map<String, Method> GET_REQUEST_MAPPINGS = new HashMap<>();
    // post request url -> target method.
    private static final Map<String, Method> POST_REQUEST_MAPPINGS = new HashMap<>();
    // formatted get request url -> original url
    // eg : "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> /user/{id}
    private static final Map<String, String> GET_URL_MAPPINGS = new HashMap<>();
    // formatted post request url -> original url
    private static final Map<String, String> POST_URL_MAPPINGS = new HashMap<>();

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
                            String url = baseUrl + getMapping.value();
                            String formattedUrl = UrlUtil.formatUrl(url);
                            GET_REQUEST_MAPPINGS.put(formattedUrl, method);
                            GET_URL_MAPPINGS.put(formattedUrl, url);
                        }
                    }
                    if (method.isAnnotationPresent(PostMapping.class)) {
                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        if (postMapping != null) {
                            String url = baseUrl + postMapping.value();
                            String formattedUrl = UrlUtil.formatUrl(url);
                            POST_REQUEST_MAPPINGS.put(formattedUrl, method);
                            POST_URL_MAPPINGS.put(formattedUrl, url);
                        }
                    }
                }
            }
        }
    }

    public static MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod) {
        MethodDetail methodDetail = new MethodDetail();
        if (httpMethod == HttpMethod.GET) {
            methodDetail.build(requestPath, GET_REQUEST_MAPPINGS, GET_URL_MAPPINGS);
            return methodDetail;
        }

        if (httpMethod == HttpMethod.POST) {
            methodDetail.build(requestPath, POST_REQUEST_MAPPINGS, POST_URL_MAPPINGS);
            return methodDetail;
        }
        return null;
    }

}
