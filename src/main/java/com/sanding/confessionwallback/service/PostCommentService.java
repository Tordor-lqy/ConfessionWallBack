package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.PostCommentDTO;
import com.sanding.confessionwallback.pojo.dto.PostCommentPageQueryDTO;

public interface PostCommentService {
    /**
     * 用户评论帖子
     * @param postCommentDTO
     */
    void commentTopic(PostCommentDTO postCommentDTO);

    /**
     * 用户删除帖子评论
     * @param postCommentId
     */
    void delCommentTopic(Long postCommentId);
    PageResult selectPostComments(PostCommentPageQueryDTO postCommentDTO);
}
