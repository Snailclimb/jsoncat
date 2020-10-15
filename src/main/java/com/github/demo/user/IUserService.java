package com.github.demo.user;

import com.github.jsoncat.annotation.ioc.Component;
import com.github.jsoncat.annotation.springmvc.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shuang.kou
 * @createTime 2020年09月30日 00:14:00
 **/
public interface IUserService {

    User get(Integer id);

    List<User> create(UserDto userDto);

    void say();
}
