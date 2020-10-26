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
    // get request url -> target method.
    // eg: "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> UserController.get(java.lang.Integer)
    private static final Map<String, Method> URL_TO_GET_REQUEST_METHOD = new HashMap<>();
    // post request url -> target method.
    private static final Map<String, Method> URL_TO_POST_REQUEST_METHOD = new HashMap<>();
    // formatted get request url -> original url
    // eg : "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> /user/{id}
    private static final Map<String, String> GET_URL_MAP = new HashMap<>();
    // formatted post request url -> original url
    private static final Map<String, String> POST_URL_MAP = new HashMap<>();

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
                            String formattedUrl = formatUrl(url);
                            if (URL_TO_GET_REQUEST_METHOD.containsKey(formattedUrl)) {
                                throw new IllegalArgumentException(String.format("duplicate get request handler for url: %s", url));
                            }
                            URL_TO_GET_REQUEST_METHOD.put(formattedUrl, method);
                            GET_URL_MAP.put(formattedUrl, url);
                        }
                    }
                    if (method.isAnnotationPresent(PostMapping.class)) {
                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        if (postMapping != null) {
                            String url = baseUrl + postMapping.value();
                            String formattedUrl = formatUrl(url);
                            if (URL_TO_POST_REQUEST_METHOD.containsKey(formattedUrl)) {
                                throw new IllegalArgumentException(String.format("duplicate post request handler for url: %s", url));
                            }
                            URL_TO_POST_REQUEST_METHOD.put(formattedUrl, method);
                            POST_URL_MAP.put(formattedUrl, url);
                        }
                    }
                }
            }
        }
    }

    public static MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod) {
        MethodDetail methodDetail = new MethodDetail();
        if (httpMethod == HttpMethod.GET) {
            methodDetail.build(requestPath, URL_TO_GET_REQUEST_METHOD, GET_URL_MAP);
            return methodDetail;
        }

        if (httpMethod == HttpMethod.POST) {
            methodDetail.build(requestPath, URL_TO_POST_REQUEST_METHOD, POST_URL_MAP);
            return methodDetail;
        }
        return null;
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
