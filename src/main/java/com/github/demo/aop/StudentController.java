package com.github.demo.aop;

import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.springmvc.GetMapping;
import com.github.jsoncat.annotation.springmvc.RequestParam;
import com.github.jsoncat.annotation.springmvc.RestController;
import com.github.jsoncat.core.config.ConfigurationManager;

@RestController("/student")
public class StudentController {

    @Autowired
    private ConfigurationManager configurationManager;

    @Autowired
    private StudentService studentService;

    @GetMapping("/summary")
    public String getAge() {
        return studentService.getSummary("1");
    }

    @GetMapping("/getconfig")
    public String getCofig(@RequestParam("key") String key) {
        return configurationManager.getString(key);

    }
}
