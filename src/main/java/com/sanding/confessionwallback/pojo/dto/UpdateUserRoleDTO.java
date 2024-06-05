package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

/**
 * 更改某用户在某圈子的角色
 * */
@Data
public class UpdateUserRoleDTO {
    private Long circleUserId; // 圈子用户ID，主键
    private Long circleId; // 圈子ID
    private Integer circleUserRole; // 用户角色 (0: 普通用户, 1: 管理员, 2: 超级管理员)
}
