package com.github.demo;

import com.github.jsoncat.annotation.GetMapping;
import com.github.jsoncat.annotation.PathVariable;
import com.github.jsoncat.annotation.PostMapping;
import com.github.jsoncat.annotation.RequestBody;
import com.github.jsoncat.annotation.RequestParam;
import com.github.jsoncat.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 14:52:00
 **/
@RestController("/user")
public class UserController {

    private static List<User> users = new ArrayList<>(Collections.singletonList(new User("盖伦", "德玛西亚", 18)));

    @GetMapping
    public User get(@RequestParam("name") String name, @RequestParam("des") String des, @RequestParam("age") Integer age) {
        return new User(name, des, age);
    }

    @GetMapping("/{name}")
    public User get(@PathVariable("name") String name) {
        System.out.println(name);
        return users.get(0);
    }

    @PostMapping
    public List<User> create(@RequestBody UserDto userDto) {
        users.add(new User(userDto.getName(), userDto.getDes(), userDto.getAge()));
        return users;
    }
}
