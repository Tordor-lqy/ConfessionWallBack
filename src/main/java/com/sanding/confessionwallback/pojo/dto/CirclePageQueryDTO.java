package com.sanding.confessionwallback.pojo.dto;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

@Data
public class CirclePageQueryDTO implements Serializable {
    // 圈子id
    private Integer circleId;
    //页数
    private Integer p;
    //每页条数
    private Integer s;
    //圈子名称
    private String circleName;
    // 圈子是否删除
    private Integer isDelete;
    // 圈子状态
    private Integer CircleStatus;


}
