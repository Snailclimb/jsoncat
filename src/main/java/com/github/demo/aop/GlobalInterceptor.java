package com.github.demo.aop;

import com.github.jsoncat.core.aop.Interceptor;
import com.github.jsoncat.core.aop.MethodInvocation;

/**
 * @author shuang.kou
 * @createTime 2020年10月09日 21:28:00
 **/
public class GlobalInterceptor extends Interceptor {
    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        System.out.println(GlobalInterceptor.class.getSimpleName() + " before method：" + methodInvocation.getTargetMethod().getName());
        Object result = methodInvocation.proceed();
        System.out.println(GlobalInterceptor.class.getSimpleName() + " after method：" + methodInvocation.getTargetMethod().getName());
        return result;
    }
}
