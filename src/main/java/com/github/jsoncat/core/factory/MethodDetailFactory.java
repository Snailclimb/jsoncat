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
        MethodDetail methodDetail = null;
        if (httpMethod == HttpMethod.GET) {
            methodDetail = handle(requestPath, RouterFactory.GET_REQUEST_MAPPINGS, RouterFactory.GET_URL_MAPPINGS);
        }

        if (httpMethod == HttpMethod.POST) {
            methodDetail = handle(requestPath, RouterFactory.POST_REQUEST_MAPPINGS, RouterFactory.GET_URL_MAPPINGS);
        }
        return methodDetail;
    }

    private static MethodDetail handle(String requestPath, Map<String, Method> mappings, Map<String, String> urlMappings) {
        MethodDetail methodDetail = new MethodDetail();
        mappings.forEach((key, value) -> {
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
