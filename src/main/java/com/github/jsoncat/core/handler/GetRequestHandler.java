package com.github.jsoncat.core.handler;

import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.common.util.UrlUtil;
import com.github.jsoncat.core.ApplicationContext;
import com.github.jsoncat.core.entity.MethodDetail;
import com.github.jsoncat.core.resolver.ParameterResolver;
import com.github.jsoncat.core.resolver.ParameterResolverFactory;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handle get request
 *
 * @author shuang.kou
 * @createTime 2020年09月24日 13:33:00
 **/
@Slf4j
public class GetRequestHandler implements RequestHandler {
    @Override
    public Object handle(FullHttpRequest fullHttpRequest) {
        String requestUri = fullHttpRequest.uri();
        Map<String, String> queryParameterMappings = UrlUtil.getQueryParams(requestUri);
        // get http request path，such as "/user"
        String requestPath = UrlUtil.getRequestPath(requestUri);
        // get target method
        ApplicationContext applicationContext = ApplicationContext.getApplicationContext();
        MethodDetail methodDetail = applicationContext.getMethodDetail(requestPath, HttpMethod.GET);
        if (methodDetail == null) {
            return null;
        }
        methodDetail.setQueryParameterMappings(queryParameterMappings);
        Method targetMethod = methodDetail.getMethod();
        if (targetMethod == null) {
            return null;
        }
        log.info("requestPath -> target method [{}]", targetMethod.getName());
        Parameter[] targetMethodParameters = targetMethod.getParameters();
        // target method parameters.
        // notice! you should convert it to array when pass into the executeMethod method
        List<Object> targetMethodParams = new ArrayList<>();
        for (Parameter parameter : targetMethodParameters) {
            ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
            if (parameterResolver != null) {
                Object param = parameterResolver.resolve(methodDetail, parameter);
                targetMethodParams.add(param);
            }
        }
        return ReflectionUtil.executeMethod(targetMethod, targetMethodParams.toArray());
    }

}
