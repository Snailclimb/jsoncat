package com.github.demo.circularDependency;

import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.ioc.Component;

@Component(name = "CircularDependencyAImpl")
public class CircularDependencyAImpl implements CircularDependencyA {

    @Autowired
    private CircularDependencyB testB;

    @Override
    public void testA() {
        System.out.println("A");
    }
}
