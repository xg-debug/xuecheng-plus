package com.xuecheng.ucenter.service.strategy;

import com.xuecheng.ucenter.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 策略工厂类：采用工厂模式，根据认证类型动态选择具体策略[pwd、wx、sms等]实现
 */
@Component
public class AuthStrategyFactory {

    private final ApplicationContext applicationContext;

    // 策略缓存（提升性能）
    private static final Map<String, String> STRATEGY_BEAN_NAMES = new ConcurrentHashMap<>();

    @Autowired
    public AuthStrategyFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        initializeStrategyCache();
    }

    /**
     * 初始化策略缓存
     */
    private void initializeStrategyCache() {
        // 获取所有AuthService的实现类
        Map<String, AuthService> beans = applicationContext.getBeansOfType(AuthService.class);
        beans.forEach((beanName, bean) -> {
            // 解析authType（根据Bean名称约定）
            String authType = beanName.replace("_authservice", "");
            STRATEGY_BEAN_NAMES.put(authType, beanName);
        });
    }

    /**
     * 获取认证策略: 根据认证类型拿到对应的bean
     * @param authType 认证类型
     * @return 策略实现
     */
    public AuthService getStrategy(String authType) {
        String beanName = STRATEGY_BEAN_NAMES.get(authType);
        if (beanName == null) {
            throw new RuntimeException("不支持的认证类型：" + authType);
        }
        return applicationContext.getBean(beanName, AuthService.class);
    }
}
