package com.github.jsoncat.core.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jsoncat.annotation.RequestBody;
import com.github.jsoncat.common.util.HttpRequestUtil;
import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.core.Router;
import io.netty.handler.codec.http.FullHttpRequest;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object handle(FullHttpRequest fullHttpRequest) {
        String requestUri = fullHttpRequest.uri();
        // get http request path，such as "/user"
        String requestPath = HttpRequestUtil.getRequestPath(requestUri);
        // get target method
        Method targetMethod = Router.postMappings.get(requestPath);
        String contentType = HttpRequestUtil.getContentType(fullHttpRequest.headers());
        // target method parameters.
        // notice! you should convert it to array when pass into the executeMethod method
        List<Object> targetMethodParams = new ArrayList<>();
        if (contentType.equals("application/json")) {
            String json = fullHttpRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
            Parameter[] targetMethodParameters = targetMethod.getParameters();
            for (Parameter parameter : targetMethodParameters) {
                RequestBody requestBody = parameter.getDeclaredAnnotation(RequestBody.class);
                if (requestBody != null) {
                    try {
                        Object obj = objectMapper.readValue(json, parameter.getType());
                        targetMethodParams.add(obj);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("only receive application/json type data");
        }
        return ReflectionUtil.executeMethod(targetMethod, targetMethodParams.toArray());
    }
}


