package com.github.demo.circularDependency;

import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.ioc.Component;

@Component(name = "CircularDependencyBImpl")
public class CircularDependencyBImpl implements CircularDependencyB {

    @Autowired
    private CircularDependencyC testC;

    @Override
    public void testB() {
        System.out.println("B");
    }
}
