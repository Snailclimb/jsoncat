package com.github.jsoncat.core;

import com.github.jsoncat.annotation.boot.ComponentScan;
import com.github.jsoncat.common.Banner;
import com.github.jsoncat.core.aop.factory.InterceptorFactory;
import com.github.jsoncat.core.boot.ApplicationRunner;
import com.github.jsoncat.core.ioc.BeanFactory;
import com.github.jsoncat.core.ioc.DependencyInjection;
import com.github.jsoncat.core.springmvc.factory.RouteMethodMapper;
import com.github.jsoncat.factory.ClassFactory;
import com.github.jsoncat.server.HttpServer;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

/**
 * 将路由和方法对应起来
 *
 * @author shuang.kou
 * @createTime 2020年09月24日 16:49:00
 **/
public final class ApplicationContext {
    private static final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext();


    public void run(Class<?> applicationClass) {
        //print banner
        Banner.printBanner();
        //analyse package
        String[] packageNames = analysisPackageNames(applicationClass);
        // Load classes with custom annotation
        ClassFactory.loadClass(packageNames);
        // Load routes
        RouteMethodMapper.loadRoutes();
        // Load beans managed by the ioc container
        BeanFactory.loadBeans();
        // Load interceptors
        InterceptorFactory.loadInterceptors(packageNames);
        // Traverse all the beans in the ioc container and inject instances for all @Autowired annotated attributes.
        DependencyInjection.dependencyInjection(packageNames);
        // Perform some callback events
        callRunners();
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    private static String[] analysisPackageNames(Class<?> applicationClass) {
        ComponentScan componentScan = applicationClass.getAnnotation(ComponentScan.class);
        return !Objects.isNull(componentScan) ? componentScan.value()
                : new String[]{applicationClass.getPackage().getName()};
    }

    private void callRunners() {
        List<ApplicationRunner> runners = new ArrayList<>(BeanFactory.getBeansOfType(ApplicationRunner.class).values());
        //The last step is to start web application
        runners.add(() -> {
            HttpServer httpServer = new HttpServer();
            httpServer.start();
        });
        for (Object runner : new LinkedHashSet<>(runners)) {
            ((ApplicationRunner) runner).run();
        }
    }

}
