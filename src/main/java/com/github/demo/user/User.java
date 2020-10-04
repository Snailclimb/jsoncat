package com.github.demo.user;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 14:52:00
 **/
@Data
@AllArgsConstructor
public class User {
    private String name;
    private String des;
    private Integer age;
}
