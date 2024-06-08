package com.sanding.confessionwallback.pojo.dto;

import lombok.Data;

@Data
public class PostUserLikeDTO {

    private Long postUserLikeId; // 主键
    private Long postId;  // 帖子ID
    private Long userId; // 用户ID
}
