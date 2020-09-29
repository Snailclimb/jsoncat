package com.github.demo;

import com.github.jsoncat.annotation.GetMapping;
import com.github.jsoncat.annotation.PathVariable;
import com.github.jsoncat.annotation.PostMapping;
import com.github.jsoncat.annotation.RequestBody;
import com.github.jsoncat.annotation.RequestParam;
import com.github.jsoncat.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 14:52:00
 **/
@RestController("/user")
public class UserController {
    private static HashMap<Integer, User> users;
    private static Integer id;

    static {
        users = new HashMap<>();
        users.put(1, new User("盖伦", "德玛西亚", 22));
        id = 2;
    }

    @GetMapping
    public User get(@RequestParam("name") String name, @RequestParam("des") String des, @RequestParam("age") Integer age) {
        return new User(name, des, age);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") Integer id) {
        return users.get(id);
    }

    @PostMapping
    public List<User> create(@RequestBody UserDto userDto) {
        users.put(id++, new User(userDto.getName(), userDto.getDes(), userDto.getAge()));
        return new ArrayList<>(users.values());
    }
}
