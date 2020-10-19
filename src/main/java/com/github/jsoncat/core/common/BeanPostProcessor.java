package com.github.jsoncat.core.common;


/**
 * bean 后置处理器
 *
 * @author tom
 * @createTime 2020年10月6日10:20:26
 */
public interface BeanPostProcessor {
    default Object postProcessAfterInitialization(Object bean) {
        return bean;
    }
}
