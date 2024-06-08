package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostCommentDTO {
    private Long postCommentId; // 主键
    private Long postId; // 帖子ID
    private Long userId; // 用户ID
    private String commentContent; // 评论内容
    private Long replyCommentId; // 回复评论ID


}
