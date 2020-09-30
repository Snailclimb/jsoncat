package com.github.demo.sms;

import com.github.jsoncat.annotation.Component;

/**
 * @author shuang.kou
 * @createTime 2020年09月30日 15:43:00
 **/
@Component(name = "aliSmsServiceImpl")
public class AliSmsServiceImpl implements SmsService {


    @Override
    public String send(SmsDto smsDto) {
        System.out.println("send message to " + smsDto.getPhone());
        return AliSmsServiceImpl.class.getSimpleName();
    }
}
