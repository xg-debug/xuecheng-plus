package com.xuecheng.auth.controller;

import com.xuecheng.ucenter.model.dto.FindPwdDto;
import com.xuecheng.ucenter.model.dto.RegisterParamsDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @ApiOperation("找回密码")
    @PostMapping("/findpassword")
    public void findPwd(@RequestBody FindPwdDto findPwdDto) {

    }

    @ApiOperation("学生注册接口")
    @PostMapping("/register")
    public void register(@RequestBody RegisterParamsDto registerParamsDto) {

    }
}
