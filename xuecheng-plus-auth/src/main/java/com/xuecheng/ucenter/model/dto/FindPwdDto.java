package com.xuecheng.ucenter.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @description 找回密码请求参数
 */
@Data
@ToString
public class FindPwdDto {
    private String cellphone;
    private String email;
    private String checkcodekey;
    private String checkcode;
    private String password;
    private String confirmpwd;

}
