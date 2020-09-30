package com.github.demo;

import com.github.jsoncat.annotation.Component;
import com.github.jsoncat.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shuang.kou
 * @createTime 2020年09月30日 00:14:00
 **/
@Component
public class UserService {
    private Integer id = 1;

    private final Map<Integer, User> users = new HashMap<Integer, User>() {
        {
            put(1, new User("盖伦", "德玛西亚", 22));
        }
    };

    public User get(Integer id) {
        return users.get(id);
    }

    public List<User> create(@RequestBody UserDto userDto) {
        users.put(++id, new User(userDto.getName(), userDto.getDes(), userDto.getAge()));
        return new ArrayList<>(users.values());

    }

    public void say() {
        System.out.println("UserService say 你真帅！");
    }
}
