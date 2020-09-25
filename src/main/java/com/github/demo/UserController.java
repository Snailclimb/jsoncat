package com.github.demo;

import com.github.jsoncat.annotation.GetMapping;
import com.github.jsoncat.annotation.RequestParam;
import com.github.jsoncat.annotation.RestController;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 14:52:00
 **/
@RestController("/user")
public class UserController {


    @GetMapping
    public User get(@RequestParam("name") String name) {
        return new User(name, "哈撒尅！！！");
    }
}
