package com.sanding.confessionwallback.pojo.dto;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

@Data
public class CirclePageQueryDTO implements Serializable {
    //圈子id
    private Long circleId;
    //页数
    private Integer p;
    //每页条数
    private Integer s;
    //圈子名称
    private String circleName;
    //圈子类别
    private String circleType;


}
