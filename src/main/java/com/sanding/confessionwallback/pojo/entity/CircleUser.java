package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;



@Data
@TableName("cw_circle_user")
public class CircleUser {

    /**
     * 用户角色
     * 0-普通用户，1-管理员，2-超级管理员
     */
    public static final Integer COM_USER = 0;
    public static final Integer MANAGER = 1;
    public static final Integer SUPER_MANAGER = 2;

    @TableId(type = IdType.AUTO)
    private Long circleUserId; // 圈子用户ID，主键
    private Long circleId; // 圈子ID
    private Long userId; // 用户ID
    private Integer circleUserRole; // 用户角色 (0: 普通用户, 1: 管理员, 2: 超级管理员)
    private LocalDateTime joinTime; // 加入时间
}