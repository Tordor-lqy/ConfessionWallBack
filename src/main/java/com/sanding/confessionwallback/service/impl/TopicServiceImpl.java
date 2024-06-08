package com.sanding.confessionwallback.service.impl;


import com.sanding.confessionwallback.mapper.TopicMapper;
import com.sanding.confessionwallback.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;
}
