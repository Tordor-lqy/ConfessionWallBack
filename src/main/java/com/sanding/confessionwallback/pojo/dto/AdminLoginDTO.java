package com.sanding.confessionwallback.pojo.dto;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理员登录
 */
@Data
public class AdminLoginDTO {
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
}
