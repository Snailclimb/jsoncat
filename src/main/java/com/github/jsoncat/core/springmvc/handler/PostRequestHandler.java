package com.github.jsoncat.core.springmvc.handler;

import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.common.util.UrlUtil;
import com.github.jsoncat.entity.MethodDetail;
import com.github.jsoncat.core.springmvc.factory.ParameterResolverFactory;
import com.github.jsoncat.core.springmvc.factory.RouteMethodMapper;
import com.github.jsoncat.core.springmvc.resolver.ParameterResolver;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 13:33:00
 **/
@Slf4j
public class PostRequestHandler implements RequestHandler {

    @Override
    public Object handle(FullHttpRequest fullHttpRequest) {
        String requestUri = fullHttpRequest.uri();
        // get http request path，such as "/user"
        String requestPath = UrlUtil.getRequestPath(requestUri);
        // get target method
        MethodDetail methodDetail = RouteMethodMapper.getMethodDetail(requestPath, HttpMethod.POST);
        if (methodDetail == null) {
            return null;
        }
        Method targetMethod = methodDetail.getMethod();
        String contentType = this.getContentType(fullHttpRequest.headers());
        // target method parameters.
        // notice! you should convert it to array when pass into the executeMethod()
        List<Object> targetMethodParams = new ArrayList<>();
        if (contentType.equals("application/json")) {
            String json = fullHttpRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
            methodDetail.setJson(json);
            Parameter[] targetMethodParameters = targetMethod.getParameters();
            for (Parameter parameter : targetMethodParameters) {
                ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
                if (parameterResolver != null) {
                    Object param = parameterResolver.resolve(methodDetail, parameter);
                    targetMethodParams.add(param);
                }
            }
        } else {
            throw new IllegalArgumentException("only receive application/json type data");
        }
        return ReflectionUtil.executeMethod(targetMethod, targetMethodParams.toArray());
    }

    private String getContentType(HttpHeaders headers) {
        String typeStr = headers.get("Content-Type");
        String[] list = typeStr.split(";");
        return list[0];
    }
}


