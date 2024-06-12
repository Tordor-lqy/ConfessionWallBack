package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDTO {
    private Long postId; // 帖子ID
    private Long groupId; // 所属圈子ID
    private String topicName; // 所属话题名称
    private Long userId; // 所属用户ID
    private String postTitle; // 帖子标题
    private String postContent; // 帖子内容
    private String postImgUrls; // 帖子图片URL数组base64编码
}
