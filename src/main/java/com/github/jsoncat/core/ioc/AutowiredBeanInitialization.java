package com.github.jsoncat.core.ioc;

import com.github.jsoncat.annotation.config.Value;
import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.ioc.Qualifier;
import com.github.jsoncat.common.util.ObjectUtil;
import com.github.jsoncat.common.util.ReflectionUtil;
import com.github.jsoncat.core.aop.factory.AopProxyBeanPostProcessorFactory;
import com.github.jsoncat.core.common.BeanPostProcessor;
import com.github.jsoncat.core.config.ConfigurationManager;
import com.github.jsoncat.exception.CanNotDetermineTargetBeanException;
import com.github.jsoncat.exception.InterfaceNotHaveImplementedClassException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shuang.kou
 * @createTime 2020年10月19日 10:08:00
 **/
public class AutowiredBeanInitialization {
    private final String[] packageNames;

    public AutowiredBeanInitialization(String[] packageNames) {
        this.packageNames = packageNames;
    }

    //二级缓存
    private static final Map<String, Object> SINGLETON_OBJECTS = new ConcurrentHashMap<>(64);

    public void initialize(Object beanInstance) {
        Class<?> beanClass = beanInstance.getClass();
        Field[] beanFields = beanClass.getDeclaredFields();
        //遍历bean的属性
        if (beanFields.length > 0) {
            for (Field beanField : beanFields) {
                if (beanField.isAnnotationPresent(Autowired.class)) {
                    Object beanFieldInstance = processAutowiredField(beanField);
                    String beanFieldName = IocUtil.getBeanName(beanField.getType());
                    // 解决循环依赖问题
                    beanFieldInstance = resolveCircularDependency(beanInstance, beanFieldInstance, beanFieldName);
                    // AOP
                    BeanPostProcessor beanPostProcessor = AopProxyBeanPostProcessorFactory.get(beanField.getType());
                    beanFieldInstance = beanPostProcessor.postProcessAfterInitialization(beanFieldInstance);
                    ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                }
                if (beanField.isAnnotationPresent(Value.class)) {
                    String key = beanField.getDeclaredAnnotation(Value.class).value();
                    ConfigurationManager configurationManager = (ConfigurationManager) BeanFactory.BEANS.get(ConfigurationManager.class.getName());
                    String value = configurationManager.getString(key);
                    if (value == null) {
                        throw new IllegalArgumentException("can not find target value for property:{" + key + "}");
                    }
                    Object convertedValue = ObjectUtil.convert(beanField.getType(), value);
                    ReflectionUtil.setField(beanInstance, beanField, convertedValue);
                }
            }
        }
    }

    /**
     * 处理被@Autowired注解标记的字段
     *
     * @param beanField 目标类的字段
     * @return 目标类的字段对应的对象
     */
    private Object processAutowiredField(Field beanField) {
        Class<?> beanFieldClass = beanField.getType();
        String beanFieldName = IocUtil.getBeanName(beanFieldClass);
        Object beanFieldInstance = BeanFactory.BEANS.get(beanFieldName);
        if (beanFieldClass.isInterface()) {
            @SuppressWarnings("unchecked")
            Set<Class<?>> subClasses = ReflectionUtil.getSubClass(packageNames, (Class<Object>) beanFieldClass);
            if (subClasses.size() == 0) {
                throw new InterfaceNotHaveImplementedClassException(beanFieldClass.getName() + "is interface and do not have implemented class exception");
            }
            if (subClasses.size() == 1) {
                Class<?> aClass = subClasses.iterator().next();
                beanFieldInstance = ReflectionUtil.newInstance(aClass);
            }
            if (subClasses.size() > 1) {
                Qualifier qualifier = beanField.getDeclaredAnnotation(Qualifier.class);
                beanFieldName = qualifier == null ? beanFieldName : qualifier.value();
                beanFieldInstance = BeanFactory.BEANS.get(beanFieldName);
            }

        }
        if (beanFieldInstance == null) {
            throw new CanNotDetermineTargetBeanException("can not determine target bean of" + beanFieldClass.getName());
        }
        return beanFieldInstance;
    }

    /**
     * 二级缓存解决循环依赖问题
     */
    private Object resolveCircularDependency(Object beanInstance, Object beanFieldInstance, String beanFieldName) {
        if (SINGLETON_OBJECTS.containsKey(beanFieldName)) {
            beanFieldInstance = SINGLETON_OBJECTS.get(beanFieldName);
        } else {
            SINGLETON_OBJECTS.put(beanFieldName, beanFieldInstance);
            initialize(beanInstance);
        }
        return beanFieldInstance;
    }
}
