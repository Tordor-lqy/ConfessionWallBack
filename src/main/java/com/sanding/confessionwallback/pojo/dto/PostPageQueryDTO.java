package com.sanding.confessionwallback.pojo.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostPageQueryDTO {

    public static final String CIRCLE_ID="circle_id";
    public static final String USER_ID="user_id";
    public static final String GROUP_ID = "group_id";
    public static final String TOPIC_ID = "topic_id";
    public static final String POST_ID="post_id";
    public static final String POST_TITLE="post_title";
    public static final String GROUP_NAME = "group_name";
    public static final String TOPIC_NAME = "topic_name";
    public static final String CIRCLE_NAME = "circle_name";
    public static final String BEGIN_TIME="post_create_time";
    public static final String END_TIME="post_create_time";
    public static final String IS_DELETE="is_delete";

    //页数
    private Integer p;
    //每页条数
    private Integer s;
    //圈子ID
    private Long circleId;
    //用户ID
    private Long userId;
    // 分组ID
    private Long groupId;
    // 话题ID
    private Long topicId;
    // 帖子id
    private Long postId;
    // 圈子名称
    private String circleName;
    // 分组名称
    private String groupName;
    // 话题名称
    private String topicName;
    // 帖子名称
    private String postTitle;
    // 是否删除
    private Integer isDelete;
    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;


}
