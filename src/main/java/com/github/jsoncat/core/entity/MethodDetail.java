package com.github.jsoncat.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author shuang.kou
 * @createTime 2020年09月28日 09:42:00
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodDetail {
    private Method method;
    private Map<String, String> urlParameterMappings;
    private Map<String, String> queryParameterMappings;
    private String json;
}
