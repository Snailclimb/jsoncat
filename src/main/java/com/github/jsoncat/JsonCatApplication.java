package com.github.jsoncat;

import com.github.jsoncat.common.Banner;
import com.github.jsoncat.core.ApplicationContext;
import com.github.jsoncat.server.HttpServer;

/**
 * @author shuang.kou
 * @createTime 2020年09月23日 17:30:00
 **/
public class JsonCatApplication {
    public static void main(String[] args) {
        Banner.printBanner();
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        applicationContext.loadRoutes("com.github.demo");
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}
