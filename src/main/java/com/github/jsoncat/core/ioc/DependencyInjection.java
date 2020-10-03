package com.github.jsoncat.core.ioc;

import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.ioc.Qualifier;
import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.exception.CanNotDetermineTargetBeanException;
import com.github.jsoncat.exception.InterfaceNotHaveImplementedClassException;
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
                            String beanName = beanFieldClass.getName();
                            Object beanFieldInstance = beans.get(beanName);
                            if (beanFieldClass.isInterface()) {
                                Set<Class<?>> subClasses = getSubClass(packageName, beanFieldClass);
                                if (subClasses.size() == 0) {
                                    throw new InterfaceNotHaveImplementedClassException("interface does not have implemented class exception");
                                }
                                if (subClasses.size() == 1) {
                                    Class<?> aClass = subClasses.iterator().next();
                                    beanFieldInstance = ReflectionUtil.newInstance(aClass);
                                }
                                if (subClasses.size() > 1) {
                                    Qualifier qualifier = beanField.getDeclaredAnnotation(Qualifier.class);
                                    beanName = qualifier == null ? beanName : qualifier.value();
                                    beanFieldInstance = beans.get(beanName);
                                }

                            }
                            if (beanFieldInstance == null) {
                                throw new CanNotDetermineTargetBeanException("can not determine target bean");
                            }
                            ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取接口对应的实现类
     */
    @SuppressWarnings("unchecked")
    public static Set<Class<?>> getSubClass(String packageName, Class<?> interfaceClass) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf((Class<Object>) interfaceClass);

    }
}
