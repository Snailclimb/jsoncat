package com.github.demo.sms;

import com.github.jsoncat.annotation.ioc.Component;

/**
 * @author shuang.kou
 * @createTime 2020年09月30日 15:43:00
 **/
@Component(name = "qiNiuSmsServiceImpl")
public class QiNiuSmsServiceImpl implements SmsService {

    @Override
    public String send(SmsDto smsDto) {
        System.out.println("send message to " + smsDto.getPhone());
        return QiNiuSmsServiceImpl.class.getSimpleName();
    }
}
