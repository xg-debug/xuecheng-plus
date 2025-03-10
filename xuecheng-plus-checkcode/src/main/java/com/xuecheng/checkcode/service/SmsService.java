package com.xuecheng.checkcode.service;

/**
 * @description 短信服务接口
 */
public interface SmsService {
    void sendSms(String cellphone, String message);
}
