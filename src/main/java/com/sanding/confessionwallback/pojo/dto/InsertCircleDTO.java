package com.sanding.confessionwallback.pojo.dto;
/**
 * 新增圈子
 */
import lombok.Data;

@Data
public class InsertCircleDTO {
    private String circleName; // 圈子名称
    private String circleDescription; // 圈子描述
    private String circleAvatar; // 圈子头像
    private String circleBackground; // 圈子背景
    private String circleType; // 圈子类型
    private Integer circleUserCount; // 用户数量
    private Integer circlePostCount; // 帖子数量
}
