package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;
/**
 * 根据圈子id增加用户(id)
* */
@Data
public class InsertUserInCircleDTO {
    //圈子id
    private Long circleId;
    //用户id
    private Long userId;
}
