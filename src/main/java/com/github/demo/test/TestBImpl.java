package com.github.demo.test;

import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.ioc.Component;

@Component(name = "TestBImpl")
public class TestBImpl implements ITestB {

    @Autowired
    private ITestC testC;

    @Override
    public void testB() {
        System.out.println("B");
    }
}
