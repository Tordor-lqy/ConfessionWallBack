package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.PostDTO;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.Post;

public interface PostService {
    PageResult selectPosts(PostPageQueryDTO postPageQueryDTO);

    Post getPostByPostId(Long postId);

    /**
     * 用户新增帖子
     * @param postDTO
     */
    void savePostTopic(PostDTO postDTO);

    /**
     * 删除帖子
     * @param postId
     */
    void delPost(Long postId);

    PageResult selectMyPost(PostPageQueryDTO postPageQueryDTO);
}
