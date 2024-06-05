package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@TableName("cw_circle")
public class Circle {
    private Integer circleId; // 圈子ID，主键
    private String circleName; // 圈子名称
    private String circleDescription; // 圈子描述
    private String circleAvatar; // 圈子头像
    private String circleBackground; // 圈子背景
    private String circleType; // 圈子类型
    private LocalDateTime circleCreateTime; // 创建时间
    private LocalDateTime circleUpdateTime; // 更新时间
    private Integer circleUserCount; // 用户数量
    private Integer circlePostCount; // 帖子数量
}