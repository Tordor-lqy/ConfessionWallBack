package com.sanding.confessionwallback.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;



@Data
public class CircleUser {
    private Integer circleUserId; // 圈子用户ID，主键
    private Integer circleId; // 圈子ID
    private Integer userId; // 用户ID
    private Integer circleUserRole; // 用户角色 (0: 普通用户, 1: 管理员, 2: 超级管理员)
    private LocalDateTime joinTime; // 加入时间
}