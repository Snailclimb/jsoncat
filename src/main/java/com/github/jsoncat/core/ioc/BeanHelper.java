package com.github.jsoncat.core.ioc;

import com.github.jsoncat.annotation.ioc.Component;

/**
 * @author shuang.kou
 * @createTime 2020年10月07日 21:23:00
 **/
public class BeanHelper {

    /**
     * get the bean name
     *
     * @param aClass target class
     * @return the bean name
     */
    public static String getBeanName(Class<?> aClass) {
        String beanName = aClass.getName();
        if (aClass.isAnnotationPresent(Component.class)) {
            Component component = aClass.getAnnotation(Component.class);
            beanName = "".equals(component.name()) ? aClass.getName() : component.name();
        }
        return beanName;
    }
}
