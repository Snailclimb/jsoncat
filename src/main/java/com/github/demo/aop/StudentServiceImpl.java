package com.github.demo.aop;

import com.github.jsoncat.annotation.ioc.Component;

@Component(name = "StudentServiceImpl")
public class StudentServiceImpl implements StudentService {

    @Override
    public String getAge(String id) {
        return "i am 12 years old";
    }
}
