package com.sanding.confessionwallback.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO implements Serializable {

    // 用户ID，主键
    private Long userId;
    // 注册时间
    private LocalDateTime userRegisterTime;
    // 用户名
    private String userName;
    // 用户账号
    private String userAccount;
    // 用户密码
    private String userPassword;
    // 用户电话
    private String userPhone;
    // 用户性别
    private String userSex;
    // 用户生日
    private LocalDateTime userBirthday;
    // 用户头像
    private String userAvatar;
    // 用户个性签名
    private String userSignature;
    // 用户状态
    private Integer userStatus;
    // 用户角色
    private String userRole;
    // 用户IP地址
    private String userIp;
    //用户openId
    private String openid;


}
