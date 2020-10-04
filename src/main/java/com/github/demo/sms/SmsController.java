package com.github.demo.sms;

import com.github.jsoncat.annotation.ioc.Autowired;
import com.github.jsoncat.annotation.springmvc.PostMapping;
import com.github.jsoncat.annotation.ioc.Qualifier;
import com.github.jsoncat.annotation.springmvc.RequestBody;
import com.github.jsoncat.annotation.springmvc.RestController;

/**
 * @author shuang.kou
 * @createTime 2020年09月30日 15:43:00
 **/
@RestController("/sms")
public class SmsController {
    @Autowired
    @Qualifier("aliSmsServiceImpl")
    private SmsService smsService;

    @PostMapping("/send")
    public String send(@RequestBody SmsDto smsDto) {
        return smsService.send(smsDto);
    }

}
