package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;

public interface PostCommentService {
    /**
     * 用户评论帖子
     * @param postCommentDTO
     */
    void commentTopic(PostCommentDTO postCommentDTO);
}
