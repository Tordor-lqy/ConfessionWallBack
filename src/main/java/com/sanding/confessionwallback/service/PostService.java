package com.sanding.confessionwallback.service;

import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.pojo.dto.PostPageQueryDTO;

public interface PostService {
    PageResult selectPosts(PostPageQueryDTO postPageQueryDTO);
}
