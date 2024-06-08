package com.sanding.confessionwallback.service.impl;

import com.sanding.confessionwallback.mapper.PostCommentMapper;
import com.sanding.confessionwallback.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostCommentServiceImpl implements PostCommentService {
    @Autowired
    private PostCommentMapper postCommentMapper;
}
