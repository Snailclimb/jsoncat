package com.github.demo.test;

import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.ioc.Component;

@Component(name = "TestAImpl")
public class TestAImpl implements ITestA {

    @Autowired
    private ITestB testB;

    @Override
    public void testA() {
        System.out.println("A");
    }
}
