package com.sanding.confessionwallback.service.impl;

import com.sanding.confessionwallback.mapper.PostUserLikeMapper;
import com.sanding.confessionwallback.service.PostUserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostUserLikeServiceImpl implements PostUserLikeService {
    @Autowired
    private PostUserLikeMapper postUserLikeMapper;

}
