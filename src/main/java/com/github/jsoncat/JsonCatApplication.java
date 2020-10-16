package com.github.jsoncat;

import com.github.jsoncat.annotation.boot.ComponentScan;
import com.github.jsoncat.annotation.boot.SpringBootApplication;
import com.github.jsoncat.core.ApplicationContext;

/**
 * @author shuang.kou
 * @createTime 2020年09月23日 17:30:00
 **/
@SpringBootApplication
@ComponentScan(value = {"com.github.demo.aop", "com.github.demo"})
public class JsonCatApplication {

    public static void main(String[] args) {
        JsonCatApplication.run(JsonCatApplication.class, args);
    }

    public static void run(Class<?> applicationClass, String... arg) {
        ApplicationContext applicationContext = ApplicationContext.getApplicationContext();
        applicationContext.run(applicationClass);
    }
}
