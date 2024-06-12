package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

@Data
public class TopicPageQueryDTO {
    //页数
    private Integer p;
    //每页条数
    private Integer s;
    //圈子ID
    private Long circleId;
    //圈子名称
    private String circleName;
    private Long TopicId; // 话题ID
    private String topicName; // 话题名称
    private Long groupId; // 分组ID
    private String groupName; //分组名称

}
