package com.sanding.confessionwallback.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginVO {
    // 管理员ID，主键
    private Long adminId;
    // 管理员名称
    private String adminName;
    // 密码
    private String adminPassword;
    // 管理员等级(0:普通管理员 ,1 : 超级管理员)
    private Integer adminRole;
    // 当前状态(0:正常 ， 1:禁用)
    private Integer adminStatus;
    // IP地址，每次登录更新
    private String adminIp;
    // 管理员创建人
    private LocalDateTime adminCreator;
    // 更新时间
    private LocalDateTime adminUpdateTime;
    // 创建时间
    private LocalDateTime adminCreateTime;
    //token
    private String token;
}
