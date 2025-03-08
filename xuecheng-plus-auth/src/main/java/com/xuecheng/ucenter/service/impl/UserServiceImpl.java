package com.xuecheng.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.model.dto.AuthParamsDto;
import com.xuecheng.ucenter.model.dto.XcUserExt;
import com.xuecheng.ucenter.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 自定义UserDetailsService用来对接Spring Security
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Resource
    private XcUserMapper xcUserMapper;

    @Resource
    ApplicationContext applicationContext;

//    @Resource
//    AuthService authService;

    /**
     * 查询用户信息组成用户身份信息根据账号查询用户信息
     * @param s AuthParamsDto类型的json数据
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AuthParamsDto authParamsDto = null;

        try {
            //将认证参数转为AuthParamsDto类型
            authParamsDto = JSON.parseObject(s, AuthParamsDto.class);
        }catch (Exception e) {
            log.info("认证请求不符合项目要求:{}",s);
            throw new RuntimeException("认证请求数据格式不对");
        }

        // 认证类型
        String authType = authParamsDto.getAuthType();
        // 根据认证的类型从Spring容器中取出不同的bean
        AuthService authService = applicationContext.getBean(authType + "_authservice", AuthService.class);
        // 调用统一的execute方法，完成认证
        XcUserExt user = authService.execute(authParamsDto);
        // 返回用户信息
        return getUserPrincipal(user);
    }

    /**
     * @description 查询用户信息
     * @param user  用户id，主键
     * @return com.xuecheng.ucenter.model.po.XcUser 用户信息
     */
    public UserDetails getUserPrincipal(XcUserExt user) {
        //用户权限,如果不加报Cannot pass a null GrantedAuthority collection
        String[] authorities = {"p1"};
        String password = user.getPassword();
        //为了安全在令牌中不放密码
        user.setPassword(null);
        //将user对象转json
        String userString = JSON.toJSONString(user);
        //创建UserDetails对象
        UserDetails userDetails = User.withUsername(userString).password(password).authorities(authorities).build();
        return userDetails;
    }
}
