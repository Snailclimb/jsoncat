package com.github.demo;

import annotation.GetMapping;
import annotation.RestController;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 14:52:00
 **/
@RestController("/user")
public class UserController {


    @GetMapping
    public User get() {
        return new User("压缩", "哈撒尅！！！");
    }
}
