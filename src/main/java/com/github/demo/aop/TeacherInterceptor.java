package com.github.demo.aop;

import com.github.jsoncat.core.aop.Interceptor;
import com.github.jsoncat.core.aop.MethodInvocation;

public class TeacherInterceptor extends Interceptor {
    @Override
    public int getOrder() {
        return 0;
    }


    @Override
    public boolean supports(Object bean) {
        return bean instanceof StudentService;
    }

    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        System.out.println(TeacherInterceptor.class.getSimpleName() + " before method：" + methodInvocation.getTargetMethod().getName());
        Object result = methodInvocation.proceed();
        result = result + " The teacher said I was great.";
        System.out.println(TeacherInterceptor.class.getSimpleName() + " after method：" + methodInvocation.getTargetMethod().getName());
        return result;
    }
}
