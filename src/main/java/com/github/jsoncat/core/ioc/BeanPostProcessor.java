package com.github.jsoncat.core.ioc;


/**
 * @author tom
 * bean 后置处理器
 * @createTime 2020年10月6日10:20:26
 */
public interface BeanPostProcessor {

    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
