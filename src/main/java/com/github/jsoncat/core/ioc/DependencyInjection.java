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
        if (beans.size() == 0) {
            return;
        }
        beans.values().forEach(bean -> {
            // 获取对象所属的类声明的所有字段/属性
            Field[] beanFields = bean.getClass().getDeclaredFields();
            if (beanFields.length == 0) {
                return;
            }
            //遍历对象所属的类声明的所有字段/属性
            for (Field beanField : beanFields) {
                if (beanField.isAnnotationPresent(Autowired.class)) {
                    //字段对应的类型
                    Class<?> beanFieldClass = beanField.getType();
                    //字段对应的类名
                    String beanName = beanFieldClass.getName();
                    //从bean容器中获取对应的对象
                    Object beanFieldInstance = beans.get(beanName);
                    //判断对象是否为接口
                    if (beanFieldClass.isInterface()) {
                        //如果是接口，获取接口对应的实现类
                        Set<Class<?>> subClasses = getSubClass(packageName, beanFieldClass);
                        //没有实现类的话就抛出异常
                        if (subClasses.size() == 0) {
                            throw new InterfaceNotHaveImplementedClassException("interface does not have implemented class exception");
                        }
                        //实现类只有一个话，直接获取
                        if (subClasses.size() == 1) {
                            Class<?> aClass = subClasses.iterator().next();
                            beanFieldInstance = ReflectionUtil.newInstance(aClass);
                        }
                        //实现类多与一个的话，根据 Qualifier 注解的值获取
                        if (subClasses.size() > 1) {
                            Class<?> aClass = subClasses.iterator().next();
                            Qualifier qualifier = beanField.getDeclaredAnnotation(Qualifier.class);
                            beanName = qualifier == null ? aClass.getName() : qualifier.value();
                            beanFieldInstance = beans.get(beanName);
                        }

                    }
                    // 如果最后获取到的字段对象为null，就抛出异常
                    if (beanFieldInstance == null) {
                        throw new CanNotDetermineTargetBeanException("can not determine target bean");
                    }
                    //通过反射设置指定对象中的指定字段的值
                    ReflectionUtil.setField(bean, beanField, beanFieldInstance);
                }
            }
        });


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
