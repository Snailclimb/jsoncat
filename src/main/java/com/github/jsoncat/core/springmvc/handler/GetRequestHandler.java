package com.github.jsoncat.core.springmvc.handler;

import com.github.jsoncat.core.ioc.BeanFactory;
import com.github.jsoncat.core.ioc.BeanHelper;
import com.github.jsoncat.core.springmvc.entity.MethodDetail;
import com.github.jsoncat.core.springmvc.factory.FullHttpResponseFactory;
import com.github.jsoncat.core.springmvc.factory.ParameterResolverFactory;
import com.github.jsoncat.core.springmvc.factory.RouteMethodMapper;
import com.github.jsoncat.core.springmvc.resolver.ParameterResolver;
import com.github.jsoncat.core.springmvc.util.UrlUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
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
    public FullHttpResponse handle(FullHttpRequest fullHttpRequest) {
        String requestUri = fullHttpRequest.uri();
        Map<String, String> queryParameterMappings = getQueryParams(requestUri);
        // get http request path，such as "/user"
        String requestPath = UrlUtil.getRequestPath(requestUri);
        // get target method
        MethodDetail methodDetail = RouteMethodMapper.getMethodDetail(requestPath, HttpMethod.GET);
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
        String beanName = BeanHelper.getBeanName(methodDetail.getMethod().getDeclaringClass());
        Object targetObject = BeanFactory.BEANS.get(beanName);
        return FullHttpResponseFactory.getSuccessResponse(targetMethod, targetMethodParams, targetObject);
    }

    /**
     * get the parameters of uri
     */
    private Map<String, String> getQueryParams(String uri) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, Charsets.toCharset(CharEncoding.UTF_8));
        Map<String, List<String>> parameters = queryDecoder.parameters();
        Map<String, String> queryParams = new HashMap<>();
        for (Map.Entry<String, List<String>> attr : parameters.entrySet()) {
            for (String attrVal : attr.getValue()) {
                queryParams.put(attr.getKey(), attrVal);
            }
        }
        return queryParams;
    }
}
