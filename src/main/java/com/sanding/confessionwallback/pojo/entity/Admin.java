package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("cw_admin")
public class Admin {
    // 管理员ID，主键
    @TableId(type = IdType.AUTO)
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
    private Long adminCreator;
    // 更新时间
    private LocalDateTime adminUpdateTime;
    // 创建时间
    private LocalDateTime adminCreateTime;
}
