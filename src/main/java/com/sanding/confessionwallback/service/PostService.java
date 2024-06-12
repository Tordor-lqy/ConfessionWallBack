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

    void adminSavePOST(PostDTO postDTO);
    /**
     * 删除帖子
     * @param postId
     */
    void delPost(Long postId);

    /**
     * 修改帖子
     * @param postDTO
     */
    void update(PostDTO postDTO);

    PageResult selectMyPost(PostPageQueryDTO postPageQueryDTO);

    void adminUpdate(PostDTO postDTO);
}
