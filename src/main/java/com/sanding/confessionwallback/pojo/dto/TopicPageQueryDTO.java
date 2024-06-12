package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

@Data
public class TopicPageQueryDTO {

    public static final String CIRCLE_ID="circle_id";
    public static final String GROUP_ID = "group_id";
    public static final String TOPIC_ID = "topic_id";
    public static final String POST_ID="post_id";
    public static final String POST_TITLE="post_title";
    public static final String GROUP_NAME = "group_name";
    public static final String TOPIC_NAME = "topic_name";
    public static final String CIRCLE_NAME = "circle_name";






    //页数
    private Integer p;
    //每页条数
    private Integer s;
    //圈子ID
    private Long circleId;
    //圈子名称
    private String circleName;
    // 话题ID
    private Long topicId;
    // 话题名称
    private String topicName;
    // 分组ID
    private Long groupId;
    //分组名称
    private String groupName;
    //帖子ID
    private Long postId;
    // 帖子名称
    private String postTitle;

}
