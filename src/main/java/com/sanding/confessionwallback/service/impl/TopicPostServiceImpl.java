package com.sanding.confessionwallback.service.impl;


import com.sanding.confessionwallback.mapper.TopicPostMapper;
import com.sanding.confessionwallback.service.TopicPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicPostServiceImpl implements TopicPostService {
    @Autowired
    private TopicPostMapper topicPostMapper;
}
