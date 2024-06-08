package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDTO {
    private Long postId; // 帖子ID 主键
    private Long circleId; // 所属圈子ID
    private Long userId; // 所属用户ID
    private String postContent; // 帖子内容
    private String postImgUrls; // 帖子图片URL数组base64编码
    private List<String> topicList;
}
