package com.sanding.confessionwallback.pojo.dto;

import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data

public class CircleDTO implements Serializable {


    private Long circleId; // 圈子ID，主键
    private String circleName; // 圈子名称
    private String circleDescription; // 圈子描述
    private String circleAvatar; // 圈子头像
    private String circleBackground; // 圈子背景
    private Integer isDelete; // 是否删除
    private Integer circleStatus; // 圈子状态

}
