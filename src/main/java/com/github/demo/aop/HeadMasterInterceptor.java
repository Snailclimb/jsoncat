package com.github.demo.aop;

import com.github.jsoncat.annotation.aop.Aspect;
import com.github.jsoncat.core.aop.intercept.Interceptor;
import com.github.jsoncat.core.aop.intercept.MethodInvocation;

@Aspect
public class HeadMasterInterceptor extends Interceptor {

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean supports(Object bean) {
        return bean instanceof StudentService;
    }

    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        System.out.println(HeadMasterInterceptor.class.getSimpleName() + " before method：" + methodInvocation.getTargetMethod().getName());
        Object result = methodInvocation.proceed();
        result = result + " The HeadMaster said I was very clever.";
        System.out.println(HeadMasterInterceptor.class.getSimpleName() + " after method：" + methodInvocation.getTargetMethod().getName());
        return result;
    }
}
