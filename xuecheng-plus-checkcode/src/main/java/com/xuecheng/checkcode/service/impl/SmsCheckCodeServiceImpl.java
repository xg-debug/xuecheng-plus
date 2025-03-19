package com.xuecheng.checkcode.service.impl;

import com.xuecheng.checkcode.model.CheckCodeParamsDto;
import com.xuecheng.checkcode.model.CheckCodeResultDto;
import com.xuecheng.checkcode.service.AbstractCheckCodeService;
import com.xuecheng.checkcode.service.CheckCodeService;
import com.xuecheng.checkcode.service.SmsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 手机验证码生成器
 */
@Service("SmsCheckCodeService")
public class SmsCheckCodeServiceImpl extends AbstractCheckCodeService implements CheckCodeService {

    @Resource
    private SmsService smsService;

    @Resource(name="NumberCheckCodeGenerator")
    @Override
    public void setCheckCodeGenerator(CheckCodeGenerator checkCodeGenerator) {
        this.checkCodeGenerator = checkCodeGenerator;
    }

    @Resource(name="UUIDKeyGenerator")
    @Override
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }


    @Resource(name="RedisCheckCodeStore")
    @Override
    public void setCheckCodeStore(CheckCodeStore checkCodeStore) {
        this.checkCodeStore = checkCodeStore;
    }



    @Override
    public CheckCodeResultDto generate(CheckCodeParamsDto checkCodeParamsDto) {
        // 生成手机验证码
        GenerateResult generate = generate(checkCodeParamsDto, 6, "sms:checkcode", 300);
        String key = generate.getKey(); // 验证码唯一标识
        String code = generate.getCode(); // 验证码

        // 发送验证码
        String cellphone = checkCodeParamsDto.getParam1();
        smsService.sendSms(cellphone, "您的验证码是：" + code + "，5分钟内有效。");

        // 返回结果
        CheckCodeResultDto checkCodeResultDto = new CheckCodeResultDto();
        checkCodeResultDto.setKey(key);
        return checkCodeResultDto;
    }
}
