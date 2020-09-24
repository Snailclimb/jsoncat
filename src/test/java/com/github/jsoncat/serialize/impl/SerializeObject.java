package com.github.jsoncat.serialize.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shuang.kou
 * @createTime 2020年09月23日 16:09:00
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SerializeObject {
    private String name;
    private String anotherName;
}
