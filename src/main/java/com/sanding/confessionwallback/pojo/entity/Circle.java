package com.sanding.confessionwallback.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


@Data
@Builder

@AllArgsConstructor
@NoArgsConstructor
@TableName("cw_circle")
public class Circle {
    @TableId(type = IdType.AUTO)
    private Long circleId; // 圈子ID，主键
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