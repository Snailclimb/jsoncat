package com.github.jsoncat.core.resolver;

import com.github.jsoncat.annotation.PathVariable;
import com.github.jsoncat.annotation.RequestParam;
import com.github.jsoncat.common.util.ObjectUtil;
import com.github.jsoncat.core.entity.MethodDetail;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @author shuang.kou
 * @createTime 2020年09月27日 20:58:00
 **/
public class PathVariableParameterResolver implements ParameterResolver {
    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        // 如果是 restful 风格的占位符
        PathVariable pathVariable = parameter.getDeclaredAnnotation(PathVariable.class);
        // 获取占位符名称
        String requestParameter = pathVariable.value();
        Map<String, String> urlParameterMappings = methodDetail.getUrlParameterMappings();
        String requestParameterValue = urlParameterMappings.get(requestParameter);
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);
    }
}
