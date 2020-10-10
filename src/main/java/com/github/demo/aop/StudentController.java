package com.github.demo.aop;

import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.springmvc.GetMapping;
import com.github.jsoncat.annotation.springmvc.RestController;

@RestController("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/summary")
    public String getAge() {
        return studentService.getSummary("1");
    }
}
