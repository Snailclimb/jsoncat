package com.github.jsoncat.core.springmvc.factory;

import com.github.jsoncat.annotation.springmvc.PathVariable;
import com.github.jsoncat.annotation.springmvc.RequestBody;
import com.github.jsoncat.annotation.springmvc.RequestParam;
import com.github.jsoncat.core.springmvc.resolver.ParameterResolver;
import com.github.jsoncat.core.springmvc.resolver.PathVariableParameterResolver;
import com.github.jsoncat.core.springmvc.resolver.RequestBodyParameterResolver;
import com.github.jsoncat.core.springmvc.resolver.RequestParamParameterResolver;

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
