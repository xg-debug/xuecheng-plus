package com.xuecheng.orders.api;

import org.springframework.beans.factory.annotation.Value;

/**
 * @description 支付宝查询接口
 */
public class AliPayTest {
    @Value("${pay.alipay.APP_ID}")
    String APP_ID;

    @Value("${pay.alipay.APP_PRIVATE_KEY}")
    String APP_PRIVATE_KEY;

    @Value("${pay.alipay.ALIPAY_PUBLIC_KEY}")
    String ALIPAY_PUBLIC_KEY;


}
