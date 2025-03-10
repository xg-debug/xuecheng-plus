package com.xuecheng.checkcode.service.impl;

import com.xuecheng.checkcode.service.CheckCodeService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service("NumberCheckCodeGenerator")
public class NumberCheckCodeGenerator implements CheckCodeService.CheckCodeGenerator {
    @Override
    public String generate(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // 生成0-9的随机数字
        }
        return sb.toString();
    }
}
