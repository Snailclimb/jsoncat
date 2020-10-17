package com.github.jsoncat.exception;

/**
 * @author tom
 * 不能初始化构造函数
 * @createTime 2020年10月6日10:20:26
 */
public class CannotInitializeConstructorException extends RuntimeException {

    public CannotInitializeConstructorException(String message) {
        super(message);
    }
}
