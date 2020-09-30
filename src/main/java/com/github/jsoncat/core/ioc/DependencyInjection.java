package com.github.jsoncat.core.ioc;

import com.github.jsoncat.annotation.Autowired;
import com.github.jsoncat.common.util.ReflectionUtil;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * @author shuang.kou
 * @createTime 2020年09月30日 07:51:00
 **/
public class DependencyInjection {

    /**
     * 遍历ioc容器所有bean的属性, 为所有带@Autowired注解的属性注入实例
     */
    public static void dependencyInjection(String packageName) {
        Map<String, Object> beans = BeanFactory.BEANS;
        if (beans.size() > 0) {
            for (Map.Entry<String, Object> beanEntry : beans.entrySet()) {
                Object beanInstance = beanEntry.getValue();
                Class<?> beanClass = beanInstance.getClass();
                Field[] beanFields = beanClass.getDeclaredFields();
                //遍历bean的属性
                if (beanFields.length > 0) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Autowired.class)) {
                            //属性类型
                            Class<?> beanFieldClass = beanField.getType();
                            if (beanFieldClass.isInterface()) {
                                //TODO 如果beanFieldClass是接口, 就获取接口对应的实现类(
                                //TODO 一个接口被多个类实现的情况处理
                                Set<Class<?>> subClass = getSubClass(packageName, beanFieldClass);
                                System.out.println(subClass.toString());
                            }
                            //获取Class类对应的实例
                            Object beanFieldInstance = beans.get(beanFieldClass.getName());
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取接口对应的实现类
     */
    public static Set<Class<?>> getSubClass(String packageName, Class<?> interfaceClass) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf((Class<Object>) interfaceClass);

    }
}
