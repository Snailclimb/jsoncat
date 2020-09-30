package com.github.jsoncat.core;

import com.github.jsoncat.core.factory.ClassFactory;
import com.github.jsoncat.core.factory.RouterFactory;
import com.github.jsoncat.core.ioc.BeanFactory;
import com.github.jsoncat.core.ioc.DependencyInjection;

/**
 * 将路由和方法对应起来
 *
 * @author shuang.kou
 * @createTime 2020年09月24日 16:49:00
 **/
public final class ApplicationContext {
    private static final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext();


    public void run(String packageName) {
        // Load classes with custom annotation
        ClassFactory.loadClass(packageName);
        // Load routes
        RouterFactory.loadRoutes();
        // Load beans managed by the ioc container
        BeanFactory.loadBeans();
        // Traverse all the beans in the ioc container and inject instances for all @Autowired annotated attributes.
        DependencyInjection.dependencyInjection(packageName);

    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

}
