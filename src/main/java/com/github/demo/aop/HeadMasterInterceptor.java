package com.github.demo.aop;

import com.github.jsoncat.core.aop.Interceptor;
import com.github.jsoncat.core.aop.Invocation;
import lombok.SneakyThrows;

public class HeadMasterInterceptor implements Interceptor {

    @Override
    public boolean supports(String beanName) {
        return beanName.equalsIgnoreCase(StudentService.class.getName());
    }

    @SneakyThrows
    @Override
    public Object intercept(Invocation invocation) {
        Object result = invocation.proceed();
        result = result + "，headMaster say : this student is very clever";
        return result;
    }
}
