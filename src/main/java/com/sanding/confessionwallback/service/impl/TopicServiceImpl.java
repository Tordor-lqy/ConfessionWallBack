package com.sanding.confessionwallback.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.TopicMapper;
import com.sanding.confessionwallback.pojo.dto.TopicPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.Group;
import com.sanding.confessionwallback.pojo.entity.Post;
import com.sanding.confessionwallback.pojo.entity.Topic;
import com.sanding.confessionwallback.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;

    @Override
    public PageResult pageByGroup(TopicPageQueryDTO topicPageQueryDTO) {
        Page<Topic> page =new Page<Topic>(topicPageQueryDTO.getP(),topicPageQueryDTO.getS());

        LambdaQueryWrapper<Topic> wrapper =new LambdaQueryWrapper<Topic>()
                .eq(Topic::getGroupId,topicPageQueryDTO.getGroupId());

        Page<Topic> resultPage = topicMapper.selectPage(page,wrapper);

        return new PageResult(resultPage.getTotal(),resultPage.getRecords());
    }
}
