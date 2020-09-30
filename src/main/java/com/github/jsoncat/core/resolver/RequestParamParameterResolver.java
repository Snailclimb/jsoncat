package com.github.jsoncat.core.resolver;

import com.github.jsoncat.annotation.RequestParam;
import com.github.jsoncat.common.util.ObjectUtil;
import com.github.jsoncat.core.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * process @RequestParam annotation
 *
 * @author shuang.kou
 * @createTime 2020年09月27日 20:58:00
 **/
public class RequestParamParameterResolver implements ParameterResolver {
    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        RequestParam requestParam = parameter.getDeclaredAnnotation(RequestParam.class);
        String requestParameter = requestParam.value();
        String requestParameterValue = methodDetail.getQueryParameterMappings().get(requestParameter);
        if (requestParameterValue == null) {
            throw new IllegalArgumentException("The specified parameter " + requestParameter + " can not be null!");
        }
        // convert the parameter to the specified type
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);

    }
}
