package com.github.demo.test;


import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.springmvc.GetMapping;
import com.github.jsoncat.annotation.springmvc.RestController;

@RestController("/test")
public class TestController {

    @Autowired
    private ITestA testA;

    @Autowired
    private ITestB testB;

    @Autowired
    private ITestC testC;

    @GetMapping("/test")
    public void test(){
        testA.testA();
        testB.testB();
        testC.testC();
    }
}
