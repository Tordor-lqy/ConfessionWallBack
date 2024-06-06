package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

@Data
public class CircleUserDTO {

    //主键id
    private Long circleUserId;
    //需要改的用户id
    private Long userId;
    //该圈子的id
    private Long  circleId;
    //此用户的权限
    private Integer circleUserRole;

}
