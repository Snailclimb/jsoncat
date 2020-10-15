package com.github.demo.user;

import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.springmvc.GetMapping;
import com.github.jsoncat.annotation.springmvc.PathVariable;
import com.github.jsoncat.annotation.springmvc.PostMapping;
import com.github.jsoncat.annotation.springmvc.RequestBody;
import com.github.jsoncat.annotation.springmvc.RequestParam;
import com.github.jsoncat.annotation.springmvc.RestController;

import java.util.List;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 14:52:00
 **/
@RestController("/user")
public class UserController {
    @Autowired
    public UserService userService;

    @GetMapping
    public User get(@RequestParam("name") String name, @RequestParam("des") String des, @RequestParam("age") Integer age) {
        return new User(name, des, age);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") Integer id) {
        return userService.get(id);
    }

    @PostMapping
    public List<User> create(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }
}
