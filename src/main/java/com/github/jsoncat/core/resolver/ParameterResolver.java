package com.github.jsoncat.core.resolver;

import com.github.jsoncat.core.entity.MethodDetail;

import java.lang.reflect.Parameter;

public interface ParameterResolver {
    Object resolve(MethodDetail methodDetail, Parameter parameter);
}
