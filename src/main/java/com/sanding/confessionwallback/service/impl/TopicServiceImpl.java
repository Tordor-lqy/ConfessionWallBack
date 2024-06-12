package com.sanding.confessionwallback.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanding.confessionwallback.common.exception.TopicException;
import com.sanding.confessionwallback.common.result.PageResult;
import com.sanding.confessionwallback.mapper.TopicMapper;
import com.sanding.confessionwallback.pojo.dto.TopicDTO;
import com.sanding.confessionwallback.pojo.dto.TopicPageQueryDTO;
import com.sanding.confessionwallback.pojo.entity.Group;
import com.sanding.confessionwallback.pojo.entity.Post;
import com.sanding.confessionwallback.pojo.entity.Topic;
import com.sanding.confessionwallback.service.TopicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;

    /**
     * 分页查询某组话题
     * @param topicPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageByGroup(TopicPageQueryDTO topicPageQueryDTO) {
        Page<Topic> page =new Page<Topic>(topicPageQueryDTO.getP(),topicPageQueryDTO.getS());

        LambdaQueryWrapper<Topic> wrapper =new LambdaQueryWrapper<Topic>()
                .eq(Topic::getGroupId,topicPageQueryDTO.getGroupId());

        Page<Topic> resultPage = topicMapper.selectPage(page,wrapper);

        return new PageResult(resultPage.getTotal(),resultPage.getRecords());
    }

    /**
     * 新建话题
     * @param topicDTO
     */
    @Override
    public void save(TopicDTO topicDTO) {
        //判断话题是否存在
        LambdaQueryWrapper<Topic> wrapper =new LambdaQueryWrapper<Topic>()
                .select(Topic::getTopicId)
                .eq(Topic::getTopicName,topicDTO.getTopicName())
                .eq(Topic::getCircleId,topicDTO.getCircleId())
                .eq(Topic::getGroupId,topicDTO.getGroupId());
        List<Long> topicId = topicMapper.selectObjs(wrapper);
        if(topicId.size()>0){
            throw new TopicException("该话题已存在");
        }else {
            //创建新话题
            Topic tp=new Topic();
            BeanUtils.copyProperties(topicDTO,tp);
            topicMapper.insert(tp);
        }
    }
}
