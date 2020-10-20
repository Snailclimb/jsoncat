package com.github.demo.circularDependency;


import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.springmvc.GetMapping;
import com.github.jsoncat.annotation.springmvc.RestController;

@RestController("/circular-dependency")
public class CircularDependencyController {

    @Autowired
    private CircularDependencyA testA;

    @Autowired
    private CircularDependencyB testB;

    @Autowired
    private CircularDependencyC testC;

    @GetMapping
    public void test() {
        testA.testA();
        testB.testB();
        testC.testC();
    }
}
