package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;
import com.sanding.confessionwallback.pojo.dto.PostUserLikeDTO;

public interface PostUserLikeService {
    /**
     * 用户点赞帖子
     * @param postId
     */
    void likePost(Long postId);

    /**
     * 用户取消点赞帖子
     * @param postId
     */
    void delLikeTopic(Long postId);

    PageResult selectPostUserLike(PostPageQueryDTO postPageQueryDTO);

    void AdminDelLikeTopic(PostUserLikeDTO postUserLikeDTO);
}
