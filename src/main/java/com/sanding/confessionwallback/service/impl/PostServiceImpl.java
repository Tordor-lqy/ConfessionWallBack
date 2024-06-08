package com.sanding.confessionwallback.service.impl;

import com.sanding.confessionwallback.mapper.PostMapper;
import com.sanding.confessionwallback.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;
}
