package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@Builder
@TableName("cw_user")
public class User {
    // 用户ID，主键
    @TableId(type = IdType.AUTO)
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
    private String userOpenid;
}