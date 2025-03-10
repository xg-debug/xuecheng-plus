package com.xuecheng.ucenter.service;

import com.xuecheng.ucenter.model.dto.FindPwdDto;
import com.xuecheng.ucenter.model.dto.RegisterParamsDto;

public interface UserService {

    public void findPwd(FindPwdDto findPwdDto);

    public void register(RegisterParamsDto registerParamsDto);
}
