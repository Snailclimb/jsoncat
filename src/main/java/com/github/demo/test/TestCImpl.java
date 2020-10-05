package com.github.demo.test;

import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.ioc.Component;

@Component(name = "TestCImpl")
public class TestCImpl implements ITestC {

    @Autowired
    private ITestA testA;

    @Override
    public void testC() {
        System.out.println("C");
    }
}
