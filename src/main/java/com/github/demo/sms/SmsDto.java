package com.github.demo.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shuang.kou
 * @createTime 2020年09月30日 7:19:00
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsDto {
    private String phone;
}
