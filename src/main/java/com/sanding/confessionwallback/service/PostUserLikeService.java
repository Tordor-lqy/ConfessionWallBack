package com.sanding.confessionwallback.service;

public interface PostUserLikeService {
    /**
     * 用户点赞帖子
     * @param postId
     */
    void likePost(Long postId);
}
