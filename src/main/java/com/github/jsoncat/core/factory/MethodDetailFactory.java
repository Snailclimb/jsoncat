package com.github.jsoncat.core.factory;

import com.github.jsoncat.common.util.UrlUtil;
import com.github.jsoncat.core.entity.MethodDetail;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author shuang.kou
 * @createTime 2020年09月29日 14:24:00
 **/
public class MethodDetailFactory {

    public static MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod) {
        if (httpMethod == HttpMethod.GET) {
            return buildMethodDetail(requestPath, RouterFactory.GET_REQUEST_MAPPINGS, RouterFactory.GET_URL_MAPPINGS);
        }

        if (httpMethod == HttpMethod.POST) {
            return buildMethodDetail(requestPath, RouterFactory.POST_REQUEST_MAPPINGS, RouterFactory.POST_URL_MAPPINGS);
        }
        return null;
    }

    private static MethodDetail buildMethodDetail(String requestPath, Map<String, Method> requestMappings, Map<String, String> urlMappings) {
        MethodDetail methodDetail = new MethodDetail();
        requestMappings.forEach((key, value) -> {
            Pattern pattern = Pattern.compile(key);
            boolean b = pattern.matcher(requestPath).find();
            if (b) {
                methodDetail.setMethod(value);
                String url = urlMappings.get(key);
                Map<String, String> urlParameterMappings = UrlUtil.getUrlParameterMappings(requestPath, url);
                methodDetail.setUrlParameterMappings(urlParameterMappings);
            }
        });
        return methodDetail;
    }

}
