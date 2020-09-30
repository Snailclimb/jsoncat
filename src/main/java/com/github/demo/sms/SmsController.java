package com.github.demo.sms;

import com.github.jsoncat.annotation.Autowired;
import com.github.jsoncat.annotation.PostMapping;
import com.github.jsoncat.annotation.Qualifier;
import com.github.jsoncat.annotation.RequestBody;
import com.github.jsoncat.annotation.RestController;

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
