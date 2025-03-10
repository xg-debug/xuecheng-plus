package com.xuecheng.checkcode.service.impl;

import com.xuecheng.checkcode.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public void sendSms(String cellphone, String message) {
        // 调用第三方短信服务 API 发送短信
        // 例如：阿里云短信、腾讯云短信等
        log.info("发送短信到：" + cellphone + "，内容：" + message);
    }
}
