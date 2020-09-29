package com.github.jsoncat.core.factory;

import com.github.jsoncat.annotation.PathVariable;
import com.github.jsoncat.annotation.RequestBody;
import com.github.jsoncat.annotation.RequestParam;
import com.github.jsoncat.core.resolver.ParameterResolver;
import com.github.jsoncat.core.resolver.PathVariableParameterResolver;
import com.github.jsoncat.core.resolver.RequestBodyParameterResolver;
import com.github.jsoncat.core.resolver.RequestParamParameterResolver;

import java.lang.reflect.Parameter;

/**
 * @author shuang.kou
 * @createTime 2020年09月28日 10:39:00
 **/
public class ParameterResolverFactory {

    public static ParameterResolver get(Parameter parameter) {
        if (parameter.isAnnotationPresent(RequestParam.class)) {
            return new RequestParamParameterResolver();
        }
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return new PathVariableParameterResolver();
        }
        if (parameter.isAnnotationPresent(RequestBody.class)) {
            return new RequestBodyParameterResolver();
        }
        return null;
    }
}
