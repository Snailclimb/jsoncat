package com.github.demo.circularDependency;

import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.ioc.Component;

@Component(name = "CircularDependencyCImpl")
public class CircularDependencyCImpl implements CircularDependencyC {

    @Autowired
    private CircularDependencyA testA;

    @Override
    public void testC() {
        System.out.println("C");
    }
}
