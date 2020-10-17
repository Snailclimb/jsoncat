package com.github.jsoncat.core.aop.intercept;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tom & shuang.kou
 * @createTime 2020年10月6日10:20:26
 */
@Getter
@Setter
public abstract class Interceptor {
    private int order = -1;

    public boolean supports(Object bean) {
        return false;
    }

    public abstract Object intercept(MethodInvocation methodInvocation);
}
