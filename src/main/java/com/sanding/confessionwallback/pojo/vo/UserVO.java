package com.sanding.confessionwallback.pojo.vo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {
    private Long userId;
    // 注册时间
    private LocalDateTime userRegisterTime;
    // 用户名
    private String userName;
    // 用户账号
    private String userAccount;
    // 用户性别
    private String userSex;
    // 用户生日
    private LocalDateTime userBirthday;
    // 用户头像
    private String userAvatar;
    // 用户个性签名
    private String userSignature;

}
