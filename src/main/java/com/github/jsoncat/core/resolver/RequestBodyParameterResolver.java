package com.github.jsoncat.core.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jsoncat.annotation.RequestBody;
import com.github.jsoncat.core.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * @author shuang.kou
 * @createTime 2020年09月28日 14:01:00
 **/
public class RequestBodyParameterResolver implements ParameterResolver {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
    }

    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        Object obj = null;
        RequestBody requestBody = parameter.getDeclaredAnnotation(RequestBody.class);
        if (requestBody != null) {
            try {
                obj = OBJECT_MAPPER.readValue(methodDetail.getJson(), parameter.getType());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
}
