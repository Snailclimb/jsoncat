package com.github.jsoncat.core.handler;

import com.github.jsoncat.annotation.RequestParam;
import com.github.jsoncat.common.util.ObjectUtil;
import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.common.util.UrlUtil;
import com.github.jsoncat.core.Router;
import io.netty.handler.codec.http.FullHttpRequest;
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
        Map<String, String> queryParams = UrlUtil.getQueryParams(requestUri);
        // get http request path，such as "/user"
        String requestPath = UrlUtil.getRequestPath(requestUri);
        // get target method
        Method targetMethod = Router.getMappings.get(requestPath);
        if (targetMethod == null) {
            return null;
        }
        log.info("requestPath -> target method [{}]", targetMethod.getName());
        Parameter[] targetMethodParameters = targetMethod.getParameters();
        // target method parameters.
        // notice! you should convert it to array when pass into the executeMethod method
        List<Object> targetMethodParams = new ArrayList<>();
        for (Parameter parameter : targetMethodParameters) {
            RequestParam requestParam = parameter.getDeclaredAnnotation(RequestParam.class);
            if (requestParam != null) {
                String requestParameter = requestParam.value();
                String requestParameterValue = queryParams.get(requestParameter);
                if (requestParameterValue == null) {
                    throw new IllegalArgumentException("The specified parameter " + requestParameter + " can not be null!");
                }
                // convert the parameter to the specified type
                Object param = ObjectUtil.convert(parameter.getType(), requestParameterValue);
                targetMethodParams.add(param);
            }
        }
        return ReflectionUtil.executeMethod(targetMethod, targetMethodParams.toArray());
    }


}
