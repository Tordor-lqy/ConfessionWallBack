package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.Post;

public interface PostService {
    PageResult selectPosts(PostPageQueryDTO postPageQueryDTO);

    Post getPostByPostId(Long postId);
}
